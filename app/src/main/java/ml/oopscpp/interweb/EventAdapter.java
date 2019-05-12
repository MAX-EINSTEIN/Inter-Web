package ml.oopscpp.interweb;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class EventAdapter extends ArrayAdapter<Event>{

    public EventAdapter(Context context,ArrayList<Event> events){
        super(context,0,events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_list_item, parent, false);
        }

        // Get the data item for this position
        Event event = getItem(position);

        // Lookup view for data population
        ImageView image = convertView.findViewById(R.id.eventImage);
        TextView title = convertView.findViewById(R.id.eventTitle);
        TextView date = convertView.findViewById(R.id.eventDate);
        // Populate the data into the template view using the data object
        image.setImageResource(event.getEventImage());
        title.setText(event.getEventTitle());
        date.setText(event.getEventDate());
        // Return the completed view to render on screen
        return convertView;
    }

}
