package com.cly.zmyy.http;

import com.cly.zmyy.constant.Constants;
import com.cly.zmyy.exception.BusinessException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HttpService {

    public static String get(String path, Header extHeader) {
        return get(path, Lists.newArrayList(extHeader));
    }

    public static String get(String path, List<Header> extHeader) {
        try {
            HttpGet get = new HttpGet(path);
            List<Header> headers = getCommonHeader();
            if (CollectionUtils.isNotEmpty(extHeader)) {
                headers.addAll(extHeader);
            }
            get.setHeaders(headers.toArray(new Header[0]));
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpEntity httpEntity = httpClient.execute(get).getEntity();
            return EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new BusinessException("异常", e);
        }
    }

    private static List<Header> getCommonHeader() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", Constants.USER_AGENT));
        headers.add(new BasicHeader("Referer", Constants.REFER));
        return headers;
    }
}
