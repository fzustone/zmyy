package com.cly.zmyy.helper;

import com.cly.zmyy.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class URLEncoderHelper {
    public static String encode(String params) {
        try {
            return URLEncoder.encode(params, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            log.error("Encoder error");
            throw new BusinessException("Encoder error", e);
        }
    }
}
