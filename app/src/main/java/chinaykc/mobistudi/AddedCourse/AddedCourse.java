package chinaykc.mobistudi.AddedCourse;

/**
 * @author ykc
 */
public class AddedCourse {

    private String name;

    private int imageId;

    private double learned_percent;

    public AddedCourse(String name, int imageId,double learned_percent) {
        this.name = name;
        this.imageId = imageId;
        this.learned_percent=learned_percent;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public double getLearned_percent(){return learned_percent;}

}