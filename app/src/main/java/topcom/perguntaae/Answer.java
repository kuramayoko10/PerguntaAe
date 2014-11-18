package topcom.perguntaae;

/**
 * Created by guicc on 10/29/14.
 */
public class Answer
{
    public static final int nfields = 5;

    private int id;
    private int idp;
    private int idu;
    private String content;
    private String date;
    private int score;
    private String author;

    public Answer(int id_, int idp_, int idu_, String content_, String date_, int score_, String author_)
    {
        id = id_;
        idp = idp_;
        idu = idu_;
        content = content_;
        date = date_;
        score = score_;
        author = author_;
    }

    public Answer(String content_, String author_, int idu_)
    {
        content = content_;
        author = author_;
        idu = idu_;
    }

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

    public void setScore(int score_)
    {
        score = score_;
    }
    public int getScore()            { return score; }

    public void setDate(String date_)   { date = date_; }
    public String getDate()             { return date; }

    public String[] getSections()
    {
        String[] sections = new String[4];

        sections[0] = content;
        sections[1] = date;
        sections[2] = String.valueOf(score);
        sections[3] = author;
        sections[4] = String.valueOf(idu);

        return sections;
    }

}
