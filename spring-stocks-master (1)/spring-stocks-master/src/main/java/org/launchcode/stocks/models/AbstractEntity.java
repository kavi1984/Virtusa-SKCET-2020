package org.launchcode.stocks.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class AbstractEntity {

    private int uid;

    @Id
    @GeneratedValue
    @Column(name = "uid", unique = true)
    public int getUid() {
        return uid;
    }

    protected void setUid(int uid) {
        this.uid = uid;
    }

}
