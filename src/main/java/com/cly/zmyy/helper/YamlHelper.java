package com.cly.zmyy.helper;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.cly.zmyy.constant.Constants.RESOURCE_PATH;

public class YamlHelper {

    public static <T> T parse(Class<T> clazz, String fileName) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(RESOURCE_PATH + fileName);
        return yaml.loadAs(inputStream, clazz);
    }


}
