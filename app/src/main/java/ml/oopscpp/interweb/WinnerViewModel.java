package ml.oopscpp.interweb;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class WinnerViewModel extends ViewModel {

    private static  final FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final DatabaseReference DATABASE_ROOT =
            FirebaseDatabase.getInstance().getReference();
    private static final Query WINNER_DATA_QUERY = DATABASE_ROOT
            .child("users").child(auth.getUid()).child("winners");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(WINNER_DATA_QUERY);

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }

}
