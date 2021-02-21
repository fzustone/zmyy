package com.cly.zmyy.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cly.zmyy.constant.Constants;
import com.cly.zmyy.exception.BusinessException;
import com.cly.zmyy.model.CityInfo;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class CityHelper {
    public static String getCityCode(String city) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(Constants.RESOURCE_PATH + "city.json")));
            List<CityInfo> cityInfos = JSON.parseObject(json, new TypeReference<List<CityInfo>>() {
            });
            return cityInfos.stream()
                    .map(CityInfo::getChildren)
                    .flatMap(Collection::stream)
                    .filter(cityInfo -> StringUtils.equals(city, cityInfo.getName()))
                    .findFirst()
                    .map(cityCode -> cityCode.getValue() + "00")
                    .orElseThrow(() -> new BusinessException("无法识别的城市"));
        } catch (IOException e) {
            throw new BusinessException("文件解析异常", e);
        }
    }

}
