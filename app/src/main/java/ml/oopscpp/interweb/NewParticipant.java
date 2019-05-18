package ml.oopscpp.interweb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class NewParticipant extends AppCompatActivity {

    ImageView image;
    RelativeLayout imageClickArea;

    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_participant);

        image = findViewById(R.id.newParticipantImage);
        imageClickArea = findViewById(R.id.imageClickArea);
        imageClickArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            image.setImageURI(imageUri);
            imageClickArea.setBackgroundColor(getResources().getColor(R.color.plain_white));
            Toast.makeText(this, imageUri.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}
