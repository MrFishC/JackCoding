package com.jack.a01_design_pattern.a01_dynamic_agent.house;

/**
 * @创建者 Jack
 * @创建时间 2021/4/7 13:43
 * @描述
 */
public class VankeCompany implements HouseFactoty {

    @Override
    public void buyHouse(int price) {
        System.out.println("jack使用" + price + "w,购买万科了80平米的房子");
    }

}
