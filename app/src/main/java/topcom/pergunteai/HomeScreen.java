package topcom.pergunteai;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;


public class HomeScreen extends Activity
{
    ImageButton btRank;
    ImageButton btProfile;
    ImageButton btSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Set this class to work with the proper activity layout
        setContentView(R.layout.activity_home_screen);

        btProfile = (ImageButton)findViewById(R.id.button_profile);
        btProfile.setOnClickListener(btProfileHandler);

        btSubmit = (ImageButton)findViewById(R.id.button_submit);
        btSubmit.setOnClickListener(btSubmitHandler);
    }

    private View.OnClickListener btProfileHandler = new View.OnClickListener()
    {
        public void onClick(View view)
        {
            Context context = getApplicationContext();
            Intent profileIntent = new Intent(context, ProfileScreen.class);

            startActivity(profileIntent);
        }
    };

    private View.OnClickListener btSubmitHandler = new View.OnClickListener()
    {
        public void onClick(View view)
        {
            Context context = getApplicationContext();
            Intent submitIntent = new Intent(context, SubmitScreen.class);

            startActivity(submitIntent);
        }
    };


}
