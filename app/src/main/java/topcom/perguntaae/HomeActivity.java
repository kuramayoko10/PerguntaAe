package topcom.perguntaae;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

//I had to add both GoogleApiClient and GooglePlayServicesClient interfaces... google guide suggests only the latter!!
public class HomeActivity extends Activity implements QuestionListFragment.OnItemSelectedListener, ImageButton.OnClickListener
{
    private ArrayList<Question> questionBank = new ArrayList<Question>();
    private FragmentTransaction ft;
    private UserProfile user;

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View inflatedView = getLayoutInflater().inflate(R.layout.activity_home, null);
        setContentView(inflatedView);

        activity = this;

        //Get user data from login and create user profile
        String fullname = (String)getIntent().getExtras().get("fullname");
        String email = (String)getIntent().getExtras().get("email");
        String bday = (String)getIntent().getExtras().get("bday");

        //Use email as key to check if user is already on DB
        ClientSend cs = new ClientSend("GET_USER " + email + " ");
        cs.execute();

        try {
            //Wait until the query finishes
            cs.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Insert a new user entry on the DB if necessary
        if(cs.getResponse().equals("NOT_FOUND"))
        {
            String insert = fullname + " " + email;
            cs = new ClientSend("INSERT_USER " + insert + " ");
            cs.execute();

            try {
                //Wait until the query finishes
                cs.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        Vector userData = cs.getData();
        user = new UserProfile((Integer)userData.elementAt(0), (String)userData.elementAt(1), (String)userData.elementAt(2), (Integer)userData.elementAt(8));


        //Assemble the fragment that deals with the question list and preview
        QuestionListFragment fragment = (QuestionListFragment)getFragmentManager().findFragmentById(R.id.listFragment);

        if(fragment != null && fragment.isInLayout()) //Safety check
        {
            ft = getFragmentManager().beginTransaction();
            try {
                fragment.setupView();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ft.add(R.id.listFragment, fragment);
            ft.commit();
        }
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

    public UserProfile getUser()        {return user;}

}
