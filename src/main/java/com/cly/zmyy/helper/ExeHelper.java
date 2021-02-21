package com.cly.zmyy.helper;

import com.cly.zmyy.constant.Constants;
import com.cly.zmyy.exception.BusinessException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExeHelper {
    public static String runProcess(String dragon, String titger) {
        try {
            //tigerPath, dragonPath, procssPath
            Process process = new ProcessBuilder(Constants.RESOURCE_PATH + "\\pyexe\\main\\main.exe", titger, dragon, Constants.RESOURCE_PATH + "\\process.png").start();
            //获取执行命令进程的响应信息
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return br.readLine();
        } catch (IOException e) {
            throw new BusinessException("验证码解析错误");
        }
    }
}
