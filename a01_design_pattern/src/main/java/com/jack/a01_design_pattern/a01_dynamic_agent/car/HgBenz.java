package com.jack.a01_design_pattern.a01_dynamic_agent.car;

/**
 * @创建者 Jack
 * @创建时间 2021/4/7 14:14
 * @描述
 */
public class HgBenz implements MercedesFactory{

    private MercedesFactory mMercedesFactory;

    public void setMercedesFactory(MercedesFactory mercedesFactory){
        mMercedesFactory = mercedesFactory;
    }

    @Override
    public void buyAmg(int price) {
        mMercedesFactory.buyAmg(price);
    }

}
