package ml.oopscpp.interweb;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import java.util.Objects;


public class FirebaseQueryLiveData extends LiveData<DataSnapshot>{
    private static final String LOG_TAG = "FirebaseQueryLiveData";

    private final Query query;
    private final MyChildEventListener childEventListener = new MyChildEventListener();

//    public FirebaseQueryLiveData(Query query) {
//        this.query = query;
//    }

    FirebaseQueryLiveData(DatabaseReference ref) {
        this.query = ref;
    }

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");
        query.addChildEventListener(childEventListener);
    }

    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive");
        query.removeEventListener(childEventListener);
    }

    private  class  MyChildEventListener implements ChildEventListener{

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e(LOG_TAG, Objects.requireNonNull(dataSnapshot.getValue(Event.class)).toString());
                setValue(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                setValue(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                setValue(dataSnapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                setValue(dataSnapshot);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

    }
}
