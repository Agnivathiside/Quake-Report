package com.example.quakereport;

import android.content.Context;

//import androidx.loader.content.AsyncTaskLoader;
import android.content.AsyncTaskLoader;
import java.io.IOException;
import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<earthquake>> {
    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link EarthquakeLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<earthquake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<earthquake> earthquakes = null;
        try {
            earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return earthquakes;
    }
}
