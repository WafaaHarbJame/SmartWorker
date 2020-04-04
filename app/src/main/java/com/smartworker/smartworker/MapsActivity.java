package com.smartworker.smartworker;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.smartworker.smartworker.db.DbOperation_Jops;
import com.smartworker.smartworker.db.DbOperation_Users;
import com.smartworker.smartworker.login.Jop;
import com.smartworker.smartworker.login.RegisterWorker;
import com.smartworker.smartworker.login.User;
import com.smartworker.smartworker.orders.OptionOrder;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    FloatingActionButton myLocationButton;
    SupportMapFragment mapFragment;
    TextView jop_name;
    LinearLayout worker;
    ImageView image_worker;
    Button btn_confirm, btn_back;
    DbOperation_Users db_user;
    DbOperation_Jops db_jop;
    DbOperation_Jops db_jops;
    double lat, lng;
    int user_id, job_id;
    String job_name;
    List<User> workers_list = new ArrayList<>();
    String name;
    //    ArrayList<MarkerData> markersArray;
    ArrayList<Marker> markers;
    int workerId;
    int city_id = 1;
    String city_name;
    List<String> city_list = new ArrayList<>();
    List<Cities> citiesList = new ArrayList<>();
    int position;
    private GoogleMap mMap;
    private Spinner mCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        myLocationButton = findViewById(R.id.myLocationButton);
        btn_confirm = (Button) findViewById(R.id.confirm);
        btn_confirm.setVisibility(View.INVISIBLE);
        db_user = new DbOperation_Users(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user_id = bundle.getInt("user_id");
            job_id = bundle.getInt("job_id");
            job_name = bundle.getString("jop_name");
        }

        System.out.println("Log jobId " + job_id);
        Toast.makeText(MapsActivity.this, "job_id" + job_id, Toast.LENGTH_LONG).show();

//        Log.e("wgetMEMBER_SHIP", "getMEMBER_SHIP" + "\n " + db_user.getMember(user_id));
//        User user = new User();
//        user = db_user.getUser_Info(user_id);

        mCity = findViewById(R.id.city);

        city_list.clear();

        db_jops = new DbOperation_Jops(this);
//        markersArray = new ArrayList<MarkerData>();
        markers = new ArrayList<Marker>();

        city_list.clear();
        citiesList = db_jops.getALLCities();
        for (int i = 0; i < citiesList.size(); i++) {
            int id = citiesList.get(i).getId();
            String name = citiesList.get(i).getName();
            Double LAT = citiesList.get(i).getLatitude();
            Double LNG = citiesList.get(i).getLongitude();
            city_list.add(name);


        }


        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, city_list);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityAdapter.notifyDataSetChanged();
        mCity.setAdapter(cityAdapter);

//        workers_list.clear();
        //  markersArray.clear();
//        workers_list = db_user.getAllUSERSBYJOBID(job_id, city_id);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), OptionOrder.class);
                in.putExtra("user_id", user_id);
                in.putExtra("worker_id", workerId);
                in.putExtra("state", 1);
                in.putExtra("job_name", job_name);
                in.putExtra("jop_id", job_id);
                Log.e("Sworker_id", "Sworker_id = " + workerId);

                startActivity(in);
            }
        });

        btn_back = (Button) findViewById(R.id.back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city_id = citiesList.get(position).getId();
                city_name = citiesList.get(position).getName();
                double lat = citiesList.get(position).getLatitude();
                double lng = citiesList.get(position).getLongitude();
                if (mMap != null) getWorkerByCityId(job_id, city_id, lat, lng);

                btn_confirm.setVisibility(View.GONE);
                workerId = 0;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void getWorkerByCityId(int jobId, int cityId, double lat, double lng) {

        List<User> workersList = db_user.getAllUSERSBYJOBID(jobId, cityId);

        if (!workersList.isEmpty()) {
            mMap.clear();
            markers.clear();
            workers_list = workersList;
            for (int i = 0; i < workers_list.size(); i++) {
                User worker = workers_list.get(i);
//                User user=new User(worker.getID(),worker.getFARST_NAME(),worker.getLAST_NAME(),worker.getPHONE_NUMBER(),worker.getPASSWORD(),worker.getMAP(),worker.getCity_name(),worker.getLatitude(),worker.getLongitude(),worker.getMEMBER_SHIP(),worker.getCity_id(),worker.getJOP_ID(),worker.getIMAGE());
                Log.e("workers_list", "data" + " --- " + worker.getFARST_NAME() + " --- " + worker.getLatitude() + " | " + worker.getLongitude() + " --- " + worker.getMEMBER_SHIP() + " --- " + worker.getCity_id());

                markers.add(createMarker(worker.getLatitude(), worker.getLongitude(), worker.getFARST_NAME(), job_name, R.drawable.ic_map_worker_active));


            }

            lat = workers_list.get(0).getLatitude();
            lng = workers_list.get(0).getLongitude();
//            LatLng start = new LatLng(workers_list.get(0).getLatitude(), workers_list.get(0).getLongitude());
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 10));
        } else {
            mMap.clear();
            Toast.makeText(this, getString(R.string.no_workers_in_this_city), Toast.LENGTH_LONG).show();
//            markers.add(createMarker(lat, lng, city_name,city_name, R.drawable.ic_map));

        }
        LatLng start = new LatLng(lat, lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 10));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        myLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getMyLocation();
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                LatLng latLng = marker.getPosition();
//                User user=new User();
//                user= (User) marker.getTag();
//                Double lat = latLng.latitude;
//                Double lng = latLng.longitude;

                int position = markers.indexOf(marker);
                User selectedWorker = workers_list.get(position);
                workerId = selectedWorker.getID();

                Toast.makeText(MapsActivity.this, "position " + " | " + selectedWorker.getID(), Toast.LENGTH_SHORT).show();
                btn_confirm.setVisibility(View.VISIBLE);

                return false;

            }
        });

        getWorkerByCityId(job_id, city_id, lat, lng);

        //getMyLocation();
    }


    private void getMyLocation() {

        Dexter.withActivity(MapsActivity.this).withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

                if (report.areAllPermissionsGranted()) {

                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();

        SmartLocation.with(MapsActivity.this).location().oneFix().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {

                createMarker(location.getLatitude(), location.getLongitude(), "my location", "", R.drawable.ic_map_customer);

                LatLng start = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 15));
            }
        });
    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {

        return mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).anchor(0.5f, 0.5f).title(title).snippet(snippet).icon(BitmapDescriptorFactory.fromResource(iconResID)));


    }


}


//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(21.419055, 39.826171);
//        LatLng sydney1 = new LatLng(21.419075, 39.826182);
//        LatLng sydney2 = new LatLng(21.419384, 39.828424);
//        LatLng sydney3 = new LatLng(21.419445, 39.828370);
//
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.addMarker(new MarkerOptions().position(sydney1).title("Marker in Sydney1"));
//
//
//        Marker m1 = mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(38.609556, -1.139637))
//                .anchor(0.5f, 0.5f)
//                .title("Title1")
//                .snippet("Snippet1")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_worker_active)));
//
//
//        Marker m2 = mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(40.4272414,-3.7020037))
//                .anchor(0.5f, 0.5f)
//                .title("Title2")
//                .snippet("Snippet2")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_customer)));
//
//        Marker m3 = mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(43.2568193,-2.9225534))
//                .anchor(0.5f, 0.5f)
//                .title("Title3")
//                .snippet("Snippet3")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_worker_blue)));
//


//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//        mMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
//            @Override
//            public void onMyLocationClick(@NonNull Location location) {
//                createMarker(location.getLatitude(), location.getLongitude(), "my location", "", R.drawable.ic_map_customer);
//
//                LatLng start = new LatLng(location.getLatitude(), location.getLongitude());
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(start));
//
//            }
//        });

//        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
//            @Override
//            public boolean onMyLocationButtonClick() {
//                return false;
//            }
//        });
