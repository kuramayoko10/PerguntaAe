package topcom.perguntaae;


public class Question
{
    private String title;
    private String content;
    private String author;

    public Question(String title_, String content_, String author_)
    {
        title = title_;
        content = content_;
        author = author_;
    }

    public void setTitle(String title_)
    {
        title = title_;
    }

    public void setContent(String content_)
    {
        content = content_;
    }

    public void setAuthor(String author_)
    {
        author = author_;
    }
}
