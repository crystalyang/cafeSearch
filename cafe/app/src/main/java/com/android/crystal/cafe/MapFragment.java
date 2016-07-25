package com.android.crystal.cafe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static View view;
    private final String TAG = getClass().getSimpleName();
    AutoCompleteTextView from;
    GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteFragment search;


    private String apiKey = "AIzaSyAy1kwW3Ja8NkKBCrMftJsrHwP7-IxtJ4U";
    private LatLng currentLoc = new LatLng(37.3355457, -121.882949);
    private String[] cafes = {"a"};
    private SupportMapFragment fragment;
    private GoogleMap mMapView;
    private MainActivity MainActivity;

    EditText text;

    public MapFragment() {
        // Required empty public constructor
    }


    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(container == null){return null;}


        view = (RelativeLayout) inflater.inflate(R.layout.fragment_map, container, false);

        text = (EditText)view.findViewById(R.id.editText);
        Button button = (Button) view.findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = text.getText().toString();


                new getCafes(getActivity(), cafes[0].toLowerCase().replace("-", "_")).execute();
            }
        });

        return view;

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);



        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        }
        fragment.getMapAsync(this);
        new getCafes(getActivity(), cafes[0].toLowerCase().replace("-", "_")).execute();


    }


 private class getCafes extends AsyncTask<String, String,  ArrayList<cafe>> {
    private ProgressDialog dialog;
    private Context context;
    private String cafes;

    public getCafes(Activity context, String cafes){
        this.context = context;
        this.cafes = cafes;
    }

    @Override
    protected void onPostExecute(ArrayList<cafe> result) {
        super.onPostExecute(result);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        for (int i = 0; i < result.size(); i++) {
            mMapView.addMarker(new MarkerOptions()
                    .title(result.get(i).getName())
                    .position(
                            new LatLng(result.get(i).getLatitude(), result
                                    .get(i).getLongitude()))
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.marker))
                    .snippet(result.get(i).getVicinity()));
        }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(result.get(0).getLatitude(), result
                        .get(0).getLongitude()))
                .zoom(14)
                .tilt(30)
                .build();
        mMapView.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Loading..");
        dialog.isIndeterminate();
        dialog.show();
    }

    @Override
    protected ArrayList<cafe> doInBackground(String... arg0) {
        cafeService service = new cafeService(apiKey);
        ArrayList<cafe> cafeList = service.findCafes(currentLoc.latitude, currentLoc.longitude, "cafe");
        for (int i = 0; i < cafeList.size(); i++) {
            cafe cafeDetail = cafeList.get(i);
            //Log.e(TAG, "CAFE:" + cafeDetail.getName());
        }
        return cafeList;
    }
}

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMapView = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng current = new LatLng(37.3355457, -121.882949);
        mMapView.addMarker(new MarkerOptions().position(current).title("current location"));
        mMapView.moveCamera(CameraUpdateFactory.newLatLng(current));
    }


}
