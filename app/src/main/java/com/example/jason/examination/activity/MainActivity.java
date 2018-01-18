package com.example.jason.examination.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jason.examination.Bean.ExameURLBean;
import com.example.jason.examination.R;
import com.example.jason.examination.adapter.MainActivityAdapter;
import com.example.jason.examination.base.BaseActivity;
import com.example.jason.examination.utils.DBExameURLBeanUtils;
import com.example.jason.examination.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ivMainActivityMenu) ImageView ivMainActivityMenu;
    @BindView(R.id.ivMainActivityCamera) ImageView ivMainActivityCamera;
    @BindView(R.id.barTitle) Toolbar barTitle;
    @BindView(R.id.rlvMainActivity) RecyclerView rlvMainActivity;
    @BindView(R.id.nvMainActivity) NavigationView nvMainActivity;
    @BindView(R.id.dlMain) DrawerLayout dlMain;
    @BindView(R.id.iv_search_main_activity) ImageView ivSearchMainActivity;
    @BindView(R.id.edt_search_main_activity) EditText edtSearchMainActivity;
    @BindView(R.id.iv_clear_search_main_activity) ImageView ivClearSearchMainActivity;
    @BindView(R.id.ll_search_main_activity) LinearLayout llSearchMainActivity;

    private ImageView ivMainDrawerBg;
    private ImageView ivMainDrawerUserAvatar;
    private ImageView ivMainDrawerSex;
    private TextView tvMainDrawerNickname;
    private TextView tvMainDrawerNotUploadVideoCount;
    private TextView tvMainDrawerAttention;
    private LinearLayout llMainDrawerVideo;
    private View headView;
    private ImageView ivMainDrawerNotLoginUserAvatar;
    private long firstBack = -1;
    private MainActivityAdapter mainActivityAdapter;

    private List<ExameURLBean> exameURLBeanList = new ArrayList<>();
    private List<ExameURLBean> exameURLBeanSearchList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initRecyclerView();
    }

    private void initView() {
        headView = nvMainActivity.inflateHeaderView(R.layout.main_activity_drawerlayout_header_layout);
        ivMainDrawerNotLoginUserAvatar = (ImageView) headView.findViewById(R.id.ivMainDrawerNotLoginUserAvatar);
        ivMainDrawerUserAvatar = (ImageView) headView.findViewById(R.id.ivMainDrawerUserAvatar);
        ivMainDrawerSex = (ImageView) headView.findViewById(R.id.ivMainDrawerSex);
        tvMainDrawerNickname = (TextView) headView.findViewById(R.id.tvMainDrawerNickname);
        tvMainDrawerNotUploadVideoCount = (TextView) headView.findViewById(R.id.tvMainDrawerNotUploadVideoCount);
        tvMainDrawerAttention = (TextView) headView.findViewById(R.id.tvMainDrawerAttention);
        llMainDrawerVideo = (LinearLayout) headView.findViewById(R.id.llMainDrawerVideo);

        headView.findViewById(R.id.flMainDrawerUser).setOnClickListener(this);
        llMainDrawerVideo.setOnClickListener(this);
        headView.findViewById(R.id.llMainDrawerNews).setOnClickListener(this);
        headView.findViewById(R.id.llMainDrawerFeedback).setOnClickListener(this);
        headView.findViewById(R.id.llMainDrawerSetting).setOnClickListener(this);
        headView.findViewById(R.id.ivMainDrawerUserAvatar).setOnClickListener(this);
        headView.findViewById(R.id.llMainDrawerLogin).setOnClickListener(this);
        ivMainActivityMenu.setOnClickListener(this);
        ivMainActivityCamera.setOnClickListener(this);
        ivClearSearchMainActivity.setOnClickListener(this);

    }

    public void initRecyclerView() {
        exameURLBeanList = DBExameURLBeanUtils.getInstance().queryAllData();
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rlvMainActivity.setLayoutManager(gridLayoutManager);
        mainActivityAdapter = new MainActivityAdapter(this, exameURLBeanList);
        rlvMainActivity.setAdapter(mainActivityAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivMainActivityMenu:
                openDrawer();
                break;
            case R.id.ivMainActivityCamera:
//                Intent intent = new Intent(this, WebViewActivity.class);
//                intent.putExtra(ConstKey.INTENT_KEY_TO_WEB_VIEW_ACTIVITY_TITLE, "模拟考试");
//                intent.putExtra(ConstKey.INTENT_KEY_TO_WEB_VIEW_ACTIVITY_URL, "http://mnks.jxedt.com/akm1/sjlx/");
//                startActivity(intent);

//                toActivity(TestExamMainActivity.class);
                break;
            case R.id.flMainDrawerUser:
                //进入自己的主页
                toActivity(UserInfoActivity.class);
                closeDrawer();
                break;
            case R.id.ivMainDrawerUserAvatar:
                //进入自己的主页
                toActivity(UserInfoActivity.class);
                closeDrawer();
                break;
            case R.id.llMainDrawerVideo:
                //视频管理页
//                toActivity(VideoManageActivity.class);
                closeDrawer();
                break;
            case R.id.llMainDrawerNews:
                //消息通知页
                // toActivity(MessageActivity
                // .class);
                closeDrawer();
                break;
            case R.id.llMainDrawerFeedback:
                //系统反馈
//                toActivity(FeedbackActivity.class);
                closeDrawer();
                break;
            case R.id.llMainDrawerSetting:
                //设置页
                toActivity(SettingActivity.class);
                closeDrawer();
                break;
            case R.id.llMainDrawerLogin:
                //登陆页
                toActivity(LoginActivity.class);
                closeDrawer();
                break;

            case R.id.iv_clear_search_main_activity:
                edtSearchMainActivity.setText("");
                break;
            default:
                break;

        }
    }

    private boolean isDrawerOpen() {
        return dlMain.isDrawerOpen(Gravity.LEFT);
    }

    private void openDrawer() {
        //打开抽屉
        if (!isDrawerOpen()) {
            dlMain.openDrawer(Gravity.LEFT);
            closeSoft();
        }
    }

    public void closeSoft() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                    0);
        }
    }


    private void closeDrawer() {
        doInUI(new Runnable() {
            @Override
            public void run() {
                //关闭抽屉
                if (isDrawerOpen()) {
                    dlMain.closeDrawer(Gravity.LEFT);
                    dlMain.closeDrawers();
                    closeSoft();
                }
            }
        }, 500);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isDrawerOpen()) {
            closeDrawer();
        } else {
            if (System.currentTimeMillis() - firstBack < 2000) {
                super.onBackPressed();
            } else {
                firstBack = System.currentTimeMillis();
                ToastHelper.showShortMessage(R.string.quit_app);
            }
        }
    }


    @OnTextChanged(value = R.id.edt_search_main_activity, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged(CharSequence text) {
        //搜索好友开始
        if (TextUtils.isEmpty(text.toString().trim())) {
            ivClearSearchMainActivity.setVisibility(View.GONE);
            exameURLBeanList = DBExameURLBeanUtils.getInstance().queryAllData();
            mainActivityAdapter.setData(exameURLBeanList);
        } else {
            ivClearSearchMainActivity.setVisibility(View.VISIBLE);

            exameURLBeanSearchList.clear();
            if (text.length() > 0 && exameURLBeanList.size() > 0) {
                for (ExameURLBean exameURLBean : exameURLBeanList) {
                    if (!TextUtils.isEmpty(exameURLBean.getName()) && exameURLBean.getName().toLowerCase().startsWith(text.toString().toLowerCase())) {
                        exameURLBeanSearchList.add(exameURLBean);
                    }
                }
                if (exameURLBeanSearchList.size() > 0) {
                    mainActivityAdapter.setData(exameURLBeanSearchList);
                } else {
                    mainActivityAdapter.setData(exameURLBeanList);
                    ToastHelper.showShortMessage("没有搜索到相关信息");
                }

            }
        }
    }

}
