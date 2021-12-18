package com.sgdm.piscine;


import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;



public class MyGoogleSignIn extends AppCompatActivity {

    public  FirebaseAuth mAuth;
    public int RC_SIGN_IN = 2021;
    private String TAG = "LOGIN";
    public GoogleSignInClient mygoogleSignin;
    public static FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        mAuth = FirebaseAuth.getInstance ();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //if (account==null) signing (); else firebaseAuthWithGoogle(account.getIdToken ());
        signing();
   }
    public void signing() {
        mAuth = FirebaseAuth.getInstance ();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder ( GoogleSignInOptions.DEFAULT_SIGN_IN )
                .requestIdToken ( getString ( R.string.default_web_client_id ) )
                .requestEmail ()
                .build ();
        mygoogleSignin = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mygoogleSignin.getSignInIntent ();
        startActivityForResult ( signInIntent, RC_SIGN_IN );
        // }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult( ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken ());
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
        Log.w(TAG, "signInResult:OK  code=22222" );
    }

    private void firebaseAuthWithGoogle(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()) {
                            FirebaseUser user = mAuth.getCurrentUser ();
                            Intent intent = new Intent(getApplicationContext (), MainActivity.class);
                            mUser = user;
                            startActivity(intent);
                        } else {
                            Log.w ( TAG, "signInWithCredential:failure", task.getException () );
                        }
                    }
                });
    }

}