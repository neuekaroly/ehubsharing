package com.example.neuekaroly.ehubsharing.database;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by neue.karoly on 2018.04.02..
 */

@Entity
public class Reservation {
    @Id
    private Long id;

    private long chargerPointId;

    private long customerId;

    @NotNull
    private Date startDate;
    private Date finishDate;

    @Generated(hash = 794562897)
    public Reservation(Long id, long chargerPointId, long customerId, @NotNull Date startDate, Date finishDate) {
        this.id = id;
        this.chargerPointId = chargerPointId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }
    @Generated(hash = 283305752)
    public Reservation() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getChargerPointId() {
        return this.chargerPointId;
    }
    public void setChargerPointId(long chargerPointId) {
        this.chargerPointId = chargerPointId;
    }
    public Date getStartDate() {
        return this.startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getFinishDate() {
        return this.finishDate;
    }
    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
    public long getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
