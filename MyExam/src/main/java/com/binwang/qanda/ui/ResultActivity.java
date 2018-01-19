package com.binwang.qanda.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.binwang.qanda.R;
import com.binwang.qanda.sqlite.DBManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ResultActivity extends Activity {
    private int resultHas = 0;
    private int resultNull = 0;
    private int resultRight = 0;
    private int resultScore = 0;
    private int resultWrong = 0;
    private int timuMax = 0;
    private TextView yida;
    private TextView weida;
    private TextView dadui;
    private TextView search;
    private TextView score;
    private TextView haoshi;
    private String[] timu;//examTable�е�ÿ����Ŀ
    private String[] daan;//examTable�е�ÿ����
    private List<String> resultWrongTimu = new ArrayList<String>();
    private List<String> resultWrongDaan = new ArrayList<String>();
    private List<String> resultRightTimu = new ArrayList<String>();
    private List<String> resultRightDaan = new ArrayList<String>();
    private List<String> errorTableTimu = new ArrayList<String>();//errorTable�е���Ŀ
    private List<String> selectedList = new ArrayList<String>();
    private boolean hasSameTimu = false;
    private String curTime;
    public static String NAME;
    public static int RemainingTime;
    private String useTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        setContentView(R.layout.activity_result);
        haoshi = (TextView) findViewById(R.id.haoshi);
        useTime = getUseTime(RemainingTime);
        getTimu();
        setResult();
        insertExamerrorTable();
        queryErrorTable();
        insertErrorTable();
        insertHistoryTable();
        yida = (TextView) findViewById(R.id.yida);
        weida = (TextView) findViewById(R.id.weida);
        dadui = (TextView) findViewById(R.id.dadui);
        score = (TextView) findViewById(R.id.score);
        search = (TextView) findViewById(R.id.search);
        yida.setText(getResources().getString(R.string.have_answer) + resultHas);
        weida.setText(getResources().getString(R.string.have_not_answer) + resultNull);
        dadui.setText(getResources().getString(R.string.answer_right) + resultRight);
        score.setText(getResources().getString(R.string.get_score) + resultScore);
        haoshi.setText(getResources().getString(R.string.time_consuming) + useTime);
        timeIsUp();
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, ExamErrorActivity.class));
                finish();
            }
        });
    }

    //�������ʱ�䵽�˶������������
    private void timeIsUp() {
        if (RemainingTime == 0) {
            score.setText(getResources().getString(R.string.time_is_over_score) + resultScore);
        } else {}
    }

    //����examresultTable���ó�result����ֵ��ͬʱ��examerrorTable�������
    private void setResult() {
        Cursor cursor = null;
        try {
            cursor = DBManager.db.query("examresultTable", null, null, null, null, null, null);
            timuMax = cursor.getCount();
            for (int i = 0; i < timuMax; i++) {
                cursor.moveToPosition(i);
                String selected = cursor.getString(0);
                String selectRight = cursor.getString(1);
                if (selected.equals("wu")) {
                    resultNull++;
                    resultWrongTimu.add(timu[i]);
                    resultWrongDaan.add(daan[i]);
                    selectedList.add(selected);
                } else if (selected.equals(selectRight)) {
                    resultRight++;
                    resultRightTimu.add(timu[i]);
                    resultRightDaan.add(daan[i]);
                } else if (!selected.equals(selectRight)) {
                    resultWrong++;
                    resultWrongTimu.add(timu[i]);
                    resultWrongDaan.add(daan[i]);
                    selectedList.add(selected);
                }
            }

            resultHas = timuMax - resultNull;
            resultScore = resultRight * 5;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    //�õ�examTable��ÿ����Ŀ
    private void getTimu() {
        Cursor cursor = null;
        try {
            cursor = DBManager.db.query("examTable", null, null, null, null, null, null);
            timu = new String[cursor.getCount()];
            daan = new String[cursor.getCount()];
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                timu[i] = cursor.getString(0);
                daan[i] = cursor.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    //��examerrorTable�в������
    private void insertExamerrorTable() {
        for (int i = 0; i < timuMax - resultRight; i++) {
            ContentValues cValue = new ContentValues();
            cValue.put("timu", resultWrongTimu.get(i));
            cValue.put("daan", resultWrongDaan.get(i));
            cValue.put("selected", selectedList.get(i));
            DBManager.db.insert("examerrorTable", null, cValue);
        }
    }

    //��errorTable�в������
    private void insertErrorTable() {
        //������ͬ�Ĵ��⣬���µĴ���ӽ�ȥ
        for (int i = 0; i < resultWrongTimu.size(); i++) {
            if (hasSameTimu(resultWrongTimu.get(i))) {

            } else {
                ContentValues cValue = new ContentValues();
                cValue.put("timu", resultWrongTimu.get(i));
                cValue.put("daan", resultWrongDaan.get(i));
                DBManager.db.insert("errorTable", null, cValue);
            }
        }
        //��������������Ĵ��⣬����������һ�����ȥ����
        for (int i = 0; i < resultRightTimu.size(); i++) {
            if (hasSameTimu(resultRightTimu.get(i))) {
                String[] whereArgs = {resultRightTimu.get(i)};
                DBManager.db.delete("errorTable", "timu=?", whereArgs);
            } else {

            }
        }
    }

    //����һ��booleanֵ���ж�errorTable����û����ͬ����Ŀ
    private boolean hasSameTimu(String thisTimu) {
        for (int i = 0; i < errorTableTimu.size(); i++) {
            if (thisTimu.equals(errorTableTimu.get(i))) {
                return true;
            } else {}
        }
        return false;
    }

    //��ȡerrorTable�еĴ���
    private void queryErrorTable() {
        Cursor cursor = null;
        try {
            cursor = DBManager.db.query("errorTable", null, null, null, null, null, null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                errorTableTimu.add(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    //��historyTable������������÷�
    private void insertHistoryTable() {
        ContentValues cValue = new ContentValues();
        cValue.put("name", ResultActivity.NAME);
        cValue.put("curtime", curTime);
        cValue.put("usetime", useTime);
        cValue.put("score", resultScore);
        DBManager.db.insert("historyTable", null, cValue);
    }

    private String getUseTime(int thistime) {
        int x = 600 - thistime;
        int y = x / 60;
        int z = x - y * 60;
        String str;
        if (y == 0) {
            str = z + "��";
        } else {
            str = y + "��" + z + "��";
        }
        return str;
    }


}
