package ml.oopscpp.interweb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class EventFragment extends Fragment {

    EventAdapter adapter;
    ArrayList<Event> arrayOfEvents;
    ListView listView;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        arrayOfEvents = new ArrayList<>();
        listView = rootView.findViewById(R.id.eventList);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("Main",dataSnapshot.getValue(Event.class).toString());
                Event newEvent = dataSnapshot.getValue(Event.class);
                arrayOfEvents.add(newEvent);
                //adapter.add(newEvent);
                adapter = new EventAdapter(getContext(), arrayOfEvents);
                // Attach the adapter to a ListView
                listView.setAdapter(adapter);
                addOnClickListenerToListViewItem();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Event newEvent = dataSnapshot.getValue(Event.class);
                arrayOfEvents.add(newEvent);
                //adapter.add(newEvent);
                adapter = new EventAdapter(getContext(), arrayOfEvents);
                // Attach the adapter to a ListView
                listView.setAdapter(adapter);
                addOnClickListenerToListViewItem();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Event newEvent = dataSnapshot.getValue(Event.class);
                arrayOfEvents.add(newEvent);
                //adapter.add(newEvent);
                adapter = new EventAdapter(getContext(), arrayOfEvents);
                // Attach the adapter to a ListView
                listView.setAdapter(adapter);
                addOnClickListenerToListViewItem();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Event newEvent = dataSnapshot.getValue(Event.class);
                arrayOfEvents.add(newEvent);
                //adapter.add(newEvent);
                adapter = new EventAdapter(getContext(), arrayOfEvents);
                // Attach the adapter to a ListView
                listView.setAdapter(adapter);
                addOnClickListenerToListViewItem();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        DatabaseReference mEventsDatabase = mDatabase.child("events");
        mEventsDatabase.addChildEventListener(childEventListener);

        return rootView;
    }

    private void addOnClickListenerToListViewItem(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent detailEventLauncher = new Intent(getContext(), DetailEvent.class);
                Event currentEvent = adapter.getItem(position);
                detailEventLauncher.putExtra("event",currentEvent);
                startActivity(detailEventLauncher);
            }
        });
    }


}
