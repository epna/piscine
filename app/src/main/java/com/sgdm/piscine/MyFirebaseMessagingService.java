package com.sgdm.piscine;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public String mMessage, mFin;
    public static final String TAG = "== Arrosage == ";
    public Map<String, String> data;
    public Integer mType;
    public Integer message, session;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived ( remoteMessage );
        RemoteMessage.Notification notification = remoteMessage.getNotification ();
        data = remoteMessage.getData ();
        message = Integer.parseInt ( remoteMessage.getData ().get("message").toString ());
        session = Integer.parseInt ( remoteMessage.getData ().get("session").toString ());
        Log.d(TAG,remoteMessage.getData ().get("message"));
        Log.d(TAG,remoteMessage.getData ().get("session").toString ());
        retrievemessage ();
    }


    public void sendNotification2(Integer mSession) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from ( this );
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder ( this, MainActivity.CHANNEL_ID )
                .setSmallIcon ( R.drawable.ic_fcm )
                .setContentTitle ( mMessage )
                .setPriority ( NotificationCompat.PRIORITY_DEFAULT );
        if (mSession > -1 && message == 20)
            notifBuilder.setContentText ( "Session " + mSession.toString () + "  Fin prévue à " + mFin + ":00" );
        if (mSession > -1 && message == 19)
            notifBuilder.setContentText ( "Session " + mSession.toString () );

        Bitmap bitmap_large = BitmapFactory.decodeResource ( this.getResources (), R.drawable.piscine );
        Drawable d = new BitmapDrawable ( getResources (), bitmap_large );
        notifBuilder.setLargeIcon ( bitmap_large )
                .setStyle ( new NotificationCompat.BigPictureStyle ()
                        .bigPicture ( bitmap_large )
                        .bigLargeIcon ( null ) );

        Random random = new Random ();
        int m = random.nextInt ( 9999 - 1000 ) + 1000;
        notificationManager.notify ( m, notifBuilder.build () );
    }


    public void retrievemessage() {
        FirebaseDatabase database = FirebaseDatabase.getInstance ();
        DatabaseReference myRef = database.getReference ( "message" );

        myRef.addListenerForSingleValueEvent ( new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMessage = (String) dataSnapshot.child ( data.get ( "message" ) + "/libelle" ).getValue ();
                if (Integer.parseInt ( data.get ( "message" ) ) == 20)
                {
                    getFin(Integer.parseInt ( data.get ( "session" ).toString () ));
                }

                    else sendNotification2 ( Integer.parseInt ( data.get ( "session" ).toString () ) );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v ( MainActivity.TAG, "erreur Firebase" );
            }
        } );
    }

    public void getFin(int session) {
        FirebaseDatabase database = FirebaseDatabase.getInstance ();
        String extension = "fin"+session;
        DatabaseReference myRef = database.getReference ( "/dataPiscine/"+extension );



        myRef.addListenerForSingleValueEvent ( new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                mFin= dataSnapshot.getValue(Integer.class).toString ();
                sendNotification2 ( Integer.parseInt ( data.get ( "session" ).toString () ));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v ( MainActivity.TAG, "erreur Firebase" );
            }
        } );
    }
}






