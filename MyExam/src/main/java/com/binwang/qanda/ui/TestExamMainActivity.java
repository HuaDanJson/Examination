package com.binwang.qanda.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.binwang.qanda.R;
import com.binwang.qanda.sqlite.DBManager;

public class TestExamMainActivity extends Activity implements OnClickListener {

    private String TAG = getClass().getName();//��дfinish()���ã���֪�������ô�...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_exam_main);
        //�趨�����activity�Ķ���Ч��
        overridePendingTransition(R.anim.alpha_scale_rotate, R.anim.push_left_out);
        TextView order = (TextView) findViewById(R.id.order);
        TextView simulate = (TextView) findViewById(R.id.simulate);
        TextView replacement = (TextView) findViewById(R.id.replacement);
        LinearLayout favorite = (LinearLayout) findViewById(R.id.favorite);
        LinearLayout wrong = (LinearLayout) findViewById(R.id.wrong);
        LinearLayout history = (LinearLayout) findViewById(R.id.history);
        order.setOnClickListener(this);
        simulate.setOnClickListener(this);
        favorite.setOnClickListener(this);
        wrong.setOnClickListener(this);
        history.setOnClickListener(this);
        DBManager.createDB();
        //DBManager.simpleCreateDB();
    }

    @Override//��дfinish()��Ŀ���Ǳ�֤�����ж���Ч��
    public void finish() {
        Log.i(TAG, "finish");
        super.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.zoom_exit);
    }

    @Override//������
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int i = v.getId();
        if (i == R.id.order) {
            Intent intent = new Intent();
            intent.setClass(TestExamMainActivity.this, OrderActivity.class);
            startActivity(intent);
            //finish();
            //�ұ����ָ���飺startActivity(new Intent(TestExamMainActivity.this, OrderActivity.class));

        } else if (i == R.id.simulate) {
            View layout = getLayoutInflater().inflate(R.layout.enter_simulate, null);
            final Dialog dialog = new Dialog(this);
            dialog.setTitle("��ܰ��ʾ");
            dialog.show();
            dialog.getWindow().setContentView(layout);
            final EditText et_name = (EditText) layout.findViewById(R.id.et_name);
            TextView confirm = (TextView) layout.findViewById(R.id.confirm);
            TextView cancel = (TextView) layout.findViewById(R.id.cancel);
            confirm.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                        Toast.makeText(TestExamMainActivity.this, "�������뿼������", Toast.LENGTH_SHORT).show();
                    } else {
                        DBManager.insertExamTable();
                        ResultActivity.NAME = et_name.getText().toString().trim();
                        //ExamActivity.intentToExamActivity(TestExamMainActivity.this, et_name.getText().toString().trim());
                        startActivity(new Intent(TestExamMainActivity.this, ExamActivity.class));
                        Toast.makeText(TestExamMainActivity.this, "���Կ�ʼ", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });

            cancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        } else if (i == R.id.favorite) {
            Cursor cursor2 = null;
            try {
                cursor2 = DBManager.db.query("collectTable", null, null, null, null, null, null);
                if (cursor2.getCount() != 0) {
                    startActivity(new Intent(this, CollectActivity.class));
                } else {
                    Toast.makeText(TestExamMainActivity.this, "����û���ղؼ�¼", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor2 != null) {
                    cursor2.close();
                }
            }

        } else if (i == R.id.wrong) {
            Cursor cursor = null;
            try {
                cursor = DBManager.db.query("errorTable", null, null, null, null, null, null);
                if (cursor.getCount() != 0) {
                    startActivity(new Intent(this, ErrorActivity.class));
                } else {
                    Toast.makeText(TestExamMainActivity.this, "�㻹û�д����¼", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

        } else if (i == R.id.history) {
            Cursor cursor1 = null;
            try {
                cursor1 = DBManager.db.query("historyTable", null, null, null, null, null, null);
                if (cursor1.getCount() != 0) {
                    startActivity(new Intent(this, HisResultActivity.class));
                } else {
                    Toast.makeText(TestExamMainActivity.this, "�㻹û��ģ�⿼�Լ�¼", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor1 != null) {
                    cursor1.close();
                }
            }

        } else {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
