package czd.mobistudi.md.model.entity;

public class BlogListBean {
    private String image;
    private String title;
    private String summary;
    private String time;
    private String category;
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public BlogListBean(){

    }

    public BlogListBean(String image, String title, String summary, String time, String category,String href) {
        this.image = image;
        this.title = title;
        this.href = href;
        this.summary = summary;
        this.time = time;
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
