package com.moiroudthibaut.tp2room;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.moiroudthibaut.tp2room.database.AppDatabase;
import com.moiroudthibaut.tp2room.database.entity.Place;
import com.moiroudthibaut.tp2room.fragment.AddPlaceFragment;
import com.moiroudthibaut.tp2room.fragment.MapFragment;
import com.moiroudthibaut.tp2room.fragment.PlaceListFragment;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, new PlaceListFragment())
                .commit();

        //buildTestData();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_add:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AddPlaceFragment())
                        .commit();
                break;
            case R.id.navigation_list:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, new PlaceListFragment())
                        .commit();
                break;
            case R.id.navigation_map:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MapFragment())
                        .commit();
                break;
        }
        return true;
    }


    public void transferItemToFocusOnMap(Place place) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, MapFragment.newInstance(place))
                .commit();
    }


    private void buildTestData() {
        Place p1 = new Place();
        p1.setName("Lyon");
        p1.setDescription("Lyon, ville française de la région historique Rhône-Alpes, se trouve " +
                "à la jonction du Rhône et de la Saône. Son centre témoigne de 2 000 ans d'histoire," +
                " avec son amphithéâtre romain des Trois Gaules, l'architecture médiévale et " +
                "Renaissance du Vieux Lyon et la modernité du quartier de la Confluence sur " +
                "la Presqu'île. Les Traboules, passages couverts entre les immeubles, relient le " +
                "Vieux Lyon à la colline de La Croix-Rousse.");
        p1.setLatitude(Float.parseFloat("45.75"));
        p1.setLongitude(Float.parseFloat("4.85"));
        p1.setPhone("06 01 02 03 04");

        Place p2 = new Place();
        p2.setName("Lyon 9");
        p2.setDescription("Mairie du 9e arrondissement, Place du Marché, Lyon, France");
        p2.setLatitude(Float.parseFloat("45.7740186"));
        p2.setLongitude(Float.parseFloat("4.806013900000039"));
        p2.setPhone("04 72 19 81 81");

        Place p3 = new Place();
        p3.setName("Montpellier");
        p3.setDescription("Mairie de Montpellier, Place Georges Frêche, Montpellier, France");
        p3.setLatitude(Float.parseFloat("43.5985356"));
        p3.setLongitude(Float.parseFloat("3.8969194999999672"));
        p3.setPhone("04 67 34 70 00");

        Place p4 = new Place();
        p4.setName("Aéroport JFK (JFK), Queens, État de New York, États-Unis");
        p4.setDescription("L'aéroport international John-F.-Kennedy de New York (souvent abrégé " +
                "New York-Kennedy ou encore JFK) (code AITA : JFK • code OACI : KJFK) est un " +
                "aéroport américain desservant la ville de New York et sa région." +
                "Il tient son nom du président américain John Fitzgerald Kennedy et est situé " +
                "dans la partie sud-est de l'arrondissement du Queens, sur la baie de Jamaica à " +
                "19 kilomètres de Manhattan et à 26 kilomètres1 du centre ville.");
        p4.setLatitude(Float.parseFloat("40.6413111"));
        p4.setLongitude(Float.parseFloat("-73.77813909999998"));
        p4.setPhone("(718) 244-4444");

        AppDatabase.getInstance(this).placeDAO().insert(p1, p2, p3, p4);
    }

}
