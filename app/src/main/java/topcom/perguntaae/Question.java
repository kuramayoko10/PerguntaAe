package topcom.perguntaae;


import java.util.ArrayList;

public class Question
{
    public static final int nfields = 5;

    private String title;
    private String content;
    private String author;
    private String category;
    private int score;
    private ArrayList<Answer> answers;

    public Question(String title_, String content_, String author_, String category_, int score_)
    {
        title = title_;
        content = content_;
        author = author_;
        category = category_;
        score = score_;

        answers = new ArrayList<Answer>();
    }

    public void setTitle(String title_)
    {
        title = title_;
    }
    public String getTitle()            { return title; }

    public void setContent(String content_)
    {
        content = content_;
    }
    public String getContent()              { return content; }

    public void setAuthor(String author_)
    {
        author = author_;
    }
    public String getAuthor()             { return author; }

    public void setCategory(String category_)
    {
        category = category_;
    }
    public String getCategory()                { return category; }

    public void setScore(int score_)
    {
        score = score_;
    }
    public int getScore()            { return score; }

    public void addAnswer(Answer a)
    {
        answers.add(a);
    }

    public String[] getSections()
    {
        int n = Question.nfields + answers.size()*(Answer.nfields);
        String[] sections = new String[n];

        sections[0] = title;
        sections[1] = content;
        sections[2] = author;
        sections[3] = category;
        sections[4] = String.valueOf(score);

        int k = 5;
        for(int i = 0; i < answers.size(); i++)
        {
            String[] answerSections = answers.get(i).getSections();

            for(int j = 0; j < answerSections.length; j++)
            {
                sections[k++] = answerSections[j];
            }
        }

        return sections;
    }
}
