package BDD;

/**
 * This Class is used to manage profile information
 * @author Gowy
 * @version 1.0
 */
public class ProfileInfo {

    private String profileName;
    private String commentString;
    private String spoolSpace;
    private String tempSpace;
    private String defaultDB;
    private String defaultAcc;


    /**
     * Constructor of the Class ProfileInfo
     * @param profileName ProfileName from Teradata
     * @param commentString CommentString from Teradata
     * @param defaultAcc DefaultAccount from Teradata
     * @param defaultDB DefaultDatabase from Teradata
     * @param spoolSpace SpoolSpace from Teradata
     * @param tempSpace TempSpace from Teradata
     */
    public ProfileInfo(String profileName, String commentString, String defaultAcc, String defaultDB, Double spoolSpace, Double tempSpace ) {
        this.profileName = profileName;
        this.commentString = commentString;
        this.spoolSpace = spoolSpace+" Go";
        this.tempSpace = tempSpace+" Go";
        this.defaultDB = defaultDB;
        this.defaultAcc = defaultAcc;
    }

    /**
     * getProfileName is used to get the ProfileName
     * @return ProfileName from Teradata
     */
    public String getProfileName() { return this.profileName; }

    /**
     * getCommentString is used to get the CommentString
     * @return CommentString from Teradata
     */
    public String getCommentString() { return this.commentString; }

    /**
     * getSpoolSpace is used to get the SpoolSpace
     * @return SpoolSpace from Teradata
     */
    public String getSpoolSpace() { return this.spoolSpace; }

    /**
     * getTempSpace is used to get the TempSpace
     * @return TempSpace from Teradata
     */
    public String getTempSpace() { return this.tempSpace; }

    /**
     * getDefaultDB is used to get the DefaultDatabase
     * @return DefaultDatabase from Teradata
     */
    public String getDefaultDB() { return this.defaultDB; }

    /**
     * getDefaultAcc is used to get the DefaultAccount
     * @return DefaultAccount from Teradata
     */
    public String getDefaultAcc() { return this.defaultAcc; }

}
