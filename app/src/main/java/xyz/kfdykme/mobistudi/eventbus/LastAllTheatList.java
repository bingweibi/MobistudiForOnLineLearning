package xyz.kfdykme.mobistudi.eventbus;

import java.util.List;

import xyz.kfdykme.mobistudi.bean.UserLastTheat;

/**
 * @author bbw
 * @date 2017/12/1
 */

public class LastAllTheatList {
    private List<UserLastTheat> lastTheatList;

    public LastAllTheatList(List<UserLastTheat> lastTheatList) {
        this.lastTheatList = lastTheatList;
    }

    public List<UserLastTheat> getLastTheatList() {
        return lastTheatList;
    }

    public void setLastTheatList(List<UserLastTheat> lastTheatList) {
        this.lastTheatList = lastTheatList;
    }
}
