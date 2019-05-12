package ml.oopscpp.interweb;

import java.util.ArrayList;

public class Event {
    private String eventTitle;
    private String eventDate;
    private int eventImage;

    private static int[] images =  {
            R.drawable.ic_menu_event,
            R.drawable.ic_menu_gallery,
            R.drawable.ic_menu_participants,
            R.drawable.ic_menu_winner,
            R.drawable.ic_menu_camera
    };

    private static String[] titles =  {
            "Race of the Champions",
            "Race of the Champions",
            "Race of the Champions",
            "Race of the Champions",
            "Race of the Champions"
    };
    private static String[] dates =  {
            "20 May, 2019",
            "20 May, 2019",
            "20 May, 2019",
            "20 May, 2019",
            "20 May, 2019"
    };

    public Event(int imageId,String title,String date){
        eventImage = imageId;
        eventTitle = title;
        eventDate = date;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventDate() {
        return eventDate;
    }

    public int getEventImage() {
        return eventImage;
    }

    public static ArrayList<Event> getUsers() {
        ArrayList<Event> events = new ArrayList<Event>();
        for(int i=0;i<5;i++){
            events.add(new Event(images[i],titles[i],dates[i]));
        }
        return events;
    }

}
