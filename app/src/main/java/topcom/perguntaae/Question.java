package topcom.perguntaae;


import java.util.ArrayList;

public class Question
{
    public static final int nfields = 6;

    private int id;
    private int idu;
    private String title;
    private String content;
    private String category;
    private String date;
    private String author;
    private int answerCount;
    private ArrayList<Answer> answers;

    public Question(int id_, int idu_, String title_, String content_, String category_, String date_, String author_, int answerCount_)
    {
        id = id_;
        idu = idu_;
        title = title_;
        content = content_;
        category = category_;
        date = date_;
        author = author_;
        answerCount = answerCount_;

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

    public void addAnswer(Answer a)         { answers.add(a); }
    public ArrayList<Answer> getAnswers()   { return answers; }
    public int getAnswerCount()             { return answerCount; }

    public String[] getSections()
    {
        int n = Question.nfields + answers.size()*(Answer.nfields);
        String[] sections = new String[n];

        sections[0] = title;
        sections[1] = content;
        sections[2] = category;
        sections[3] = date;
        sections[4] = author;
        sections[5] = String.valueOf(idu);

        int k = 6;
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
