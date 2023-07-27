/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<earthquake>> {


        private static final String LOG_TAG = EarthquakeActivity.class.getName();
        final String USGS_REQUEST_URL =
                "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson";
        private earthquakeAdapter mAdapter;
        /**
         * Constant value for the earthquake loader ID. We can choose any integer.
         * This really only comes into play if you're using multiple loaders.
         */
        private static final int EARTHQUAKE_LOADER_ID = 1;
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
//        ArrayList<earthquake> earthquakes = new ArrayList<>();
//        earthquakes.add(new earthquake("7.2","San Francisco","Feb 2, 2016"));
//        earthquakes.add(new earthquake("6.9", "India", "Mar 31 2022"));
//        earthquakes.add(new earthquake("5.8","Bangladesh","Porsu din bara"));
//        earthquakes.add(new earthquake("108","bedroom maahshaallah", "kalke raat"));
//        earthquakes.add(new earthquake("69","bondhur bari", "porsu raat"));
//        earthquakes.add(new earthquake("216","OYO","kore vule gechi"));
//        earthquakes.add(new earthquake("10","kombol er tola","exam er porer din"));
        // Create a new adapter that takes the list of earthquakes as input
        // Find a reference to the {@link ListView} in the layout

//        ArrayList<earthquake> earthquakes= QueryUtils.extractEarthquakes();
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

//        final earthquakeAdapter adapter = new earthquakeAdapter(this,earthquakes );

        mAdapter = new earthquakeAdapter(this, new ArrayList<earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                earthquake currentEarthquake = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
//        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
//        task.execute(USGS_REQUEST_URL);
        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();
        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        // Create a new {@link ArrayAdapter} of earthquakes
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, earthquakes);
//        earthquakeAdapter adapter=new earthquakeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        // earthquakeListView.setAdapter(adapter);
    }
//        private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<earthquake>> {
//
//            @Override
//            protected List<earthquake> doInBackground(String... urls) {
//                if (urls.length < 1 || urls[0] == null) {
//                    return null;
//                }
//                List<earthquake> result = null;
//                try {
//                    result = QueryUtils.fetchEarthquakeData(urls[0]);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                return result;
//            }
//
//            @Override
//            protected void onPostExecute(List<earthquake> data) {
//                // Clear the adapter of previous earthquake data
//                mAdapter.clear();
//                // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
//                // data set. This will trigger the ListView to update.
//                if (data != null && !data.isEmpty()) {
//                    mAdapter.addAll(data);
//                }
//            }
//        }


    @Override
    public Loader<List<earthquake>> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<earthquake>> loader, List<earthquake> earthquakes) {
        mAdapter.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()){
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<earthquake>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
