package database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by neue.karoly on 2018.04.02..
 */

@Entity
public class JoinCustomersWithChargerPoints {
    @Id
    private Long id;

    private Long chargerPointId;
    private Long customerId;
    @Generated(hash = 101475766)
    public JoinCustomersWithChargerPoints(Long id, Long chargerPointId,
            Long customerId) {
        this.id = id;
        this.chargerPointId = chargerPointId;
        this.customerId = customerId;
    }
    @Generated(hash = 1957591274)
    public JoinCustomersWithChargerPoints() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getChargerPointId() {
        return this.chargerPointId;
    }
    public void setChargerPointId(Long chargerPointId) {
        this.chargerPointId = chargerPointId;
    }
    public Long getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
