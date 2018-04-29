package com.example.neuekaroly.ehubsharing.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by neue.karoly on 2018.04.02..
 */

@Entity
public class Customer {
    @Id
    private Long id;

    @ToMany(referencedJoinProperty = "customerId")
    @OrderBy("id ASC")
    private List<Reservation> reservations;

    @ToMany
    @JoinEntity(
            entity = JoinCustomersWithChargerPoints.class,
            sourceProperty = "customerId",
            targetProperty = "chargerPointId"
    )
    private List<ChargerPoint> chargerPointsWitThisCustomer;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1697251196)
    private transient CustomerDao myDao;

    @Generated(hash = 470110355)
    public Customer(Long id) {
        this.id = id;
    }

    @Generated(hash = 60841032)
    public Customer() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1178570761)
    public List<Reservation> getReservations() {
        if (reservations == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ReservationDao targetDao = daoSession.getReservationDao();
            List<Reservation> reservationsNew = targetDao
                    ._queryCustomer_Reservations(id);
            synchronized (this) {
                if (reservations == null) {
                    reservations = reservationsNew;
                }
            }
        }
        return reservations;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1960428445)
    public synchronized void resetReservations() {
        reservations = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 462117449)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCustomerDao() : null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 762491746)
    public List<ChargerPoint> getChargerPointsWitThisCustomer() {
        if (chargerPointsWitThisCustomer == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ChargerPointDao targetDao = daoSession.getChargerPointDao();
            List<ChargerPoint> chargerPointsWitThisCustomerNew = targetDao
                    ._queryCustomer_ChargerPointsWitThisCustomer(id);
            synchronized (this) {
                if (chargerPointsWitThisCustomer == null) {
                    chargerPointsWitThisCustomer = chargerPointsWitThisCustomerNew;
                }
            }
        }
        return chargerPointsWitThisCustomer;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1131510589)
    public synchronized void resetChargerPointsWitThisCustomer() {
        chargerPointsWitThisCustomer = null;
    }
}
