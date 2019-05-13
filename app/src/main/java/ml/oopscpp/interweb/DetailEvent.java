package ml.oopscpp.interweb;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        Event event = (Event) getIntent().getParcelableExtra("event");

        ImageView image = findViewById(R.id.detailEventImage);
        TextView title = findViewById(R.id.detailEventTitle);
        TextView date = findViewById(R.id.detailEventDate);

        image.setImageURI(Uri.parse(event.getEventImage()));
        title.setText(event.getEventTitle());
        date.setText(event.getEventDate());
    }
}
