package cn.jack.module_fragment_01.contract;

import java.util.List;

import cn.jack.library_common_business.entiy.ArticleInfo;
import cn.jack.library_common_business.entiy.ProjectInfoList;
import cn.jack.module_fragment_01.mvvm.model.entity.BanInfos;
import jack.wrapper.base.contract.IBaseViewStateContract;

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 15:55
 * @描述
 */
public interface IPageOneLisenter extends IBaseViewStateContract {
    void showBannerInfos(List<BanInfos> banInfosList);
    void showPageInfos(List<ArticleInfo> articleInfos);
}
