package com.binwang.qanda.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
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
import android.widget.LinearLayout;
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

public class OrderActivity extends Activity implements OnClickListener, OnItemClickListener {

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
    private String[] selectTf;//ÿ���û����������û�д���Щ����ļ���
    private String[] selectRight;//���б����ղص���Ŀÿһ����ȷ�𰸵ļ���
    private String[] selected;//ÿһ���û���ѡ��ѡ���¼�ļ���
    private Dialog dialog;//ѡ������
    private GridView gridView1;//ѡ������
    private View select_subject_layout;//ѡ������
    private int[] gridViewItemColor;//ѡ������
    private String[] gridViewItemText;//ѡ������
    private List<Map<String, Object>> gridViewItemList;//ѡ������
    private SimpleAdapter sim_adapter;//ѡ������
    private String timu = null;//��ǰ��Ŀ
    private String daan = null;//��ǰ��
    protected int activityCloseEnterAnimation;
    protected int activityCloseExitAnimation;
    private String TAG;//��дfinish()���ã���֪�������ô�...


    //submit.setText("������");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        resetTitlebar();
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
        RelativeLayout collect = (RelativeLayout) findViewById(R.id.collect);
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
        initialTimuMaxandRight();
        initialSelectedandTf(timuMax);
        initialGridViewItem(timuMax);
        setSelectTfandgridColorText();
        //����׼����ȫ����ʾ��Ŀ
        showTimu();
    }

    //���������
    private void resetTitlebar() {
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.view_comm_titlebar);
        TextView title = (TextView) findViewById(R.id.titlebar_title);
        LinearLayout back = (LinearLayout) findViewById(R.id.titlebar_left_layout);
        title.setText("˳����ϰ");
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override//��дfinish()��Ŀ���Ǳ�֤�����ж���Ч��
    public void finish() {
        Log.i(TAG, "finish");
        super.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    //��ʼ�����������������
    public void initialSelectedandTf(int this_timuMax) {
        selected = new String[this_timuMax];
        selectTf = new String[this_timuMax];
        for (int i = 0; i < selected.length; i++) {
            selected[i] = "wu";
            selectTf[i] = "wu";
        }
    }

    //��ʼ��ѡ���item��ɫ��ѡ���item����
    private void initialGridViewItem(int this_timuMax) {
        gridViewItemColor = new int[this_timuMax];
        gridViewItemText = new String[this_timuMax];
    }

    //����ȷ���ղر������Ƿ��Ѿ�����һ���ˣ�����ͬ�ķ���trueֵ
    public boolean hasTheSame() {
        Cursor cursor = null;
        try {
            cursor = DBManager.db.query("collectTable", null, null, null, null, null, null);
            cursor.moveToFirst();
            if (cursor.getString(0).equals(timu)) {
                return true;
            } else {
            }
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equals(timu)) {
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

    //�õ���Ŀ����timuMax��ÿ�����ȷ�𰸼���selectRight[]
    public void initialTimuMaxandRight() {
        Cursor cursor = null;
        try {
            cursor = DBManager.db.query("practiceTable", null, null, null, null, null, null);
            //�趨timuMax��ֵ
            timuMax = cursor.getCount();
            //�趨sxelectRight�����С
            selectRight = new String[timuMax];
            //����cursor����ȡ������ȷ��
            cursor.moveToFirst();
            int i = 0;
            selectRight[i] = cursor.getString(1).split("\\|")[4];
            while (cursor.moveToNext()) {
                i++;
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

    //��ʾ��Ŀ
    public void showTimu() {
        setSelectBgColor();
        Cursor cursor = null;
        try {
            cursor = DBManager.db.query("practiceTable", null, null, null, null, null, null);
            cursor.moveToPosition(timuCurrent);
            timu = cursor.getString(0);
            daan = cursor.getString(1);
            String[] daans = daan.split("\\|");
            title.setText((timuCurrent + 1) + "." + timu);
            selectOne.setText(daans[0]);
            selectTwo.setText(daans[1]);
            selectThree.setText(daans[2]);
            selectFour.setText(daans[3]);
            subjectTop.setText(timuCurrent + 1 + "/" + timuMax);
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
            if (selected[timuCurrent] == "wu") {
                selected[timuCurrent] = "A";
                setSelectBgColor();
            }

        } else if (i == R.id.selectTwo) {
            if (selected[timuCurrent] == "wu") {
                selected[timuCurrent] = "B";
                setSelectBgColor();
            }

        } else if (i == R.id.selectThree) {
            if (selected[timuCurrent] == "wu") {
                selected[timuCurrent] = "C";
                setSelectBgColor();
            }

        } else if (i == R.id.selectFour) {
            if (selected[timuCurrent] == "wu") {
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

            //�ղ���Ŀ
        } else if (i == R.id.collect) {//����ͬ�ģ��Ͳ��ղ���
            if (hasTheSame()) {
                Toast.makeText(this, "��һ���Ѿ��ղ���", Toast.LENGTH_SHORT).show();
            } else {
                //û���ղع����Ϳ����ղ�
                ContentValues cv = new ContentValues();
                cv.put("timu", timu);
                cv.put("daan", daan);
                DBManager.db.insert("collectTable", null, cv);
                Toast.makeText(this, "�ղسɹ�", Toast.LENGTH_SHORT).show();
            }

        } else {
        }
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
                selectTf[i] = "wu";
                gridViewItemColor[i] = R.color.select_agodefault;
            } else if (selected[i].equals(selectRight[i])) {
                selectTf[i] = "true";
                gridViewItemColor[i] = R.color.select_right;
            } else if (selected[i] != selectRight[i]) {
                selectTf[i] = "false";
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