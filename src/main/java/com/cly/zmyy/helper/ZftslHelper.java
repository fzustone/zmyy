package com.cly.zmyy.helper;

import com.cly.zmyy.constant.Constants;
import com.cly.zmyy.exception.BusinessException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ZftslHelper {

    public static String getZftsl() {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            engine.eval(new String(Files.readAllBytes(Paths.get(Constants.RESOURCE_PATH + "\\js\\app.js"))));
            Invocable invocable = (Invocable) engine;
            return invocable.invokeFunction("zftsl", "Nashorn").toString();
        } catch (ScriptException | NoSuchMethodException | IOException e) {
            throw new BusinessException("zftsl 生成失败");
        }
    }
}
