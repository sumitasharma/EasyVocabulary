package com.example.sumitasharma.easyvocabulary.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.data.WordContract;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.PROGRESS_LOADER;


public class ProgressFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = ProgressFragment.class.getSimpleName();
    private final LoaderManager.LoaderCallbacks<Cursor> callback = ProgressFragment.this;
    View rootView;
    ArrayList<Integer> wordsGraphCount = new ArrayList<>();
    GraphView mGraphView;
    private int mLoaderId;
    private Context mContext = getContext();

    public ProgressFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_progress, container, false);
        mGraphView = rootView.findViewById(R.id.graph);
        initializeLoader(PROGRESS_LOADER, getContext());

//        select
//        strftime('%D', lastUpdated) as Days,
//                count(*) as GroupedValues
//        from words
//        group by Days;
        return rootView;
    }

    private void initializeLoader(int loaderId, Context context) {
        Timber.i("Inside initializeLoader");
        this.mLoaderId = loaderId;
        this.mContext = context;
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<String> progressLoader = loaderManager.getLoader(mLoaderId);
        if (progressLoader == null) {
            loaderManager.initLoader(mLoaderId, null, callback);
        } else {
            loaderManager.restartLoader(mLoaderId, null, callback);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //Setup the uri that will get the data I need from my ContentProvider
        Uri loaderUri = WordContract.WordsEntry.CONTENT_URI;
        String[] select = {"strftime('%Y-%m-%d', lastUpdated) as Days", "count(*) as GroupedValues"};
        String where = "wordPracticed = 1 GROUP BY Days";
        //String[] selectParam = {WordContract.WordsEntry.COLUMN_LAST_UPDATED,WordContract.WordsEntry.COLUMN_LAST_UPDATED};
        CursorLoader cursorLoader = new CursorLoader(mContext, loaderUri, select, where, null, null);
//        String[] select = {"?","?","?"};
//        String[] selectParam = {WordContract.WordsEntry.COLUMN_WORD, WordContract.WordsEntry.COLUMN_LAST_UPDATED, WordContract.WordsEntry.COLUMN_WORD_PRACTICED};
//        CursorLoader cursorLoader = new CursorLoader(mContext, loaderUri, select, null, selectParam, null);
        //and get a CursorLoader from my ContentProvider
        return cursorLoader;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Timber.i("cursor data :" + data.getCount());
        //int i = 1;
        DataPoint[] dataPoints = new DataPoint[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {

            //  wordsGraphCount.add(data.getInt(1));
            Timber.i("day " + data.getString(0) + "count(*)" + data.getInt(1) + "i is:" + i);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date quizDate;
            try {
                quizDate = format.parse(data.getString(0));
            } catch (ParseException e) {
                throw new RuntimeException("Parsing failed for Date" + data.getString(0));
            }
            dataPoints[i] = new DataPoint(quizDate, data.getInt(1));
            //i++;
            // Timber.i("Inside data moveToNext" + data.getColumnName(0));
            //           Timber.i("Inside data moveToNext" + data.getString(data.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD)));
            //         Timber.i("Inside data moveToNext" + data.getString(data.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD_PRACTICED)));
            i++;
        }

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);

        mGraphView.addSeries(series);
        // set date label formatter
        mGraphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        mGraphView.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

//// set manual x bounds to have nice steps
//        mGraphView.getViewport().setMinX(((Date) dataPoints[0].getX()).getTime());
//        mGraphView.getViewport().setMaxX(d3.getTime());
//        mGraphView.getViewport().setXAxisBoundsManual(true);
//
//// as we use dates as labels, the human rounding to nice readable numbers
//// is not necessary
//        mGraphView.getGridLabelRenderer().setHumanRounding(false);


    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
