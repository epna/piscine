package com.sgdm.piscine;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    final static String CHANNEL_ID = "4952";
    public static final String TAG = "== Arrosage == ";
    public Boolean sortie = false;
    public FirebaseAuth mAuth;
    public int RC_SIGN_IN = 2021;
    public static FirebaseUser Fuser;
    public GoogleSignInClient mygoogleSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        FragmentManager supportFragmentManager= getSupportFragmentManager ();
        NavHostFragment navHostFragment =
                (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        //ImageView imageView = (ImageView) findViewById(R.id.image_login);
        //Glide.with(this).load(mUser.getPhotoUrl ().toString ()).into(imageView);

       //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(bottomNav, navController);
        FirebaseMessaging.getInstance ().subscribeToTopic ( "sgdm_piscine" );
        createNotificationChannel ();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            FirebaseMessaging.getInstance ().subscribeToTopic ( "sgdm_piscine" );
            createNotificationChannel ();
            sortie = true;
        }
    }




    private void createNotificationChannel() {
        // Créer le NotificationChannel, seulement pour API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "sgdm_piscine";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel ( CHANNEL_ID, name, importance );
            channel.setDescription ( "Notification channel description" );
            // Enregister le canal sur le système : attention de ne plus rien modifier après
            NotificationManager notificationManager = getSystemService ( NotificationManager.class );
            Objects.requireNonNull ( notificationManager ).createNotificationChannel ( channel );
        }
    }



}