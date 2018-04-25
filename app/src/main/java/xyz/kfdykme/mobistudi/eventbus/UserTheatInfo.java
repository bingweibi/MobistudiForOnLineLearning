package xyz.kfdykme.mobistudi.eventbus;

/**
 * @author bbw
 * @date 2017/11/28
 */

public class UserTheatInfo {

    private String userTheat;
    private String id;

    public UserTheatInfo(String userTheat, String id) {
        this.userTheat = userTheat;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserTheat() {
        return userTheat;
    }

    public void setUserTheat(String userTheat) {
        this.userTheat = userTheat;
    }
}
