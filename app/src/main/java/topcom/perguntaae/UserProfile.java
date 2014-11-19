package topcom.perguntaae;

import java.util.ArrayList;

public class UserProfile
{
    private String fullname;
    private String email;
    private int id;
    private int score;
    private ArrayList<Question> myQuestions;
    private ArrayList<Answer> myAnswers;
    private String selectedCategory = "Biologia";

    public UserProfile(int id_, String fullname_, String email_, int score_)
    {
        id = id_;
        fullname = fullname_;
        email = email_;
        score = score_;
    }

    public UserProfile(String fullname_, String email_)
    {
        fullname = fullname_;
        email = email_;
    }

    public void setID(int id_)  {id = id_;}
    public void setCategory(String category_)   {selectedCategory = category_;}

    public String getName()     {return fullname;}
    public int getID()          {return id;}
    public int getScore()       {return score;}
    public String getSelectedCategory() {return selectedCategory;}

    public ArrayList<Question> getMyQuestions()     {return myQuestions;}
    public ArrayList<Answer> getMyAnswers()     {return myAnswers;}
}
