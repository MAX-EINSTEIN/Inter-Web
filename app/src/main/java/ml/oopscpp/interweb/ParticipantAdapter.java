package ml.oopscpp.interweb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class ParticipantAdapter extends ArrayAdapter<Participant>{

    public ParticipantAdapter(Context context, ArrayList<Participant> events){
        super(context,0,events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.participant_list_item, parent, false);
        }

        // Get the data item for this position
        Participant participant = getItem(position);

        ImageView image = convertView.findViewById(R.id.participantImage);
        TextView name = convertView.findViewById(R.id.participantName);
        TextView contact = convertView.findViewById(R.id.participantContact);

       image.setImageResource(participant.getParticipantImage());
       name.setText(participant.getParticipantName());
       contact.setText(participant.getParticipantContact());

        // Return the completed view to render on screen
        return convertView;
    }

}
