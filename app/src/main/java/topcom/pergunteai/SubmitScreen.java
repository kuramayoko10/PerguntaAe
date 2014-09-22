package topcom.pergunteai;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;

/**
 * Created by guicc on 9/22/14.
 */
public class SubmitScreen extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Hide the input method until the user actually selects an EditText object
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Set the back button on action bar
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //Set this class to work with the proper activity layout
        setContentView(R.layout.activity_submit_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);

        return true;
    }
}
