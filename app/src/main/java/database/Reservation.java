package database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by neue.karoly on 2018.04.02..
 */

@Entity
public class Reservation {
    @Id
    private Long id;

    private long chargerPointId;

    private long customerId;    

    @ToOne(joinProperty = "chargerPointId")
    private ChargerPoint chargerPoint;

    @NotNull
    private Date startDate;
    private Date finishDate;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1812844338)
    private transient ReservationDao myDao;
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
    @Generated(hash = 504405002)
    private transient Long chargerPoint__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1274480480)
    public ChargerPoint getChargerPoint() {
        long __key = this.chargerPointId;
        if (chargerPoint__resolvedKey == null
                || !chargerPoint__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ChargerPointDao targetDao = daoSession.getChargerPointDao();
            ChargerPoint chargerPointNew = targetDao.load(__key);
            synchronized (this) {
                chargerPoint = chargerPointNew;
                chargerPoint__resolvedKey = __key;
            }
        }
        return chargerPoint;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 31441620)
    public void setChargerPoint(@NotNull ChargerPoint chargerPoint) {
        if (chargerPoint == null) {
            throw new DaoException(
                    "To-one property 'chargerPointId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.chargerPoint = chargerPoint;
            chargerPointId = chargerPoint.getId();
            chargerPoint__resolvedKey = chargerPointId;
        }
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
    @Generated(hash = 530608998)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getReservationDao() : null;
    }
    public long getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
