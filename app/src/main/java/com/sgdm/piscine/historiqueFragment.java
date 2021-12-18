package com.sgdm.piscine;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.graphics.Color;
import android.widget.SeekBar;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sgdm.piscine.MainActivity;
import com.sgdm.piscine.R;



public class historiqueFragment extends Fragment {
    //public Integer randomint=9;
    //public LineView lineViewCLPH;
    //public LineView lineViewTETX;
    public LineChartView chartCLPH, chartTETX;
    public SeekBar seekBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historique, container, false);
        chartCLPH =  rootView.findViewById(R.id.chartCLPH);
        chartTETX = rootView.findViewById(R.id.chartTETX);
        seekBar = rootView.findViewById ( R.id.seekBar );
        initdata(10);
        seekBar.setOnSeekBarChangeListener ( new SeekBar.OnSeekBarChangeListener () {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                initdata(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        } );
        return rootView;
    }
    public void initdata(Integer dataNumber) {
        FirebaseDatabase database = FirebaseDatabase.getInstance ();
        Query myRef = database.getReference ( "/constantePiscine" ).limitToLast ( dataNumber );
        myRef.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<AxisValue> listDATE= new ArrayList<>();
                List<PointValue> listCL = new ArrayList<PointValue>();
                List<PointValue> listPH = new ArrayList<PointValue>();
                List<PointValue> listTE = new ArrayList<PointValue>();
                List<PointValue> listTX = new ArrayList<PointValue>();
                Integer count=0;
                for (DataSnapshot readData : dataSnapshot.getChildren ()) {
                    count++;
                    listCL.add(new PointValue(count, Float.parseFloat(readData.child ( "cl" ).getValue ().toString ())));
                    listPH.add(new PointValue(count, Float.parseFloat(readData.child ( "ph" ).getValue ().toString ())));
                    listTE.add(new PointValue(count, Float.parseFloat(readData.child ( "te" ).getValue ().toString ())));
                    listTX.add(new PointValue(count, Float.parseFloat(readData.child ( "tx" ).getValue ().toString ())));
                    String stamp = readData.child ( "stamp" ).getValue ().toString ();
                    //stamp=stamp.substring(0,5)+ " " + stamp.substring(11,16);
                    listDATE.add(new AxisValue(count).setLabel ( stamp)  );

                }

                Axis axisDate = new Axis (  )
                        .setValues(listDATE)
                        .setHasTiltedLabels ( true )
                        .setHasLines(true)
                        .setMaxLabelChars ( 3 )
                        .setHasSeparationLine ( true );


                //ChartCLPH
                chartCLPH.setInteractive(false);
                //In most cased you can call data model methods in builder-pattern-like manner.
                Line lineCL = new Line(listCL)
                        .setColor(Color.BLUE)
                        .setStrokeWidth(3)
                        .setCubic ( true );
                Line linePH = new Line(listPH)
                        .setColor(Color.RED)
                        .setStrokeWidth(3)
                        .setCubic ( true );
                ;
                List<Line> linesCLPH = new ArrayList<Line>();
                linesCLPH.add(lineCL);
                linesCLPH.add(linePH);

                LineChartData dataCLPH = new LineChartData();
                dataCLPH.setLines(linesCLPH);
                dataCLPH.setAxisYLeft(new Axis()
                        .setName("PH")
                        .setTextColor ( Color.RED )
                        .setHasLines(true)
                        .setAutoGenerated ( true )
                        .setMaxLabelChars(3));

                dataCLPH.setAxisYRight (new Axis()
                        .setName("CL")
                        .setTextColor ( Color.BLUE )
                        .setHasLines(true)
                        .setAutoGenerated ( true )
                        .setMaxLabelChars(3));




                dataCLPH.setAxisXBottom ( axisDate );
                chartCLPH.setLineChartData(dataCLPH);

                //ChartTETX
                chartTETX.setInteractive(false);
                //Format des lignes.
                Line lineTE = new Line(listTE)
                        .setColor(Color.BLUE)
                        .setCubic ( true )
                        .setStrokeWidth ( 2 );
                Line lineTX = new Line(listTX)
                        .setColor(Color.RED)
                        .setCubic ( true )
                        .setStrokeWidth ( 2)
                        ;

                List<Line> linesTETX = new ArrayList<Line>();
                linesTETX.add(lineTE);
                linesTETX.add(lineTX);

                LineChartData dataTETX = new LineChartData();
                // Axe vertical

                dataTETX.setAxisYLeft(new Axis()
                        .setName("Temp. ext.")
                        .setTextColor ( Color.RED )
                        .setHasLines(true)
                        .setAutoGenerated ( true )
                        .setMaxLabelChars(3));
                dataTETX.setAxisYRight (new Axis()
                        .setName("Temp. eau")
                        .setTextColor ( Color.BLUE )
                        .setHasLines(true)
                        .setAutoGenerated ( true )
                        .setMaxLabelChars(3));




                dataTETX.setLines(linesTETX);
                dataTETX.setAxisXBottom ( axisDate );
                chartTETX.setLineChartData(dataTETX);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w ( MainActivity.TAG, "loadPost:onCancelled", databaseError.toException () );
                // ...
            }
        } );
    }


}