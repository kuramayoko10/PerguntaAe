package topcom.perguntaae;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutionException;


public class QuestionListFragment extends Fragment
{
    private OnItemSelectedListener listener;
    private ArrayList<Question> questionList = new ArrayList<Question>();
    private ArrayAdapter<Question> itemAdapter;

    private String category = "Biologia";
    private Vector columnNames;
    private Vector data;

    public interface OnItemSelectedListener
    {
        public void OnTableRowSelected(int index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_list, container, false);

        ListView list = (ListView)view.findViewById(R.id.listTable);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.OnTableRowSelected(i);
            }
        });

        columnNames = new Vector();
        data = new Vector();

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        if(activity instanceof OnItemSelectedListener)
        {
            listener = (OnItemSelectedListener) activity;
            questionList = ((HomeActivity)activity).getQuestionBank();
        }
        else
        {
            throw new ClassCastException(activity.toString() + " must implement QuestionListFragment.OnItemSelectedListener");
        }
    }

    public void setupView() throws ExecutionException, InterruptedException {
        //Fill up the table
        UserProfile user = ((HomeActivity)HomeActivity.activity).getUser();
        Vector data = new Vector();
        ClientSend connect = new ClientSend("REFRESH home " + user.getSelectedCategory() + " ");

        connect.execute();
        connect.finishQuery(); //wait until it finishes

        data = connect.getData();

        if(data != null)
        {
            refreshList(data);
        }
    }

    public void refreshList(Vector data)
    {
        ListView table = (ListView)getView().findViewById(R.id.listTable);

        questionList.clear();
        for (int i = 0; i < data.size(); i++)
        {
            Question q;
            String author;
            Vector row = (Vector) data.get(i);
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");

            q = new Question((Integer)row.get(0), (Integer)row.get(1), (String) row.get(2), (String) row.get(3), (String) row.get(4), (String) df.format(row.get(5)), (String) row.get(9), (Long)row.get(10));
            questionList.add(q);
        }

        //Notify only once after every question has been read
        itemAdapter = new SpecialArrayAdapter(getActivity().getApplicationContext(), questionList);
        table.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
    }
}
