package Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by neue.karoly on 2018.03.30..
 */

@Entity
public class ChargerPoint {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String name;
    private double longitude;
    private double latitude;
    private String adress;
    private int cost;
    private String openingHours;
    private String connectorTypes;
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
}
