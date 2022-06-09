package com.example.babysitter;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.babysitter.Classes.Date;
import com.example.babysitter.Classes.Employee;
import com.example.babysitter.Classes.ProfilePhoto;
import com.example.babysitter.Classes.SetImageViewFromUrl;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.babysitter.databinding.ActivityMapsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    // for menu
    BottomNavigationView navigationView;
    TextView title;
    Employee[] allLocationsForEmp;
    Context context = this;

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
        //to load all deals from the database
        new History();


        // menu code
        navigationView = findViewById(R.id.nav);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.homeIcon:
                        if (login.dbClass.getCurrentUser().getRole().equals("2"))
                            title.setText("Employees around you");
                        else if (login.dbClass.getCurrentUser().getRole().equals("1"))
                            title.setText("Parents around you");
                        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++)
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
        getAllLocationsForEmp();



        mMap.getUiSettings().setAllGesturesEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "requesting location", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
            return;
        } else {
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        // Add a marker in Sydney and move the camera
        //getting all locations for the employees and show it on the map

        mMap.setOnMarkerClickListener(this);
    }

    private void showAllEmployeesOnMap() {
        StringRequest request = new StringRequest(Request.Method.POST, login.dbClass.getUrl() + "?action=getAllProfilePhotos", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray allUsersPhotos = new JSONArray(response);
                    ProfilePhoto[] profilePhoto = new ProfilePhoto[allUsersPhotos.length()];
                    for (int i=0;i<allUsersPhotos.length();i++) {
                        JSONObject userPhoto = allUsersPhotos.getJSONObject(i);
                        profilePhoto[i] = new ProfilePhoto(userPhoto.getString("id"), userPhoto.getString("profile_image_path"));
                    }


                    //adding marker on the map for all locations from db
                    for (int i=0; i<allLocationsForEmp.length;i++) {
                        double[] cordinates = new double[2];
                        try {
                            cordinates = getLocationFromAddress(allLocationsForEmp[i].getAddress().getCity());
                        } catch (IOException e) {
                        }
                        LatLng location = new LatLng(cordinates[0], cordinates[1]);

                        for (int j = 0; j < profilePhoto.length; j++) {
                            if (allLocationsForEmp[i].getId().equals(profilePhoto[j].getUserId())) {
                                allLocationsForEmp[i].setProfilePhoto(profilePhoto[j]);
                                new GetImageFromUrl(location, allLocationsForEmp[i]).execute(profilePhoto[j].getImageUrl());
                            }
                        }

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
                    }

                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error" + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    public double[] getLocationFromAddress(String strAddress) throws IOException {
        double[] point = new double[2]; //index 0 is latitude, 1 is longitude
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            point[0] = location.getLatitude();
            point[1] = location.getLongitude();

        } catch (Exception e) {
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return point;
    }

    public void getAllLocationsForEmp(){
        StringRequest request = new StringRequest(Request.Method.POST, login.dbClass.getUrl() + "?action=getAllLocationsForEmp", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray allEmployees = new JSONArray(response);
                    allLocationsForEmp = new Employee[allEmployees.length()];
                    for (int i=0; i<allEmployees.length();i++){
                        JSONObject emp = allEmployees.getJSONObject(i);

                        String[] dateOfSubAsString = emp.getString("birthdate").split("-");
                        Date dateOfbirth = new Date(dateOfSubAsString[2], dateOfSubAsString[1], dateOfSubAsString[0]);
                        com.example.babysitter.Classes.Address address = new com.example.babysitter.Classes.Address(emp.getString("city"),emp.getString("street"),emp.getString("house_number"));

                        allLocationsForEmp[i] = new Employee(emp.getString("id"),emp.getString("first_name"), emp.getString("last_name"), emp.getString("phone_number"), dateOfbirth, emp.getString("password"), emp.getString("email"), emp.getString("role"), emp.getString("status"), emp.getString("rate"), emp.getString("special_demands"), emp.getString("working_hours_in_month"), emp.getString("experience"), address);
                    }
                    showAllEmployeesOnMap();

                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error" + e.toString(), Toast.LENGTH_LONG).show();
                }


            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override

            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Employee emp = (Employee) marker.getTag();
        //Toast.makeText(context, ""+((Employee)marker.getTag()).toString(), Toast.LENGTH_SHORT).show();
        TextView txtclose, fullName, city, rating, workedHours, dealsMade;
        ImageView profilePhoto;
        Button btnRequest;
        Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.map_user_popup);

        txtclose = myDialog.findViewById(R.id.txtclose);
        fullName = myDialog.findViewById(R.id.fullName);
        city = myDialog.findViewById(R.id.city);
        btnRequest = myDialog.findViewById(R.id.btnRequest);
        profilePhoto = myDialog.findViewById(R.id.profilePhoto);
        rating = myDialog.findViewById(R.id.rating);
        workedHours = myDialog.findViewById(R.id.workedHours);
        dealsMade = myDialog.findViewById(R.id.dealsMade);

        fullName.setText(emp.getFirstName() + " " + emp.getLastName());
        city.setText(emp.getAddress().getCity());
        rating.setText(emp.getRate());
        workedHours.setText(emp.getWorkingHoursInMonth());

        dealsMade.setText(""+History.allDeals.size());


        new SetImageViewFromUrl(profilePhoto).execute(emp.getProfilePhoto().getImageUrl());


        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        return true;
    }



    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
        LatLng location;
        Bitmap bitmap;
        Employee currentEmployee;
        public GetImageFromUrl(LatLng location, Employee currentEmployee){
            this.location = location;
            this.currentEmployee = currentEmployee;
        }
        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            bitmap = null;
            InputStream inputStream;
            try {
                inputStream = new java.net.URL(stringUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
                Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, 170, 170, false);

                Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(currentEmployee.getFirstName() + " " + currentEmployee.getLastName()).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                marker.showInfoWindow();
                marker.setTag(currentEmployee);


        }
    }

}