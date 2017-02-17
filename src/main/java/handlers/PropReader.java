package handlers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

public class PropReader {

    Properties prop = new Properties();
    InputStream input = null;
    ArrayList<String> propKeys = null;

	public void readPropFile(String path){
        try {

            input = new FileInputStream(path);

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            getAllValues();

            //error catching:
            //file read issue and closing stream
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getPropValue(String p){
	    return prop.getProperty(p);
    }

    private void getAllValues(){
        for (String p:propKeys) {
            System.out.println(prop.getProperty(p));
        }
    }
    
    private void setPropKeys(){
        Enumeration<?> e = prop.propertyNames();
        while (e.hasMoreElements()) {
            propKeys.add((String) e.nextElement());
        }

    }
}

