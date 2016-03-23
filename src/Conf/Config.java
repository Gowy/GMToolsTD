package Conf;

import java.io.*;
import java.util.Properties;

/**
 * This Class is used to configure the application
 * @author Gowy
 * @version 1.0
 */
public class Config {

    /** configuration file */
    final private String confFile = "GMToolsTD.cfg";

    /** Parameter SERVER_TD */
    final public String SERVER_TD = "server_TD";
    /** Parameter USERNAME_TD */
    final public String USERNAME_TD = "username_TD";
    /** Parameter V_USERS */
    final public String V_USERS = "view_usersv_all";
    /** Parameter V_ALLRIGHTS */
    final public String V_ALLRIGHTS = "view_allrightsv";
    /** Parameter V_ROLEMEMBERS */
    final public String V_ROLEMEMBERS = "view_rolemembersv";
    /** Parameter V_PROFILEINFO */
    final public String V_PROFILEINFO = "view_profileinfov";
    /** Parameter V_DISKSPACE */
    final public String V_DISKSPACE = "view_diskspacev";
    /** Parameter V_DATABASES */
    final public String V_DATABASES = "view_databasesv";


    private Properties properConf = new Properties();
    private String[][] defaultConf = new String[8][2];


    /**
     * Constructor of the Class Config
     */
    public Config() {
        initDefaultConf();
        try {
            loadConf();
        } catch (IOException e) {
            // default values will be enough
        }
    }

    /**
     * loadConf is used to load configuration file
     * @throws IOException if error while reading the configuration file
     */
    public void loadConf() throws IOException {
        File f = new File(confFile);
        if ( f.exists() ) {
            InputStream in = new FileInputStream(confFile);
            Properties properT = new Properties();
            properT.load(in);
            loadConfWithProperties(properT);
            in.close();
        }
    }

    /**
     * initDefaultConf is used to initialize the default configuration
     */
    public void initDefaultConf() {

        //names
        defaultConf[0][0] = SERVER_TD;
        defaultConf[1][0] = USERNAME_TD;
        defaultConf[2][0] = V_USERS;
        defaultConf[3][0] = V_ALLRIGHTS;
        defaultConf[4][0] = V_ROLEMEMBERS;
        defaultConf[5][0] = V_PROFILEINFO;
        defaultConf[6][0] = V_DISKSPACE;
        defaultConf[7][0] = V_DATABASES;

        //values
        defaultConf[0][1] = "";
        defaultConf[1][1] = "";
        defaultConf[2][1] = "dbc.usersV_all";
        defaultConf[3][1] = "dbc.AllRightsV";
        defaultConf[4][1] = "dbc.RoleMembersV";
        defaultConf[5][1] = "dbc.profileinfoV";
        defaultConf[6][1] = "dbc.diskspaceV";
        defaultConf[7][1] = "dbc.databasesV";

        // set the property in the configuration with default values
        for (int i = 0; i < defaultConf.length ; i++) {
            properConf.setProperty(defaultConf[i][0],defaultConf[i][1]);
        }

    }


    /**
     * loadConfWithProperties is used to load the configuration from a Properties object
     * @param prop properties to load in the configuration
     */
    private void loadConfWithProperties(Properties prop) {
        for (int i = 0; i < defaultConf.length ; i++) {
            String tmpValue = prop.getProperty(defaultConf[i][0]);
            if (tmpValue == null) { tmpValue = defaultConf[i][1]; }
            properConf.setProperty(defaultConf[i][0],tmpValue);
        }
    }

    /**
     * getDefaultConfNames is used to get the list of names parameters known in the configuration
     * @return List of names parameters known in the configuration
     */
    public String[] getDefaultConfNames() {
        String[] result = new String[defaultConf.length];

        for (int i = 0 ; i < defaultConf.length; i++) {
            result[i] = defaultConf[i][0];
        }

        return result;
    }

    /**
     * getDefaultConfValues is used to get the list of values parameters known in the configuration
     * @return List of values parameters known in the configuration
     */
    public String[] getDefaultConfValues() {
        String[] result = new String[defaultConf.length];

        for (int i = 0 ; i < defaultConf.length; i++) {
            result[i] = defaultConf[i][1];
        }

        return result;
    }

    /**
     * saveConfig is used to store configuration in a file
     * @exception IOException if error while writing the configuration file
     */
    public void saveConfig() throws IOException {
        Properties savingConf = new Properties();

        for (int i = 0; i < defaultConf.length ; i++) {
            savingConf.setProperty(defaultConf[i][0],properConf.getProperty(defaultConf[i][0]));
        }

        OutputStream out = new FileOutputStream(confFile);
        savingConf.store(out,"GMToolsTD Config");
    }

    /**
     * getValueConf is used to get a value parameter known in the configuration
     * @param name name of the parameter
     * @return a value parameter known in the configuration
     */
    public String getValueConf(String name) {
        return properConf.getProperty(name);
    }

    /**
     * setValueConf is used to get a value parameter known in the configuration
     * @param name name of the parameter
     * @param value value of the parameter
     */
    public void setValueConf(String name, String value) {
        properConf.setProperty(name,value);
    }

    /**
     * main is used to test the Class Config
     * @param args args of the test
     * @exception Exception if error during the execution of the test
     */
    public static void main(String[] args) throws Exception {
        Config pc = new Config();
        pc.saveConfig();
        System.exit(0);

    }



}
