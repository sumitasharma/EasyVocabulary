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

import java.util.ArrayList;

import timber.log.Timber;


public class ProgressFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = ProgressFragment.class.getSimpleName();
    private final static int LOADER_ID = 102;
    private final LoaderManager.LoaderCallbacks<Cursor> callback = ProgressFragment.this;
    View rootView;
    ArrayList<Integer> wordsGraphCount = new ArrayList<>();
    GraphView graph;
    private int mLoaderId;
    private Context mContext = getContext();

    public ProgressFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_progress, container, false);
        graph = rootView.findViewById(R.id.graph);
        initializeLoader(LOADER_ID, getContext());

//        select
//        strftime('%W', lastUpdated) Weeks
//                count(*) as GroupedValues
//        from words
//        group by Weeks;
        return rootView;
    }

    private void initializeLoader(int loaderId, Context context) {
        Timber.i("Inside initializeLoader");
        this.mLoaderId = loaderId;
        this.mContext = context;
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<String> wordPracticeLoader = loaderManager.getLoader(mLoaderId);
        if (wordPracticeLoader == null) {
            loaderManager.initLoader(mLoaderId, null, callback);
        } else {
            loaderManager.restartLoader(mLoaderId, null, callback);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //Setup the uri that will get the data I need from my ContentProvider
        Uri loaderUri = WordContract.WordsEntry.CONTENT_URI;
        String[] select = {"strftime('%D', lastUpdated) Weeks", "count(*)"};
        String where = "wordPracticed = 'true' GROUP BY Weeks";
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
        int i = 1;
        while (data.moveToNext()) {

            wordsGraphCount.add(data.getInt(1));
            i++;
            // Timber.i("Inside data moveToNext" + data.getColumnName(0));
            //           Timber.i("Inside data moveToNext" + data.getString(data.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD)));
            //         Timber.i("Inside data moveToNext" + data.getString(data.getColumnIndex(WordContract.WordsEntry.COLUMN_WORD_PRACTICED)));

        }
//        Timber.i("wordsgraph :" + wordsGraphCount.get(0));
//        // Timber.i("data is : "+data.getInt(0));
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
//                new DataPoint(0, wordsGraphCount.get(0)),
//                new DataPoint(1, wordsGraphCount.get(0)),
//                new DataPoint(2, wordsGraphCount.get(0))
//        });
//        graph.addSeries(series);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
