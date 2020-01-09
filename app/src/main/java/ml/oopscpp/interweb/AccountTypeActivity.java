package ml.oopscpp.interweb;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class AccountTypeActivity extends AppCompatActivity {

    private static final String TAG = "AccountTypeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!checkFirstRun()){
            startActivity(new Intent(AccountTypeActivity.this,Login.class));
            finish();
        }else{
            setContentView(R.layout.activity_company_info);

            UUID uuid = UUID.randomUUID();
            String id = uuid.toString();

            Log.e(TAG, "onCreate: unique id" + id);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(activate());
                }
            }, 4000);

            ImageView companyIcon = findViewById(R.id.companyIcon);
            if(companyIcon != null){
                companyIcon.animate().rotation(360).setDuration(3500);
            }

        }
    }

    Runnable activate(){
        return new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_account_type);
                findViewById(R.id.paid_individual_plan).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(AccountTypeActivity.this,Login.class));
                        finish();
                    }
                });
            }
        };
    }

    private boolean checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "1.0.0";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {
            return false;

        } else if (savedVersionCode == DOESNT_EXIST) {
            Log.e(TAG, "checkFirstRun: Here in Does nto exist");
            // Update the shared preferences with the current version code
            prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).commit();
            // This is just a normal run
            return true;
            // TODO This is a new install (or the user cleared the shared preferences)

        } else if (currentVersionCode > savedVersionCode) {
            return false;
            // TODO This is an upgrade
        }

        return  false;
    }

}

