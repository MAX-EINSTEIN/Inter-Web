package ml.oopscpp.interweb;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "GoogleMapsFragment";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private static boolean mLocationAccessGranted = false;

    SupportMapFragment mapFragment;
    private GoogleMap mMap;

    public GoogleMapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.fab.setVisibility(View.INVISIBLE);

        mainActivity.setTitle("Venue");

        View rootView = inflater.inflate(R.layout.fragment_google_maps, container, false);

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
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mLocationAccessGranted = true;
            initMap();
        }
        else{
            ActivityCompat.requestPermissions(getActivity(),permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationAccessGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mLocationAccessGranted = true;
                    initMap();
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

}
