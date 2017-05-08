package com.example.mohamedelech.perfview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by mohamed.elech on 19.04.2017.
 */

public class BenchDetailActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 20;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 20;
    private SQLiteDatabaseHandler db;

    InputStream inputStream;
    EditText txt_date;
    EditText txt_weight;
    EditText txt_reps;
    EditText txt_address;
    ImageView imgView;
    Performance perf = new Performance();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bench_detail);
        db = new SQLiteDatabaseHandler(this);

        int position = this.getIntent().getIntExtra("position", -1);

        //affiche perf
        txt_date = (EditText) findViewById(R.id.editDatePerf);
        txt_weight = (EditText) findViewById(R.id.editWeight);
        txt_reps = (EditText) findViewById(R.id.editReps);
        txt_address = (EditText) findViewById(R.id.editTextAddress);
        imgView = (ImageView) findViewById(R.id.imageView3);

         perf = db.allPerf().get(position);

        txt_date.setText(perf.getDate(), TextView.BufferType.NORMAL);
        txt_weight.setText(perf.getWeight(), TextView.BufferType.NORMAL);
        txt_reps.setText(perf.getReps(), TextView.BufferType.NORMAL);
        txt_address.setText(perf.getAdresse(), TextView.BufferType.NORMAL);

        if(perf.getPhoto() != null){
            if (checkStoragePermission()) {
                //afficher image
                Uri imageUri = Uri.parse(perf.getPhoto());

                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    imgView.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }
        }
        //import photo
            imgView .setOnClickListener(new View.OnClickListener() {
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

        //Mise a jour de la perf
        Button btn_update = (Button) findViewById(R.id.update_btn);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = txt_date.getText().toString();
                String weight = txt_weight.getText().toString();
                String reps = txt_reps.getText().toString();
                String address = txt_address.getText().toString();
                String photo = perf.getPhoto();

                db.updatePerf(perf,date,perf.getMovement(),reps,weight,photo,address);
                Log.v("DetailActivity", "On button update clicked");
                Intent intent = new Intent(BenchDetailActivity.this, BenchListActivity.class);
                startActivity(intent);
            }
        });

        //Pour supprimer la perf
        Button btn_delete = (Button) findViewById(R.id.delete_btn);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteOne(perf);
                Log.v("DetailActivity", "On button delete clicked");
                Intent intent = new Intent();

                //Test si tab vide. Si vide renvoie à home
                if(db.allPerf().size() == 0) {
                    intent = new Intent(BenchDetailActivity.this, MainActivity.class);
                    Log.v("DetailActivity", "Array empty");
                } else {
                    intent = new Intent(BenchDetailActivity.this, BenchListActivity.class);
                    Log.v("DetailActivity", "Perf deleted");
                }
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            perf.setPhoto(data.getDataString());
            Toast.makeText(this, "Photo imported", Toast.LENGTH_LONG).show();
            //afficher image
            Uri imageUri = Uri.parse(perf.getPhoto());
            try {
                inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                imgView.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkStoragePermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return true;
        }
        return true;
    }
}
