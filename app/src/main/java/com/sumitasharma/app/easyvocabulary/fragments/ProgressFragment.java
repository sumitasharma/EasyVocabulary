package com.sumitasharma.app.easyvocabulary.fragments;

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

import com.sumitasharma.app.easyvocabulary.R;
import com.sumitasharma.app.easyvocabulary.data.WordContract;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

import timber.log.Timber;

import static com.sumitasharma.app.easyvocabulary.util.WordUtil.PROGRESS_LOADER;


public class ProgressFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final LoaderManager.LoaderCallbacks<Cursor> callback = ProgressFragment.this;
    View mRootView;
    private Context mContext = getContext();

    public ProgressFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_progress, container, false);
        initializeLoader(PROGRESS_LOADER, getContext());
        return mRootView;
    }

    private void initializeLoader(int loaderId, Context context) {
        Timber.i("Inside initializeLoader");
        int mLoaderId = loaderId;
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
        return new CursorLoader(mContext, loaderUri, select, where, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data.getCount() == 0)
            return;
        BarChart chart = mRootView.findViewById(R.id.bar_chart);
        ArrayList<BarEntry> BarEntry = new ArrayList<>();
        int i = 0;
        while (data.moveToNext()) {
            BarEntry.add(new BarEntry(i, data.getInt(1)));
            i++;
        }
        BarDataSet dataSet = new BarDataSet(BarEntry, getResources().getString(R.string.graph_y_label));
        String[] labels = new String[data.getCount()];
        int labelIndex = 0;
        data.moveToFirst();
        do {
            labels[labelIndex] = data.getString(0);
            labelIndex++;
        } while (data.moveToNext());

        final String[] chartLabel = labels;

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return chartLabel[(int) value];
            }
        };

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        BarData graphData = new BarData(dataSet);
        Description description = new Description();
        description.setText(getResources().getString(R.string.graph_label_description));
        chart.setDescription(description);
        chart.setData(graphData);
        chart.invalidate();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
