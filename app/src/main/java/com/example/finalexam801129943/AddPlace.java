package com.example.finalexam801129943;;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AddPlace extends AppCompatActivity implements setCityInterface{

    EditText SearchText;
    Button Go;
    RecyclerView AddPlaceRecyclerView;
    String cityName;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        intent= getIntent();

        SearchText=findViewById(R.id.SearchText);
        AddPlaceRecyclerView = findViewById(R.id.AddPlaceRecyclerView);
        Go = findViewById(R.id.Go);

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Cities().execute("https://maps.googleapis.com/maps/api/place/autocomplete/json?key+abc&input="+SearchText.getText().toString());
                System.out.println("https://maps.googleapis.com/maps/api/place/autocomplete/json?key=AIzaSyBQZbW99md8SGz4xnKhgVbXh18MPP4gREA&input="+SearchText.getText().toString());
            }
        });

    }

    @Override
    public void setText(String city) {
        this.cityName = city;
        intent.putExtra("city",city);
        setResult(1,intent);
        System.out.println("CITYNAME: "+city);

        finish();
    }


    private class Cities extends AsyncTask<String,Void, ArrayList<String>> {

        JSONArray jsonArray;
        @Override
        protected ArrayList doInBackground(String... strings) {
            HttpURLConnection connection = null;
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = null;
            String result = null;
            URL url = null;
            ArrayList<String> arrayList = new ArrayList<>();
            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    result = stringBuilder.toString();
                    JSONObject jsonObject1 = new JSONObject(result);
                    jsonArray = jsonObject1.getJSONArray("predictions");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject11 = (JSONObject) jsonArray.get(i);
                        System.out.println("DESCRIPTION"+jsonObject11.getString("description"));
                        arrayList.add(jsonObject11.getString("description"));
                    }

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return arrayList;
        }


        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            LinearLayoutManager llm = new LinearLayoutManager(AddPlace.this);
            AddPlaceRecyclerView.setLayoutManager(llm);
            final AddplaceAdapter messagesAdapter = new AddplaceAdapter(strings,AddPlace.this);
            AddPlaceRecyclerView.setAdapter(messagesAdapter);
        }
    }




}
