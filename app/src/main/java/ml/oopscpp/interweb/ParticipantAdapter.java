package ml.oopscpp.interweb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder> {

    private ArrayList<Participant> mParticipants;

    ParticipantAdapter(ArrayList<Participant> participantsList){
        mParticipants = participantsList;
    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int LayoutIdForListItem = R.layout.participant_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        final boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(LayoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new ParticipantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {
        Glide.with(holder.image).load(mParticipants.get(position).getParticipantImage()).into(holder.image);
        holder.name.setText(mParticipants.get(position).getParticipantName());
        holder.contact.setText(mParticipants.get(position).getParticipantContact());
    }

    @Override
    public int getItemCount() {
        return mParticipants.size();
    }

    public class ParticipantViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView name;
        private TextView contact;

        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.participantImage);
            name = itemView.findViewById(R.id.participantName);
            contact = itemView.findViewById(R.id.participantContact);
        }
    }

}