package topcom.perguntaae;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by guicc on 10/25/14.
 */
public class QuestionPreviewFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_question_preview, container, false);

        return view;
    }

    public void setTitle(String title)
    {
        TextView titleView = (TextView)getView().findViewById(R.id.textTitle);
        titleView.setText(title);
    }

    public void setContent(String content)
    {
        TextView contentView = (TextView)getView().findViewById(R.id.textContent);
        contentView.setText(content);
    }

    public void setUser(String user)
    {
        TextView userView = (TextView)getView().findViewById(R.id.textUser);
        userView.setText(user);
    }
}
