package BDD;

/**
 * This Class is used to manage Lab information
 * @author GMA
 * @version 0.1
 */
public class LabInfo {

    private String group = "";
    private String lab = "";
    private String droit = "";

    /**
     * Constructor of the class LabInfo
     * @param group Lab Group from Teradata
     * @param lab Lab from Teradata
     * @param droit right from Teradata
     */
    public LabInfo (String group, String lab, String droit) {
        this.group = group;
        this.lab = lab;
        this.droit = droit;
    }

    /**
     * getGroup is used to get the Lab Group
     * @return Lab Group
     */
    public String getGroup(){ return this.group; }

    /**
     * getLab is used to get the Lab
     * @return Lab
     */
    public String getLab() {
        return this.lab;
    }

    /**
     * getDroit is used to get the rights on the Lab
     * @return Rights on the Lab
     */
    public String getDroit() {
        return this.droit;
    }


}
