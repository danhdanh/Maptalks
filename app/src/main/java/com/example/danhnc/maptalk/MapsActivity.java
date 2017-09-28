package com.example.danhnc.maptalk;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;




public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Fragment frag;
    private GoogleMap mMap;
    ImageView imgSearch;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    Button taoFragment;
    String x = "Chua co toa do" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        //set sư kiện click cho button search
        iniView();
        //set sự kiện click cho button tao fragment (tao fragment cho moi vi tri khac nhau)
        taoFragment= findViewById(R.id.btntaofragment);
        taoFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag = new PositionFragment();
                //Bundle bundle= new Bundle();
                //bundle.putString("toa do ", x);
                //frag.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.flPosition, frag);
                transaction.commit();

//                callFragment(new PositionFragment());
//                Toast.makeText(getApplicationContext(),"minh suc vat" ,Toast.LENGTH_LONG).show();
            }
        });

    }




    private void iniView(){
        imgSearch= findViewById(R.id.imgsearch);
        imgSearch.setOnClickListener(Click_Search);
    }

    View.OnClickListener Click_Search = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                        .build(MapsActivity.this);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
//                callFragment(new PositionFragment());
            } catch (GooglePlayServicesRepairableException e) {
                Log.d("aaa1",e.toString());
            } catch (GooglePlayServicesNotAvailableException e) {
                Log.d("aaa",e.toString());
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                Place place = PlaceAutocomplete.getPlace(this, data);
                Toast.makeText(this,place.getName(),Toast.LENGTH_LONG).show();
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),17));


            }
            else if (requestCode == PlaceAutocomplete.RESULT_ERROR ){
                Status status = PlaceAutocomplete.getStatus(this,data);
            }
            else if (requestCode == RESULT_CANCELED){
                Toast.makeText(this, "cancel" ,Toast.LENGTH_LONG);
            }
        }
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
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        if (ContextCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng).title("ahiahiahi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));
                changeOffsetCenter(latLng.latitude,latLng.longitude);
                Toast.makeText(MapsActivity.this, latLng.toString(), Toast.LENGTH_SHORT).show();

                frag = new PositionFragment();
                FragmentManager fragmentmanager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentmanager.beginTransaction();
                transaction.replace(R.id.flPosition,frag);
                transaction.commit();
            }
        });
    }

    public void changeOffsetCenter(double latitude, double longtitude){
        Point mapPoint = mMap.getProjection().toScreenLocation(new LatLng(latitude,longtitude));
        mapPoint.set(mapPoint.x-30,mapPoint.y+550);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(mMap.getProjection().fromScreenLocation(mapPoint)));
    }
}
