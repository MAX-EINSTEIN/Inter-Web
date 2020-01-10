package ml.oopscpp.interweb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WinnerAdapter extends RecyclerView.Adapter<WinnerAdapter.WinnerViewHolder>{

    private ArrayList<Winner> mWinners;

    public WinnerAdapter(ArrayList<Winner> winnersList){
        mWinners = winnersList;
    }

    @NonNull
    @Override
    public WinnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int LayoutIdForListItem = R.layout.winners_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        final boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(LayoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new WinnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WinnerViewHolder holder, int position) {
        Glide.with(holder.image).load(mWinners.get(position).getImage()).into(holder.image);
        holder.name.setText(mWinners.get(position).getName());
        holder.event.setText(mWinners.get(position).getEventName());
    }

    @Override
    public int getItemCount() {
        return mWinners.size();
    }

    public class WinnerViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView event;

        public WinnerViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.winnerPic);
            name = itemView.findViewById(R.id.winnerName);
            event = itemView.findViewById(R.id.winningEvent);
        }
    }
}