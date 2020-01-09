package ml.oopscpp.interweb;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HallOfFameFragment extends Fragment {

    private ListView winnersList;
    private WinnerAdapter adapter;
    private ArrayList<Winners> eventWinners;

    public HallOfFameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Hall of Fame");

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.fab.setVisibility(View.INVISIBLE);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hall_of_fame, container, false);

        eventWinners = new ArrayList<>();
        winnersList = rootView.findViewById(R.id.winnersList);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("Hall Of Fame",dataSnapshot.getValue(Winners.class).toString());
                Winners winner = dataSnapshot.getValue(Winners.class);
                if(winner != null)
                   eventWinners.add(winner);
                if(eventWinners!=null && getContext() != null)
                    adapter = new WinnerAdapter(getContext(),eventWinners);
                // Attach the adapter to a ListView
                winnersList.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("Hall Of Fame",dataSnapshot.getValue(Winners.class).toString());
                Winners winner = dataSnapshot.getValue(Winners.class);
                if(winner != null)
                    eventWinners.add(winner);
                if(eventWinners!=null && getContext() != null)
                    adapter = new WinnerAdapter(getContext(),eventWinners);
                // Attach the adapter to a ListView
                winnersList.setAdapter(adapter);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.e("Hall Of Fame",dataSnapshot.getValue(Winners.class).toString());
                Winners winner = dataSnapshot.getValue(Winners.class);
                if(winner != null)
                    eventWinners.add(winner);
                if(eventWinners!=null && getContext() != null)
                    adapter = new WinnerAdapter(getContext(),eventWinners);
                // Attach the adapter to a ListView
                winnersList.setAdapter(adapter);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("Hall Of Fame",dataSnapshot.getValue(Winners.class).toString());
                Winners winner = dataSnapshot.getValue(Winners.class);
                if(winner != null)
                    eventWinners.add(winner);
                if(eventWinners!=null && getContext() != null)
                    adapter = new WinnerAdapter(getContext(),eventWinners);
                // Attach the adapter to a ListView
                winnersList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if(auth!=null){
            DatabaseReference mWinnersDatabase = mDatabase.child("users").child(auth.getUid()).child("winners");
            mWinnersDatabase.addChildEventListener(childEventListener);
        }

        return rootView;
    }

}


class Winners{

    private String image;
    private String name;
    private String eventName;

    Winners(){

    }

    Winners(String image,String name,String event){
        this.image = image;
        this.name = name;
        this.eventName = event;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getEventName() {
        return eventName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


}

class WinnerAdapter extends ArrayAdapter<Winners>{

    public WinnerAdapter(Context context, ArrayList<Winners> winners){
        super(context,0,winners);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.winners_list_item, parent, false);
        }

        // Get the data item for this position
        Winners winner = getItem(position);

        ImageView image = convertView.findViewById(R.id.winnerPic);
        TextView name = convertView.findViewById(R.id.winnerName);
        TextView event = convertView.findViewById(R.id.winningEvent);

        Glide.with(image).load(winner.getImage()).into(image);
        name.setText(winner.getName());
        event.setText(winner.getEventName());

        // Return the completed view to render on screen
        return convertView;
    }


}
