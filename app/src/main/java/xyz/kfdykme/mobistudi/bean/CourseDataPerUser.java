package xyz.kfdykme.mobistudi.bean;

/**
 * Project Name: Mobistudi2
 * Class Description: 作为每个课程的数据统计
 * All    | dataAll
 * userA  | dataA
 * userB  | dataB
 * ......
 * @author kf
 * @date 2017/7/3 00:14
 * Last Edit on 2017/7/3 00:14
 * 修改备注：
 */

public class CourseDataPerUser {

    public static int TYPE_ALL = 0;

    public static int TYPE_USER =1;
    int type = TYPE_USER;

    StudyUser mUser;

    Data mData = new Data(0,0,0);

    public CourseDataPerUser(int type) {
        this.type = type;
        this.mData = mData;
    }

    public CourseDataPerUser(int type, StudyUser mUser) {
        this.type = type;
        this.mUser = mUser;
        this.mData = mData;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public StudyUser getUser() {
        return mUser;
    }

    public void setUser(StudyUser mUser) {
        this.mUser = mUser;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data mData) {
        this.mData = mData;
    }

    public static class Data{

        //总的答题次数*wieght／次
        int allWeight;

        int goodWegiht;

        int badWeight;

        public Data(int allWeight, int goodWegiht, int badWeight) {
            this.allWeight = allWeight;
            this.goodWegiht = goodWegiht;
            this.badWeight = badWeight;
        }

        public int getAllWeight() {
            return allWeight;
        }

        public void setAllWeight(int allWeight) {
            this.allWeight = allWeight;
        }

        public int getGoodWegiht() {
            return goodWegiht;
        }

        public void setGoodWegiht(int goodWegiht) {
            this.goodWegiht = goodWegiht;
        }

        public int getBadWeight() {
            return badWeight;
        }

        public void setBadWeight(int badWeight) {
            this.badWeight = badWeight;
        }
    }
}
