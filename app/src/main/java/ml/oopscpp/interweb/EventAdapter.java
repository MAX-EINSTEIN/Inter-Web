package ml.oopscpp.interweb;

import java.util.ArrayList;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


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
        TextView title = convertView.findViewById(R.id.eventTitle);
        TextView date = convertView.findViewById(R.id.eventDate);
        ImageView image = convertView.findViewById(R.id.eventImage);
        TextView venue = convertView.findViewById(R.id.eventVenue);

        title.setText(event.getEventTitle());
        date.setText(event.getEventDate());
        Glide.with(image).load(event.getEventImage()).into(image);
        venue.setText(event.getEventVenue());
        // Return the completed view to render on screen
        return convertView;
    }

}
