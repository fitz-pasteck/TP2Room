package com.moiroudthibaut.tp2room.fragment;

import android.arch.lifecycle.Observer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moiroudthibaut.tp2room.MainActivity;
import com.moiroudthibaut.tp2room.R;
import com.moiroudthibaut.tp2room.database.AppDatabase;
import com.moiroudthibaut.tp2room.database.entity.Place;
import com.moiroudthibaut.tp2room.manager.PlaceAdapter;

import java.util.List;

public class PlaceListFragment extends Fragment implements
        PlaceAdapter.OnPlaceClickListener,
        Observer<List<Place>> {

    private RecyclerView placeRecyclerView;
    private PlaceAdapter placeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_place_list, container, false);

        placeRecyclerView = rootView.findViewById(R.id.placeResultRecyclerView);
        placeAdapter = new PlaceAdapter(getContext(), this);
        placeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        placeRecyclerView.setAdapter(placeAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppDatabase.getInstance(getContext()).placeDAO().subscribeGetAll().observe(this, this);
    }

    @Override
    public void onPlaceClick(Place place) {
        Log.d("OnPlaceClick", "Clicked on place n°" + place.getId());
        MainActivity mainAct = (MainActivity) getActivity();
        if (mainAct != null)
            mainAct.transferItemToFocusOnMap(place);
    }

    @Override
    public void onDeleteClick(Place place) {
        Log.d("OnDeleteClick", "Place n°" + place.getId() + " deleted");
        AppDatabase.getInstance(getContext()).placeDAO().delete(place);
        Snackbar snackbar = Snackbar.make(placeRecyclerView, R.string.delete_msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onChanged(@Nullable List<Place> places) {
        placeAdapter.setData(places);
    }
}
