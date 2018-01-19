package com.aidebar.greendaotest.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.jason.examination.Bean.ExameURLBean;

import com.aidebar.greendaotest.gen.ExameURLBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig exameURLBeanDaoConfig;

    private final ExameURLBeanDao exameURLBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        exameURLBeanDaoConfig = daoConfigMap.get(ExameURLBeanDao.class).clone();
        exameURLBeanDaoConfig.initIdentityScope(type);

        exameURLBeanDao = new ExameURLBeanDao(exameURLBeanDaoConfig, this);

        registerDao(ExameURLBean.class, exameURLBeanDao);
    }
    
    public void clear() {
        exameURLBeanDaoConfig.clearIdentityScope();
    }

    public ExameURLBeanDao getExameURLBeanDao() {
        return exameURLBeanDao;
    }

}