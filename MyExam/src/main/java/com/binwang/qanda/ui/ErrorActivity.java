package com.binwang.qanda.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.binwang.qanda.R;
import com.binwang.qanda.sqlite.DBManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorActivity extends Activity implements OnClickListener, OnItemClickListener {

    private TextView selectOne;
    private TextView selectTwo;
    private TextView selectThree;
    private TextView selectFour;
    private TextView title;//��Ŀ��
    private TextView answer;//������
    private TextView type;
    private ImageView imageOne;
    private ImageView imageTwo;
    private ImageView imageThree;
    private ImageView imageFour;
    private RelativeLayout relOne;
    private RelativeLayout relTwo;
    private RelativeLayout relThree;
    private RelativeLayout relFour;
    private TextView subjectTop;
    private TextView submit;
    private TextView rightAnswer;
    private TextView help;
    private int timuMax;//��Ŀ��������
    private int timuCurrent = 0;//��ʾ��ǰ��Ŀ�ǵڼ��⣬0��ʾ��һ��
    private String[] selectRight;//���б����ղص���Ŀÿһ����ȷ�𰸵ļ���
    private String[] selected;//ÿһ���û���ѡ��ѡ���¼�ļ���
    private MyAdapter myAdapter;
    private String timu = null;//��ǰ��Ŀ
    private String daan = null;//��ǰ��
    private boolean hasCollect = true;//true��collectTable�������ղ�����һ����Ŀ
    private Dialog dialog;//ѡ������
    private GridView gridView1;//ѡ������
    private View select_subject_layout;//ѡ������
    private int[] gridViewItemColor;//ѡ������
    private String[] gridViewItemText;//ѡ������
    private List<Map<String, Object>> gridViewItemList;//ѡ������
    private SimpleAdapter sim_adapter;//ѡ������
    private String TAG;//��дfinish()���ã���֪�������ô�...

    //ע�⣺collectTable�Ѿ�Ԥ�������в�����һ��ȫΪ�յ���Ŀ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        //�趨�����activity�Ķ���Ч��
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        //�����Ǹ��������ʵ��
        title = (TextView) findViewById(R.id.title);
        selectOne = (TextView) findViewById(R.id.selectOne);
        selectTwo = (TextView) findViewById(R.id.selectTwo);
        selectThree = (TextView) findViewById(R.id.selectThree);
        selectFour = (TextView) findViewById(R.id.selectFour);
        rightAnswer = (TextView) findViewById(R.id.rightAnswer);
        answer = (TextView) findViewById(R.id.answer);
        type = (TextView) findViewById(R.id.type);
        RelativeLayout back = (RelativeLayout) findViewById(R.id.back);
        RelativeLayout subject = (RelativeLayout) findViewById(R.id.subject);
        RelativeLayout collect = (RelativeLayout) findViewById(R.id.remove);
        RelativeLayout forward = (RelativeLayout) findViewById(R.id.forward);
        relOne = (RelativeLayout) findViewById(R.id.relOne);
        relTwo = (RelativeLayout) findViewById(R.id.relTwo);
        relThree = (RelativeLayout) findViewById(R.id.relThree);
        relFour = (RelativeLayout) findViewById(R.id.relFour);
        selectOne = (TextView) findViewById(R.id.selectOne);
        selectTwo = (TextView) findViewById(R.id.selectTwo);
        selectThree = (TextView) findViewById(R.id.selectThree);
        selectFour = (TextView) findViewById(R.id.selectFour);
        imageOne = (ImageView) findViewById(R.id.imageOne);
        imageTwo = (ImageView) findViewById(R.id.imageTwo);
        imageThree = (ImageView) findViewById(R.id.imageThree);
        imageFour = (ImageView) findViewById(R.id.imageFour);
        subjectTop = (TextView) findViewById(R.id.tv_subjectTop);
        submit = (TextView) findViewById(R.id.submit);
        help = (TextView) findViewById(R.id.help);
        RelativeLayout rel_help = (RelativeLayout) findViewById(R.id.rel_help);
        //ԭ��ע�ͣ��ȶ�ȡ���ݿ��еĻ���, �������϶�ȽϺ�ʱ��Ӧʹ��AsyncTask
        //ԭ��ע�ͣ�new QueryTask().execute();
        //�����Ǹ���������Ӽ�����
        submit.setOnClickListener(this);
        selectOne.setOnClickListener(this);
        selectTwo.setOnClickListener(this);
        selectThree.setOnClickListener(this);
        selectFour.setOnClickListener(this);
        back.setOnClickListener(this);
        subject.setOnClickListener(this);
        collect.setOnClickListener(this);
        forward.setOnClickListener(this);
        rel_help.setOnClickListener(this);
        //�����Ǹ��ֳ�ʼ��
        //����û���ղ���Ŀ
        //remindNoCollect();
        /*ԭ����initialTimuMaxandRight��
        ����������ȥ���ղ�ʱҪ����������������Ը�ΪsetTimuMaxandRight*/
        setTimuMaxandRight();
        initialSelected(timuMax);
        initialGridViewItem(timuMax);
        setSelectTfandgridColorText();
        //����׼����ȫ����ʾ��Ŀ
        showTimu();
    }

    @Override//��дfinish()��Ŀ���Ǳ�֤�����ж���Ч��
    public void finish() {
        Log.i(TAG, "finish");
        super.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    //��ʼ�����������������
    public void initialSelected(int this_timuMax) {
        selected = new String[this_timuMax];
        for (int i = 0; i < selected.length; i++) {
            selected[i] = "wu";
        }
    }

    //��ʼ��ѡ���item��ɫ��ѡ���item����
    private void initialGridViewItem(int this_timuMax) {
        gridViewItemColor = new int[this_timuMax];
        gridViewItemText = new String[this_timuMax];
    }

    //����û���ղ���Ŀ
    public void remindNoCollect() {
        getTimuMax();
        if (hasTheSame("��") && timuMax == 1) {
            hasCollect = false;
            Toast.makeText(this, "����ʱû���ղص���Ŀ", Toast.LENGTH_SHORT).show();
        } else {
        }
    }


    //�õ���Ŀ����timuMax��ÿ�����ȷ�𰸼���selectRight[]�Լ�ÿ���Ѵ�Ĵ�selected
    //�������getTimuMax��רΪcollect��remove��Ŀ�Ժ�����������������õ�
    public void getTimuMax() {
        Cursor cursor = null;
        try {
            cursor = DBManager.db.query("errorTable", null, null, null, null, null, null);
            timuMax = cursor.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void setSelected() {
        for (int i = 0; i < timuMax; i++) {

        }
    }

    //�����ж��ղر����Ƿ����ĳ����ͬ��String����Ŀ�����򷵻�true
    public boolean hasTheSame(String str) {
        //submit.setText("������getTimuMaxandRight()");
        Cursor cursor = null;
        try {
            cursor = DBManager.db.query("collectTable", null, null, null, null, null, null);
            cursor.moveToFirst();
            if (cursor.getString(0).equals(str)) {
                return true;
            } else {
            }
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equals(str)) {
                    return true;
                } else {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    //�õ���Ŀ����timuMax��ÿ�����ȷ�𰸼���selectRight[]�Լ�ÿ���Ѵ�Ĵ�selected
    public void setTimuMaxandRight() {
        //submit.setText("������getTimuMaxandRight()");
        Cursor cursor = null;
        try {
            cursor = DBManager.db.query("errorTable", null, null, null, null, null, null);
            //�趨timuMax��ֵ
            timuMax = cursor.getCount();
            //�趨sxelectRight�����С
            selectRight = new String[timuMax];
            //����cursor����ȡ������ȷ��
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                selectRight[i] = cursor.getString(1).split("\\|")[4];
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }
    /*private class QueryTask extends AsyncTask<Params, Progress, Result>*/

    //��ʾ��Ŀ
    public void showTimu() {
        //submit.setText("������showTimu()");
        setSelectBgColor();
        Cursor cursor = null;
        try {
            cursor = DBManager.db.query("errorTable", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                cursor.move(timuCurrent);
                timu = cursor.getString(0);
                daan = cursor.getString(1);
            }
            String[] daans = daan.split("\\|");
            title.setText((timuCurrent + 1) + "." + timu);
            selectOne.setText(daans[0]);
            selectTwo.setText(daans[1]);
            selectThree.setText(daans[2]);
            selectFour.setText(daans[3]);
            subjectTop.setText(timuCurrent + 1 + "/" + timuMax);
            //submit.setText("selectRightΪ"+selectRight[timuCurrent]+"  selectedΪ"+selected[timuCurrent]);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override//���������
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int i = v.getId();
        if (i == R.id.forward) {
            if (timuCurrent < timuMax - 1) {
                timuCurrent++;
                showTimu();
            }

        } else if (i == R.id.back) {
            if (timuCurrent > 0) {
                timuCurrent--;
                showTimu();
            }

        } else if (i == R.id.selectOne) {
            if (selected[timuCurrent] == "wu" && hasCollect) {
                selected[timuCurrent] = "A";
                setSelectBgColor();
            }

        } else if (i == R.id.selectTwo) {
            if (selected[timuCurrent] == "wu" && hasCollect) {
                selected[timuCurrent] = "B";
                setSelectBgColor();
            }

        } else if (i == R.id.selectThree) {
            if (selected[timuCurrent] == "wu" && hasCollect) {
                selected[timuCurrent] = "C";
                setSelectBgColor();
            }

        } else if (i == R.id.selectFour) {
            if (selected[timuCurrent] == "wu" && hasCollect) {
                selected[timuCurrent] = "D";
                setSelectBgColor();
            }


            //ѡ���
        } else if (i == R.id.subject) {
            dialog = new AlertDialog.Builder(this).create();
            //��ʾdialog
            dialog.show();
            //ʵ��ѡ��򲼾�
            select_subject_layout = getLayoutInflater().inflate(R.layout.select_subject, null);
            //��ѡ��򲼾�װ��dialog
            dialog.getWindow().setContentView(select_subject_layout);
            //�õ�gridview�����������item������������list������ɫ��������������
            getGridViewList();
            //׼����item�������������id
            String[] from = {"gridViewItemImage2", "gridViewItemText2"};
            //׼����item�������������ʵ�������ַintֵ
            int[] to = {R.id.gridViewItemImage2, R.id.gridViewItemText2};
            //������׼���õ�����װ��������
            sim_adapter = new SimpleAdapter(this, gridViewItemList, R.layout.item2_gridview_selectsubject, from, to);
            //��������װ��gridView����
            gridView1 = (GridView) select_subject_layout.findViewById(R.id.gridView1);
            gridView1.setAdapter(sim_adapter);
            gridView1.smoothScrollToPosition(getBoundPosition());
            //��gridViewװ������
            gridView1.setOnItemClickListener(this);


            //ȡ���ղ�
        } else if (i == R.id.remove) {//��Ŀ��������1����ɾ������
            if (timuMax > 1) {
                String[] whereArgs = {timu};
                DBManager.db.delete("errorTable", "timu=?", whereArgs);
                Toast.makeText(this, getResources().getString(R.string.cancel_success), Toast.LENGTH_SHORT).show();
                selected = getStringArray(selected, timuCurrent);
                setTimuMaxandRight();
                if (timuCurrent > timuMax - 1) {
                    timuCurrent--;
                } else {
                }
                //��Ŀ����ֻʣһ�⣬�������ɿ���
            } else if (timuMax == 1) {
                String[] whereArgs = {timu};
                DBManager.db.delete("errorTable", "timu=?", whereArgs);
                finish();
                Toast.makeText(this, getResources().getString(R.string.clear_wrong_text), Toast.LENGTH_SHORT).show();
            } else {

            }
            showTimu();

            //����ղ�
        } else if (i == R.id.titlebar_right_text) {
            AlertDialog dialog = new AlertDialog.Builder(this).setMessage(getResources().getString(R.string.are_you_sure_clear))
                    .setPositiveButton(getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DBManager.db.delete("errorTable", null, null);
                            finish();
                            Toast.makeText(ErrorActivity.this, getResources().getString(R.string.clear_wrong_text), Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton(getResources().getString(R.string.cancel), null).create();
            dialog.show();

        } else {
        }
    }

    //������������Լ�д������ȥ���ַ���������ָ��λ�õ�Ԫ���õ�
    public String[] getStringArray(String[] strs, int index) {
        List<String> strlist = new ArrayList<String>();
        int m = 0;
        for (String str : strs) {
            if (m != index) {
                strlist.add(str);
            } else {}
            m++;
        }
        strs = new String[strlist.size()];
        strs = strlist.toArray(strs);
        return strs;
    }

    //�趨һ��intֵ��ʹ��ѡ���ʱʼ�հѵ�ǰ����ĿΪ����
    public int getBoundPosition() {
        if (timuCurrent + 5 > timuMax - 1) {
            return timuMax - 1;
        } else {
        }
        return timuCurrent + 5;
    }

    @Override//gridView�е�item�ĵ��������
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        // TODO Auto-generated method stub
        timuCurrent = position;
        showTimu();
        dialog.dismiss();
    }

    //���ô���Դ��������Լ�gridView��item����Ҫ�����list
    public void setSelectTfandgridColorText() {
        for (int i = 0; i < timuMax; i++) {
            if (selected[i] == "wu") {
                gridViewItemColor[i] = R.color.select_agodefault;
            } else if (selected[i].equals(selectRight[i])) {
                gridViewItemColor[i] = R.color.select_right;
            } else if (selected[i] != selectRight[i]) {
                gridViewItemColor[i] = R.color.select_error;
            }
            gridViewItemText[i] = String.valueOf(i + 1);
        }
    }

    //����ѡ����Ŀ��item������list
    public List<Map<String, Object>> getGridViewList() {
        gridViewItemList = new ArrayList<Map<String, Object>>();
        setSelectTfandgridColorText();
        for (int i = 0; i < timuMax; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("gridViewItemImage2", gridViewItemColor[i]);
            map.put("gridViewItemText2", gridViewItemText[i]);
            gridViewItemList.add(map);
        }
        return gridViewItemList;
    }

    //�趨��Ŀ����ѡ����ȷ���ı���ɫ��onCreate()����һ�Σ�ÿ��showTimu()��Ҫ����һ��
    public void setSelectBgColor() {
        if (selected[timuCurrent] == "wu") {
            setDefaultColor();
        } else {
            setDefaultColor();
            if (selected[timuCurrent] == "A") {
                setErrorColor(selectOne, relOne, imageOne);
            } else if (selected[timuCurrent] == "B") {
                setErrorColor(selectTwo, relTwo, imageTwo);
            } else if (selected[timuCurrent] == "C") {
                setErrorColor(selectThree, relThree, imageThree);
            } else {
                setErrorColor(selectFour, relFour, imageFour);
            }

            if (selectRight[timuCurrent].equals("A")) {
                setRightColor(selectOne, relOne, imageOne);
            } else if (selectRight[timuCurrent].equals("B")) {
                setRightColor(selectTwo, relTwo, imageTwo);
            } else if (selectRight[timuCurrent].equals("C")) {
                setRightColor(selectThree, relThree, imageThree);
            } else {
                setRightColor(selectFour, relFour, imageFour);
            }
        }
    }

    private void setDefaultColor() {
        selectOne.setBackgroundResource(R.color.select_default);
        selectTwo.setBackgroundResource(R.color.select_default);
        selectThree.setBackgroundResource(R.color.select_default);
        selectFour.setBackgroundResource(R.color.select_default);
        relOne.setBackgroundResource(R.color.select_default);
        relTwo.setBackgroundResource(R.color.select_default);
        relThree.setBackgroundResource(R.color.select_default);
        relFour.setBackgroundResource(R.color.select_default);
        imageOne.setImageResource(R.drawable.defaults);
        imageTwo.setImageResource(R.drawable.defaults);
        imageThree.setImageResource(R.drawable.defaults);
        imageFour.setImageResource(R.drawable.defaults);
    }

    private void setErrorColor(TextView a, RelativeLayout b, ImageView c) {
        a.setBackgroundResource(R.color.select_error);
        b.setBackgroundResource(R.color.select_error);
        c.setImageResource(R.drawable.wrong);
    }

    private void setRightColor(TextView a, RelativeLayout b, ImageView c) {
        a.setBackgroundResource(R.color.select_right);
        b.setBackgroundResource(R.color.select_right);
        c.setImageResource(R.drawable.right);
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_gridview_selectsubject, null);
            }
            TextView tv = com.binwang.qanda.util.ViewHolder.get(convertView, R.id.gridViewItemText);
            if (selected[position] == selectRight[position]) {
                tv.setBackgroundColor(Color.parseColor("#00CC00"));
            } else {
                tv.setBackgroundColor(Color.parseColor("#CC0000"));
            }
            tv.setText("" + timuCurrent);
            return convertView;
        }

    }

}
//�ɵ�1��ΪʲôString֮�������е�ʱ��==���ã��е�ʱ�������equals