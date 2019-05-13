package ml.oopscpp.interweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewEvent extends AppCompatActivity {

    private ImageView image;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        image = findViewById(R.id.newEventImage);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        button = findViewById(R.id.btnSubmit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEventData();
            }
        });
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }

    private void submitEventData(){
            TextView title = findViewById(R.id.newEventTitle);
            TextView date = findViewById(R.id.newEventDate);
            TextView venue = findViewById(R.id.newEventVenue);
            Event newEvent = new Event(imageUri,title.getText().toString(),date.getText().toString(),venue.getText().toString());
            Intent addToList = new Intent(NewEvent.this,MainActivity.class);
            addToList.putExtra("newEvent",newEvent);
            addToList.putExtra("uniqueId","NewEvent");
            startActivity(addToList);
    }
}
