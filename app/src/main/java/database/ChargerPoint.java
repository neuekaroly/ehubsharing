package database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * Created by neue.karoly on 2018.03.30..
 */

@Entity
public class ChargerPoint {
    @Id(autoincrement = true)
    private Long id;

    @ToMany(referencedJoinProperty = "chargerPointId")
    private List<Reservation> reservations;

    @NotNull
    private String name;
    private double longitude;
    private double latitude;
    private String adress;
    private int cost;
    private String openingHours;
    private String connectorTypes;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 763632063)
    private transient ChargerPointDao myDao;
    @Generated(hash = 757165202)
    public ChargerPoint(Long id, @NotNull String name, double longitude,
            double latitude, String adress, int cost, String openingHours,
            String connectorTypes) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.adress = adress;
        this.cost = cost;
        this.openingHours = openingHours;
        this.connectorTypes = connectorTypes;
    }
    @Generated(hash = 1840407101)
    public ChargerPoint() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public String getAdress() {
        return this.adress;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }
    public int getCost() {
        return this.cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    public String getOpeningHours() {
        return this.openingHours;
    }
    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }
    public String getConnectorTypes() {
        return this.connectorTypes;
    }
    public void setConnectorTypes(String connectorTypes) {
        this.connectorTypes = connectorTypes;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1887750360)
    public List<Reservation> getReservations() {
        if (reservations == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ReservationDao targetDao = daoSession.getReservationDao();
            List<Reservation> reservationsNew = targetDao
                    ._queryChargerPoint_Reservations(id);
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
    @Generated(hash = 451635386)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getChargerPointDao() : null;
    }
}
