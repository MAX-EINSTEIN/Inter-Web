package ml.oopscpp.interweb;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{

    private ArrayList<Event> mEvents;
    private ListItemClickListener mListItemClickListener;

    EventAdapter(ArrayList<Event> eventList, ListItemClickListener listener){
        mEvents = eventList;
        mListItemClickListener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int LayoutIdForListItem = R.layout.event_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        final boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(LayoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.title.setText(mEvents.get(position).getEventTitle());
        holder.date.setText(mEvents.get(position).getEventDate());
        holder.venue.setText(mEvents.get(position).getEventVenue());
        Glide.with(holder.image).load(mEvents.get(position).getEventImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    public class EventViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

        TextView title;
        TextView date;
        ImageView image;
        TextView venue;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.eventName);
            date = itemView.findViewById(R.id.eventDate);
            image = itemView.findViewById(R.id.eventImage);
            venue = itemView.findViewById(R.id.eventVenue);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            mListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
