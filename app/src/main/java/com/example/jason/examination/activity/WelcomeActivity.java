package com.example.jason.examination.activity;

import android.os.Bundle;

import com.example.jason.examination.Bean.ExameURLBean;
import com.example.jason.examination.R;
import com.example.jason.examination.base.BaseActivity;
import com.example.jason.examination.constants.ConstKey;
import com.example.jason.examination.utils.DBExameURLBeanUtils;
import com.example.jason.examination.utils.SharePreferenceUtil;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class WelcomeActivity extends BaseActivity {


    private String[] name = {
            "中国人事考试网",
            "黑龙江人事考试网",
            "吉林人事考试网",
            "辽宁人事考试网",
            "江苏人事考试网",
            "山东人事考试网",
            "安徽人事考试网",
            "河北人事考试网",
            "河南人事考试网",
            "湖北人事考试网",
            "湖南人事考试网",
            "江西人事考试网",
            "陕西人事考试网",
            "山西人事考试网",
            "四川人事考试网",
            "青海人事考试网",
            "海南人事考试网",
            "广东人事考试网",
            "贵州人事考试网",
            "浙江人事考试网",
            "福建人事考试网",
            "甘肃人事考试网",
            "云南人事考试网",
            "内蒙古人事考试网",
            "宁夏人事考试网",
            "广西人事考试网",
            "新疆人事考试网",
            "西藏人事考试网",
            "北京人事考试网",
            "天津人事考试网",
            "上海人事考试网",
            "重庆人事考试网"
    };

    private String url[] = {
            "http://www.cpta.com.cn",
            "http://www.hljrsks.org.cn/hljrsks/index/index.ks",
            "http://www.jlzkb.com/cms/root/index.vm",
            "http://www.lnrsks.com",
            "http://rsks.jshrss.gov.cn",
            "http://www.rsks.sdhrss.gov.cn",
            "http://www.apta.gov.cn",
            "http://www.hebpta.com.cn",
            "http://www.hnrsks.com",
            "http://www.hbsrsksy.cn",
            "http://www.hunanpta.com",
            "http://www.jxpta.com",
            "www.sxrsks.cn",
            "http://www.sxpta.com",
            "http://www.scpta.gov.cn",
            "http://www.qhpta.com/web",
            "http://ea.hainan.gov.cn",
            "http://www.gdrsks.gov.cn",
            "http://www.gzpta.gov.cn",
            "http://www.zjks.com",
            "http://www.fjpta.com",
            "http://www.rst.gansu.gov.cn",
            "http://www.ynrsksw.cn",
            "http://www.impta.com",
            "http://www.nxpta.gov.cn",
            "http://www.gxpta.com.cn",
            "http://www.xjrsks.com.cn/website",
            "http://www.xz.hrss.gov.cn",
            "http://www.bjrbj.gov.cn/bjpta/professional",
            "http://www.tjkpzx.com",
            "http://www.shrsks.org",
            "http://kszx.cqhrss.gov.cn/u/kszx"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //第一：默认初始化
        Bmob.initialize(this, "66780a64cd33942356701f85caf06551");

        if (SharePreferenceUtil.getInstance().getBoolean(ConstKey.FIRST_TIME_SAVE_EXAME_DATA, true)) {
            for (int i = 0; i < name.length; i++) {
                ExameURLBean exameURLBean = new ExameURLBean(System.currentTimeMillis(), name[i], url[i]);
                DBExameURLBeanUtils.getInstance().insertOneData(exameURLBean);
            }
            SharePreferenceUtil.getInstance().putBoolean(ConstKey.FIRST_TIME_SAVE_EXAME_DATA, false);
        }

        doInUI(new Runnable() {
            @Override
            public void run() {
                BmobUser bmobUser = BmobUser.getCurrentUser();
                if (bmobUser != null) {
                    // 允许用户使用应用
                    toActivity(MainActivity.class);
                    WelcomeActivity.this.finish();
                } else {
                    toActivity(LoginActivity.class);
                    WelcomeActivity.this.finish();
                }
            }
        }, 50);
    }
}

