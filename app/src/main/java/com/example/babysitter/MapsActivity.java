package com.example.babysitter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.babysitter.databinding.ActivityMapsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    // for menu
    BottomNavigationView navigationView;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        title = findViewById(R.id.title);

        // menu code
        navigationView = findViewById(R.id.nav);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.homeIcon:
                        title.setText("Employees around you");
                            for(int i=0; i<getSupportFragmentManager().getBackStackEntryCount();i++)
                                getSupportFragmentManager().popBackStack();
                        break;

                    case R.id.profileIcon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.map, new Profile()).addToBackStack(null).commit();
                        title.setText("Profile");
                        break;

                    case R.id.infoIcon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.map, new informationPage()).addToBackStack(null).commit();
                        title.setText("Info");
                        break;
                    case R.id.historyIcon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.map, new History()).addToBackStack(null).commit();
                        title.setText("History");
                }

                return true;
            }
        });



    }
    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng haifa  = new LatLng(32.7996175,35.0517955);

        mMap.addMarker(new MarkerOptions().position(haifa).title("Haifa").icon(null)).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(haifa,16));
        // Add a marker in Sydney and move the camera


    }
}