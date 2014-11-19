package topcom.perguntaae;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class QuestionDetailsActivity extends Activity implements Button.OnClickListener
{
    private Integer idx;
    public static Activity activity;
    private ArrayAdapter<String[]> itemAdapter;
    private ArrayList<String[]> answerList;
    private String[] details;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View inflatedView = getLayoutInflater().inflate(R.layout.activity_question_details, null);
        setContentView(inflatedView);

        activity = this;

        //ID of the question the user selected
        idx = (Integer)getIntent().getExtras().get("idx");

        Button buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);

        setupView();
    }

    public void setupView()
    {
        Question q = ((HomeActivity)HomeActivity.activity).getQuestionByIndex(idx.intValue());
        details = q.getSections();

        ListView listAnswers = (ListView)findViewById(R.id.answer_list);
        TextView questionTitle = (TextView)findViewById(R.id.textQuestTitle);
        TextView questionContent = (TextView)findViewById(R.id.textQuestContent);
        TextView questionAuthor = (TextView)findViewById(R.id.textQuestAuthor);     //exhibits name, score and date

        questionTitle.setText(details[0]);
        questionContent.setText(details[1]);

        //TODO: Grab author's score... idu on details[5]
        int score = 0;
        String aux = details[3] + String.valueOf(score);
        questionAuthor.setText(aux);

        int answerSize = details.length;
        int i = Question.nfields;
        while(i < answerSize)
        {
            String[] content = new String[Answer.nfields];

            for(int j = 0; j < Answer.nfields; j++)
                content[j] = details[i++];

            answerList.add(content);
        }

        itemAdapter = new AnswerAdapter(getApplicationContext(), answerList);
        listAnswers.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    //Answer submission
    public void onClick(View view)
    {
        TextView title = (TextView)findViewById(R.id.textQuestTitle);
        EditText editAnswer = (EditText)findViewById(R.id.editAnswer);

        //Get User's name and id
        UserProfile user = ((HomeActivity)HomeActivity.activity).getUser();

        //Create an incomplete object Answer
        Answer newAnswer = new Answer(editAnswer.getText().toString(), user.getName(), user.getID());

        //Save to DB
        ClientSend cs = new ClientSend("SUBMIT_ANSWER ");

        try
        {
            //Wait until the process ends
            cs.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Refresh
        if(cs.getResponse().equals("INSERTION_OK"))
        {
            answerList.add(newAnswer.getSections());
            itemAdapter.notifyDataSetChanged();
        }
        else
        {
            //TODO: Pop a window warning the user

        }
    }
}
