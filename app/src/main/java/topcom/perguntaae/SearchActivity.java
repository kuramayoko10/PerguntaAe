package topcom.perguntaae;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by guicc on 11/15/14.
 */
public class SearchActivity extends Activity
{
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        View inflatedView = getLayoutInflater().inflate(R.layout.activity_search, null);
        setContentView(inflatedView);
    }
}
