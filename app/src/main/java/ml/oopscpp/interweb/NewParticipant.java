package ml.oopscpp.interweb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class NewParticipant extends AppCompatActivity {

    RoundedImageView image;
    RelativeLayout imageClickArea;
    Button submit;
    private Uri imageUri;
    private String imageUrl;

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

        submit = findViewById(R.id.submitParticipantData);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Participant a_participant = createParticipant();

                uploadToDatabase(a_participant);

                Log.e("UNIQUE","Uploaded to Database");

                if(getCallingActivity()!=null){
                    //create a new intent...
                    Intent intent = new Intent();
                    intent.putExtra("participantName",a_participant.getParticipantName());
                    intent.putExtra("participantContact",a_participant.getParticipantContact());
                    setResult(RESULT_OK,intent);
                    Log.e("UNIQUE","Exiting Activity for result");
                    finish();
                }else {
                    // Returning Back to Main Activity
                    Log.e("UNIQUE","To Main");
                    Intent back = new Intent(NewParticipant.this,MainActivity.class);
                    startActivity(back);
                    finish();
                }

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
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {

            imageUri = data.getData();
            image.setImageURI(imageUri);
            imageClickArea.setBackgroundColor(getResources().getColor(R.color.plain_white));

            Log.e("NewParticipant","Here");

            // Uploading selected image to Firebase Storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageRef = storage.getReference();
            Log.e("NewParticipant","participant_images/"+imageUri.getLastPathSegment());
            final StorageReference imageRef = storageRef.child("participant_images/"+imageUri.getLastPathSegment());
            UploadTask uploadTask = imageRef.putFile(imageUri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(getApplicationContext(),"Sorry! Image can't be uploaded",Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Toast.makeText(getApplicationContext(),"Image uploaded",Toast.LENGTH_SHORT).show();
                }
            });

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }

                    // Continue with the task to get the download URL
                    return imageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Toast.makeText(getApplicationContext(),"Image Url received",Toast.LENGTH_SHORT).show();
                        imageUrl = Objects.requireNonNull(downloadUri).toString();
                        Log.e("NewParticipant",imageUrl);
                    }  // Handle failures
                    // ...
                }
            });

        }
    }

    private Participant createParticipant(){
        String name = ((TextView)findViewById(R.id.newParticipantName)).getText().toString();
        String contact = ((TextView)findViewById(R.id.newParticipantContact)).getText().toString();
        String affiliation = ((TextView)findViewById(R.id.newParticipantAffiliation)).getText().toString();
        String age = ((TextView)findViewById(R.id.newParticipantAge)).getText().toString();
        String about = ((TextView)findViewById(R.id.newParticipantAbout)).getText().toString();

        if(imageUrl==null){
            imageUrl = "https://www.gstatic.com/mobilesdk/160503_mobilesdk/logo/2x/firebase_28dp.png";
        }

        Participant newParticipant = new Participant(imageUrl,name,contact,affiliation,age,about);

        return newParticipant;
    }

    private void uploadToDatabase(Participant participant){
        // Writing to Firebase Realtime Database
        String eventId = "participant" + participant.getParticipantContact();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("participants").child(eventId).setValue(participant);
    }
}
