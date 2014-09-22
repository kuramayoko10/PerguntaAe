package topcom.pergunteai;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by guicc on 9/22/14.
 */
public class ProfileScreen extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Set the back button on action bar
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //Set this class to work with the proper activity layout
        setContentView(R.layout.activity_profile_screen);
    }
}
