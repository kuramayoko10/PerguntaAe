package topcom.perguntaae;

/**
 * Created by guicc on 10/29/14.
 */
public class Answer
{
    public static final int nfields = 3;

    private String content;
    private String author;
    private int score;

    public Answer(String content_, String author_, int score_)
    {
        content = content_;
        author = author_;
        score = score_;
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

    public String[] getSections()
    {
        String[] sections = new String[3];

        sections[0] = content;
        sections[1] = author;
        sections[2] = String.valueOf(score);

        return sections;
    }

}
