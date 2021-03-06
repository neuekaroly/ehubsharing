package com.example.neuekaroly.ehubsharing.database;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHARGER_POINT".
*/
public class ChargerPointDao extends AbstractDao<ChargerPoint, Long> {

    public static final String TABLENAME = "CHARGER_POINT";

    /**
     * Properties of entity ChargerPoint.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Longitude = new Property(2, double.class, "longitude", false, "LONGITUDE");
        public final static Property Latitude = new Property(3, double.class, "latitude", false, "LATITUDE");
        public final static Property Adress = new Property(4, String.class, "adress", false, "ADRESS");
        public final static Property Cost = new Property(5, int.class, "cost", false, "COST");
        public final static Property OpeningHours = new Property(6, String.class, "openingHours", false, "OPENING_HOURS");
        public final static Property ConnectorTypes = new Property(7, String.class, "connectorTypes", false, "CONNECTOR_TYPES");
    }

    private DaoSession daoSession;

    private Query<ChargerPoint> customer_ChargerPointsWitThisCustomerQuery;

    public ChargerPointDao(DaoConfig config) {
        super(config);
    }
    
    public ChargerPointDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHARGER_POINT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT NOT NULL ," + // 1: name
                "\"LONGITUDE\" REAL NOT NULL ," + // 2: longitude
                "\"LATITUDE\" REAL NOT NULL ," + // 3: latitude
                "\"ADRESS\" TEXT," + // 4: adress
                "\"COST\" INTEGER NOT NULL ," + // 5: cost
                "\"OPENING_HOURS\" TEXT," + // 6: openingHours
                "\"CONNECTOR_TYPES\" TEXT);"); // 7: connectorTypes
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHARGER_POINT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ChargerPoint entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
        stmt.bindDouble(3, entity.getLongitude());
        stmt.bindDouble(4, entity.getLatitude());
 
        String adress = entity.getAdress();
        if (adress != null) {
            stmt.bindString(5, adress);
        }
        stmt.bindLong(6, entity.getCost());
 
        String openingHours = entity.getOpeningHours();
        if (openingHours != null) {
            stmt.bindString(7, openingHours);
        }
 
        String connectorTypes = entity.getConnectorTypes();
        if (connectorTypes != null) {
            stmt.bindString(8, connectorTypes);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ChargerPoint entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
        stmt.bindDouble(3, entity.getLongitude());
        stmt.bindDouble(4, entity.getLatitude());
 
        String adress = entity.getAdress();
        if (adress != null) {
            stmt.bindString(5, adress);
        }
        stmt.bindLong(6, entity.getCost());
 
        String openingHours = entity.getOpeningHours();
        if (openingHours != null) {
            stmt.bindString(7, openingHours);
        }
 
        String connectorTypes = entity.getConnectorTypes();
        if (connectorTypes != null) {
            stmt.bindString(8, connectorTypes);
        }
    }

    @Override
    protected final void attachEntity(ChargerPoint entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ChargerPoint readEntity(Cursor cursor, int offset) {
        ChargerPoint entity = new ChargerPoint( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // name
            cursor.getDouble(offset + 2), // longitude
            cursor.getDouble(offset + 3), // latitude
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // adress
            cursor.getInt(offset + 5), // cost
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // openingHours
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // connectorTypes
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ChargerPoint entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setLongitude(cursor.getDouble(offset + 2));
        entity.setLatitude(cursor.getDouble(offset + 3));
        entity.setAdress(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCost(cursor.getInt(offset + 5));
        entity.setOpeningHours(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setConnectorTypes(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ChargerPoint entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ChargerPoint entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ChargerPoint entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "chargerPointsWitThisCustomer" to-many relationship of Customer. */
    public List<ChargerPoint> _queryCustomer_ChargerPointsWitThisCustomer(Long customerId) {
        synchronized (this) {
            if (customer_ChargerPointsWitThisCustomerQuery == null) {
                QueryBuilder<ChargerPoint> queryBuilder = queryBuilder();
                queryBuilder.join(JoinCustomersWithChargerPoints.class, JoinCustomersWithChargerPointsDao.Properties.ChargerPointId)
                    .where(JoinCustomersWithChargerPointsDao.Properties.CustomerId.eq(customerId));
                customer_ChargerPointsWitThisCustomerQuery = queryBuilder.build();
            }
        }
        Query<ChargerPoint> query = customer_ChargerPointsWitThisCustomerQuery.forCurrentThread();
        query.setParameter(0, customerId);
        return query.list();
    }

}
