package com.smartworker.smartworker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

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
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.smartworker.smartworker.login.RegisterWorker;

import java.util.List;
import java.util.Locale;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class MyWorkerLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    Double latitude, longitude;
    private GoogleMap mMap;
    private Button mSaveLocation;
    String location;
    private FloatingActionButton mMyLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_worker_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mSaveLocation = findViewById(R.id.saveLocation);
        mMyLocationButton = findViewById(R.id.myLocationButton);

        getMyLocation();
        mMyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyLocation();
            }
        });

        mSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkerLocationActivity.this, RegisterWorker.class);
                Toast.makeText(MyWorkerLocationActivity.this, "mSaveLocation" + latitude + longitude, Toast.LENGTH_SHORT).show();
                Bundle addBundle = new Bundle();
                addBundle.putDouble("latitude", latitude);
                addBundle.putDouble("longitude", longitude);
                addBundle.putString("location",location);
                intent.putExtras(addBundle);
                intent.putExtra(AppConstants.latitude, latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("location", location);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                mMap.clear();
//                createMarker(latLng.latitude, latLng.longitude, "markar", "", R.drawable.ic_map_customer);
//                latitude=latLng.latitude;
//                longitude=latLng.longitude;
//                Toast.makeText(MyWorkerLocationActivity.this, ""+latitude+longitude, Toast.LENGTH_SHORT).show();
//                getAddress(MyWorkerLocationActivity.this,latLng.latitude,latLng.longitude);
//
//
//            }
//        });


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
        getMyLocation();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                createMarker(latLng.latitude, latLng.longitude, "markar", "", R.drawable.ic_map_customer);
                latitude = latLng.latitude;
                longitude = latLng.longitude;

                getAddress(MyWorkerLocationActivity.this, latLng.latitude, latLng.longitude);


            }
        });

    }


    private void getMyLocation() {

        Dexter.withActivity(MyWorkerLocationActivity.this).withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

                if (report.areAllPermissionsGranted()) {
                    SmartLocation.with(MyWorkerLocationActivity.this).location().oneFix().start(new OnLocationUpdatedListener() {
                        @Override
                        public void onLocationUpdated(Location location) {

                            createMarker(location.getLatitude(), location.getLongitude(), "My location", "", R.drawable.ic_map_customer);
                            //23.592676, 45.467175

                            LatLng start = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 15));
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            getAddress(MyWorkerLocationActivity.this, latitude, longitude);


                        }
                    });
                }

                if (report.isAnyPermissionPermanentlyDenied()) {
                    Toast.makeText(MyWorkerLocationActivity.this, "You can't show location without permission  ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();

            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                // Toast.makeText(MyWorkerLocationActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();

            }
        }).onSameThread().check();


    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {


        return mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).anchor(0.5f, 0.5f).title(title).snippet(snippet).icon(BitmapDescriptorFactory.fromResource(iconResID)));


    }

    public String getAddress(Context context, double lat, double lng) {
        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);

            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            add = add + "," + obj.getAdminArea();
//            add = add + "\n" + obj.getSubAdminArea();
            add = add + "," + obj.getLocality();
            Log.e("tag", "add" + add);
            if (obj.getLocality().isEmpty()) {
                location = obj.getCountryName();
//
            } else {
                location = obj.getCountryName() + "," + obj.getLocality();

            }
//
//            catch

            return add;
        } catch (Exception e) {
            //  e.printStackTrace();
            //Toast.makeText(this,"no Address", Toast.LENGTH_SHORT).show();
            return "no Address";
        }
    }
}
