package ml.oopscpp.interweb;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Objects;

public class GalleryFragment extends Fragment {

    private ImageAdapter adapter;
    private ArrayList<String> arrayOfImageUrls;
    private ListView imageList;

    private Uri imageUri;
    private String imageUrl;

    private static final int PICK_IMAGE = 1;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set Toolbar's title
        getActivity().setTitle("Gallery");

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.fab.setVisibility(View.VISIBLE);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        arrayOfImageUrls = new ArrayList<>();
        imageList = rootView.findViewById(R.id.imageList);

        FirebaseAuth auth = FirebaseAuth.getInstance();


        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String image = dataSnapshot.getValue(String.class);
                if(image != null)
                    arrayOfImageUrls.add(image);
                //adapter.add(new Image Url);
                if(getContext()!=null && arrayOfImageUrls != null)
                adapter = new ImageAdapter(getContext(), arrayOfImageUrls);
                // Attach the adapter to a ListView
                imageList.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        if(auth != null){
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            DatabaseReference mImagesDatabase = mDatabase.child("users").child(auth.getUid()).child("images");
            mImagesDatabase.addChildEventListener(childEventListener);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do what you want
                openGallery();
                Toast.makeText(getContext(), "New Photos", Toast.LENGTH_SHORT).show();
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
            imageUri = data.getData();
            // Uploading selected image to Firebase Storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageRef = storage.getReference();
            Log.e("Gallery","images/"+imageUri.getLastPathSegment());
            final StorageReference imageRef = storageRef.child("images/"+imageUri.getLastPathSegment());
            UploadTask uploadTask = imageRef.putFile(imageUri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(getContext(),"Sorry! Image can't be uploaded",Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
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
                        Toast.makeText(getContext(),"Image Url received",Toast.LENGTH_SHORT).show();
                        imageUrl = Objects.requireNonNull(downloadUri).toString();
                        Log.e("Gallery",imageUrl);
                        addToFirebaseRealtimeDatabase(imageUrl);
                    }  // Handle failures
                    // ...

                }
            });

        }
    }

    private void  addToFirebaseRealtimeDatabase(String url){

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if(auth != null){
            // Writing Image's Url to Firebase Realtime Database
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            DatabaseReference mImagesDatabase = mDatabase.child("users").child(auth.getUid()).child("images");
            mImagesDatabase.push().setValue(url);
        }

    }
}