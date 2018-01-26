package com.example.cesar.marvel;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import com.example.cesar.marvel.adapters.ituneArrayAdapter;
import com.example.cesar.marvel.pojo.itune;

public class MainActivity extends Activity {

    private ArrayAdapter<String> arrayAdapter;

    private ListView listView;
    private ituneArrayAdapter ituneArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);

        // Array adapter to give data to the list from te service
        ituneArrayAdapter = new ituneArrayAdapter(this,
                R.layout.itunes_layout, new ArrayList<itune>());
        // Give the adapter to the list
        listView.setAdapter(ituneArrayAdapter);
        new ProcesaJson(ituneArrayAdapter).execute("https://itunes.apple.com/search?term=maluma");
    }
    // Async task
    public class ProcesaJson extends AsyncTask<String, Integer, ArrayList<itune>> {
        private ituneArrayAdapter adapter;
        public ProcesaJson(ituneArrayAdapter adapter){
            this.adapter = adapter;
        }
        @Override
        protected ArrayList<itune> doInBackground(String... urls) {
            // New json
            Json json = new Json();
            // Response from the service as a string
            String jsonString = json.serviceCall(urls[0]);
            ArrayList<itune> arrayList = new ArrayList<>();
            // Give data to the array list. Only the collection name
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject dato = jsonArray.getJSONObject(i);
                    itune ituneObj = new itune();
                    ituneObj.collectionName = dato.getString("collectionName");
                    ituneObj.trackName = dato.getString("trackName");
                    ituneObj.trackPrice = dato.getDouble("trackPrice");
                    arrayList.add(ituneObj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return arrayList;
        }

        // Create new method to give the data to the adapter
        @Override
        protected void onPostExecute(ArrayList<itune> strings) {
            adapter.clear();
            adapter.addAll(strings);
            adapter.notifyDataSetChanged();
        }
    }

}