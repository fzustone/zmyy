package com.cly.zmyy.service;

import com.cly.zmyy.helper.CityHelper;
import org.junit.jupiter.api.Test;

class ZmyyServiceTest {

    @Test
    void getCityCode() {
        System.out.println(CityHelper.getCityCode("郑州市"));
    }

    @Test
    void get() {
        ZmyyService zmyyService = new ZmyyService();
        System.out.println(zmyyService.transformHospital2Id());
    }

    @Test
    void getVaccineProduct() {
        ZmyyService zmyyService = new ZmyyService();
        zmyyService.run();

    }

    @Test
    void getPic() {
        ZmyyService zmyyService = new ZmyyService();
        //zmyyService.secKill();
        zmyyService.run();

    }
    @Test
    void getProduct() {
        ZmyyService zmyyService = new ZmyyService();
        //zmyyService.secKill();
        //zmyyService.init();
        //zmyyService.getDesignatedHospitalVaccine(1921);

    }
    //{"status":200,"id":1921,"customer":"郑州市郑东新区博学路社区卫生服务中心","list":[{"customer":"郑州市郑东新区博学路社区卫生服务中心","customerid":1921,"StartTime":"08:00:00","EndTime":"09:00:00","mxid":"AAAAAOJdAAAvYjQB","qty":10},{"customer":"郑州市郑东新区博学路社区卫生服务中心","customerid":1921,"StartTime":"09:00:00","EndTime":"10:00:00","mxid":"AAAAAONdAAAvYjQB","qty":0},{"customer":"郑州市郑东新区博学路社区卫生服务中心","customerid":1921,"StartTime":"10:00:00","EndTime":"11:30:00","mxid":"AAAAAPNdAAAvYjQB","qty":0}],"ver":"2.0"}
String s="{\"status\":200,\"id\":1921,\"customer\":\"郑州市郑东新区博学路社区卫生服务中心\",\"list\":[{\"customer\":\"郑州市郑东新区博学路社区卫生服务中心\",\"customerid\":1921,\"StartTime\":\"08:00:00\",\"EndTime\":\"09:00:00\",\"mxid\":\"AAAAAOJdAAAvYjQB\",\"qty\":10},{\"customer\":\"郑州市郑东新区博学路社区卫生服务中心\",\"customerid\":1921,\"StartTime\":\"09:00:00\",\"EndTime\":\"10:00:00\",\"mxid\":\"AAAAAONdAAAvYjQB\",\"qty\":0},{\"customer\":\"郑州市郑东新区博学路社区卫生服务中心\",\"customerid\":1921,\"StartTime\":\"10:00:00\",\"EndTime\":\"11:30:00\",\"mxid\":\"AAAAAPNdAAAvYjQB\",\"qty\":0}],\"ver\":\"2.0\"}";
}