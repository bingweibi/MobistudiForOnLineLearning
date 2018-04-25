package xyz.kfdykme.mobistudi.eventbus;

/**
 * @author bbw
 * @date 2017/11/27
 * 用于传递签到信息
 */

public class LearnDays {
    private int learnDay;
    private String id;

    public LearnDays(int learnDay, String id) {
        this.learnDay = learnDay;
        this.id = id;
    }

    public int getLearnDay() {
        return learnDay;
    }

    public void setLearnDay(int learnDay) {
        this.learnDay = learnDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
