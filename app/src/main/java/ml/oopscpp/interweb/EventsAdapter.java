package ml.oopscpp.interweb;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/***
 * The adapter class for the RecyclerView, contains the sports data.
 */
class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>  {

    // Member variables.
    private ArrayList<Event> mEventData;
    private Context mContext;

    /**
     * Constructor that passes in the sports data and the context.
     *
     * @param eventsData ArrayList containing the sports data.
     * @param context Context of the application.
     */
    EventsAdapter(Context context, ArrayList<Event> eventsData) {
        this.mEventData = eventsData;
        this.mContext = context;
    }


    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.event_list_item, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(EventsAdapter.ViewHolder holder,
                                 int position) {
        // Get current sport.
        Event currentEvent = mEventData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentEvent);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mEventData.size();
    }



    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mSportsImage;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            mInfoText = itemView.findViewById(R.id.eventDate);
            mSportsImage = itemView.findViewById(R.id.eventImage);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(Event currentEvent){
            // Populate the textviews with data.
            mTitleText.setText(currentEvent.getTitle());
            mInfoText.setText(currentEvent.getInfo());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(
                    currentEvent.getImageResource()).into(mSportsImage);
        }

        /**
         * Handle click to show DetailActivity.
         *
         * @param view View that is clicked.
         */
        @Override
        public void onClick(View view) {

        }
    }

}