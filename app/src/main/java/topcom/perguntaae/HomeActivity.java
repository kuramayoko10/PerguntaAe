package topcom.perguntaae;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import java.util.ArrayList;


public class HomeActivity extends Activity implements QuestionListFragment.OnItemSelectedListener
{
    private ArrayList<Question> questionBank = new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        QuestionListFragment fragment = (QuestionListFragment)getFragmentManager().findFragmentById(R.id.listFragment);

        if(fragment != null && fragment.isInLayout()) //Safety check
        {
            fragment.setupView();
        }
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
    public void OnTableRowSelected(int index)
    {
        QuestionPreviewFragment fragment = (QuestionPreviewFragment)getFragmentManager().findFragmentById(R.id.previewFragment);
        Question selected = questionBank.get(index);

        if(fragment != null && fragment.isInLayout())   //Landscape mode
        {
            fragment.setTitle(selected.getTitle());
            fragment.setContent(selected.getContent());
            fragment.setUser(selected.getAuthor());
        }
        else //Portrait mode
        {
            Intent intent = new Intent(getApplicationContext(), QuestionDetailsActivity.class);
            String[] contentArray = {selected.getTitle(), selected.getContent(), selected.getAuthor()};

            intent.putExtra("question_details", contentArray);
            //answer_details
            startActivity(intent);
        }
    }

    public void addQuestionToBank(Question q)
    {
        questionBank.add(q);
    }

    public Question getQuestionByIndex(int i)
    {
        return questionBank.get(i);
    }
}
