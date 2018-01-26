package com.example.cesar.marvel;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayAdapter<String> arrayAdapter;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);

        // Array adapter to give data to the list from te service
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, new ArrayList<String>());
        // Give the adapter to the list
        listView.setAdapter(arrayAdapter);
        new ProcesaJson(arrayAdapter).execute("https://itunes.apple.com/search?term=maluma");
    }
    // Async task
    public class ProcesaJson extends AsyncTask<String, Integer, ArrayList<String>> {
        private ArrayAdapter<String> adapter;
        public ProcesaJson(ArrayAdapter<String> adapter){
            this.adapter = adapter;
        }
        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            // New json
            Json json = new Json();
            // Response from the service as a string
            String jsonString = json.serviceCall(urls[0]);
            ArrayList<String> arrayList = new ArrayList<>();
            // Give data to the array list. Only the collection name
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject dato = jsonArray.getJSONObject(i);
                    arrayList.add(dato.getString("collectionName"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return arrayList;
        }

        // Create new method to give the data to the adapter
        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            adapter.clear();
            adapter.addAll(strings);
            adapter.notifyDataSetChanged();
        }
    }

}