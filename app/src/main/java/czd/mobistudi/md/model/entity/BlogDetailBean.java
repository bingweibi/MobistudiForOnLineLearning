package czd.mobistudi.md.model.entity;

public class BlogDetailBean {
    private String title;
    private String content;

    public BlogDetailBean(){

    }

    public BlogDetailBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
