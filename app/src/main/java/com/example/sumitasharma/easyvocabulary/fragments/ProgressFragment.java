package com.example.sumitasharma.easyvocabulary.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sumitasharma.easyvocabulary.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class ProgressFragment extends Fragment {
    private static final String TAG = ProgressFragment.class.getSimpleName();
    View rootView;

    public ProgressFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_progress, container, false);
        GraphView graph = rootView.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)
        });
        graph.addSeries(series);
//        select
//        strftime('%W', aDate) WeekNumber,
//                max(date(aDate, 'weekday 0', '-7 day')) WeekStart,
//                max(date(aDate, 'weekday 0', '-1 day')) WeekEnd,
//                count(*) as GroupedValues
//        from t1
//        group by WeekNumber;
        return rootView;
    }
}
