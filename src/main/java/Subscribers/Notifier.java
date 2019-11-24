package Subscribers;

import java.util.*;

public class Notifier {
    public void Create(){
        //init remind task
        Timer tm = new Timer();
        Calendar start_calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
        //Date using UTC time. UTC = Moscow - 3 hours
        start_calendar.set(Calendar.HOUR_OF_DAY, 10);
        start_calendar.set(Calendar.MINUTE,0);
        start_calendar.add(Calendar.DAY_OF_MONTH,1);
        Date start_date = start_calendar.getTime();
        tm.schedule(new RemindTask(),start_date, 86400000);
    }
}
