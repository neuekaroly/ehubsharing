package database;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RESERVATION".
*/
public class ReservationDao extends AbstractDao<Reservation, Long> {

    public static final String TABLENAME = "RESERVATION";

    /**
     * Properties of entity Reservation.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ChargerPointId = new Property(1, long.class, "chargerPointId", false, "CHARGER_POINT_ID");
        public final static Property CustomerId = new Property(2, long.class, "customerId", false, "CUSTOMER_ID");
        public final static Property StartDate = new Property(3, java.util.Date.class, "startDate", false, "START_DATE");
        public final static Property FinishDate = new Property(4, java.util.Date.class, "finishDate", false, "FINISH_DATE");
    }

    private DaoSession daoSession;

    private Query<Reservation> customer_ReservationsQuery;

    public ReservationDao(DaoConfig config) {
        super(config);
    }
    
    public ReservationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RESERVATION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"CHARGER_POINT_ID\" INTEGER NOT NULL ," + // 1: chargerPointId
                "\"CUSTOMER_ID\" INTEGER NOT NULL ," + // 2: customerId
                "\"START_DATE\" INTEGER NOT NULL ," + // 3: startDate
                "\"FINISH_DATE\" INTEGER);"); // 4: finishDate
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RESERVATION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Reservation entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getChargerPointId());
        stmt.bindLong(3, entity.getCustomerId());
        stmt.bindLong(4, entity.getStartDate().getTime());
 
        java.util.Date finishDate = entity.getFinishDate();
        if (finishDate != null) {
            stmt.bindLong(5, finishDate.getTime());
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Reservation entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getChargerPointId());
        stmt.bindLong(3, entity.getCustomerId());
        stmt.bindLong(4, entity.getStartDate().getTime());
 
        java.util.Date finishDate = entity.getFinishDate();
        if (finishDate != null) {
            stmt.bindLong(5, finishDate.getTime());
        }
    }

    @Override
    protected final void attachEntity(Reservation entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Reservation readEntity(Cursor cursor, int offset) {
        Reservation entity = new Reservation( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // chargerPointId
            cursor.getLong(offset + 2), // customerId
            new java.util.Date(cursor.getLong(offset + 3)), // startDate
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)) // finishDate
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Reservation entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setChargerPointId(cursor.getLong(offset + 1));
        entity.setCustomerId(cursor.getLong(offset + 2));
        entity.setStartDate(new java.util.Date(cursor.getLong(offset + 3)));
        entity.setFinishDate(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Reservation entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Reservation entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Reservation entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "reservations" to-many relationship of Customer. */
    public List<Reservation> _queryCustomer_Reservations(long customerId) {
        synchronized (this) {
            if (customer_ReservationsQuery == null) {
                QueryBuilder<Reservation> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.CustomerId.eq(null));
                queryBuilder.orderRaw("T.'_id' ASC");
                customer_ReservationsQuery = queryBuilder.build();
            }
        }
        Query<Reservation> query = customer_ReservationsQuery.forCurrentThread();
        query.setParameter(0, customerId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getChargerPointDao().getAllColumns());
            builder.append(" FROM RESERVATION T");
            builder.append(" LEFT JOIN CHARGER_POINT T0 ON T.\"CHARGER_POINT_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Reservation loadCurrentDeep(Cursor cursor, boolean lock) {
        Reservation entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        ChargerPoint chargerPoint = loadCurrentOther(daoSession.getChargerPointDao(), cursor, offset);
         if(chargerPoint != null) {
            entity.setChargerPoint(chargerPoint);
        }

        return entity;    
    }

    public Reservation loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Reservation> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Reservation> list = new ArrayList<Reservation>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Reservation> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Reservation> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
