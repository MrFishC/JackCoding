package cn.jack.module_fragment_02.entiy;

import java.util.List;

/**
 * @创建者 Jack
 * @创建时间 2021/3/27 11:37
 * @描述
 */
public class ProjectSortInfo {

    /**
     * children : []
     * courseId : 13
     * id : 294
     * name : 完整项目
     * order : 145000
     * parentChapterId : 293
     * userControlSetTop : false
     * visible : 0
     */

    private String  courseId;
    private String id;
    private String name;
    private int order;
    private String parentChapterId;
    private boolean userControlSetTop;
    private int     visible;
    private List<?> children;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(String parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ProjectSortInfo{" +
                "courseId='" + courseId + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", parentChapterId='" + parentChapterId + '\'' +
                ", userControlSetTop=" + userControlSetTop +
                ", visible=" + visible +
                ", children=" + children +
                '}';
    }
}
