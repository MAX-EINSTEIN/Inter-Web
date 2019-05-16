package ml.oopscpp.interweb;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        Event event = getIntent().getParcelableExtra("event");

        ImageView image = findViewById(R.id.detailEventImage);
        TextView title = findViewById(R.id.detailEventTitle);
        TextView date = findViewById(R.id.detailEventDate);
        TextView venue = findViewById(R.id.detailEventVenue);
        TableLayout table = findViewById(R.id.participantTable);
        Glide.with(image).load("https://firebasestorage.googleapis.com/v0/b/inter-web-b3d3b.appspot.com/o/IMG_20180616_150528_2.jpg?alt=media&token=53016e9d-4854-413a-9d6d-25ca723bfe57").into(image);
        //image.setImageURI(Uri.parse(event.getEventImage()));
        title.setText(event.getEventTitle());
        date.setText(event.getEventDate());
        venue.setText((event.getEventVenue()));

        for (int i = 0; i < event.getEventParticipants().size(); i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            row.setPadding(32,8,32,8);
            TextView pName = new TextView(this);
            pName.setText(event.getEventParticipants().get(i));
            TextView iName = new TextView(this);
            iName.setText(event.getEventCollaborators().get(i));
            row.addView(pName);
            row.addView(iName);
            table.addView(row);
        }

    }
}
