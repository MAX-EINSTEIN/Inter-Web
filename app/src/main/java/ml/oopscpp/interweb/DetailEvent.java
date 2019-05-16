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

        TextView title = findViewById(R.id.detailEventTitle);
        TextView date = findViewById(R.id.detailEventDate);
        ImageView image = findViewById(R.id.detailEventImage);
        TextView venue = findViewById(R.id.detailEventVenue);
        TableLayout table = findViewById(R.id.participantTable);


        title.setText(event.getEventTitle());
        date.setText(event.getEventDate());
        Glide.with(image).load(event.getEventImage()).into(image);
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
