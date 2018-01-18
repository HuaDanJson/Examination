package com.example.jason.examination.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jason on 2018/1/17.
 */

@Entity // 标识实体类，greenDAO会映射成sqlite的一个表，表名为实体类名的大写形式
public class ExameURLBean {
    @Id(autoincrement = false)
    public long creatTimeAsId;  //把创建时间作为表的ID
    @Property(nameInDb = "ExameURLBean")
    public String name;  // 每个地址名称
    public String url;//每个链接

    @Generated(hash = 644278875)
    public ExameURLBean(long creatTimeAsId, String name, String url) {
        this.creatTimeAsId = creatTimeAsId;
        this.name = name;
        this.url = url;
    }

    @Generated(hash = 1975956505)
    public ExameURLBean() {
    }

    public long getCreatTimeAsId() {
        return this.creatTimeAsId;
    }

    public void setCreatTimeAsId(long creatTimeAsId) {
        this.creatTimeAsId = creatTimeAsId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ExameURLBean{" +
                "creatTimeAsId=" + creatTimeAsId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
