package chinaykc.mobistudi.Series;

/**
 * Created by china on 2017/9/26.
 * @author ykc
 */

public class Series {

    private String title;
    private String content;
    private long like;
    private int video_number;

    public long getAddLongLike(){
        like++;
        return like;
    }

    public Series(String title, String content, int video_number, long like) {
        this.title = title;
        this.content = content;
        this.like = like;
        this.video_number = video_number;
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getLike() {
        if (like < 99999){
            return like + "❤";
        } else{
            return "99999+❤";
        }

    }

    public String getVideo_number() {
        return video_number + "个视频";

    }
}
