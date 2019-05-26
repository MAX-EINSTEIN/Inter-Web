package ml.oopscpp.interweb;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Objects;



public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "GoogleMapsFragment";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private static final float DEFAULT_MAP_ZOOM = 16f;

    private static boolean mLocationAccessGranted = false;

    private GoogleMap mMap;

    public GoogleMapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity == null) {
            Log.e(TAG, "onCreateView: Main Activity can't be accesed");
        }
        else{
            mainActivity.fab.setVisibility(View.INVISIBLE);
        }

        assert mainActivity != null;
        mainActivity.setTitle("Venue");

        View rootView = inflater.inflate(R.layout.fragment_google_maps, container, false);

        final ImageButton button = rootView.findViewById(R.id.toggleButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getDrawer().openDrawer(GravityCompat.START);
            }
        });

        getLocationPermission();
        Log.e(TAG, "onCreateView: I am being created");
        return rootView;
    }

    private void initMap(){
        Log.e(TAG, "initMap: Here I am");
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentByTag("mapFragment");
        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.mapFragmentContainer, mapFragment, "mapFragment");
            ft.commit();
            fm.executePendingTransactions();
        }
        mapFragment.getMapAsync(this);
    }

    private void getLocationPermission(){
        String[] permissions = {FINE_LOCATION};
        if(ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()).getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mLocationAccessGranted = true;
            initMap();
        }
        else{
            requestPermissions(permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationAccessGranted = false;
        Log.e(TAG, "onRequestPermissionsResult: Here");
        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mLocationAccessGranted = true;
                initMap();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(mLocationAccessGranted){
            getDeviceLocation();
            if(ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) return;
            mMap.setMyLocationEnabled(true);
        }
    }

    private  void getDeviceLocation(){
        Log.e(TAG, "getDeviceLocation: Getting device's current location");

        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getContext()));
        try {
            final Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Log.e(TAG, "onComplete: Location Found successfully");
                        Location currentLocation = (Location) task.getResult();
                        if(currentLocation != null)
                            moveCameraTo(currentLocation);
                        else{
                            Toast.makeText(getContext(), "Turn on Location Services", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Log.e(TAG, "onComplete: Couldn't get devices location");
                        Toast.makeText(getContext(), "There was an error getting devices location", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: Security exception" + e.getMessage());
            Toast.makeText(getContext(), "Couldn't get current Location of the device", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveCameraTo(Location location){
        Toast.makeText(getContext(), "moving camera to current Location", Toast.LENGTH_SHORT).show();
        LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,DEFAULT_MAP_ZOOM));
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
    }

}
