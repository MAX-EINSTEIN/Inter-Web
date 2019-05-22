package ml.oopscpp.interweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailEvent extends AppCompatActivity {

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        event = getIntent().getParcelableExtra("event");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            return true;
        }

        if(id == R.id.action_add_winner){
            Intent addWinner = new Intent(DetailEvent.this,AddWinner.class);
            addWinner.putExtra("eventName",event.getEventTitle());
            startActivity(addWinner);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_edit).setVisible(true);
        menu.findItem(R.id.action_add_winner).setVisible(true);
        return true;
    }
}
