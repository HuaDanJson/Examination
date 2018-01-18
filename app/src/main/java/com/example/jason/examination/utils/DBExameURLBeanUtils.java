package com.example.jason.examination.utils;

import android.content.Context;

import com.aidebar.greendaotest.gen.ExameURLBeanDao;
import com.example.jason.examination.Bean.ExameURLBean;

import java.util.List;

/**
 * Created by Json on 2017/3/29.
 */

public class DBExameURLBeanUtils {

    private ExameURLBeanDao dbUserInvestmentDao;
    private static DBExameURLBeanUtils dbUserInvestmentUtils = null;

    public DBExameURLBeanUtils(Context context) {
        dbUserInvestmentDao = DaoManager.getInstance(context).getNewSession().getExameURLBeanDao();
    }

    public static DBExameURLBeanUtils getInstance() {
        return dbUserInvestmentUtils;
    }

    public static void Init(Context context) {
        if (dbUserInvestmentUtils == null) {
            dbUserInvestmentUtils = new DBExameURLBeanUtils(context);
        }
    }

    /**
     * 完成对数据库中插入一条数据操作
     *
     * @param
     * @return
     */
    public void insertOneData(ExameURLBean dbUserInvestment) {
        dbUserInvestmentDao.insertOrReplace(dbUserInvestment);
    }

    /**
     * 完成对数据库中插入多条数据操作
     *
     * @param dbUserInvestmentList
     * @return
     */
    public boolean insertManyData(List<ExameURLBean> dbUserInvestmentList) {
        boolean flag = false;
        try {
            dbUserInvestmentDao.insertOrReplaceInTx(dbUserInvestmentList);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中删除一条数据操作
     *
     * @param dbUserInvestment
     * @return
     */
    public boolean deleteOneData(ExameURLBean dbUserInvestment) {
        boolean flag = false;
        try {
            dbUserInvestmentDao.delete(dbUserInvestment);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中删除一条数据 ByKey操作
     *
     * @return
     */
    public boolean deleteOneDataByKey(long id) {
        boolean flag = false;
        try {
            dbUserInvestmentDao.deleteByKey(id);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中批量删除数据操作
     *
     * @return
     */
    public boolean deleteManData(List<ExameURLBean> dbUserInvestmentList) {
        boolean flag = false;
        try {
            dbUserInvestmentDao.deleteInTx(dbUserInvestmentList);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库更新数据操作
     *
     * @return
     */
    public boolean updateData(ExameURLBean dbUserInvestment) {
        boolean flag = false;
        try {
            dbUserInvestmentDao.update(dbUserInvestment);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库批量更新数据操作
     *
     * @return
     */
    public boolean updateManData(List<ExameURLBean> dbUserInvestmentList) {
        boolean flag = false;
        try {
            dbUserInvestmentDao.updateInTx(dbUserInvestmentList);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库查询数据操作
     *
     * @return
     */
    public ExameURLBean queryOneData(long id) {
        return dbUserInvestmentDao.load(id);
    }

    /**
     * 完成对数据库查询所有数据操作
     *
     * @return
     */
    public List<ExameURLBean> queryAllData() {
        return dbUserInvestmentDao.loadAll();
    }

    /**
     * 完成对数据库条件查询数据操作
     *
     * @return
     */
    public List<ExameURLBean> queryDataDependName(String name) {
        return dbUserInvestmentDao.queryBuilder().where(ExameURLBeanDao.Properties.Name.eq(name)).build().list();
    }

    /**
     * 完成对数据库条件模糊查询数据操作
     *
     * @return
     */
    public List<ExameURLBean> queryDataLikeName(String name) {
        return dbUserInvestmentDao.queryBuilder().where(ExameURLBeanDao.Properties.Name.like("%" + name + "%")).build().list();
    }


}

