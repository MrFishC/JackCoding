package cn.jack.module_fragment_02.contract;

import cn.jack.library_common_business.entiy.ProjectInfoList;
import jack.wrapper.base.contract.IBaseViewStateContract;

/**
 * @创建者 Jack
 * @创建时间 2021/3/30 17:06
 * @描述
 */
public interface ISubjectLisenter extends IBaseViewStateContract {

    void onSuccess(ProjectInfoList projectInfoList);

}
