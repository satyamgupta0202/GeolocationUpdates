package com.example.geolocationupdates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<LatLng> location = new ArrayList<LatLng>();
    static ArrayList<String> city = new ArrayList<String>();
    //static ArrayList<LatLng> location = new ArrayList<LatLng>();
    static  ArrayAdapter arrayAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.geolocationupdates", Context.MODE_PRIVATE);
        ArrayList<String> lattitudes = new ArrayList<>();
        ArrayList<String>longitudes = new ArrayList<>();
        city.clear();
        location.clear();
        lattitudes.clear();
        longitudes.clear();

        try{
            city=        (ArrayList)ObjectSerializer.deserialize(sharedPreferences.getString("city",ObjectSerializer.serialize(new ArrayList<String>())));
           lattitudes= (ArrayList)ObjectSerializer.deserialize(sharedPreferences.getString("lat",ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes= (ArrayList)ObjectSerializer.deserialize(sharedPreferences.getString("lon",ObjectSerializer.serialize(new ArrayList<String>())));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(city.size()>0 && lattitudes.size()>0 && longitudes.size()>0){
            if(city.size()==lattitudes.size() && city.size()==longitudes.size()){
                for(int i=0;i<lattitudes.size();i++){
                    location.add( new LatLng(Double.parseDouble(lattitudes.get(i)),Double.parseDouble(longitudes.get(i))));
                }
            }
        }
        else{
            city.add("new place.........");
            location.add(new LatLng(0,0));
        }


        listView = findViewById(R.id.listView);
        city.add("Add item to the android list");
        location.add(new LatLng(0,0));
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 ,city);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("placenum",position);
                startActivity(intent);
               // Toast.makeText(MainActivity.this, city.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }
}