package com.example.mohamedelech.perfview;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
/**
 * Created by mohamed.elech on 10.05.2017.
 */

public class calendarActivity extends AppCompatActivity {

        Toolbar toolbar;
        private SQLiteDatabaseHandlerAgenda db;
        ListView list;

        CompactCalendarView compactCalendar;
        private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.calendar);
            toolbar = (Toolbar) findViewById(R.id.toolbar2);
            db = new SQLiteDatabaseHandlerAgenda(this);
            list = (ListView) findViewById(R.id.list_agenda);

            long date = System.currentTimeMillis();
            android.icu.text.SimpleDateFormat sdf = new android.icu.text.SimpleDateFormat("MMMM yyyy");
            String dateString = sdf.format(date);
            toolbar.setTitle(dateString);

            //initListView();

            compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
            compactCalendar.setUseThreeLetterAbbreviation(true);

            List<Agenda> agendas = db.allagendas();

            if (agendas != null) {
                for (int i = 0; i < agendas.size(); i++) {
                    Event ev = new Event(Color.RED, agendas.get(i).getDate(), "Training Day");
                    compactCalendar.addEvent(ev);
                }
            }

            compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                @Override
                public void onDayClick(Date dateClicked) {
                    Context context = getApplicationContext();

                    if (compactCalendar.getEvents(dateClicked).size() == 0) {
                        Event ev = new Event(Color.RED, dateClicked.getTime(), "Training Day");
                        compactCalendar.addEvent(ev);
                        Agenda agenda = new Agenda(1, dateClicked.getTime());
                        db.addAgenda(agenda);
                        long date = dateClicked.getTime()+86400000;
                        android.icu.text.SimpleDateFormat sdf = new android.icu.text.SimpleDateFormat("dd MM yyyy");
                        String dateString = sdf.format(date);
                        //initListView();
                        Toast.makeText(context, "Traning Planned for "+dateString, Toast.LENGTH_SHORT).show();
                    } else {
                        List<Event> events = compactCalendar.getEvents(dateClicked);
                        Event event = events.get(0);
                        db.deleteOne(db.getAgenda(event.getTimeInMillis()));
                        compactCalendar.removeEvents(events);
                        long date = dateClicked.getTime()+86400000;
                        android.icu.text.SimpleDateFormat sdf = new android.icu.text.SimpleDateFormat("dd MM yyyy");
                        String dateString = sdf.format(date);
                        //initListView();
                        Toast.makeText(context, "Traning Deleted for "+dateString, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onMonthScroll(Date firstDayOfNewMonth) {
                    toolbar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
                }
            });

        }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initListView(){
        // list all events after todays
        List<Agenda> agendas = db.allagendasAfter();

        if ((agendas.size()> 0) && (agendas.size()<= 3)) {
            String[] itemsNames = new String[3];

            for (int i = 0; i < agendas.size(); i++) {
                itemsNames[i] = agendas.get(i).toString();
            }

            // display like string instances
            list = (ListView) findViewById(R.id.list_agenda);
            list.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, itemsNames));

        }
    }

}


