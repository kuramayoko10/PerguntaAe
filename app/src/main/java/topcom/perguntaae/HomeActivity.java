package topcom.perguntaae;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

//I had to add both GoogleApiClient and GooglePlayServicesClient interfaces... google guide suggests only the latter!!
public class HomeActivity extends Activity implements QuestionListFragment.OnItemSelectedListener, ImageButton.OnClickListener
{
    private ArrayList<Question> questionBank = new ArrayList<Question>();
    public static Activity activity;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        activity = this;
        ft = getFragmentManager().beginTransaction();

        QuestionListFragment fragment = (QuestionListFragment)getFragmentManager().findFragmentById(R.id.listFragment);

        if(fragment != null && fragment.isInLayout()) //Safety check
        {
            fragment.setupView();
            ft.add(R.id.listFragment, fragment);
            ft.commit();
        }

        ImageButton buttonSubmit = (ImageButton)findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);
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
            intent.putExtra("idx", Integer.valueOf(index));
            startActivity(intent);
        }
    }

    public void addQuestionToBank(Question q)
    {
        questionBank.add(q);
    }

    public ArrayList<Question> getQuestionBank()    { return questionBank; }

    public void addAnswerToQuestion(int index, Answer a)
    {
        questionBank.get(index).addAnswer(a);
    }

    public Question getQuestionByIndex(int i)
    {
        return questionBank.get(i);
    }

    @Override
    public void onClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(), SubmitQuestionActivity.class);
        startActivity(intent);
    }

    public void refresh()
    {
        QuestionListFragment fragment = (QuestionListFragment) getFragmentManager().findFragmentById(R.id.listFragment);

        fragment.refreshList();

        //ft = getFragmentManager().beginTransaction();
        //ft.detach(fragment);
        //ft.attach(fragment);
        //ft.commitAllowingStateLoss();
    }
}
