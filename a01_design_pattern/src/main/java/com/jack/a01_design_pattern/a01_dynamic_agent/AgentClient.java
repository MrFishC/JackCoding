package com.jack.a01_design_pattern.a01_dynamic_agent;

import com.jack.a01_design_pattern.a01_dynamic_agent.car.BenzMaker;
import com.jack.a01_design_pattern.a01_dynamic_agent.car.HgBenz;
import com.jack.a01_design_pattern.a01_dynamic_agent.car.MercedesFactory;
import com.jack.a01_design_pattern.a01_dynamic_agent.house.HomeLink;
import com.jack.a01_design_pattern.a01_dynamic_agent.house.HouseFactoty;
import com.jack.a01_design_pattern.a01_dynamic_agent.house.VankeCompany;

/**
 * @创建者 Jack
 * @创建时间 2021/4/7 14:07
 * @描述
 */
public class AgentClient {
    public static void main(String[] args){

        //静态代理
//        HouseFactoty subject = new VankeCompany();
//        HomeLink homeLink = new HomeLink();
//        homeLink.setHouseFactoty(subject);
//        homeLink.buyHouse(160);

//        MercedesFactory mercedesFactory = new BenzMaker();
//        HgBenz hgBenz = new HgBenz();
//        hgBenz.setMercedesFactory(mercedesFactory);
//        hgBenz.buyAmg(180);

        DynamicAgentCompany dynamicAgentCompany = new DynamicAgentCompany();

        HouseFactoty vanke = new VankeCompany();
        dynamicAgentCompany.setFactory(vanke);
        HouseFactoty houseFactoty = (HouseFactoty) dynamicAgentCompany.getProxyInstance();
        houseFactoty.buyHouse(160);

        MercedesFactory benzMaker = new BenzMaker();
        dynamicAgentCompany.setFactory(benzMaker);
        MercedesFactory mercedesFactory = (MercedesFactory) dynamicAgentCompany.getProxyInstance();
        mercedesFactory.buyAmg(180);

    }
}
