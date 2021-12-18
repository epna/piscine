
package com.sgdm.piscine;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.appyvet.materialrangebar.RangeBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ParametresFragment extends Fragment {
public RangeBar rb_Session1, rb_Session2, rb_Session3, rb_Session4, rb_frequence;
public Button btn_Filtration;
public Boolean manuel=false;

final String TAGP = "=piscine=";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate ( R.layout.fragment_parametres, container, false );
        rb_frequence = root.findViewById ( R.id.rb_frequence );
        rb_Session1= root.findViewById ( R.id.rb_session1 );
        rb_Session2= root.findViewById ( R.id.rb_session2 );
        rb_Session3=root.findViewById ( R.id.rb_session3);
        rb_Session4= root.findViewById ( R.id.rb_session4);
        btn_Filtration = root.findViewById ( R.id.btn_Filtration );

        initdata ();

        return root;
    }

    public void initdata() {






        FirebaseDatabase database = FirebaseDatabase.getInstance ();
        final DatabaseReference myRef = database.getReference ( "/dataPiscine" );
        myRef.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rb_Session1.setRangePinsByValue (
                        Integer.parseInt ( dataSnapshot.child ( "debut1" ).getValue ().toString () ),
                        Integer.parseInt ( dataSnapshot.child ( "fin1" ).getValue ().toString () ) );
                rb_Session2.setRangePinsByValue (
                        Integer.parseInt ( dataSnapshot.child ( "debut2" ).getValue ().toString () ),
                        Integer.parseInt ( dataSnapshot.child ( "fin2" ).getValue ().toString () ) );
                rb_Session3.setRangePinsByValue (
                        Integer.parseInt ( dataSnapshot.child ( "debut3" ).getValue ().toString () ),
                        Integer.parseInt ( dataSnapshot.child ( "fin3" ).getValue ().toString () ) );
                rb_Session4.setRangePinsByValue (
                        Integer.parseInt ( dataSnapshot.child ( "debut4" ).getValue ().toString () ),
                        Integer.parseInt ( dataSnapshot.child ( "fin4" ).getValue ().toString () ) );
                rb_frequence.setSeekPinByValue ( ( Integer.parseInt (dataSnapshot.child ( "frequence" ).getValue ().toString ()))) ;
                boolean xxx = (Integer.parseInt ( dataSnapshot.child ( "automatique" ).getValue ().toString () )==0);
                btn_Filtration.setEnabled( xxx);

                if (Integer.parseInt ( dataSnapshot.child ( "manuel" ).getValue ().toString ())==1 )
                {
                    btn_Filtration.setText ( getString(R.string.stop) );
                    manuel=true;
                }
                else
                {
                    btn_Filtration.setText ( getString(R.string.start) );
                    manuel=false;
                }
                }


            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w ( TAGP, "loadPost:onCancelled", databaseError.toException () );
                // ...
            }
        } );


        btn_Filtration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance ();
                final DatabaseReference myRef = database.getReference ( "/dataPiscine" );
                float  etat = 0;
                if (manuel) {
                    etat = 0;
                    btn_Filtration.setText ( getString ( R.string.stop ) );

                }
                else {
                    etat = 1;
                    btn_Filtration.setText ( getString ( R.string.start ) );
                }
                manuel=!manuel;
                myRef.child("manuel").setValue ( etat);
            }
        });
        }


    @Override
    public void onPause() {
        super.onPause ();
        FirebaseDatabase database = FirebaseDatabase.getInstance ();
        final DatabaseReference myRef = database.getReference ( "/dataPiscine" );


        myRef.child("debut1").setValue ( Integer.parseInt ( rb_Session1.getLeftPinValue () ));
        myRef.child("fin1").setValue ( Integer.parseInt ( rb_Session1.getRightPinValue () ));
        myRef.child("debut2").setValue ( Integer.parseInt ( rb_Session2.getLeftPinValue () ));
        myRef.child("fin2").setValue ( Integer.parseInt ( rb_Session2.getRightPinValue () ));
        myRef.child("debut3").setValue ( Integer.parseInt ( rb_Session3.getLeftPinValue () ));
        myRef.child("fin3").setValue ( Integer.parseInt ( rb_Session3.getRightPinValue () ));
        myRef.child("debut4").setValue ( Integer.parseInt ( rb_Session4.getLeftPinValue () ));
        myRef.child("fin4").setValue ( Integer.parseInt ( rb_Session4.getRightPinValue () ));
        myRef.child("frequence").setValue ( Integer.parseInt ( rb_frequence.getRightPinValue () ));
    }
}




