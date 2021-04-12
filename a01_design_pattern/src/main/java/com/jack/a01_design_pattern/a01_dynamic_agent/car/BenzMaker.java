package com.jack.a01_design_pattern.a01_dynamic_agent.car;

/**
 * @创建者 Jack
 * @创建时间 2021/4/7 14:15
 * @描述
 */
public class BenzMaker implements MercedesFactory{

    @Override
    public void buyAmg(int price) {
        System.out.println("花费" + price + "w,购买G系列AMG");
    }

}
