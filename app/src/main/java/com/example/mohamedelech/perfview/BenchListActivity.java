package com.example.mohamedelech.perfview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by mohamed.elech on 18.04.2017.
 */

public class BenchListActivity extends AppCompatActivity {
    private SQLiteDatabaseHandler db;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bench_list);
        list = (ListView) findViewById(R.id.list_bench);
        db = new SQLiteDatabaseHandler(this);

        // Initialiser la liste des perf pour remplir notre ListView
        this.initListView();

        //Permet de voir en détail une personne
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentDetail = new Intent(BenchListActivity.this, BenchDetailActivity.class);
                intentDetail.putExtra("position", i);
                startActivity(intentDetail);
            }
        });

        //Pour ajouter une perf
        FloatingActionButton btn_add = (FloatingActionButton) findViewById(R.id.fabAdd);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("BenchListActivity", "On button add clicked");
                Intent intent = new Intent(BenchListActivity.this, AddPerfActivity.class);
                startActivity(intent);
            }
        });

        //Pour revenir à l'accueil
        FloatingActionButton btn_back = (FloatingActionButton) findViewById(R.id.fab);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("BenchListActivity", "On button back clicked");
                Intent intent = new Intent(BenchListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Initialiser la liste des perf pour remplir notre ListView
    public void initListView(){
    // list all perf
    List<Performance> perfs = db.allPerf();

    if (perfs != null) {
        String[] itemsNames = new String[perfs.size()];

        for (int i = 0; i < perfs.size(); i++) {
            itemsNames[i] = perfs.get(i).toString();
        }

        // display like string instances
        list = (ListView) findViewById(R.id.list_bench);
        list.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, itemsNames));

        }
    }
}