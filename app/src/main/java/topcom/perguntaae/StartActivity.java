package topcom.perguntaae;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class StartActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener
{
    //GoogleSignIn
    private GoogleApiClient googleSignIn;
    private boolean intentInProgress;
    private static final int RC_SIGN_IN = 0;

    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_start);

        //Google API Login
        googleSignIn = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }

    protected void onStart()
    {
        super.onStart();
        googleSignIn.connect();
    }

    protected void onStop()
    {
        super.onStop();

        if(googleSignIn.isConnected())
            googleSignIn.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        //Retrieve user profile data
        if(Plus.PeopleApi.getCurrentPerson(googleSignIn) != null)
        {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(googleSignIn);
            String name = currentPerson.getDisplayName();
            String nickname = currentPerson.getNickname();
            String email = Plus.AccountApi.getAccountName(googleSignIn);
            Person.Image photo = currentPerson.getImage();
            String birthday = currentPerson.getBirthday();

            //Start Home Activity
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("fullname", name);
            intent.putExtra("nickname", nickname);
            intent.putExtra("email", email);
            intent.putExtra("bday", birthday);
            startActivity(intent);
        }
    }

    @Override
    public void onDisconnected()
    {
        //Try to reconnect
        googleSignIn.connect();
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        //Try to reconnect
        googleSignIn.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        if(!intentInProgress && connectionResult.hasResolution())
        {
            try
            {
                intentInProgress = true;
                startIntentSenderForResult(connectionResult.getResolution().getIntentSender(), RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) //The intent was canceled before it was sent
            {
                intentInProgress = false;
                googleSignIn.connect();
            }
        }
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent)
    {
        if(requestCode == RC_SIGN_IN)
            intentInProgress = false;

        if(!googleSignIn.isConnecting())
            googleSignIn.connect();
    }
}
