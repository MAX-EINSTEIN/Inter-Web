package ml.oopscpp.interweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddWinner extends AppCompatActivity {

    private boolean winnerChosen = false;
    private Winners winner;

    private static int SELECT_PARTICIPANT = 55555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_winner);

        winner = new Winners();

        Button chooseWinnerButton = findViewById(R.id.choose_winner_button);
        Button announceWinnerButton = findViewById(R.id.announce_winner_button);

        chooseWinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(AddWinner.this,SelectParticipant.class);
                startActivityForResult(data,SELECT_PARTICIPANT);
            }
        });

        announceWinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(winnerChosen == true){
                    Intent intent = getIntent();
                    if(intent!=null){
                        winner.setEventName(intent.getStringExtra("eventName"));
                        addToFirebaseRealtimeDatabase(winner);
                        Toast.makeText(AddWinner.this, "Winner Announced", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else {
                    Toast.makeText(AddWinner.this, "Please choose a winner first", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==SELECT_PARTICIPANT){
            winner.setName(data.getStringExtra("name"));
            winner.setImage(data.getStringExtra("image"));
            winnerChosen = true;
            Toast.makeText(this, "Winner Chosen", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToFirebaseRealtimeDatabase(Winners winner){
        // Writing to Firebase Realtime Database
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if(auth!=null)
        {
            String winnerId = winner.getName()+"_"+winner.getEventName();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            DatabaseReference mWinnersDatabase = mDatabase.child("users").child(auth.getUid()).child("winners");
            mWinnersDatabase.child(winnerId).setValue(winner);
        }

    }

}
