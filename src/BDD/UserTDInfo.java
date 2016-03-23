package BDD;

/**
 * This Class is used to manage User information
 * @author Gowy
 * @version 1.0
 */
public class UserTDInfo {

    private String username;
    private String commentString;
    private String profileName;
    private String pwdDateLastModif;
    private String createDate;
    private String lockedDate;
    private String lockedCount;

    /**
     * Constructor of the Class UserTDInfo
     * @param username UserName from Teradata
     * @param commentString CommentString from Teradata
     * @param profileName ProfileName from Teradata
     * @param pwdDateLastModif Password Date Last Modification from Teradata
     * @param createDate Create Date from Teradata
     * @param lockedDate Locked Date from Teradata
     * @param lockedCount Locked Count from Teradata
     */
    public UserTDInfo(String username, String commentString, String profileName, String pwdDateLastModif, String createDate, String lockedDate, String lockedCount ) {
        this.username = username;
        this.commentString = commentString;
        this.profileName = profileName;
        this.pwdDateLastModif = pwdDateLastModif;
        this.createDate = createDate;
        this.lockedDate = lockedDate;
        this.lockedCount = lockedCount;
    }


    /**
     * getCommentString is used to get the CommentString value
     * @return CommentString from Teradata
     */
    public String getCommentString() {
        return this.commentString;
    }

    /**
     * getProfileName is used to get the ProfileName value
     * @return ProfileName from Teradata
     */
    public String getProfileName() {
        return this.profileName;
    }

    /**
     * getPwdDateLastModif is used to get the Password Date Last Modification value
     * @return Password Date Last Modification from Teradata
     */
    public String getPwdDateLastModif() {
        return this.pwdDateLastModif;
    }

    /**
     * getCreateDate is used to get the Create Date value
     * @return Create Date from Teradata
     */
    public String getCreateDate() {
        return this.createDate;
    }

    /**
     * getLockedDate is used to get the Locked Date value
     * @return Locked Date from Teradata
     */
    public String getLockedDate() {
        return this.lockedDate;
    }

    /**
     * getLockedCount is used to get the Locked Count value
     * @return Locked Count from Teradata
     */
    public String getLockedCount() {
        return this.lockedCount;
    }

}
