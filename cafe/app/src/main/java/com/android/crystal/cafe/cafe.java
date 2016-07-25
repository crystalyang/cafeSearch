package com.android.crystal.cafe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;

/**
 * Created by Crystal on 7/22/16.
 */
public class cafe implements Comparator<cafe>{
    private String id;
    private String icon;
    private String name;
    private String vicinity;
    private Double rate;
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

    public Double getRate(){return rate;}

    public int getRanking(){return (int)(rate * 10);}

    public void setRate(String rate){this.rate = Double.parseDouble(rate);}

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
            //result.rate = Double.parseDouble(pontoFeferencia.getString("rating"));
            if(pontoFeferencia.has("rating")) {
                result.setRate(pontoFeferencia.getString("rating"));
            }else{
                result.setRate("0");
            }


            return result;
        } catch (JSONException ex) {

        }
        return null;
    }

    @Override
    public int compare(cafe o1, cafe o2) {
        return o1.getRate().compareTo(o2.getRate());
    }



//    @Override
//    public String toString() {
//        return "Place{" + "id=" + id + ", icon=" + icon + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + '}';
//    }
}