package chinaykc.mobistudi;

/**
 * @author ykc
 */
public class HotCourse {

    private String name;

    private int imageId;

    public HotCourse(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

}