package xyz.kfdykme.mobistudi.structmap.model;

import xyz.kfdykme.mobistudi.bean.StudySubject;

/**
 * Project Name: Mobistudi
 * Class Description:
 * Created by kf on 2017/9/8 21:42.
 * Last Edit on 2017/9/8 21:42
 * 修改备注：
 */

public class StructMapModel implements IStructMapModel {

    StudySubject mSubject;


    public StructMapModel(){

    }

//    public void initMap(StructView view){
//
//
//
//
//        }
//
//    }

    @Override
    public void initStructMapColor() {

    }



    @Override
    public StudySubject getSubject() {
        return mSubject;
    }

    @Override
    public void setSubject(StudySubject subject) {
        mSubject = subject;
    }
}
