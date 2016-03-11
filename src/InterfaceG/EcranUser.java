package InterfaceG;

import BDD.LabInfo;
import BDD.ProfileInfo;
import BDD.UserTDInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class is used to create the User screen
 * @author GMA
 * @version 0.1
 */
public class EcranUser extends ModelEcran implements InterfaceEcran {


    // ecran user
    private Text textUserName;
    private Button btnValidUser;
    private Button btnResetUser;
    private Group groupUser;
    private Group groupUserRole;
    private Group groupUserLabs;
    private Text textUserInfoCommentString;
    private Text textUserInfoProfile;
    private Text textUserInfoPWDLModif;
    private Text textUserInfoCreate;
    private Text textUserInfoLockDate;
    private Text textUserInfoLockCount;
    private Text textUserProfileName;
    private Text textUserProfileComment;
    private Text textUserProfileSpool;
    private Text textUserProfileTemp;
    private Text textUserProfileDefDB;
    private Text textUserProfileDefAcc;

    private Text[] tabTextRole;
    private Text[][] tabTextLabs;


    private ScrolledComposite ScrollCompRole;
    private Composite CompScRole;
    private ScrolledComposite ScrollCompLabs;
    private Composite CompScLabs;

    private UserTDInfo userTDInfo;
    private ProfileInfo profileInfo;
    private List<String> listRole;
    private List<LabInfo> listLabs;

    private boolean afficheInfo = false;
    private boolean afficheProfile = false;
    private boolean afficheRoles = false;
    private boolean afficheLabs = false;

    // autres
    private GridData gridData;


    public EcranUser(EcranPrincipal ep) {
        super(ep,"User");
    }


    // ecran Module Utilisateur
    public void createContents() {
        majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Open Module");

        groupUser = new Group(compositePrincipal, SWT.NONE);
        groupUser.setText("User");
        groupUser.setLayout(new GridLayout(2, false));
        gridData = new GridData(SWT.LEFT, SWT.TOP, true, true, 2, 1);
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = SWT.FILL;
        gridData.grabExcessVerticalSpace = true;
        groupUser.setLayoutData(gridData);


        Composite compoUserAction = new Composite(groupUser, SWT.RIGHT);
        compoUserAction.setLayout(new GridLayout(4, false));
        gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
        compoUserAction.setLayoutData(gridData);


        Label lblUserName = new Label(compoUserAction, SWT.NONE);
        lblUserName.setText("Username :");

        textUserName = new Text(compoUserAction, SWT.BORDER);
        gridData = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
        gridData.widthHint = 150;
        textUserName.setLayoutData(gridData);
        textUserName.setEnabled(false);


        btnValidUser = new Button(compoUserAction, SWT.NONE);
        btnValidUser.setEnabled(false);
        btnValidUser.setText("Search");

        btnResetUser = new Button(compoUserAction, SWT.NONE);
        btnResetUser.setEnabled(false);
        btnResetUser.setText("Reset");


        Group groupUserInfo = new Group(groupUser, SWT.NONE);
        groupUserInfo.setText("Info");
        groupUserInfo.setLayout(new GridLayout(2, false));
        gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        groupUserInfo.setLayoutData(gridData);

        Label lblUserInfoCommentString = new Label(groupUserInfo, SWT.NONE);
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblUserInfoCommentString.setLayoutData(gridData);
        lblUserInfoCommentString.setText("CommentString :");

        textUserInfoCommentString = new Text(groupUserInfo, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
        gridData.widthHint = 150;
        textUserInfoCommentString.setLayoutData(gridData);


        Label lblUserInfoProfile = new Label(groupUserInfo, SWT.NONE);
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblUserInfoProfile.setLayoutData(gridData);
        lblUserInfoProfile.setText("ProfileName :");

        textUserInfoProfile = new Text(groupUserInfo, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
        textUserInfoProfile.setLayoutData(gridData);

        Label lblUserInfoPWDLModif = new Label(groupUserInfo, SWT.NONE);
        lblUserInfoPWDLModif.setText("PWD Last Modification :");
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblUserInfoPWDLModif.setLayoutData(gridData);

        textUserInfoPWDLModif = new Text(groupUserInfo, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
        textUserInfoPWDLModif.setLayoutData(gridData);

        Label lblUserInfoCreate = new Label(groupUserInfo, SWT.NONE);
        lblUserInfoCreate.setText("Create :");
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblUserInfoCreate.setLayoutData(gridData);

        textUserInfoCreate = new Text(groupUserInfo, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
        textUserInfoCreate.setLayoutData(gridData);

        Label lblUserInfoLockDate = new Label(groupUserInfo, SWT.NONE);
        lblUserInfoLockDate.setText("Locked Date :");
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblUserInfoLockDate.setLayoutData(gridData);

        textUserInfoLockDate = new Text(groupUserInfo, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
        textUserInfoLockDate.setLayoutData(gridData);

        Label lblUserInfoLockCount = new Label(groupUserInfo, SWT.NONE);
        lblUserInfoLockCount.setText("Locked Count :");
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblUserInfoLockCount.setLayoutData(gridData);

        textUserInfoLockCount = new Text(groupUserInfo, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
        textUserInfoLockCount.setLayoutData(gridData);



        Group groupUserProfile = new Group(groupUser, SWT.RIGHT);
        groupUserProfile.setText("Profile");
        groupUserProfile.setLayout(new GridLayout(2, false));
        gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        groupUserProfile.setLayoutData(gridData);

        Label lblUserProfileName = new Label(groupUserProfile, SWT.NONE);
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblUserProfileName.setLayoutData(gridData);
        lblUserProfileName.setText("ProfileName :");

        textUserProfileName = new Text(groupUserProfile, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
        gridData.widthHint = 150;
        textUserProfileName.setLayoutData(gridData);

        Label lblUserProfileComment = new Label(groupUserProfile, SWT.NONE);
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblUserProfileComment.setLayoutData(gridData);
        lblUserProfileComment.setText("CommentString :");

        textUserProfileComment = new Text(groupUserProfile, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
        gridData.widthHint = 150;
        textUserProfileComment.setLayoutData(gridData);

        Label lblUserProfileSpool = new Label(groupUserProfile, SWT.NONE);
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblUserProfileSpool.setLayoutData(gridData);
        lblUserProfileSpool.setText("Spool Limit :");

        textUserProfileSpool = new Text(groupUserProfile, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
        textUserProfileSpool.setLayoutData(gridData);

        Label lblUserProfileTemp = new Label(groupUserProfile, SWT.NONE);
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblUserProfileTemp.setLayoutData(gridData);
        lblUserProfileTemp.setText("Temp Limit :");

        textUserProfileTemp = new Text(groupUserProfile, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
        textUserProfileTemp.setLayoutData(gridData);

        Label lblUserProfileDefDB = new Label(groupUserProfile, SWT.NONE);
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblUserProfileDefDB.setLayoutData(gridData);
        lblUserProfileDefDB.setText("Default Database :");

        textUserProfileDefDB = new Text(groupUserProfile, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
        textUserProfileDefDB.setLayoutData(gridData);

        Label lblUserProfileDefAcc = new Label(groupUserProfile, SWT.NONE);
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblUserProfileDefAcc.setLayoutData(gridData);
        lblUserProfileDefAcc.setText("Default Account :");

        textUserProfileDefAcc = new Text(groupUserProfile, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
        textUserProfileDefAcc.setLayoutData(gridData);



        // group permettant d'afficher les roles de l'utilisateurs
        groupUserRole = new Group(groupUser, SWT.NONE);
        //groupUserRole.setTouchEnabled(true);
        groupUserRole.setText("Role");
        groupUserRole.setLayout(new GridLayout(1, false));
        gridData = new GridData(SWT.FILL, SWT.TOP, false, true, 1, 1);
        groupUserRole.setLayoutData(gridData);

        Composite compUserRoleF = new Composite(groupUserRole, SWT.NONE);
        gridData = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
        compUserRoleF.setLayout(new GridLayout(1, false));
        compUserRoleF.setLayoutData(gridData);

        Text textUserRoleFTitre = new Text(compUserRoleF, SWT.CENTER | SWT.READ_ONLY);
        textUserRoleFTitre.setText("Role Fonctionnel");
        gridData = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
        textUserRoleFTitre.setLayoutData(gridData);



        // group permettant d'afficher les labs de l'utilisateurs
        groupUserLabs = new Group(groupUser, SWT.NONE);
        groupUserLabs.setText("Labs");
        groupUserLabs.setLayout(new GridLayout(1, false));
        gridData = new GridData(SWT.FILL, SWT.TOP, false, true, 1, 1);
        groupUserLabs.setLayoutData(gridData);

        Composite compUserLabs = new Composite(groupUserLabs, SWT.NONE);
        gridData = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
        compUserLabs.setLayout(new GridLayout(3, false));
        compUserLabs.setLayoutData(gridData);

        Text textUserLabsTitreGroupe = new Text(compUserLabs, SWT.CENTER | SWT.READ_ONLY);
        textUserLabsTitreGroupe.setText("Groupe");
        gridData = new GridData(SWT.CENTER, SWT.TOP, true, false, 1, 1);
        gridData.widthHint = 150;
        textUserLabsTitreGroupe.setLayoutData(gridData);

        Text textUserLabsTitreLab = new Text(compUserLabs, SWT.CENTER | SWT.READ_ONLY);
        textUserLabsTitreLab.setText("Lab");
        gridData = new GridData(SWT.CENTER, SWT.TOP, true, false, 1, 1);
        gridData.widthHint = 100;
        textUserLabsTitreLab.setLayoutData(gridData);

        Text textUserLabsTitreDroit = new Text(compUserLabs, SWT.CENTER | SWT.READ_ONLY);
        textUserLabsTitreDroit.setText("Droit");
        gridData = new GridData(SWT.CENTER, SWT.TOP, true, false, 1, 1);
        gridData.widthHint = 100;
        textUserLabsTitreDroit.setLayoutData(gridData);

        btnValidUser.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                final String username = textUserName.getText();
                listRole = new ArrayList<>();
                majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Search information");

                new Thread(new Runnable() {
                    public void run() {

                        try {
                            userTDInfo = ecranPrincipal.getTdConnexion().requestUserTDInfo(username);
                            afficheInfo = true;
                        } catch (SQLException err) {
                            majInformation(ecranPrincipal.MESSAGE_ERROR,err.getMessage());
                            afficheInfo = false;
                            userTDInfo = null;
                        }

                        if (userTDInfo != null) {
                            try {
                                profileInfo = ecranPrincipal.getTdConnexion().requestProfileInfo(userTDInfo.getProfileName());
                                afficheProfile = true;
                            } catch (SQLException err) {
                                majInformation(ecranPrincipal.MESSAGE_ERROR,err.getMessage());
                                afficheProfile = false;
                            } catch (Exception err) {
                                majInformation(ecranPrincipal.MESSAGE_ERROR,err.getMessage());
                                afficheProfile = false;
                            }
                        } else {
                            afficheProfile = false;
                        }

                        try {
                            listRole = ecranPrincipal.getTdConnexion().requestListRole(username);
                            afficheRoles = true;
                        } catch (SQLException err) {
                            majInformation(ecranPrincipal.MESSAGE_ERROR,err.getMessage());
                            afficheRoles = false;
                        }

                        try {
                            listLabs = ecranPrincipal.getTdConnexion().requestListLabs(username);
                            afficheLabs = true;
                        } catch (SQLException err) {
                            majInformation(ecranPrincipal.MESSAGE_ERROR,err.getMessage());
                            afficheLabs = false;
                        }


                        ecranPrincipal.getDisplay().asyncExec(new Runnable() {
                            public void run() {
                                if (afficheInfo) {
                                    // group information
                                    textUserInfoCommentString.setText(userTDInfo.getCommentString());
                                    textUserInfoProfile.setText(userTDInfo.getProfileName());
                                    textUserInfoPWDLModif.setText(userTDInfo.getPwdDateLastModif());
                                    textUserInfoCreate.setText(userTDInfo.getCreateDate());
                                    textUserInfoLockDate.setText(userTDInfo.getLockedDate());
                                    textUserInfoLockCount.setText(userTDInfo.getLockedCount());
                                } else {
                                    textUserInfoCommentString.setText("");
                                    textUserInfoProfile.setText("");
                                    textUserInfoPWDLModif.setText("");
                                    textUserInfoCreate.setText("");
                                    textUserInfoLockDate.setText("");
                                    textUserInfoLockCount.setText("");
                                }

                                if (afficheProfile) {
                                    // group profile
                                    textUserProfileName.setText(profileInfo.getProfileName());
                                    textUserProfileComment.setText(profileInfo.getCommentString());
                                    textUserProfileDefDB.setText(profileInfo.getDefaultDB());
                                    textUserProfileDefAcc.setText(profileInfo.getDefaultAcc());
                                    textUserProfileSpool.setText(profileInfo.getSpoolSpace());
                                    textUserProfileTemp.setText(profileInfo.getTempSpace());
                                } else {
                                    textUserProfileName.setText("");
                                    textUserProfileComment.setText("");
                                    textUserProfileDefDB.setText("");
                                    textUserProfileDefAcc.setText("");
                                    textUserProfileSpool.setText("");
                                    textUserProfileTemp.setText("");
                                }

                                if (ScrollCompRole!= null) {
                                    ScrollCompRole.dispose();
                                }

                                if (afficheRoles) {
                                    // group roles (groupUserRole)
                                    int tmp = listRole.size();

                                    if (tmp < 1) {
                                        // on gère une liste vide ?
                                        System.out.println("La liste des roles est vide !");
                                    } else {

                                        if (ScrollCompRole == null || ScrollCompRole.isDisposed()) {

                                            ScrollCompRole = new ScrolledComposite(groupUserRole, SWT.H_SCROLL | SWT.V_SCROLL);
                                            ScrollCompRole.setAlwaysShowScrollBars(true);
                                            ScrollCompRole.setLayout(new GridLayout(3, false));
                                            gridData = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
                                            ScrollCompRole.setLayoutData(gridData);
                                            ScrollCompRole.setExpandHorizontal(true);
                                            ScrollCompRole.setExpandVertical(true);

                                            CompScRole = new Composite(ScrollCompRole, SWT.NONE);
                                            ScrollCompRole.setContent(CompScRole);
                                            CompScRole.setLayout(new GridLayout(1, false));

                                        }


                                        if ((tabTextRole != null) && (tabTextRole.length > 0)) {
                                            for (int i = 0; i < tabTextRole.length ; i++) {
                                                tabTextRole[i].dispose();
                                            }
                                        }

                                        tabTextRole = new Text[tmp];
                                        for (int i = 0; i < tmp; i++ ) {
                                            gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
                                            tabTextRole[i] = new Text(CompScRole, SWT.CENTER | SWT.READ_ONLY | SWT.BORDER );
                                            tabTextRole[i].setLayoutData(gridData);
                                            tabTextRole[i].setText(listRole.get(i));
                                        }

                                        ScrollCompRole.setMinSize(CompScRole.computeSize(SWT.DEFAULT, SWT.DEFAULT));

                                    }

                                }

                                if (ScrollCompLabs!= null) {
                                    ScrollCompLabs.dispose();
                                }

                                if (afficheLabs) {
                                    // group labs (groupUserLabs)
                                    int tmp = listLabs.size();

                                    if (tmp < 1) {
                                        // on gère une liste vide ?
                                        System.out.println("La liste des labs est vide !");
                                    } else {

                                        if (ScrollCompLabs == null || ScrollCompLabs.isDisposed()) {

                                            ScrollCompLabs = new ScrolledComposite(groupUserLabs, SWT.H_SCROLL | SWT.V_SCROLL);
                                            ScrollCompLabs.setAlwaysShowScrollBars(true);
                                            ScrollCompLabs.setLayout(new GridLayout(3, false));
                                            gridData = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
                                            ScrollCompLabs.setLayoutData(gridData);
                                            ScrollCompLabs.setExpandHorizontal(true);
                                            ScrollCompLabs.setExpandVertical(true);

                                            CompScLabs = new Composite(ScrollCompLabs, SWT.NONE);
                                            ScrollCompLabs.setContent(CompScLabs);
                                            CompScLabs.setLayout(new GridLayout(3, false));

                                        }


                                       /* if ((tabTextLabs != null) && (tabTextLabs.length > 0)) {
                                            for (int i = 0; i < tabTextLabs.length ; i++) {
                                                tabTextLabs[i][0].dispose();
                                                tabTextLabs[i][1].dispose();
                                                tabTextLabs[i][2].dispose();
                                            }
                                        } */

                                        tabTextLabs = new Text[tmp][3];
                                        for (int i = 0; i < tmp; i++ ) {
                                            gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
                                            gridData.widthHint = 150;
                                            tabTextLabs[i][0] = new Text(CompScLabs, SWT.CENTER | SWT.READ_ONLY | SWT.BORDER );
                                            tabTextLabs[i][0].setLayoutData(gridData);
                                            tabTextLabs[i][0].setText(listLabs.get(i).getGroup());

                                            gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
                                            gridData.widthHint = 100;
                                            tabTextLabs[i][1] = new Text(CompScLabs, SWT.CENTER | SWT.READ_ONLY | SWT.BORDER );
                                            tabTextLabs[i][1].setLayoutData(gridData);
                                            tabTextLabs[i][1].setText(listLabs.get(i).getLab());

                                            gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
                                            gridData.widthHint = 100;
                                            tabTextLabs[i][2] = new Text(CompScLabs, SWT.CENTER | SWT.READ_ONLY | SWT.BORDER );
                                            tabTextLabs[i][2].setLayoutData(gridData);
                                            tabTextLabs[i][2].setText(listLabs.get(i).getDroit());
                                        }

                                        ScrollCompLabs.setMinSize(CompScLabs.computeSize(SWT.DEFAULT, SWT.DEFAULT));

                                    }
                                }


                                if (afficheRoles || afficheLabs ) {
                                    compositePrincipal.pack(true);
                                    ecranPrincipal.refreshShell();
                                }

                            }
                        });

                    }}).start();
            }
        });


        btnResetUser.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Reset information");
                new Thread(new Runnable() {
                    public void run() {
                        ecranPrincipal.getDisplay().asyncExec(new Runnable() {
                            public void run() {
                                // group information
                                textUserInfoCommentString.setText("");
                                textUserInfoProfile.setText("");
                                textUserInfoPWDLModif.setText("");
                                textUserInfoCreate.setText("");
                                textUserInfoLockDate.setText("");
                                textUserInfoLockCount.setText("");

                                // group profile
                                textUserProfileName.setText("");
                                textUserProfileComment.setText("");
                                textUserProfileDefDB.setText("");
                                textUserProfileDefAcc.setText("");
                                textUserProfileSpool.setText("");
                                textUserProfileTemp.setText("");

                                ScrollCompLabs.dispose();
                                ScrollCompRole.dispose();
                            }
                        });
                    }}).start();
            }
        });

        compositePrincipal.pack(true);

    }

    public void deleteContents() {
        majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Close Module");
        groupUser.dispose();
        compositePrincipal.pack(true);
    }

    public void activatedContents() {

        if (textUserName.getText().equals("")) {
            textUserName.setText(ecranPrincipal.getTextUsername().getText());
        }

        btnValidUser.setEnabled(true);
        btnResetUser.setEnabled(true);
        textUserName.setEnabled(true);
    }

    public void desactivatedContents() {

        btnValidUser.setEnabled(false);
        btnResetUser.setEnabled(false);
        textUserName.setEnabled(false);
    }





}
