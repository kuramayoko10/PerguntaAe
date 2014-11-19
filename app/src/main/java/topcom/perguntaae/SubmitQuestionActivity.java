package topcom.perguntaae;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guicc on 10/28/14.
 */
public class SubmitQuestionActivity extends Activity implements Button.OnClickListener
{
    private String[] categories = {"Biologia", "Fisica", "Geografia", "Historia", "Ingles", "Matematica", "Portugues", "Quimica"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View inflatedView = getLayoutInflater().inflate(R.layout.activity_submit_question, null);
        setContentView(inflatedView);

        Button buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);

        Spinner spinnerCategories = (Spinner)findViewById(R.id.spinnerCategories);
        String[] categories = new String[11];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);

        categories[0] = "Artes";
        categories[1] = "Biologia";
        categories[2] = "Educacao Fisica";
        categories[3] = "Filosofia";
        categories[4] = "Fisica";
        categories[5] = "Geografia";
        categories[6] = "Historia";
        categories[7] = "Ingles";
        categories[8] = "Matematica";
        categories[9] = "Portugues";
        categories[10] = "Quimica";
        //categories[11] = "";

        spinnerCategories.setAdapter(adapter);
    }


    @Override
    public void onClick(View view)
    {
        //Question newQuestion;
        EditText editTitle = (EditText)findViewById(R.id.editTitle);
        EditText editQuestion = (EditText)findViewById(R.id.editQuestion);
        Spinner spinnerCategories = (Spinner)findViewById(R.id.spinnerCategories);
        String filename = editTitle.getText().toString().replaceAll(" ", "");
        int idx;
        UserProfile user = ((HomeActivity)HomeActivity.activity).getUser();

        idx = spinnerCategories.getSelectedItemPosition();

        Question newQuestion = new Question(editTitle.getText().toString(), editQuestion.getText().toString(), categories[idx], user.getName(), user.getID());

        //Save to file
        try
        {
            //File path = new File(filename);
            ///ContextWrapper cw = new ContextWrapper(getApplicationContext());
            //File directory = cw.getDir("assets/questions", Context.MODE_PRIVATE);
            FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            //outputStream = new FileOutputStream(path);
            String sectionDiv = "_pa_section_", author = "newuser", score = "0";
            byte[] buffer = new byte[sectionDiv.length()], buffer2 = new byte[author.length()];
            buffer = sectionDiv.getBytes();
            buffer2 = author.getBytes();

            outputStream.write(editTitle.getText().toString().getBytes());
            outputStream.write(buffer);

            outputStream.write(editQuestion.getText().toString().getBytes());
            outputStream.write(buffer);

            outputStream.write(buffer2);
            outputStream.write(buffer);

            buffer2 = new byte[categories[idx].length()];
            buffer2 = categories[idx].getBytes();
            outputStream.write(buffer2);
            outputStream.write(buffer);

            buffer2 = new byte[score.length()];
            buffer2 = score.getBytes();
            outputStream.write(buffer2);
            outputStream.write(buffer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Refresh
        ((HomeActivity)HomeActivity.activity).addQuestionToBank(newQuestion);
        ((HomeActivity)HomeActivity.activity).refreshList();

        //Go back to the home activity
        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeIntent);
    }
}
