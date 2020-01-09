package ml.oopscpp.interweb;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;
import java.util.Objects;


public class EventFragment extends Fragment implements EventAdapter.ListItemClickListener{

    private EventAdapter adapter;
    private ArrayList<Event> arrayOfEvents;
    private RecyclerView recyclerView;
    private final EventFragment self = this;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set Toolbar's title
        Objects.requireNonNull(getActivity()).setTitle("Events");

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.fab.setVisibility(View.VISIBLE);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        recyclerView = rootView.findViewById(R.id.eventList);
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        arrayOfEvents = new ArrayList<>();

        EventViewModel viewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();


        liveData.observe(this, new Observer<DataSnapshot>() {

            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    Event newEvent = dataSnapshot.getValue(Event.class);

                    for (Event val:arrayOfEvents) {
                        if(newEvent!=null && newEvent.getEventTitle().equals(val.getEventTitle()))
                            arrayOfEvents.remove(val);
                    }

                    if(newEvent!=null)
                        arrayOfEvents.add(newEvent);

                    if(arrayOfEvents != null)
                        adapter = new EventAdapter(arrayOfEvents, self);

                    recyclerView.setAdapter(adapter);
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

        arrayOfEvents.clear();
    }

    @Override
    public void onListItemClick(int clickedItemIndex){
        Intent detailEventLauncher = new Intent(getContext(), DetailEvent.class);
        Event currentEvent = arrayOfEvents.get(clickedItemIndex);
        detailEventLauncher.putExtra("event",currentEvent);
        startActivity(detailEventLauncher);
    }
}
