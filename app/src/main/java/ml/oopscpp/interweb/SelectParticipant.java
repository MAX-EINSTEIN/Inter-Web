package ml.oopscpp.interweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SelectParticipant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_participant);

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer,new ParticipantFragment()).commit();
    }

    public void returnResult(String name,String contact,String image){
        if(getCallingActivity()!=null){
            Intent data = new Intent();
            data.putExtra("name",name);
            data.putExtra("contact",contact);
            data.putExtra("image",image);
            setResult(RESULT_OK,data);
            finish();
        }
    }
}
