package topcom.perguntaae;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by guicc on 10/25/14.
 */
public class QuestionDetailsActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            String[] details = extras.getStringArray("question_details");
            TextView questionTitle = (TextView)findViewById(R.id.textQuestTitle);
            TextView questionContent = (TextView)findViewById(R.id.textQuestContent);
            TextView questionAuthor = (TextView)findViewById(R.id.textQuestAuthor);

            questionTitle.setText(details[0]);
            questionContent.setText(details[1]);
            questionAuthor.setText(details[2]);
        }
    }
}
