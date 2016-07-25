package com.android.crystal.cafe;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Crystal on 7/22/16.
 */
public class cafe {
    private String id;
    private String icon;
    private String name;
    private String vicinity;
    private Double latitude;
    private Double longitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    static cafe jsonToPontoFreferencia(JSONObject pontoFeferencia) {
        try {
            cafe result = new cafe();
            JSONObject geometry = (JSONObject) pontoFeferencia.get("geometry");
            JSONObject location = (JSONObject) geometry.get("location");
            result.setLatitude((Double) location.get("lat"));
            result.setLongitude((Double) location.get("lng"));
            result.setIcon(pontoFeferencia.getString("icon"));
            result.setName(pontoFeferencia.getString("name"));
            result.setVicinity(pontoFeferencia.getString("vicinity"));
            result.setId(pontoFeferencia.getString("id"));
            return result;
        } catch (JSONException ex) {

        }
        return null;
    }


    @Override
    public String toString() {
        return "Place{" + "id=" + id + ", icon=" + icon + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }
}