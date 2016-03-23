package BDD;

import Conf.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class is used to manage Teradata Connexion
 * @author GMA
 * @version 0.1
 */
public class TDConnexion {

    // Server connexion information
    private String server = "";
    private String username = "";
    private String password = "";

    private Config config;

    // connexion
    private Connection teradata = null;

    /**
     * Constructor of the Classe TDConnexion
     * @param server adresse for Teradata Server
     * @param username username for Teradata Server
     * @param password password for Teradata Server
     * @param config configuration
     * @throws SQLException if error while connecting to Teradata Server
     * @throws ClassNotFoundException if erreur while searching the Class "com.teradata.jdbc.TeraDriver"
     */
    public TDConnexion (String server, String username, String password, Config config) throws SQLException, ClassNotFoundException {
        this.server = server;
        this.username = username;
        this.password = password;
        this.config = config;
        String teradataUrl="jdbc:teradata://"+this.server+"/";
        Class.forName("com.teradata.jdbc.TeraDriver");

        teradata = DriverManager.getConnection(teradataUrl, this.username, this.password);
    }

    /**
     * deconnexion is used to close the Teradata connexion
     */
    public void deconnexion() {
        if (teradata != null) {
            try {
                teradata.close();
                teradata = null;
            } catch (SQLException e) {
                //nothing to do here
            }
        }
    }

    /**
     * isConnected is used to know if the connection is active (without executing SQL)
     * @return true if the connection is active
     */
    public boolean isConnected() {
        return (teradata != null);
    }


    /**
     * requestNumberAMP is used to get the number of AMP on the Teradata Server
     * @return number of AMP on the Teradata Server
     * @throws SQLException if error while executing of SQL query
     */
    public int requestNumberAMP() throws SQLException {
        int result = 0;
        ResultSet rs = null;
        String base = config.getValueConf(config.V_DISKSPACE);

        try {
            String query="select cast((max(vproc)+1) as integer) from "+base+" ;";
            //System.out.println(query);
            PreparedStatement stmt=teradata.prepareStatement(query);
            rs=stmt.executeQuery();
            while(rs.next()) {
                result=rs.getInt(1);
            }
            rs.close();
        } catch (SQLException e) {
            deconnexion();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return result;
    }


    /**
     * requestSpoolSum is used to get the summary of the spool current usage on the Teradata Server for a user
     * @param user Teradata username
     * @return SpoolUsage with summary information
     * @throws SQLException if error while executing of SQL query
     */
    public SpoolUsage requestSpoolSum(String user) throws SQLException {
        SpoolUsage result = null;
        ResultSet rs = null;
        String base = config.getValueConf(config.V_DISKSPACE);
        try {
            String query="select cast(sum(currentspool) as decimal(20,2)) as SumCurrentSpool " +
                    ",cast(max(currentspool)*(max(vproc)+1) as decimal(20,2)) as SumRealSpool " +
                    ",cast(case when max(currentspool) > 0 then (100 - (avg(currentspool)/MAX(currentspool)*100)) else 0 end as decimal(3,0)) as SkewFactor   " +
                    "from "+base+" " +
                    "where upper(databasename) = upper('"+user+"');";

            PreparedStatement stmt=teradata.prepareStatement(query);
            rs=stmt.executeQuery();
            while(rs.next()) {
                result = new SpoolUsage(rs.getDouble(1),rs.getDouble(2),rs.getDouble(3));
            }


        } catch (SQLException e) {
            deconnexion();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        if (result == null) { throw new SQLException("User ["+user+"] doesn't exist"); }

        return result;
    }

    // get the detail of the spool current usage

    /**
     * requestSpoolDet is used to get the spool usage detail on the Teradata server for a user
     * @param user Teradata username
     * @return list of SpoolUsage
     * @throws SQLException if error while executing of SQL query
     */
    public List<SpoolUsage> requestSpoolDet(String user) throws SQLException {
        List<SpoolUsage> result = new ArrayList<SpoolUsage>();
        ResultSet rs = null;
        String base = config.getValueConf(config.V_DISKSPACE);
        try {
            String query="select Vproc, currentspool, maxprofilespool " +
                    "from "+base+" " +
                    "where upper(databasename) = upper('"+user+"') " +
                    "order by currentspool desc, Vproc asc;";
            PreparedStatement stmt=teradata.prepareStatement(query);
            rs=stmt.executeQuery();
            while(rs.next()) {
                result.add(new SpoolUsage(rs.getInt(1),rs.getDouble(2),rs.getDouble(3)));
            }

        } catch (SQLException e) {
            deconnexion();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        if (result.isEmpty()) { throw new SQLException("User ["+user+"] doesn't exist"); }

        return result;
    }


    /**
     * requestUserTDInfo is used to get user information on Teradata server for a user
     * @param user Teradata username
     * @return UserTDInfo for the user
     * @throws SQLException if error while executing of SQL query
     */
    public UserTDInfo requestUserTDInfo(String user) throws SQLException {
        UserTDInfo result = null;
        ResultSet rs = null;
        String base = config.getValueConf(config.V_USERS);
        String message = "";
        try {
            String query="select \n" +
                    "username as CompteTD\n" +
                    ",coalesce(commentString,'') as Nom\n" +
                    ",coalesce(profilename,'') as profilenom\n" +
                    ",cast(cast(cast(passwordlastmoddate as date format 'YYYY-MM-DD') as varchar(10)) || ' ' || cast(passwordlastmodtime as varchar(8)) as timestamp(0))  as PWDDateModif\n" +
                    ",createtimestamp\n" +
                    ",coalesce(cast(lockeddate as date format 'YYYY-MM-DD') || ' ' ||\n" +
                    " \tcase when length(trim(lockedtime)) > 4 \n" +
                    "\t\tthen substr(trim(lockedtime),1,2)||':'||substr(trim(lockedtime),4,2)||':00'\n" +
                    "\t\telse '0'||substr(trim(lockedtime),1,1)||':'||substr(trim(lockedtime),3,2)||':00'\n" +
                    "\tend,'') as LockedDateTime\n" +
                    ",lockedcount\n" +
                    "from "+base+"\n" +
                    "where upper(username) = upper('"+user+"')\n" +
                    ";";

            PreparedStatement stmt=teradata.prepareStatement(query);
            rs=stmt.executeQuery();
            while(rs.next()) {
                result = new UserTDInfo(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
            }

        } catch (SQLException e) {
            deconnexion();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        if (result == null) { throw new SQLException("User ["+user+"] doesn't exist"); }

        return result;
    }


    /**
     * requestProfileInfo is used to get profile information on the Teradata serveur for a profile
     * @param profileName Teradata profile name
     * @return ProfileInfo with Teradata profile information
     * @throws SQLException if error while executing of SQL query
     */
    public ProfileInfo requestProfileInfo(String profileName) throws SQLException {
        ProfileInfo result = null;
        ResultSet rs = null;
        String base = config.getValueConf(config.V_PROFILEINFO);

        try {
            String query="select profilename\n" +
                    ",coalesce(commentstring,'') as NomString\n" +
                    ",coalesce(defaultaccount,'') as defacc\n" +
                    ",coalesce(defaultDB,'') as defDB\n" +
                    ",cast((spoolspace/(1000*1000*1000)) as decimal(20,2)) as spoolspc\n" +
                    ",cast((tempspace/(1000*1000*1000)) as decimal(20,2)) as tempspc\n" +
                    "from "+base+"\n" +
                    "where profilename = '"+profileName+"';";

            PreparedStatement stmt=teradata.prepareStatement(query);
            rs=stmt.executeQuery();
            while(rs.next()) {
                result = new ProfileInfo(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getDouble(6));
            }


        } catch (SQLException e) {
            deconnexion();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        if (result == null) { throw new SQLException("Profile ["+profileName+"] doesn't exist"); }

        return result;
    }


    /**
     * requestListRole is used to get the list of role on the Teradata server for a user
     * @param user Teradata username
     * @return list of role
     * @throws SQLException if error while executing of SQL query
     */
    public List<String> requestListRole(String user) throws SQLException {
        List<String> result = new ArrayList<String>();
        ResultSet rs = null;
        String base = config.getValueConf(config.V_ROLEMEMBERS);
        try {
            String query="select RoleName from "+base+" where upper(Grantee) = upper('"+user+"') order by RoleName;";

            PreparedStatement stmt=teradata.prepareStatement(query);
            rs=stmt.executeQuery();
            while(rs.next()) {
                result.add(rs.getString(1));
            }


        } catch (SQLException e) {
            deconnexion();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        if (result.size() < 1) { throw new SQLException("User ["+user+"] doesn't have 'Role Fonctionnel'"); }

        return result;
    }

    /**
     * requestListLabs is used to get the list of Labs on the Teradata server for a user
     * @param user Teradata username
     * @return list of labs
     * @throws SQLException if error while executing of SQL query
     */
    public List<LabInfo> requestListLabs(String user) throws SQLException {
        List<LabInfo> result = new ArrayList<LabInfo>();
        ResultSet rs = null;
        String baseAR = config.getValueConf(config.V_ALLRIGHTS);
        String baseDB = config.getValueConf(config.V_DATABASES);
        try {
            String query="select \n" +
                    "LEC.LabGroup\n" +
                    ",LEC.Lab\n" +
                    ",case when ECR.accessright is null then 'Lecture' else 'Lecture et Ecriture' end as Droits\n" +
                    "from (\n" +
                    "select listlab.LabGroup, listlab.Lab, src.accessright \n"+
                    "from "+baseAR+" src \n"+
                    "inner join ( \n"+
                    "select OwnerName as LabGroup, Databasename as Lab from "+baseDB+" where ownername like 'labs!_%' escape '!' \n"+
                    ") listlab \n"+
                    "on (src.databasename = listlab.Lab) \n"+
                    "where upper(username) = upper('"+user+"') and accessright = 'R ' \n"+
                    "group by listlab.LabGroup, listlab.Lab, src.accessright \n"+
                    ") LEC\n" +
                    "left outer join (\n" +
                    "select listlab.LabGroup, listlab.Lab, src.accessright \n"+
                    "from "+baseAR+" src \n"+
                    "inner join ( \n"+
                    "select OwnerName as LabGroup, Databasename as Lab from "+baseDB+" where ownername like 'labs!_%' escape '!' \n"+
                    ") listlab \n"+
                    "on (src.databasename = listlab.Lab) \n"+
                    "where upper(username) = upper('"+user+"') and accessright = 'U ' \n"+
                    "group by listlab.LabGroup, listlab.Lab, src.accessright \n"+
                    ") ECR\n" +
                    "on ( LEC.LabGroup = ECR.LabGroup \n" +
                    "     and LEC.Lab = ECR.Lab )\n" +
                    "group by LEC.LabGroup, LEC.Lab,  Droits\n" +
                    "order by LEC.LabGroup, LEC.Lab,  Droits\n" +
                    ";";

            PreparedStatement stmt=teradata.prepareStatement(query);
            rs=stmt.executeQuery();
            while(rs.next()) {
                result.add(new LabInfo(rs.getString(1),rs.getString(2),rs.getString(3)));
            }

        } catch (SQLException e) {
            deconnexion();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        if (result.size() < 1) { throw new SQLException("User ["+user+"] doesn't have right on 'Labs'"); }

        return result;
    }




    /**
     * main is used to test the Class TDConnexion
     * @param args args of the test
     * @throws Exception if error while executing the test
     */
    public static void main(String[] args) throws Exception {
        String connurl="jdbc:teradata://192.168.0.11";
        Class.forName("com.teradata.jdbc.TeraDriver");
        Connection conn=DriverManager.getConnection(connurl, "dbc", "dbc");
        String query="update sysdba.gmat set C1=1 where C1=3";
        System.out.println(query);

        PreparedStatement stmt=conn.prepareStatement(query);
        //ResultSet rs=stmt.executeQuery();

        int j = stmt.executeUpdate();
        System.out.println(j+ " : result");
    /*    while(rs.next()) {
            String col1=rs.getString(1);
            System.out.println("col1="+col1);
        }*/
       // rs.close();
        conn.close();

    }


}
