package com.moiroudthibaut.tp2room.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.moiroudthibaut.tp2room.R;
import com.moiroudthibaut.tp2room.database.AppDatabase;
import com.moiroudthibaut.tp2room.database.entity.Place;

public class AddPlaceFragment extends Fragment implements View.OnClickListener {

    private EditText name, description, phone, latitude, longitude;
    private Button insert;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_place, container, false);

        name = rootView.findViewById(R.id.add_place_name);
        description = rootView.findViewById(R.id.add_place_description);
        phone = rootView.findViewById(R.id.add_place_phone);
        latitude = rootView.findViewById(R.id.add_place_latitude);
        longitude = rootView.findViewById(R.id.add_place_longitude);

        insert = rootView.findViewById(R.id.btn_insert);
        insert.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        final String nameTxt = name.getText().toString();
        if (nameTxt.isEmpty()) {
            name.setError(getString(R.string.field_error_empty));
            return;
        }

        final String descTxt = description.getText().toString();
        if (descTxt.isEmpty()) {
            description.setError(getString(R.string.field_error_empty));
            return;
        }

        final String phoneTxt = phone.getText().toString();
        if (phoneTxt.isEmpty()) {
            phone.setError(getString(R.string.field_error_empty));
            return;
        }

        final String latitudeTxt = latitude.getText().toString();
        if (latitudeTxt.isEmpty()) {
            latitude.setError(getString(R.string.field_error_empty));
            return;
        }
        final float latitudeFloat;
        try {
            latitudeFloat = Float.parseFloat(latitudeTxt);
        } catch (NumberFormatException nFE) {
            latitude.setError(getString(R.string.field_error_float));
            return;
        }

        final String longitudeTxt = longitude.getText().toString();
        if (longitudeTxt.isEmpty()) {
            longitude.setError(getString(R.string.field_error_empty));
            return;
        }
        final float longitudeFloat;
        try {
            longitudeFloat = Float.parseFloat(longitudeTxt);
        } catch (NumberFormatException nFE) {
            longitude.setError(getString(R.string.field_error_float));
            return;
        }

        Place place = new Place();
        place.setName(nameTxt);
        place.setDescription(descTxt);
        place.setPhone(phoneTxt);
        place.setLatitude(latitudeFloat);
        place.setLongitude(longitudeFloat);

        AppDatabase.getInstance(getContext()).placeDAO().insert(place);

        if (getView() != null) {
            Snackbar snackbar = Snackbar.make(getView(), R.string.add_msg, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        emptyForm();
    }

    private void emptyForm() {
        name.setText("");
        name.setError(null);
        description.setText("");
        description.setError(null);
        phone.setText("");
        phone.setError(null);
        latitude.setText("");
        latitude.setError(null);
        longitude.setText("");
        longitude.setError(null);
    }
}
