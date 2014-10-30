package topcom.perguntaae;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by guicc on 10/29/14.
 */
public class AnswerAdapter extends ArrayAdapter<String[]>
{
    private ArrayList<String[]> values = null;
    private final Context context;

    public AnswerAdapter(Context context, ArrayList<String[]> values)
    {
        super(context, R.layout.layout_answer_row, values);

        this.context = context;
        this.values = values;
    }

    public View getView(int position, View convertView, ViewGroup container)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_answer_row, container, false);

        TextView textContent = (TextView)view.findViewById(R.id.textAnsContent);
        TextView textAuthor = (TextView)view.findViewById(R.id.textAnsUser);

        String[] currentAnswer = values.get(position);

        textContent.setText(currentAnswer[0]);
        textAuthor.setText(currentAnswer[1]);

        return view;
    }
}
