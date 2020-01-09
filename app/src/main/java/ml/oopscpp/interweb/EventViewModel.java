package ml.oopscpp.interweb;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventViewModel extends ViewModel {
    private static  final FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final DatabaseReference EVENT_DATA_REF =
            FirebaseDatabase.getInstance().getReference();

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(EVENT_DATA_REF.child("users").child(auth.getUid()).child("events"));

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }
}

