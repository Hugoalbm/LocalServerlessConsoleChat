package src.config;

import java.io.File;
import java.io.FileOutputStream;
import com.thoughtworks.xstream.XStream;

import src.tools.LogColor;

public class ConfigReader {

    //if the file does not exist, create a new config object with the default settings and serialize it to xml
    //if the file exists, deserialize it and return the config object
    //if an exception occurs, return a default config object without seralizing it to a file
    public static Config read() {
        try {
            Config c;
            File f = new File("../multicastChat/config/config.xml");
            XStream xs = new XStream();
            xs.allowTypes(new Class[] { Config.class });
            
            if (!f.exists()) {
                c = new Config();
                f.createNewFile();
                xs.toXML(c, new FileOutputStream(f));
                
                if (c.colorText) LogColor.printlnRed("Configuration created...");
                else System.out.println("Configuration created...");
                
                return c;
            }

            c = (Config) xs.fromXML(f);
            
            if (c.colorText) LogColor.printlnRed("Configuration loaded...");
            else System.out.println("Configuration loaded...");
            
            return c;

        } catch (Exception e) {
            e.printStackTrace();
            return new Config();
        }
    }
    
}
