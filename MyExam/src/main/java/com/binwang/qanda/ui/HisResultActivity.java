package com.binwang.qanda.ui;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.binwang.qanda.R;
import com.binwang.qanda.sqlite.DBManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HisResultActivity extends Activity implements OnClickListener{
	
	private Dialog dialog;
	private View his_result_layout;
	private TextView hisName;
	private TextView hisResult;
	private SimpleAdapter sim_adapter;
	private List<Map<String,String>> resultItemList = new ArrayList<Map<String, String>>();
	private ListView hisListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_his_result);
		resetTitlebar();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		getResultItemList();
		//׼����item�������������id
		String[] from ={"hisName","curTime","useTime","hisResult"};
		//׼����item�������������ʵ�������ַintֵ
        int[] to = {R.id.hisName,R.id.curTime,R.id.useTime,R.id.hisResult};
        //������׼���õ�����װ��������
        sim_adapter=new SimpleAdapter(this, resultItemList, R.layout.item_listview_hisresult, from, to);
		//��������װ��gridView����
        hisListView = (ListView)findViewById(R.id.hisListView);
        hisListView.setAdapter(sim_adapter);
	}
	
	//�Զ��������
	private void resetTitlebar() {
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.view_comm_titlebar);
		TextView title = (TextView) findViewById(R.id.titlebar_title);
		TextView right = (TextView) findViewById(R.id.titlebar_right_text);
		LinearLayout back = (LinearLayout) findViewById(R.id.titlebar_left_layout);
		title.setText("��ʷ�ɼ�");
		right.setText("���");
		right.setOnClickListener(this);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.titlebar_right_text) {
			AlertDialog dialog = new AlertDialog.Builder(this).setMessage("ȷ��Ҫ�����")
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							DBManager.db.delete("historyTable", null, null);
							finish();
							Toast.makeText(HisResultActivity.this, "�����ȫ�����Խ����¼", Toast.LENGTH_SHORT).show();
						}
					}).setNegativeButton("ȡ��", null).create();
			dialog.show();

		} else {
		}
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	//��ȡ������ѡ����Ŀ��item������list
		private List<Map<String, String>> getResultItemList(){        
	        Cursor cursor=null;
	        try{
	        	cursor=DBManager.db.query("historyTable",null,null,null,null,null,null);
	        	for(int i=0;i<cursor.getCount();i++){
	        		cursor.moveToPosition(i);
	        		Map<String,String> map=new HashMap<String,String>();
	        		map.put("hisName",cursor.getString(0));
	        		map.put("curTime",cursor.getString(1));
	        		map.put("useTime",cursor.getString(2));
	        		map.put("hisResult",cursor.getString(3));
	        		resultItemList.add(map);
	        	}
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	if(cursor!=null){
	        		cursor.close();
	        	}
	        }
	        return resultItemList;
	    }


}
