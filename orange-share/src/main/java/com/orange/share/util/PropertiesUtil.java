package com.orange.share.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    // 静态块中不能有非静态属性，所以加static
    private static Properties prop = null;

    //静态块中的内容会在类别加载的时候先被执行
    static {
        try {
            prop = new Properties();
            // prop.load(new FileInputStream(new File("C:\\jdbc.properties")));
            prop.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("configs/jdbc.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //静态方法可以被类名直接调用
    public static String getValue(String key) {
        return prop.getProperty(key);

    }
}
