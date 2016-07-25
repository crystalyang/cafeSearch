package com.android.crystal.cafe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;



public class ListFragment extends Fragment {
    private String apiKey = "AIzaSyAy1kwW3Ja8NkKBCrMftJsrHwP7-IxtJ4U";
    private LatLng currentLoc = new LatLng(37.3355457, -121.882949);
    private String[] cafes = {"a"};
    private String TAG = "RecyclerViewFragment";
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<cafe> cafesList = new ArrayList<>();
    private int Size;
    private static View rootView;
    private RecyclerViewAdapter mAdapter;



    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        new getCafes(getActivity(), cafes[0].toLowerCase().replace("-", "_")).execute();


        return rootView;
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

            cafesList = result;
            Size = result.size();

            mAdapter = new RecyclerViewAdapter(cafesList,Size);
            mRecyclerView.setAdapter(mAdapter);


        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected ArrayList<cafe> doInBackground(String... arg0) {
            cafeService service = new cafeService(apiKey);
            ArrayList<cafe> cafeList = service.findCafes(currentLoc.latitude, currentLoc.longitude, "cafe");

            return cafeList;
        }
    }

}
