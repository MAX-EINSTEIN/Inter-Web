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
        ImageView image = convertView.findViewById(R.id.eventImage);
        TextView title = convertView.findViewById(R.id.eventTitle);
        TextView date = convertView.findViewById(R.id.eventDate);
        TextView venue = convertView.findViewById(R.id.eventVenue);
        // Populate the data into the template view using the data object
        Glide.with(image).load("https://firebasestorage.googleapis.com/v0/b/inter-web-b3d3b.appspot.com/o/IMG_20180616_150528_2.jpg?alt=media&token=53016e9d-4854-413a-9d6d-25ca723bfe57").into(image);
        //image.setImageURI(Uri.parse(event.getEventImage()));
        title.setText(event.getEventTitle());
        date.setText(event.getEventDate());
        venue.setText(event.getEventVenue());
        // Return the completed view to render on screen
        return convertView;
    }

}
