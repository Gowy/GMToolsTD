package InterfaceG;


import BDD.TDConnexion;
import Conf.Config;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * This Class is used to create the Primary Window
 * @author Gowy
 * @version 1.0
 */
public class EcranPrincipal {

    // ecran auxiliaire
    private EcranUser ecranUser;
    private EcranSpool ecranSpool;
    private EcranConfig ecranConfig;

    private Config conf = new Config();

    // ecran principal
    private Display display;
    private Shell shell;
    private Composite pageComposite;

    //menu principal
    private MenuItem mntmSpoolUsage;
    private MenuItem mntmUser;
    private MenuItem mntmConfig;
    private MenuItem mntmExit;
    private MenuItem mntmAbout;

    // instance layout data
    private GridData gridData;

    // ecran connexion
    private Group grpConnexion;
    private Text textServer;
    private Text textUsername;
    private Text textPassword;
    private Button btnConnect;
    private Button btnDisconnect;

    // ecran information
    private Group groupInfo;
    private Text textInformation;



    // constante
    /** dateFormat is used to format the date **/
    final private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /** heureFormat is used to format the time **/
    final private DateFormat heureFormat = new SimpleDateFormat("HH:mm:ss");

    /** AFFICHE_RIEN is used when nothing need to be visible */
    final public int AFFICHE_RIEN = 0;
    /** AFFICHE_SPOOL is used when the spool screen need to be visible */
    final public int AFFICHE_SPOOL = 1;
    /** AFFICHE_USER is used when the user screen need to be visible */
    final public int AFFICHE_USER = 2;
    /** AFFICHE_CONFIG is used when config screen need to be visible */
    final public int AFFICHE_CONFIG = 3;

    /** MESSAGE_ERROR is used when an error is logged */
    final public String MESSAGE_ERROR = "ERR";
    /** MESSAGE_WARNING is used when a warning is logged */
    final public String MESSAGE_WARNING = "WRN";
    /** MESSAGE_INFORMATION is used when an information is logged */
    final public String MESSAGE_INFORMATION = "INF";


    // Variable
    private String nomModuleConnexion = "Connexion";
    private String entryServer = "";
    private String entryUsername = "";
    private String entryPassword = "";
    private boolean connexionStatut = false;
    private Integer nbrAMP = 0;
    private LinkedList<String> listMessageInfo = new LinkedList<String>();


    private TDConnexion tdConnect;
    private int afficheChoix = AFFICHE_RIEN;


    /**
     * createContents is used to create the primary window elements
     */
    protected void createContents() {
        shell = new Shell();
        shell.setSize(1024, 650);
        shell.setText("GMToolsTD");
        shell.setLayout(new GridLayout());


        /* Création du menu principal */
        Menu menuPrincipal = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menuPrincipal);

        MenuItem mntmModule = new MenuItem(menuPrincipal, SWT.CASCADE);
        mntmModule.setText("Module");

        Menu menuModule = new Menu(mntmModule);
        mntmModule.setMenu(menuModule);

        mntmSpoolUsage = new MenuItem(menuModule, SWT.NONE);
        mntmSpoolUsage.setText("Spool Usage");

        mntmUser = new MenuItem(menuModule, SWT.NONE);
        mntmUser.setText("User Information");

        new MenuItem(menuModule, SWT.SEPARATOR);

        mntmConfig = new MenuItem(menuModule, SWT.NONE);
        mntmConfig.setText("Configuration");

        new MenuItem(menuModule, SWT.SEPARATOR);

        mntmExit = new MenuItem(menuModule, SWT.NONE);
        mntmExit.setText("Exit");

        MenuItem mntmHelp = new MenuItem(menuPrincipal, SWT.CASCADE);
        mntmHelp.setText("Help");

        Menu menuAbout = new Menu(mntmHelp);
        mntmHelp.setMenu(menuAbout);

        mntmAbout = new MenuItem(menuAbout, SWT.NONE);
        mntmAbout.setText("About");


        /* Creation main screen elements */
        pageComposite = new Composite(shell, SWT.NONE);
        pageComposite.setLayout(new GridLayout(2,false));
        gridData = new GridData();
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = SWT.FILL;
        gridData.grabExcessVerticalSpace = true;
        pageComposite.setLayoutData(gridData);


        grpConnexion = new Group(pageComposite, SWT.NONE );
        grpConnexion.setText("Connection");
        grpConnexion.setLayout(new GridLayout(2,false));
        gridData = new GridData();
        gridData.heightHint = 110;
        grpConnexion.setLayoutData(gridData);


        Label lblServer = new Label(grpConnexion, SWT.NONE);
        lblServer.setText("Server :");

        textServer = new Text(grpConnexion, SWT.NONE);
        textServer.setLayoutData(new GridData(110,20));

        Label lblUsername = new Label(grpConnexion, SWT.NONE);
        lblUsername.setSize(65, 20);
        lblUsername.setText("Username :");

        textUsername = new Text(grpConnexion,SWT.NONE);
        textUsername.setLayoutData(new GridData(110,20));

        Label lblPassword = new Label(grpConnexion, SWT.NONE);
        lblPassword.setSize(65, 20);
        lblPassword.setText("Password :");

        textPassword = new Text(grpConnexion, SWT.PASSWORD);
        textPassword.setLayoutData(new GridData(110,20));

        btnConnect = new Button(grpConnexion, SWT.NONE);
        btnConnect.setLayoutData(new GridData(SWT.RIGHT,SWT.CENTER, false, false));
        btnConnect.setText("Connect");

        btnDisconnect = new Button(grpConnexion, SWT.NONE);
        btnDisconnect.setEnabled(false);
        btnDisconnect.setText("Disconnect");



        groupInfo = new Group(pageComposite, SWT.NONE);
        groupInfo.setText("Information");
        groupInfo.setLayout(new GridLayout(1, false));
        gridData = new GridData();
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.heightHint = 110;
        groupInfo.setLayoutData(gridData);

        textInformation = new Text(groupInfo, SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
        gridData = new GridData();
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = SWT.FILL;
        gridData.grabExcessVerticalSpace = true;
        textInformation.setLayoutData(gridData);


        // renseignement des informations provenant de config
        textServer.setText(conf.getValueConf(conf.SERVER_TD));
        textUsername.setText(conf.getValueConf(conf.USERNAME_TD));
        textPassword.setText("");


        mntmSpoolUsage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                majAffichage(AFFICHE_SPOOL);
            }
        });


        mntmUser.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                majAffichage(AFFICHE_USER);
            }
        });

        mntmConfig.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                majAffichage(AFFICHE_CONFIG);
            }
        });

        mntmAbout.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION);
                messageBox.setText("About...");
                messageBox.setMessage("Not implemented yet ...");
                messageBox.open();
            }
        });

        mntmExit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                // afficher un message box avec du blabla
                shell.close();

            }
        });

        btnConnect.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // connexion Teradata
                btnConnect.setEnabled(false);
                entryServer = textServer.getText();
                entryUsername = textUsername.getText();
                entryPassword = textPassword.getText();

                new Thread(new Runnable() {
                    public void run() {
                        try {

                            tdConnect = new TDConnexion(entryServer, entryUsername, entryPassword, conf);
                            majInformation(MESSAGE_INFORMATION,nomModuleConnexion,"Connexion OK");
                            connexionStatut = true;
                        } catch ( SQLException err) {
                            majInformation(MESSAGE_ERROR,nomModuleConnexion,"Connexion KO : "+err.getMessage());
                            connexionStatut = false;
                        } catch (ClassNotFoundException err) {
                            majInformation(MESSAGE_ERROR,nomModuleConnexion,"Error ClassNotFoundException : "+err.getMessage());
                            connexionStatut = false;
                        }
                        if (connexionStatut) {
                            try {
                               nbrAMP = tdConnect.requestNumberAMP();
                            } catch (Exception err) {
                                majInformation(MESSAGE_ERROR,nomModuleConnexion,"Calculation of AMPs KO : "+err.getMessage());
                                connexionStatut = false;
                                tdConnect.deconnexion();
                            }
                        }

                       Display.getDefault().asyncExec(new Runnable() {
                           public void run() {

                               if (connexionStatut) {
                                   activationON();

                                   textServer.setEnabled(false);
                                   textUsername.setEnabled(false);
                                   textPassword.setEnabled(false);

                                   btnConnect.setEnabled(false);
                                   btnDisconnect.setEnabled(true);
                               } else {
                                   btnConnect.setEnabled(true);
                               }
                            }
                       });
                    }
                }).start();




            }
        });


        btnDisconnect.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                manageDisconnection();
            }
        });


        //creating secondary screen
        ecranUser = new EcranUser(this);
        ecranSpool = new EcranSpool(this);
        ecranConfig = new EcranConfig(this);


        // update of the information message (log)
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        if (!listMessageInfo.isEmpty()) {
                            Display.getDefault().asyncExec(new Runnable() {
                                public void run() {
                                    refreshInformation();
                                }
                            });
                        }
                        try {
                            Thread.sleep(500);
                        } catch (Exception e) {
                            //nothing to do
                        }
                    }
                } catch (Exception e) {
                    //What to do when the while send an error ?
                }
                }
        }).start();

    }


    /**
     * supprAffichage is used to delete the current secondary screen
     */
    private void supprAffichage() {
        switch (afficheChoix) {
            case AFFICHE_SPOOL :
                ecranSpool.deleteContents();
                break;
            case AFFICHE_USER :
                ecranUser.deleteContents();
                break;
            case AFFICHE_CONFIG :
                ecranConfig.deleteContents();
                break;
            default: break;
        }
        afficheChoix = AFFICHE_RIEN;
    }

    /**
     * ajoutAffichage is used to add a secondary screen
     * @param choix constant AFFICHE_*
     */
    private void ajoutAffichage(int choix) {
        switch(choix) {
            case AFFICHE_SPOOL :
                ecranSpool.createContents();
                afficheChoix = AFFICHE_SPOOL;
                mntmSpoolUsage.setEnabled(false);
                mntmUser.setEnabled(true);
                mntmConfig.setEnabled(true);
                shell.layout();
                break;
            case AFFICHE_USER :
                ecranUser.createContents();
                afficheChoix = AFFICHE_USER;
                mntmSpoolUsage.setEnabled(true);
                mntmUser.setEnabled(false);
                mntmConfig.setEnabled(true);
                shell.layout();
                break;
            case AFFICHE_CONFIG :
                ecranConfig.createContents();
                afficheChoix = AFFICHE_CONFIG;
                mntmSpoolUsage.setEnabled(true);
                mntmUser.setEnabled(true);
                mntmConfig.setEnabled(false);
                shell.layout();
                break;
            default: break;
        }
    }

    /**
     * majAffichage is used to manage the secondary screen switch
     * @param choix constant AFFICHE_*
     */
    private void majAffichage(int choix) {
        supprAffichage();
        ajoutAffichage(choix);
        if (tdConnect != null) {
            activationON();
        }
    }

    /**
     * activationOFF is used to desactivate the current secondary screen elements
     */
    private void activationOFF() {
        switch (afficheChoix) {
            case AFFICHE_SPOOL :
                ecranSpool.desactivatedContents();
                break;
            case AFFICHE_USER :
                ecranUser.desactivatedContents();
                break;
            case AFFICHE_CONFIG :
                ecranConfig.desactivatedContents();
                break;
            default: break;
        }
    }

    /**
     * activationON is used to activate the current secondary screen elements
     */
    private void activationON() {

        switch(afficheChoix) {
            case AFFICHE_SPOOL :
                ecranSpool.activatedContents();
                shell.layout();
                break;
            case AFFICHE_USER :
                ecranUser.activatedContents();
                shell.layout();
                break;
            case AFFICHE_CONFIG :
                ecranConfig.activatedContents();
                shell.layout();
                break;
            default: break;
        }

    }

    /**
     * majInformation is used to add information in the log
     * @param typeMessage Type of log
     * @param moduleMessage Name of the secondary screen
     * @param corpsMessage Message of log
     */
    public void majInformation (String typeMessage, String moduleMessage, String corpsMessage) {
            Date date = new Date();
            listMessageInfo.addFirst(dateFormat.format(date)+" - "+typeMessage+" - "+moduleMessage+" - "+corpsMessage);
    }


    /**
     * refreshInformation is used to refresh the log information screen
     */
    private void refreshInformation () {
        if (textInformation != null) {
            String messageInfo = "";

            while (!listMessageInfo.isEmpty()) {
                messageInfo =  listMessageInfo.getLast() + "\n" + messageInfo;
                listMessageInfo.removeLast();
            }

            if (textInformation.getText() != null) {
                textInformation.setText(messageInfo + textInformation.getText());
            } else {
                textInformation.setText(messageInfo);
            }
        }
    }


    protected void manageDisconnection() {
        new Thread(new Runnable() {
            public void run() {

                Display.getDefault().asyncExec(new Runnable() {
                    public void run() {
                        activationOFF();
                        btnDisconnect.setEnabled(false);
                    }
                });

                tdConnect.deconnexion();
                tdConnect = null;
                majInformation(MESSAGE_INFORMATION,nomModuleConnexion,"Disconnection");

                Display.getDefault().asyncExec(new Runnable() {
                    public void run() {
                        textServer.setEnabled(true);
                        textUsername.setEnabled(true);
                        textPassword.setEnabled(true);

                        btnConnect.setEnabled(true);
                    }
                });
            }
        }).start();
    }

    /**
     * getConfig is used to get the current configuration (Config object)
     * @return current configuration
     */
    protected Config getConfig () {
        return this.conf;
    }

    /**
     * getMainComposite is used to get the main composite to add a secondary screen
     * @return main composite
     */
    protected Composite getMainComposite() {
        return pageComposite;
    }

    /**
     * getTdConnexion is used to get the current Teradata Connexion (TDConnexion)
     * @return Teradata connexion
     */
    protected TDConnexion getTdConnexion() {
        return tdConnect;
    }

    /**
     * getNumberAmps is used to get the number of AMP
     * @return number of AMP
     */
    protected int getNumberAmps() {
        return nbrAMP;
    }

    /**
     * getDisplay is used to get the Display of the primary Window
     * @return display of the primary Window
     */
    protected Display getDisplay() {
        return display;
    }

    /**
     * getTextUsername is used to get the username Text element
     * @return username text element
     */
    protected Text getTextUsername() {
        return textUsername;
    }

    /**
     * open is used to show the primary screen
     */
    public void open() {
        display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

       // programme stop with exit 0
        System.exit(0);

    }

    /**
     * refreshShell is used to refresh the primary screen
     */
    protected void refreshShell() {
        shell.layout();
    }

    /**
     * main is used to test the Class EcranPrincipal
     * @param args args of the test
     */
    public static void main(String[] args) {
        try {
            EcranPrincipal window = new EcranPrincipal();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
