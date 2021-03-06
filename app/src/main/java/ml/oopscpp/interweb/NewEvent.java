package ml.oopscpp.interweb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Objects;

public class NewEvent extends AppCompatActivity {

    private ImageView image;
    private static final int PICK_IMAGE = 1;
    private static final int ADD_PARTICIPANT = 2;
    private static final  int SELECT_PARTICIPANT = 3;
    private Uri imageUri;
    private String imageUrl;

    private ArrayList<String> participants;
    private ArrayList<String> contacts;

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

        Button button = findViewById(R.id.submitEventData);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEventData();
            }
        });

        participants = new ArrayList<>();
        contacts = new ArrayList<>();

        Button addParticipantButton = findViewById(R.id.addParticipantButton);

        addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addParticipantsToLists();
            }
        });

        Button newParticipantButton = findViewById((R.id.newParticipantButton));
        newParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("UNIQUE","Starting Activity for result");
                Intent newParticipantActivity = new Intent(NewEvent.this,NewParticipant.class);
                startActivityForResult(newParticipantActivity,ADD_PARTICIPANT);
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

            // Uploading selected image to Firebase Storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageRef = storage.getReference();
            Log.e("NewEvent","event_images/"+imageUri.getLastPathSegment());
            final StorageReference imageRef = storageRef.child("event_images/"+imageUri.getLastPathSegment());
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
                        Log.e("NewEvent",imageUrl);
                    }  // Handle failures
                    // ...

                }
            });
        }
        else if(resultCode == RESULT_OK && requestCode == ADD_PARTICIPANT){
            addParticipant(data.getStringExtra("participantName"),data.getStringExtra("participantContact"));
        }
        else if(resultCode == RESULT_OK && requestCode == SELECT_PARTICIPANT){
            Log.e("NewEvent","Here in onActivityResult");
            addParticipant(data.getStringExtra("name"),data.getStringExtra("contact"));
        }
    }

    public void addParticipant(String name,String contact){
        participants.add(name);
        contacts.add(contact);
        Toast.makeText(getApplicationContext(),name+contact,Toast.LENGTH_SHORT).show();
    }

    private void addParticipantsToLists(){
        Intent getResult = new Intent(NewEvent.this,SelectParticipant.class);
        startActivityForResult(getResult,SELECT_PARTICIPANT);
        Log.e("NewEvent","Here in addParticipants to List");
    }

    private void submitEventData(){
            TextView title = findViewById(R.id.newEventTitle);
            TextView date = findViewById(R.id.newEventDate);
            TextView venue = findViewById(R.id.newEventVenue);

            if(participants == null){
                participants.add("A");
                contacts.add("10");
            }

            String eventId;

            if(imageUrl == null){
                imageUrl = "https://www.gstatic.com/mobilesdk/160503_mobilesdk/logo/2x/firebase_28dp.png";
                eventId = "event" + "00000000000000000000000000000";
            }else {
                eventId = "event" + imageUri.hashCode();
            }

            Event newEvent = new Event(imageUrl,title.getText().toString(),date.getText().toString(),venue.getText().toString(),participants, contacts);

            FirebaseAuth auth = FirebaseAuth.getInstance();

            if(auth!=null) {
                // Writing Event to Firebase Realtime Database
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                DatabaseReference mEventsDatabase = mDatabase.child("users").child(auth.getUid()).child("events");
                mEventsDatabase.child(eventId).setValue(newEvent);

                // Writing Image to Firebase Realtime Database
                mDatabase.child("images").push().setValue(imageUrl);
                DatabaseReference mImagesDatabase = mDatabase.child("users").child(auth.getUid()).child("images");
                mImagesDatabase.push().setValue(imageUrl);
            }

//            // Showing notification to user
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Inter Web")
//                    .setSmallIcon(R.drawable.ic_announcement)
//                    .setContentTitle("New Event")
//                    .setContentText("")
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            // Returning Back to Main Activity
            Intent backToMain = new Intent(NewEvent.this,MainActivity.class);
            startActivity(backToMain);
    }

}
