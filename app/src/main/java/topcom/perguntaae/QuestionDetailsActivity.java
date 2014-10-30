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

/**
 * Created by guicc on 10/25/14.
 */
public class QuestionDetailsActivity extends Activity implements Button.OnClickListener
{
    private Integer idx;
    public static Activity activity;
    private ArrayAdapter<String[]> itemAdapter;
    private ArrayList<String[]> answers;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);

        activity = this;
        answers = new ArrayList<String[]>();

        idx = (Integer)getIntent().getExtras().get("idx");

        //Set the back button on action bar
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Button buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);

        setupView();
    }

    public void setupView()
    {
        String[] details = ((HomeActivity)HomeActivity.activity).getQuestionByIndex(idx.intValue()).getSections();
        ListView listAnswers = (ListView)findViewById(R.id.answer_list);
        TextView questionTitle = (TextView)findViewById(R.id.textQuestTitle);
        TextView questionContent = (TextView)findViewById(R.id.textQuestContent);
        TextView questionAuthor = (TextView)findViewById(R.id.textQuestAuthor);

        questionTitle.setText(details[0]);
        questionContent.setText(details[1]);
        questionAuthor.setText(details[2]);

        int answerSize = details.length;
        int i = Question.nfields;
        while(i < answerSize)
        {
            String[] content = new String[3];
            content[0] = details[i++];
            content[1] = details[i++];
            content[2] = details[i++];
            answers.add(content);
        }

        itemAdapter = new AnswerAdapter(getApplicationContext(), answers);
        listAnswers.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view)
    {
        TextView title = (TextView)findViewById(R.id.textQuestTitle);
        EditText editAnswer = (EditText)findViewById(R.id.editAnswer);
        String author = "answerUser", sectionDiv = "_pa_section_";
        byte[] buffer = sectionDiv.getBytes();
        Answer newAnswer = new Answer(editAnswer.getText().toString(), author, 0);

        //Save to file
        try
        {
            FileOutputStream outputStream = openFileOutput(title.toString().replaceAll(" ", ""), Context.MODE_APPEND);

            outputStream.write(buffer);
            outputStream.write(editAnswer.getText().toString().getBytes());
            outputStream.write(buffer);
            outputStream.write(author.getBytes());

            ((HomeActivity)HomeActivity.activity).addAnswerToQuestion(idx.intValue(), newAnswer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Refresh
        answers.add(newAnswer.getSections());
        itemAdapter.notifyDataSetChanged();
    }
}
