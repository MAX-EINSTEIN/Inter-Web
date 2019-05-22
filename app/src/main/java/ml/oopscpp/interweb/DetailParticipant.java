package ml.oopscpp.interweb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Telephony;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailParticipant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_participant);

        Participant participant = getIntent().getParcelableExtra("details");

        ImageView image =  findViewById(R.id.p_pic);
        Glide.with(image).load(participant.getParticipantImage()).into(image);
        ((TextView) findViewById(R.id.p_name)).setText(participant.getParticipantName());
        ((TextView) findViewById(R.id.p_contact)).setText(participant.getParticipantContact());
        ((TextView) findViewById(R.id.p_affiliation)).setText(participant.getParticipantAffiliation());
        ((TextView) findViewById(R.id.p_age)).setText(participant.getParticipantAge());
        ((TextView) findViewById(R.id.p_about)).setText(participant.getParticipantAbout());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_edit).setVisible(true);
        return true;
    }
}
