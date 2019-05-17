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


public class ImageAdapter extends ArrayAdapter<String>{

    public ImageAdapter(Context context, ArrayList<String> events){
        super(context,0,events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_list_item, parent, false);
        }

        // Get the data iten for this position
        String imageUrl = getItem(position);

        // Find the imageView and load images into it with glide
        ImageView image = convertView.findViewById(R.id.galleryImage);
        Glide.with(image).load(imageUrl).into(image);

        // Return the completed view to render on screen
        return convertView;
    }

}
