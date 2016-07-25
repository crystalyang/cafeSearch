package com.android.crystal.cafe;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Crystal on 7/22/16.
 */
public class cafeService {
    private String API_KEY;

    public cafeService(String apikey){
        this.API_KEY=apikey;
    }

    public void setApiKey(String apikey){
        this.API_KEY = apikey;
    }

    public ArrayList<cafe> findCafes(double latitude, double longitude, String placeSpacification){
        String urlString = makeUrl(latitude, longitude);
        try{
            String json = getJSON(urlString);
            System.out.println(json);
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("results");
            ArrayList<cafe> arrayList = new ArrayList<cafe>();
            for(int i = 0; i<array.length();i++){
                try{
                    cafe Cafe = cafe.jsonToPontoFreferencia((JSONObject) array.get(i));
                    arrayList.add(Cafe);

                }catch(Exception e){
                    Log.e("",e.getMessage());

                }
            }
            return arrayList;
        }catch(JSONException ex){

        }
        return null;
    }

    private String makeUrl(double latitude, double longitude){
        StringBuilder urlString = new StringBuilder(
                "https://maps.googleapis.com/maps/api/place/search/json?"
        );
        //add if place is "" when type is not determined

        urlString.append("&location=");
        urlString.append(Double.toString(latitude));
        urlString.append(",");
        urlString.append(Double.toString(longitude));
        urlString.append("&radius=3000");
        urlString.append("&types=cafe");
        urlString.append("&sensor=false&key="+ API_KEY);

        return urlString.toString();
    }

    protected String getJSON(String url){
        String s = getUrlContents(url);
        return getUrlContents(url);
    }

    private String getUrlContents(String theUrl){
        StringBuilder content = new StringBuilder();
        try{
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()), 8);
            String line;
            while ((line = bufferedReader.readLine())!= null){
                content.append(line + "\n");
            }
            bufferedReader.close();

        }catch(Exception e){
            e.printStackTrace();

        }
        return content.toString();
    }
}
