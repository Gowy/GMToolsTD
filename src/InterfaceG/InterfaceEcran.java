package InterfaceG;

/**
 * This Interface is used to implements primary method on each screen
 * @author Gowy
 * @version 1.0
 */
public interface InterfaceEcran {

    /**
     * createContents is used to create screen elements in the primary window
     */
    void createContents();

    /**
     * deleteContents is used to delete screen elements from the primary window
     */
    void deleteContents();

    /**
     * activatedContents is used to activate the screen elements
     */
    void activatedContents();

    /**
     * desactivatedContents is used to desactivate the screen elements
     */
    void desactivatedContents();

}
