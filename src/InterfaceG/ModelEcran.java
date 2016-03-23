package InterfaceG;


import org.eclipse.swt.widgets.Composite;

/**
 * This Class is used to create a Screen Model for the Interface
 * @author Gowy
 * @version 1.0
 */
abstract class ModelEcran {

    /** EcranPrincipal is used to have link with the primary screen */
    protected EcranPrincipal ecranPrincipal;
    /** compositePrincipal is used to add the current screen in the primary screen */
    protected Composite compositePrincipal;
    /** moduleMessage is used to have a name for each screen */
    protected String moduleMessage;

    /**
     * Constructor of the Class ModelEcran
     * @param ep Primary Window
     * @param nomModule Name of the Screen
     */
    public ModelEcran(EcranPrincipal ep, String nomModule) {
        this.ecranPrincipal = ep;
        this.compositePrincipal = ep.getMainComposite();
        this.moduleMessage = nomModule;
    }

    /**
     * majInformation is used to log information
     * @param typeMessage Type of log
     * @param message message of log
     */
    public void majInformation(String typeMessage, String message) {
        this.ecranPrincipal.majInformation(typeMessage,this.moduleMessage,message);
    }


}
