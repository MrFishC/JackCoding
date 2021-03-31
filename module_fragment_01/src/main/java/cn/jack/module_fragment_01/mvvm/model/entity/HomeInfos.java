package cn.jack.module_fragment_01.mvvm.model.entity;

import java.util.List;
import cn.jack.library_common_business.entiy.ProjectInfoList;

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 16:21
 * @描述
 */
public class HomeInfos {
    private List<BanInfos>  mBannerInfo;
    private ProjectInfoList mProjectInfoList;

    public HomeInfos(List<BanInfos> bannerInfo, ProjectInfoList projectInfoList) {
        mBannerInfo = bannerInfo;
        mProjectInfoList = projectInfoList;
    }

    public List<BanInfos> getBannerInfo() {
        return mBannerInfo;
    }

    public void setBannerInfo(List<BanInfos> bannerInfo) {
        mBannerInfo = bannerInfo;
    }

    public ProjectInfoList getProjectInfoList() {
        return mProjectInfoList;
    }

    public void setProjectInfoList(ProjectInfoList projectInfoList) {
        mProjectInfoList = projectInfoList;
    }

    @Override
    public String toString() {
        return "HomeInfos{" +
                "mBannerInfo=" + mBannerInfo +
                ", mProjectInfoList=" + mProjectInfoList +
                '}';
    }
}
