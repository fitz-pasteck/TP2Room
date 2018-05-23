package com.moiroudthibaut.tp2room.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moiroudthibaut.tp2room.R;
import com.moiroudthibaut.tp2room.database.AppDatabase;
import com.moiroudthibaut.tp2room.database.entity.Place;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    public static final String ARGUMENT_PLACE_TO_FOCUS_NAME = "placeToFocus";

    MapView mMapView;
    private GoogleMap googleMap;
    private List<Place> places;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        mMapView = rootView.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        places = AppDatabase.getInstance(getContext()).placeDAO().getAll();

        mMapView.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;

        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.style_json));
            if (!success) {
                Log.e("StyleTag", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("StyleTag", "Can't find style. Error: ", e);
        }

        if (places != null) {
            for (Place place : places) {
                LatLng placeLatLng = new LatLng(place.getLatitude(), place.getLongitude());
                googleMap.addMarker(new MarkerOptions()
                        .position(placeLatLng)
                        .title(place.getName())
                        .snippet(place.getDescription()));
            }
        }
        if (getArguments() != null) {
            int placeToFocusId = getArguments().getInt(ARGUMENT_PLACE_TO_FOCUS_NAME, -1);
            if (placeToFocusId != -1) {
                Place placeToFocus = AppDatabase.getInstance(getContext()).placeDAO().get(placeToFocusId);
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(placeToFocus.getLatitude(), placeToFocus.getLongitude()), 12
                ));
            }
        }
    }

    public static MapFragment newInstance(Place place) {

        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        args.putInt(ARGUMENT_PLACE_TO_FOCUS_NAME, place.getId());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
