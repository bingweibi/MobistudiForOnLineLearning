package chinaykc.mobistudi.SeriesContent;

/**
 * Created by china on 2017/9/26.
 * @author ykc
 */

public class SeriesContent {
    private String title;
    private String content;
    private long like;
    private String duration;
    private long watch_times;
    private int position;


    public SeriesContent(int position,long watch_times, long like,String duration,String title) {
        this.position=position;
        this.watch_times = watch_times;
        this.like = like;
        this.duration=duration;
        this.title=title;
    }


    public String getTitle() {
        return title;
    }

    public long getLike() {
        return like;
    }

    public String getDuration() {
        return duration;
    }

    public long getWatch_times() {
        return watch_times;
    }
}
