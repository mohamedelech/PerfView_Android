package com.example.mohamedelech.perfview;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;

import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {

    protected Button button;
    private SQLiteDatabaseHandlerAgenda db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new SQLiteDatabaseHandlerAgenda(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        button = (Button) findViewById(R.id.benchpress);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BenchListActivity.class);
                startActivity(intent);
            }
        });

        List<Agenda> agendas = db.allagendasAfter();

        if (agendas.size()>0) {

            Long date = agendas.get(0).getDate();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date);
            calendar.set(Calendar.HOUR_OF_DAY,8);
            calendar.set(Calendar.MINUTE,00);

            Intent intent = new Intent(getApplicationContext(),Notification_reciver.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_agenda) {
            Intent intent = new Intent(MainActivity.this, calendarActivity.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_SaisiePerf) {
            Intent intent = new Intent(MainActivity.this, AddPerfActivity.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_compass) {
            Intent intent = new Intent(MainActivity.this, CompassActivity.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_Ra) {
            return true;

        } else if (id == R.id.nav_Rm) {
            Intent intent = new Intent(MainActivity.this, CounterSensorActivity.class);
            startActivity(intent);
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
