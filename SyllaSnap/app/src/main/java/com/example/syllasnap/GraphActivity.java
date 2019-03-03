package com.example.syllasnap;

import android.graphics.Color;
import android.os.Bundle;

import com.example.syllasnap.calendar.CalendarManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        CalendarManager.WeekDataCallback weekDataCallback = new CalendarManager.WeekDataCallback() {
            @Override
            public void onWeekData(int[] weekData) {
                GraphView graph = (GraphView) findViewById(R.id.graph);

                BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                        new DataPoint(0, weekData[0]),
                        new DataPoint(1, weekData[1]),
                        new DataPoint(2, weekData[2]),
                        new DataPoint(3, weekData[3]),
                        new DataPoint(4, weekData[4]),
                        new DataPoint(5, weekData[5]),
                        new DataPoint(6, weekData[6]),
                });
                graph.addSeries(series);
                series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                    @Override
                    public int get(DataPoint data) {
                        return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
                    }
                });

                series.setSpacing(50);

            }
        };

        CalendarManager.getInstance().requestWeekData(weekDataCallback);
    }

}
