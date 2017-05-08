package com.example.mohamedelech.perfview;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by mohamed.elech on 15.04.2017.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddPerfActivity extends AppCompatActivity implements LocationListener {

    private SQLiteDatabaseHandler db;

    public static final int REQUEST_CODE = 20;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    Performance perf;
    EditText txt_date;
    EditText txt_address;
    EditText txt_test;
    Button btn_import;
    LocationManager lManager;
    Location location;
    String provider;

    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_perf);

        db = new SQLiteDatabaseHandler(this);

        if (checkLocationPermission()){
                lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                provider = lManager.getBestProvider(criteria, false);
                txt_address = (EditText) findViewById(R.id.editTextAddress);
                location = lManager.getLastKnownLocation(provider);
                afficherAdresse();
            }


        //Set date
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date);
        txt_date = (EditText) findViewById(R.id.editDatePerf);
        txt_date.setText((dateString), TextView.BufferType.NORMAL);

        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });

        //save perf
        Button button = (Button) findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText date = (EditText) findViewById(R.id.editDatePerf);
                EditText weight = (EditText) findViewById(R.id.editWeight);
                EditText reps = (EditText) findViewById(R.id.editReps);
                EditText address = (EditText) findViewById(R.id.editTextAddress);
                EditText photo = (EditText) findViewById(R.id.test);

               perf = new Performance(1, date.getText().toString(),
                        "Bench Press",reps.getText().toString(), weight.getText().toString(),photo.getText().toString(),address.getText().toString());

                db.addPerf(perf);

                //Go to List
                Intent intent = new Intent(AddPerfActivity.this, BenchListActivity.class);
                startActivity(intent);

            }
        });

        //import photo
        btn_import = (Button) findViewById(R.id.importPhoto);
        btn_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String picturePath = picture.getPath();
                Uri data = Uri.parse(picturePath);

                photoPickerIntent.setDataAndType(data,"image/*");

                startActivityForResult(photoPickerIntent,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        txt_test = (EditText) findViewById(R.id.test);

        if (resultCode == RESULT_OK){
                btn_import.setBackgroundColor(Color.GREEN);
                txt_test.setText(data.getDataString());
                Toast.makeText(this, "Photo imported", Toast.LENGTH_LONG).show();
            }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    location = lManager.getLastKnownLocation(provider);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void afficherAdresse() {

        //Le geocoder permet de récupérer ou chercher des adresses
        //gràce à un mot clé ou une position
        Geocoder geo = new Geocoder(AddPerfActivity.this, Locale.getDefault());
        try {
            //Ici on récupère la premiere adresse trouvé gràce à la position que l'on a récupéré
            List<Address> adresses = geo.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);

            if (adresses != null && adresses.size() == 1) {
                Address adresse = adresses.get(0);
                //Si le geocoder a trouver une adresse, alors on l'affiche
                txt_address.setText(String.format("%s \n%s %s",
                        adresse.getAddressLine(0),
                        adresse.getPostalCode(),
                        adresse.getLocality()));
            } else {
                //sinon on affiche un message d'erreur
                txt_address.setText("L'adresse n'a pu être déterminée");
            }
        } catch (IOException e) {
            e.printStackTrace();
            txt_address.setText("L'adresse n'a pu être déterminée");
        }
    }

    private void updateDate() {
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTextLabel();
        }
    };

    private void updateTextLabel() {
        Long date = dateTime.getTimeInMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date);
        txt_date.setText(dateString);
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}