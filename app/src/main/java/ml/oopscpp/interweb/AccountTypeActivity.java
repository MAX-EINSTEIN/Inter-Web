package ml.oopscpp.interweb;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class AccountTypeActivity extends AppCompatActivity {

    private static final String TAG = "AccountTypeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_type);
        findViewById(R.id.paid_individual_plan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountTypeActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

