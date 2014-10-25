package topcom.perguntaae;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;


public class HomeActivity extends Activity implements QuestionListFragment.OnItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnTableRowSelected(String title, String content, String author)
    {
        QuestionPreviewFragment fragment = (QuestionPreviewFragment)getFragmentManager().findFragmentById(R.id.previewFragment);

        if(fragment != null && fragment.isInLayout())   //Landscape mode
        {
            fragment.setTitle("Titulo");
            fragment.setContent("Conteudo");
            fragment.setUser("Usuario");
        }
        else //Portrait mode
        {
            Intent intent = new Intent(getApplicationContext(), QuestionDetailsActivity.class);
            String[] contentArray = {"Titulo", "Conteudo", "Usuario"};

            intent.putExtra("question_preview", contentArray);
            startActivity(intent);
        }
    }
}
