package ml.oopscpp.interweb;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.provider.Settings;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static int ERROR_DIALOG_REQUEST = 9001;

    public FloatingActionButton fab;
    private static boolean RUN_ONCE = true;

    public DrawerLayout getDrawer() {
        return drawer;
    }

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);
        RoundedImageView userImage = headerLayout.findViewById(R.id.userImage);
        TextView userName = headerLayout.findViewById(R.id.userName);
        TextView userMail = headerLayout.findViewById(R.id.userMail);
        ImageButton logoutButton = headerLayout.findViewById(R.id.logout_button);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if(user.getPhotoUrl()!=null)
               Glide.with(userImage).load(user.getPhotoUrl()).into(userImage);
            userName.setText(user.getDisplayName());
            userMail.setText(user.getEmail());
        } else {
            Toast.makeText(getApplicationContext(), "Login to backup and sync your data", Toast.LENGTH_SHORT).show();
        }



        logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Logging out...",Toast.LENGTH_SHORT).show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        AuthUI.getInstance()
                                .signOut(getApplicationContext())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        ((ActivityManager)getSystemService(ACTIVITY_SERVICE))
                                                .clearApplicationUserData();
                                        finish();
                                    }
                                });
                    }
                }, 1500);

            }
        });

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.fragment,new EventFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_event);

            if(RUN_ONCE)
            {
                checkFirstRun();
                RUN_ONCE = false;
            }

            if(!haveNetworkConnection()){
                showDialog();
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        for (Fragment fragment:getSupportFragmentManager().getFragments())
            if(fragment!=null) getSupportFragmentManager().beginTransaction().remove(fragment).commit();

        if (id == R.id.nav_event) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment,new EventFragment()).commit();
        } else if (id == R.id.nav_gallery) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment,new GalleryFragment()).commit();
        } else if (id == R.id.nav_participants) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment,new ParticipantFragment()).commit();
        } else if (id == R.id.nav_hall_of_fame) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment,new HallOfFameFragment()).commit();
        }else if( id == R.id.nav_venue){
            if (isServicesOK()){
                init();
            }
        }
        else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Connect to wifi (or turn on cellular data) or quit")
                .setCancelable(false)
                .setPositiveButton("Connect to WIFI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        Toast.makeText(getBaseContext(), "Wait till wifi is connected,\nthen press back button",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("Stay offline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "App not connected to network",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Quit Application", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void checkFirstRun() {

        String lastUserId = "";

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth!=null){
            lastUserId = auth.getUid();
        }

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_USER_ID_KEY = "user_id";
        final String DOES_NOT_EXIST = "no_id";

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedUserId = prefs.getString(PREF_USER_ID_KEY,DOES_NOT_EXIST);

        // Check for first run or upgrade
        if (lastUserId.equals(savedUserId)) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            FirebaseDatabase.getInstance().getReference().keepSynced(true);
            return;

        } else if (savedUserId.equals(DOES_NOT_EXIST)) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            FirebaseDatabase.getInstance().getReference().keepSynced(true);
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        }

        // Update the shared preferences with the current version code
        prefs.edit().putString(PREF_USER_ID_KEY, lastUserId).apply();
    }



    private void init(){
        for (Fragment fragment:getSupportFragmentManager().getFragments())
            if(fragment!=null) getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment,new GoogleMapsFragment()).commit();
    }

    public boolean isServicesOK(){
        Log.e(TAG,"Checking if service required for google maps are available");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            Log.e(TAG,"Everything is Ok");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.e(TAG, "isServicesOK: Follow these steps to enable the services");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(MainActivity.this, "You can't use google maps", Toast.LENGTH_SHORT).show();
        }

        return false;
    }


}





