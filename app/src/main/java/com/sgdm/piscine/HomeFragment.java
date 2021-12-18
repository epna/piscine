package com.sgdm.piscine;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;


public class HomeFragment extends Fragment {

    public TextView  txt_notif1, txt_notif2;
    final String TAG = "PISCINE ==> ";
    public GaugeView xxPiscine;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate ( R.layout.fragment_home, container, false );
        root = inflater.inflate ( R.layout.fragment_home, container, false );
        txt_notif1 = root.findViewById ( R.id.txt_notif1 );
        txt_notif2 = root.findViewById ( R.id.txt_notif2 );
        xxPiscine = root.findViewById ( R.id.view_Piscine );
        FirebaseMessaging.getInstance ().subscribeToTopic ( "sgdm_piscine" );





        initdata ();
        return root;
    }

    public void initdata() {
        FirebaseDatabase database = FirebaseDatabase.getInstance ();
        DatabaseReference myRef = database.getReference ( "/constantePiscine" );
        myRef.limitToLast ( 1 );
        myRef.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot readData : dataSnapshot.getChildren ()) {
                    xxPiscine.mCL = Float.parseFloat ( readData.child ( "cl" ).getValue ().toString ()) ;
                    xxPiscine.mPH = Float.parseFloat ( readData.child ( "ph" ).getValue ().toString ());
                    xxPiscine.mTE = Float.parseFloat ( readData.child ( "te" ).getValue ().toString ());
                    xxPiscine.mTX = Float.parseFloat ( readData.child ( "tx" ).getValue ().toString ());
                    txt_notif2.setText ( String.format ( "derniére mise à jour %s", readData.child ( "stamp" ).getValue ().toString () ) );
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w ( TAG, "loadPost:onCancelled", databaseError.toException () );
                // ...
            }
        } );

        DatabaseReference ref = database.getReference ( "/dataPiscine" );
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot readData) {
                txt_notif1.setText ( "Filtration arrêtée" );
                if (Integer.parseInt ( readData.child ( "manuel" ).getValue ().toString ()) == 1)
                    txt_notif1.setText ( "Filtration manuelle en cours" );
                if (Integer.parseInt ( readData.child ( "automatique" ).getValue ().toString () ) > 0 ) {
                    Integer session = Integer.parseInt ( readData.child ( "automatique" ).getValue ().toString () );
                    String tmp = "Filtration  automatique en cours" + System.getProperty("line.separator") + " Session " +
                            session.toString ()  + " " +
                            "fin prévue à " +
                            readData.child ( "fin" +  session.toString () ).getValue ().toString () + ":00";

                    txt_notif1.setText ( tmp );
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }
}