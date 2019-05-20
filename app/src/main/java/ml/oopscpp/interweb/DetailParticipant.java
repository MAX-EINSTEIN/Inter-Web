package ml.oopscpp.interweb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Telephony;
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
}
