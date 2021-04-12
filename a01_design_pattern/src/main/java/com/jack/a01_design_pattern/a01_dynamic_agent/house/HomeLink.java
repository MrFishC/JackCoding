package com.jack.a01_design_pattern.a01_dynamic_agent.house;

/**
 * @创建者 Jack
 * @创建时间 2021/4/7 13:42
 * @描述
 */
public class HomeLink implements HouseFactoty {

    private HouseFactoty mHouseFactoty;

    public void setHouseFactoty(HouseFactoty houseFactoty){
        mHouseFactoty = houseFactoty;
    }

    @Override
    public void buyHouse(int price) {
        mHouseFactoty.buyHouse(price);
    }

}
