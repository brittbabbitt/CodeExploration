package handlers;

public class PropManager {
    private PropReader propReader = new PropReader();

    //properties files for configs
    private String dbPropsFile = "/resources/properties/db.properties";


    public static PropManager propManager(){
        return new PropManager();
    }

    private PropManager(){
        readPropFiles();
    }


    //sequence of reading the property files
    private void readPropFiles(){
        propReader.readPropFile(dbPropsFile);
    }


}
