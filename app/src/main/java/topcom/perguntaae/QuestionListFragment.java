package topcom.perguntaae;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

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

        //Fill up the table

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
}
