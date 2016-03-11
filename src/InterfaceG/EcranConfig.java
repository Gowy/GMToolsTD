package InterfaceG;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.IOException;

/**
 * This Class is used to create the Config screen
 * @author GMA
 * @version 0.1
 */
public class EcranConfig extends ModelEcran implements InterfaceEcran {

    private Group groupConfig;
    private GridData gridData;

    private Button btnLoadConfig;
    private Button btnSaveConfig;
    private Button btnDefaultConfig;
    private Button btnResetConfig;

    private ScrolledComposite ScrollCompConfig;
    private Composite CompScConfig;

    private Text[] tabTextConfigValue;
    private Text[] tabTextConfigName;


    public EcranConfig(EcranPrincipal ep) {
        super(ep,"Config");
    }


    public void createContents() {
        majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Open Module");

        groupConfig = new Group(compositePrincipal, SWT.NONE);
        groupConfig.setText("Configuration");
        groupConfig.setLayout(new GridLayout(1, false));
        gridData = new GridData(SWT.LEFT, SWT.TOP, true, true, 2, 1);
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = SWT.FILL;
        gridData.grabExcessVerticalSpace = true;
        groupConfig.setLayoutData(gridData);


        Composite compoConfigAction = new Composite(groupConfig, SWT.RIGHT);
        compoConfigAction.setLayout(new GridLayout(4, false));
        gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        compoConfigAction.setLayoutData(gridData);

        btnLoadConfig = new Button(compoConfigAction, SWT.NONE);
        btnLoadConfig.setEnabled(true);
        btnLoadConfig.setText("Load");

        btnSaveConfig = new Button(compoConfigAction, SWT.NONE);
        btnSaveConfig.setEnabled(true);
        btnSaveConfig.setText("Save");

        btnResetConfig = new Button(compoConfigAction, SWT.NONE);
        btnResetConfig.setEnabled(true);
        btnResetConfig.setText("Reset");

        btnDefaultConfig = new Button(compoConfigAction, SWT.NONE);
        btnDefaultConfig.setEnabled(true);
        btnDefaultConfig.setText("Default");


        Group groupConfigInfo = new Group(groupConfig, SWT.NONE);
        groupConfigInfo.setText("Information");
        groupConfigInfo.setLayout(new GridLayout(1, false));
        gridData = new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1);
        groupConfigInfo.setLayoutData(gridData);


        ScrollCompConfig = new ScrolledComposite(groupConfigInfo, SWT.H_SCROLL | SWT.V_SCROLL);
        ScrollCompConfig.setAlwaysShowScrollBars(true);
        ScrollCompConfig.setLayout(new GridLayout(1, false));
        gridData = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
        ScrollCompConfig.setLayoutData(gridData);
        ScrollCompConfig.setExpandHorizontal(true);
        ScrollCompConfig.setExpandVertical(true);

        CompScConfig = new Composite(ScrollCompConfig, SWT.NONE);
        ScrollCompConfig.setContent(CompScConfig);
        CompScConfig.setLayout(new GridLayout(2, false));

        String[] listParam = ecranPrincipal.getConfig().getDefaultConfNames();

        tabTextConfigValue = new Text[listParam.length];
        tabTextConfigName = new Text[listParam.length];

        for (int i = 0 ; i < listParam.length ; i++) {

            gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
            gridData.widthHint = 150;

            tabTextConfigName[i] = new Text(CompScConfig, SWT.CENTER | SWT.READ_ONLY | SWT.BORDER );
            tabTextConfigName[i].setLayoutData(gridData);
            tabTextConfigName[i].setText(listParam[i]);

            gridData = new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1);
            gridData.widthHint = 250;

            tabTextConfigValue[i] = new Text(CompScConfig, SWT.CENTER | SWT.BORDER );
            tabTextConfigValue[i].setLayoutData(gridData);
            tabTextConfigValue[i].setText(ecranPrincipal.getConfig().getValueConf(listParam[i]));
        }

        ScrollCompConfig.setMinSize(CompScConfig.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        btnLoadConfig.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Load configuration");
                new Thread(new Runnable() {
                    public void run() {
                        ecranPrincipal.getDisplay().asyncExec(new Runnable() {
                            public void run() {

                                try {
                                    ecranPrincipal.getConfig().loadConf();
                                } catch (IOException err) {
                                    majInformation(ecranPrincipal.MESSAGE_ERROR,"During the conf file read : "+err.getMessage());
                                }

                                String[] listParam = ecranPrincipal.getConfig().getDefaultConfNames();

                                for (int i = 0 ; i < listParam.length ; i++) {
                                    tabTextConfigValue[i].setText(ecranPrincipal.getConfig().getValueConf(listParam[i]));
                                }

                            }
                        });
                    }}).start();
            }
        });

        btnSaveConfig.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Save configuration");
                new Thread(new Runnable() {
                    public void run() {
                        ecranPrincipal.getDisplay().asyncExec(new Runnable() {
                            public void run() {

                                String[] listParam = ecranPrincipal.getConfig().getDefaultConfNames();

                                for (int i = 0 ; i < listParam.length ; i++) {
                                    ecranPrincipal.getConfig().setValueConf(listParam[i],tabTextConfigValue[i].getText());
                                }

                                try {
                                    ecranPrincipal.getConfig().saveConfig();
                                } catch (IOException err) {
                                    majInformation(ecranPrincipal.MESSAGE_ERROR,"During the conf file save : "+err.getMessage());
                                }
                            }
                        });
                    }}).start();
            }
        });

        btnResetConfig.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Reset configuration");
                new Thread(new Runnable() {
                    public void run() {
                        ecranPrincipal.getDisplay().asyncExec(new Runnable() {
                            public void run() {

                                String[] listParam = ecranPrincipal.getConfig().getDefaultConfNames();

                                for (int i = 0 ; i < listParam.length ; i++) {
                                    tabTextConfigValue[i].setText(ecranPrincipal.getConfig().getValueConf(listParam[i]));
                                }
                            }
                        });
                    }}).start();
            }
        });


        btnDefaultConfig.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Load default configuration");
                new Thread(new Runnable() {
                    public void run() {
                        ecranPrincipal.getDisplay().asyncExec(new Runnable() {
                            public void run() {
                                String[] listValues = ecranPrincipal.getConfig().getDefaultConfValues();

                                for (int i = 0 ; i < listValues.length ; i++) {
                                    tabTextConfigValue[i].setText(listValues[i]);
                                }
                            }
                        });
                    }}).start();
            }
        });


        compositePrincipal.pack(true);
        ecranPrincipal.refreshShell();

    }


    public void deleteContents() {
        majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Close Module");
        groupConfig.dispose();
        compositePrincipal.pack(true);
    }


    public void activatedContents() {

    }


    public void desactivatedContents() {

    }
}
