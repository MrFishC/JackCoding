package cn.jack.module_login.mvvm.modle.entity;

import java.util.List;

/**
 * @创建者 Jack
 * @创建时间 2021/3/17 14:25
 * @描述
 */
public class UserInfo {

    private String        email;
    private String        icon;
    private String        id;
    private String password;
    private String token;
    private String type;
    private String        username;
    private List<?>       chapterTops;
    private List<Integer> collectIds; //收藏的文章id

    /**********************************************我的积分******************************************/
    private String coinCount;
    private int level;
    private String rank;
    private String userId, reason, desc, date;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<?> getChapterTops() {
        return chapterTops;
    }

    public void setChapterTops(List<?> chapterTops) {
        this.chapterTops = chapterTops;
    }

    public List<Integer> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }

    public String getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(String coinCount) {
        this.coinCount = coinCount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "email='" + email + '\'' +
                ", icon='" + icon + '\'' +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", chapterTops=" + chapterTops +
                ", collectIds=" + collectIds +
                ", coinCount='" + coinCount + '\'' +
                ", level=" + level +
                ", rank='" + rank + '\'' +
                ", userId='" + userId + '\'' +
                ", reason='" + reason + '\'' +
                ", desc='" + desc + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

}
