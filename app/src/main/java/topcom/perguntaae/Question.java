package topcom.perguntaae;


public class Question
{
    private String title;
    private String content;
    private String author;
    private String category;
    private int score;

    public Question(String title_, String content_, String author_, String category_, int score_)
    {
        title = title_;
        content = content_;
        author = author_;
        category = category_;
        score = score_;
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
}
