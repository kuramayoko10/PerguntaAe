package topcom.perguntaae;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
        ImageButton imgCategory = (ImageButton)view.findViewById(R.id.imgCategory);
        TextView textAnsCount = (TextView)view.findViewById(R.id.textAnswerCount);

        Question q = values.get(position);

        String title = q.getTitle();
        String category = q.getCategory();
        int ansCount = q.getAnswerCount();

        textTitle.setText(title);
        textAnsCount.setText(String.valueOf(ansCount));

        if(category.equals("Artes"))
            imgCategory.setImageResource(R.drawable.artes);
        else if(category.equals("Biologia"))
            imgCategory.setImageResource(R.drawable.biologia);
        else if(category.equals("Educação Física"))
            imgCategory.setImageResource(R.drawable.edfisica);
        else if(category.equals("Filosofia"))
            imgCategory.setImageResource(R.drawable.filosofia);
        else if(category.equals("Física"))
            imgCategory.setImageResource(R.drawable.fisica);
        else if(category.equals("Geografia"))
            imgCategory.setImageResource(R.drawable.geografia);
        else if(category.equals("História"))
            imgCategory.setImageResource(R.drawable.historia);
        else if(category.equals("Inglês"))
            imgCategory.setImageResource(R.drawable.ingles);
        else if(category.equals("Matemática"))
            imgCategory.setImageResource(R.drawable.matematica);
        else if(category.equals("Multidisciplinar"))
            imgCategory.setImageResource(R.drawable.multi);
        else if(category.equals("Língua Portuguesa"))
            imgCategory.setImageResource(R.drawable.portugues);
        else if(category.equals("Química"))
            imgCategory.setImageResource(R.drawable.quimica);
        else if(category.equals("Sociologia"))
            imgCategory.setImageResource(R.drawable.sociologia);

        view.setBackgroundColor(Color.WHITE);

        return view;
    }
}
