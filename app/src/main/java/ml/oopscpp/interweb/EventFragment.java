package ml.oopscpp.interweb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.StringTokenizer;


public class EventFragment extends Fragment
        implements EventAdapter.ListItemClickListener{

    private static final String LOG_TAG = "EventFragment";

    private EventAdapter mEventAdapter;
    private ArrayList<Event> mEvents;
    private RecyclerView mEventsList;
    private final EventFragment self = this;
    private ArrayList<String> mKeys = new ArrayList<>();

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

        mEventsList = rootView.findViewById(R.id.eventList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mEventsList.setLayoutManager(layoutManager);
        mEventsList.addItemDecoration(
                new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL));

        mEvents = new ArrayList<>();

        EventViewModel viewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();


        liveData.observe(this, new Observer<DataSnapshot>() {

            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    Event newEvent = dataSnapshot.getValue(Event.class);

                    String key = dataSnapshot.getKey();

                    if(mKeys.contains(key)){
                        int index = mKeys.indexOf(key);
                        mEvents.set(index, newEvent);
                    }else{
                        mKeys.add(key);
                        if(newEvent!=null)
                            mEvents.add(newEvent);
                    }

                    if(mEvents != null){
//                        Collections.sort(mEvents, new Comparator<Event>() {
//                            @Override
//                            public int compare(Event lhs, Event rhs) {
//                                int compareCode = 0;
//                                String sDate1 = lhs.getEventDate();
//                                String sDate2 = rhs.getEventDate();
//                                @SuppressLint("SimpleDateFormat")
//                                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy");
//                                try {
//                                    Date date1 = formatter.parse(sDate1);
//                                    Date date2 = formatter.parse(sDate2);
//                                    compareCode = date1.compareTo(date2);
//                                }catch (Exception e){
//                                    Log.e(LOG_TAG, "Error parsing dates");
//                                }
//
//                                return compareCode;
//                            }
//                        });
                        mEventAdapter = new EventAdapter(mEvents, self);
                    }


                    mEventsList.setAdapter(mEventAdapter);
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
        Event currentEvent = mEvents.get(clickedItemIndex);
        detailEventLauncher.putExtra("event",currentEvent);
        startActivity(detailEventLauncher);
    }
}
