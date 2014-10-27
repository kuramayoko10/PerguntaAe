package topcom.perguntaae;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guicc on 10/27/14.
 */
public class SpecialArrayAdapter extends ArrayAdapter<Question>
{
    private ArrayList<Question> values = null;
    private final Context context;

    public SpecialArrayAdapter(Context context, ArrayList<Question> values)
    {
        super(context, R.layout.layout_tablerow, values);

        this.context = context;
        this.values = values;
    }

    public View getView(int position, View convertView, ViewGroup container)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_tablerow, container, false);
        TextView textTitle = (TextView)view.findViewById(R.id.textQuestTitle);
        TextView textCategory = (TextView)view.findViewById(R.id.textCategory);
        TextView textScore = (TextView)view.findViewById(R.id.textScore);

        Question q = values.get(position);

        String title = q.getTitle();
        String category = q.getCategory();
        int score = q.getScore();

        textTitle.setText(title);
        textCategory.setText(category);
        textScore.setText(String.valueOf(score));

        if(position % 2 == 0)
        {
            //transparent
            //view.setBackgroundColor(context.getResources().getColor(R.color.even_row_color));
            //view.invalidate();
        }
        else
        {
            view.setBackgroundColor(context.getResources().getColor(R.color.odd_row_color));
            view.invalidate();
        }

        return view;
    }
}
