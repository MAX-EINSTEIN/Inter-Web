package ml.oopscpp.interweb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;


public class EventFragment extends Fragment
        implements EventAdapter.ListItemClickListener{

    private static final String LOG_TAG = "EventFragment";

    private ArrayList<Event> mUpcomingEvents = new ArrayList<>();
    private ArrayList<String> mUpcomingEventKeys = new ArrayList<>();
    private RecyclerView mUpcomingEventsList;
    private EventAdapter mUpcomingEventsAdapter;
    private ArrayList<Event> mFavoriteEvents = new ArrayList<>();
    private ArrayList<String> mFavoriteEventKeys = new ArrayList<>();
    private RecyclerView mFavoriteEventsList;
    private EventAdapter mFavoriteEventsAdapter;
    private ArrayList<Event> mPastEvents = new ArrayList<>();
    private ArrayList<String> mPastEventKeys = new ArrayList<>();
    private RecyclerView mPastEventsList;
    private EventAdapter mPastEventsAdapter;
    private final EventFragment self = this;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(LOG_TAG, "on CreateView Called");

        // Set Toolbar's title
        Objects.requireNonNull(getActivity()).setTitle("Events");

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.fab.setVisibility(View.VISIBLE);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        mUpcomingEventsList = rootView.findViewById(R.id.upcomingEventsList);
        LinearLayoutManager upcomingEventsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mUpcomingEventsList.setLayoutManager(upcomingEventsLayoutManager);

        mFavoriteEventsList = rootView.findViewById(R.id.favoriteEventsList);
        LinearLayoutManager favoriteEventsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mFavoriteEventsList.setLayoutManager(favoriteEventsLayoutManager);

        mPastEventsList = rootView.findViewById(R.id.pastEventsList);
        LinearLayoutManager pastEventsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mPastEventsList.setLayoutManager(pastEventsLayoutManager);

        EventViewModel viewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();

        liveData.observe(this, new Observer<DataSnapshot>() {

            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    Event newEvent = dataSnapshot.getValue(Event.class);

                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy");
                    Date Today = Calendar.getInstance().getTime();
                    Date eventDate = new Date();
                    try {
                        assert newEvent != null;
                        eventDate = formatter.parse(newEvent.getEventDate());
                    }catch (Exception e){
                        Log.e(LOG_TAG, Objects.requireNonNull(e.getMessage()));
                    }

                    String key = dataSnapshot.getKey();

                    if(Today.compareTo(eventDate)>0){

                        if(mPastEventKeys.contains(key)){
                            int index = mPastEventKeys.indexOf(key);
                            mPastEvents.set(index, newEvent);
                        }else{
                            mPastEventKeys.add(key);
                            mPastEvents.add(newEvent);
                        }

                        if(mPastEvents != null){
                            sortEventDataAndKeys(mPastEvents, mPastEventKeys);
                            mPastEventsAdapter = new EventAdapter(mPastEvents, self);
                        }

                        mPastEventsList.setAdapter(mPastEventsAdapter);
                    }else{

                        if(mUpcomingEventKeys.contains(key)){
                            int index = mUpcomingEventKeys.indexOf(key);
                            mUpcomingEvents.set(index, newEvent);
                        }else{
                            mUpcomingEventKeys.add(key);
                            mUpcomingEvents.add(newEvent);
                        }

                        if(mUpcomingEvents != null){
                            sortEventDataAndKeys(mUpcomingEvents, mUpcomingEventKeys);
                            mUpcomingEventsAdapter = new EventAdapter(mUpcomingEvents, self);
                        }

                        mUpcomingEventsList.setAdapter(mUpcomingEventsAdapter);
                    }

                    if(mFavoriteEventKeys.contains(key)){
                        int index = mFavoriteEventKeys.indexOf(key);
                        mFavoriteEvents.set(index, newEvent);
                    }else{
                        mFavoriteEventKeys.add(key);
                        mFavoriteEvents.add(newEvent);
                    }

                    if(mFavoriteEvents != null){
                        sortEventDataAndKeys(mFavoriteEvents, mFavoriteEventKeys);
                        mFavoriteEventsAdapter = new EventAdapter(mFavoriteEvents, self);
                    }

                    mFavoriteEventsList.setAdapter(mFavoriteEventsAdapter);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        MainActivity mainActivity = (MainActivity)getActivity();
        if(mainActivity!=null)
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newEventActivity = new Intent(getActivity(),NewEvent.class);
                startActivity(newEventActivity);
            }
        });
    }


    @Override
    public void onListItemClick(int clickedItemIndex){
        Intent detailEventLauncher = new Intent(getContext(), DetailEvent.class);
        Event currentEvent = mUpcomingEvents.get(clickedItemIndex);
        detailEventLauncher.putExtra("event",currentEvent);
        startActivity(detailEventLauncher);
    }

    private int compareEventsByDate(Event lhs, Event rhs){
        int compareCode = 0;
        String sDate1 = lhs.getEventDate();
        String sDate2 = rhs.getEventDate();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy");
        try {
            Date date1 = formatter.parse(sDate1);
            Date date2 = formatter.parse(sDate2);
            if(date1!=null && date2!=null)
                compareCode = date1.compareTo(date2);
        }catch (Exception e){
            Log.e(LOG_TAG, "Error parsing dates");
        }

        return compareCode;
    }

    private void sortEventDataAndKeys(ArrayList<Event> eventList, ArrayList<String> keyList){
        for(int i = 0; i< eventList.size()-1; i++){
            for(int j = 0; j< eventList.size()-i-1; j++){
                if(compareEventsByDate(eventList.get(j), eventList.get(j+1))<0){
                    Collections.swap(eventList, j, j+1);
                    Collections.swap(keyList, j, j+1);
                }
            }
        }
    }
}
