package com.moiroudthibaut.tp2room.manager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.moiroudthibaut.tp2room.R;
import com.moiroudthibaut.tp2room.database.entity.Place;

public class PlaceViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTxtView, phoneTxtView, descriptionTxtView;
    private ImageButton btnDelete;

    public PlaceViewHolder(View v) {
        super(v);
        nameTxtView = v.findViewById(R.id.name_txt_view);
        phoneTxtView = v.findViewById(R.id.phone_txt_view);
        descriptionTxtView = v.findViewById(R.id.description_txt_view);
        btnDelete = v.findViewById(R.id.btn_delete);
    }

    public void bind(final PlaceAdapter.OnPlaceClickListener listener, final Place place) {
        nameTxtView.setText(place.getName());
        phoneTxtView.setText(place.getPhone());
        descriptionTxtView.setText(place.getDescription());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPlaceClick(place);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClick(place);
            }
        });
    }

}
