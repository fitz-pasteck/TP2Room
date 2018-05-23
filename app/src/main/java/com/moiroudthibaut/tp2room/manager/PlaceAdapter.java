package com.moiroudthibaut.tp2room.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moiroudthibaut.tp2room.R;
import com.moiroudthibaut.tp2room.database.entity.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

    private final List<Place> places = new ArrayList<>();
    private final LayoutInflater inflater;
    private final OnPlaceClickListener listener;

    public PlaceAdapter(Context context, OnPlaceClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        holder.bind(listener, places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setData(List<Place> places) {
        this.places.clear();
        this.places.addAll(places);
        notifyDataSetChanged();
    }

    public interface OnPlaceClickListener {
        void onPlaceClick(Place place);
        void onDeleteClick(Place place);
    }
}
