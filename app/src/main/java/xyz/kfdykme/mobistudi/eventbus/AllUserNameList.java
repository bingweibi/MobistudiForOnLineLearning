package xyz.kfdykme.mobistudi.eventbus;

import java.util.List;

import xyz.kfdykme.mobistudi.bean.AllUserName;

/**
 * @author bbw
 * @date 2017/12/1
 */

public class AllUserNameList {
    private List<AllUserName> allUserNameList;

    public AllUserNameList(List<AllUserName> allUserNameList) {
        this.allUserNameList = allUserNameList;
    }

    public List<AllUserName> getAllUserNameList() {
        return allUserNameList;
    }

    public void setAllUserNameList(List<AllUserName> allUserNameList) {
        this.allUserNameList = allUserNameList;
    }
}
