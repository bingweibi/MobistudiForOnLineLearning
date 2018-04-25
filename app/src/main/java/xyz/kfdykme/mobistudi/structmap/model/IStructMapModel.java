package xyz.kfdykme.mobistudi.structmap.model;

import xyz.kfdykme.mobistudi.bean.StudySubject;
import xyz.kfdykme.mobistudi.common.IBaseModel;

/**
 * Project Name: Mobistudi
 * Class Description:
 * Created by kf on 2017/9/8 17:41.
 * Last Edit on 2017/9/8 17:41
 * 修改备注：
 */

public interface IStructMapModel  extends IBaseModel{

    void initStructMapColor();

    StudySubject getSubject();

    void setSubject(StudySubject subject);
}
