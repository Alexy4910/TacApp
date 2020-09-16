package ubisolutions.net.template.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Config {
    public static final String URL_UPDATE = "http://update.ubisolutions.net/";

    public Boolean debug = false;
    public String client = "Geodis";

    public static Config Deserialize(File configFile) {
        Config param = new Config();
        try {
            FileInputStream in = new FileInputStream(configFile);
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            param = gson.fromJson(new InputStreamReader(in, "UTF-8"), Config.class);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }

    public static void createConfigFile(File configFile) {
        try {
            Config param = new Config();
            FileOutputStream out = new FileOutputStream(configFile);
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String jsonParam = gson.toJson(param);
            out.write(jsonParam.getBytes(Charset.forName("UTF-8")));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}