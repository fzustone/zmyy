package com.cly.zmyy.helper;

import com.cly.zmyy.constant.Constants;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class PicHelper {
    public static void saveCaptcha(String dragon, String tiger) {
        try {
            File dragonPic = new File(Constants.RESOURCE_PATH + "\\dragon.png");
            File tigerPic = new File(Constants.RESOURCE_PATH + "\\tiger.png");
            // Base64 保存为图片
            byte[] bytes = Base64.getDecoder().decode(dragon);
            FileUtils.writeByteArrayToFile(dragonPic, bytes);
            byte[] bytes1 = Base64.getDecoder().decode(tiger);
            FileUtils.writeByteArrayToFile(tigerPic, bytes1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
