package topcom.perguntaae;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by guicc on 10/25/14.
 */
public class QuestionListFragment extends Fragment
{
    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener
    {
        public void OnTableRowSelected(String title, String content, String author);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_list, container, false);

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        if(activity instanceof OnItemSelectedListener)
        {
            listener = (OnItemSelectedListener) activity;
        }
        else
        {
            throw new ClassCastException(activity.toString() + " must implement QuestionListFragment.OnItemSelectedListener");
        }
    }

    public void setupView()
    {
        //Fill up the table
        AssetManager assets = getActivity().getAssets();
        String[] files = null, sections = null;
        String fileContent;
        ArrayList<String> questionList = new ArrayList<String>();
        ListView table = (ListView)getView().findViewById(R.id.listTable);
        ArrayAdapter<String> itemAdapter;

        try {
            files = assets.list("questions");  //Read files from assets/questions dir
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream input;
        try
        {
            for(int i = 0; i < files.length; i++)
            {
                int size;
                byte[] buffer;
                String dir = "questions/";
                Question q;

                input = assets.open(dir.concat(files[i]));
                size = input.available();
                buffer = new byte[size];
                input.read(buffer);
                input.close();

                fileContent = new String(buffer);
                sections = fileContent.split("_pa_section_");

                if(sections.length == 3) {
                    q = new Question(sections[0], sections[1], sections[2]);
                    ((HomeActivity)getActivity()).addQuestionToBank(q);
                    questionList.add(sections[0]);
                }
                else
                    throw new IOException("Cannot find three sections on question read");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Notify only once after every question has been read
        itemAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, questionList);
        table.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
    }
}
