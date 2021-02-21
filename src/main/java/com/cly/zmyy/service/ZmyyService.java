package com.cly.zmyy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cly.zmyy.constant.Constants;
import com.cly.zmyy.exception.BusinessException;
import com.cly.zmyy.helper.*;
import com.cly.zmyy.http.HttpService;
import com.cly.zmyy.model.Config;
import com.cly.zmyy.model.resp.Hospital;
import com.cly.zmyy.model.resp.Response;
import com.cly.zmyy.model.resp.SubTimeDetail;
import com.cly.zmyy.model.resp.SubscribeableTime;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
public class ZmyyService {
    private ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private volatile Config config;

    private volatile List<Header> basicHeaders;


    public ZmyyService() {
        try {
            config = YamlHelper.parse(Config.class, "conf.yaml");
            int hospitalId = transformHospital2Id();
            config.setHospitalId(hospitalId);
            basicHeaders = getHeader();
        } catch (FileNotFoundException e) {
            log.error("解析yaml异常");
        }
    }

    private List<Header> getHeader() {
        BasicHeader cookie = new BasicHeader("cookie", config.getCookie());
        BasicHeader zftsl = new BasicHeader("zftsl", ZftslHelper.getZftsl());
        return Lists.newArrayList(cookie, zftsl);
    }


    public int transformHospital2Id() {
        String params = "[\"" + config.getProvince() + "\",\"" + config.getCity() + "\",\"" + StringUtils.defaultString(config.getDistrict(), "") + "\"]";
        String newUrl = Constants.CUSTOMER_LIST_URL + "&city=" + URLEncoderHelper.encode(params) + "&id=0&cityCode=" + CityHelper.getCityCode(config.getCity()) + "&product=0";

        String json = HttpService.get(newUrl, Collections.EMPTY_LIST);
        Response<Hospital> response = JSON.parseObject(json, new TypeReference<Response<Hospital>>() {
        });
        if (response.getStatus() != 200 || CollectionUtils.isEmpty(response.getList())) {
            throw new BusinessException("状态码不是200或者医院列表为空");
        }
        return response.getList()
                .stream()
                .filter(hospital -> StringUtils.equals(hospital.getCname(), config.getCustomerName()))
                .findFirst()
                .map(Hospital::getId)
                .orElseThrow(() -> new BusinessException("找不到医院"));
    }


    public void run() {
        //四价2 九价1
        //int vaccine = getDesignatedHospitalVaccine(hospitalId);
        //获取可预约时间列表
        List<String> enableDate = getEnableDate();

        //多线程处理每一个时间都预约，哪一个能约到就停止预约！
        //如果出现异常怎么解决？
        enableDate.stream()
                .peek(date -> getSubscribeableVaccineList(date))
                .count();

    }


    /**
     * 获取可预约时间列表
     * {"date":"2021-02-18","enable":false
     *
     * @return
     */
    public List<String> getEnableDate() {
        String url = Constants.CUST_SUBSCRIBE_DATE_URL + "&pid=" + config.getVaccineId() + "&id=" + config.getHospitalId() + "&month=" + config.getMonth();
        String json = HttpService.get(url, new BasicHeader("cookie", config.getCookie()));
        Response<SubscribeableTime> response = JSON.parseObject(json, new TypeReference<Response<SubscribeableTime>>() {
        });
        if (response.getStatus() != 200 || CollectionUtils.isEmpty(response.getList())) {
            throw new BusinessException("状态码不是200或者疫苗列表为空");
        }
        return response.getList()
                .stream()
                .filter(SubscribeableTime::getEnable)
                .map(SubscribeableTime::getDate)
                .collect(Collectors.toList());
    }

    public void getSubscribeableVaccineList(String date) {
        String url = Constants.CUST_SUBSCRIBE_DATE_DETAIL_URL + "&pid=" + config.getVaccineId() + "&id=" + config.getHospitalId() + "&scdate=" + date;
        String json = HttpService.get(url, basicHeaders);
        Response<SubTimeDetail> response = JSON.parseObject(json, new TypeReference<Response<SubTimeDetail>>() {
        });
        if (response.getStatus() != 200 || CollectionUtils.isEmpty(response.getList())) {
            throw new BusinessException("状态码不是200或者疫苗列表为空");
        }
        response.getList()
                .parallelStream()
                .filter(vaccine -> vaccine.getQty() > 0)
                .map(a -> secKill(date, a))
                .collect(Collectors.toList());
    }

    public String secKill(String date, SubTimeDetail dateDetail) {
        String json = HttpService.get(Constants.GET_CAPTCHA_URL, basicHeaders);

        Response response = JSON.parseObject(json, Response.class);
        if (response.getStatus() != 0) {
            throw new BusinessException("状态码不是200或者疫苗列表为空");
        }
        PicHelper.saveCaptcha(response.getDragon(), response.getTiger());
        String xValue = ExeHelper.runProcess(Constants.RESOURCE_PATH + "\\dragon.png", Constants.RESOURCE_PATH + "\\tiger.png");
        //验证验证码
        String guid = checkCaptcha(xValue);
        saveOrder(dateDetail.getMxid(), date, guid);
        return "";
    }

    public String checkCaptcha(String xValue) {
        String url = Constants.CAPTCHA_VERIFY_URL + "&token=&x=" + xValue + "&y=5";
        String json = HttpService.get(url, basicHeaders);
        Response response = JSON.parseObject(json, Response.class);
        if (response.getStatus() != 200 || StringUtils.isBlank(response.getGuid())) {
            throw new BusinessException("状态码不是200或者疫苗列表为空");
        }
        return response.getGuid();

    }

    public void saveOrder(String mxid, String date, String guid) {
        String url = Constants.SAVE_URL + "&birthday=" + config.getBirthday() + "&tel=" + config.getTel() + "&sex=" + config.getSex() + "&cname=" + URLEncoderHelper.encode(config.getName()) + "&doctype=1&idcard=" + config.getIdcard() + "&mxid=" + mxid + "&date=" + date + "&pid=" + config.getVaccineId() + "&Ftime=1&guid=" + guid;
        BasicHeader cookie = new BasicHeader("cookie", config.getCookie());
        BasicHeader zftsl = new BasicHeader("zftsl", ZftslHelper.getZftsl());
        BasicHeader conn = new BasicHeader("Connection", "keep-alive");
        String json = HttpService.get(url, Lists.newArrayList(cookie, zftsl, conn));
        Response response = JSON.parseObject(json, Response.class);
        if (response.getStatus() != 200) {
            throw new BusinessException("状态码不是200或者疫苗列表为空");
        }

    }


    /* *//**
     * 获取指定医院的疫苗列表并过滤需要的疫苗
     * @param hospitalId 指定医院的ID
     * @return
     *//*
    public int getDesignatedHospitalVaccine(int hospitalId) {
        String url = Constants.CUSTOMER_PRODUCT_URL + "&id=" + hospitalId;
        String json = HttpService.get(url, new BasicHeader("cookie", config.getCookie()));
        Response<Vaccine> response = JSON.parseObject(json, new TypeReference<Response<Vaccine>>() {
        });
        if (response.getStatus() != 200 || CollectionUtils.isEmpty(response.getList())) {
            throw new BusinessException("状态码不是200或者疫苗列表为空");
        }
        return response.getList()
                .stream()
                .filter(vaccine -> StringUtils.equals(vaccine.getText(), config.getProductName()))
                .findFirst()
                .map(Vaccine::getId)
                .orElseThrow(() -> new BusinessException("找不到指定疫苗，请检查配置信息"));
    }
*/


}
