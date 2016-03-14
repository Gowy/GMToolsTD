package InterfaceG;

import BDD.SpoolUsage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * This Class is used to create the Spool screen
 * @author GMA
 * @version 0.1
 */
public class EcranSpool extends ModelEcran implements InterfaceEcran{

    // ecran spool
    private Group groupSpool;
    private Group groupSpoolSum;
    private Text textCurrentSum;
    private Text textRealSum;
    private Text textSkewFactor;

    private Text textMaxCurrentSum;
    private Text textMaxRealSum;
    private Text textMaxSkewFactor;

    //ecran spool detail
    private Group groupSpoolDetail;
    private ScrolledComposite ScrollCompSpoolDetail;
    private Composite CompScSpoolDetail;
    private Group groupSpoolDetailAgg;
    private ScrolledComposite ScrollCompSpoolDetailAgg;
    private Composite CompScSpoolDetailAgg;
    Text[][] tabTextSpoolDet;
    Text[][] tabTextSpoolDetAgg;
    Map<Integer,SpoolUsage> AggTextSpoolDet;
    private Button btnStartSpool;
    private Button btnStopSpool;
    private Button btnResetSpool;
    private Button btnRadioSizeTo;
    private Button btnRadioSizeGo;
    private Button btnRadioSizeMo;
    private Button btnRadioSizeKo;
    private Text textSpoolUser;
    private Text textSpoolFreq;
    private Text textSpoolInfoStart;
    private Text textSpoolInfoCurrent;
    private Text textSpoolInfoIter;

    private String sizeValueSumSpool;
    private String sizeValueSpool;
    private boolean thSpoolActive;
    private Integer freqCalcSpool = 5;
    private String textSpoolUserValue;
    private String textCurrentSumValue;
    private String textRealSumValue;
    private String textSkewFactorValue;
    private String[][] tabTextSpoolDetValue;
    private String[][] tabTextSpoolDetAggValue;
    private int compteurIter = 0;

    private GridData gridData;
    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final DateFormat heureFormat = new SimpleDateFormat("HH:mm:ss");


    public EcranSpool(EcranPrincipal ep) {
        super(ep,"Spool");
    }

    @Override
    public void createContents() {
        majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Open Module");
        /* création des éléments de l'écran spool */
        groupSpool = new Group(compositePrincipal, SWT.NONE);
        groupSpool.setText("Spool");
        groupSpool.setLayout(new GridLayout(4,false));
        gridData = new GridData(SWT.LEFT, SWT.TOP, true, true, 2, 1);
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = SWT.FILL;
        gridData.grabExcessVerticalSpace = true;
        groupSpool.setLayoutData(gridData);


        Composite compoSpoolBtn = new Composite(groupSpool,SWT.NONE);
        compoSpoolBtn.setLayout(new GridLayout(3,false));
        gridData = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
        compoSpoolBtn.setLayoutData(gridData);

        btnStartSpool = new Button(compoSpoolBtn, SWT.NONE);
        btnStartSpool.setEnabled(false);
        btnStartSpool.setText("Start");

        btnStopSpool = new Button(compoSpoolBtn, SWT.NONE);
        btnStopSpool.setEnabled(false);
        btnStopSpool.setText("Stop");

        btnResetSpool = new Button(compoSpoolBtn, SWT.NONE);
        btnResetSpool.setEnabled(false);
        btnResetSpool.setText("Reset");


        Group groupSpoolInfo = new Group(groupSpool, SWT.RIGHT);
        groupSpoolInfo.setText("Info");
        groupSpoolInfo.setLayout(new GridLayout(2,false));
        gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2);
        groupSpoolInfo.setLayoutData(gridData);

        Label lblSpoolInfoStart = new Label(groupSpoolInfo, SWT.NONE);
        lblSpoolInfoStart.setText("Start :");
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblSpoolInfoStart.setLayoutData(gridData);

        textSpoolInfoStart = new Text(groupSpoolInfo, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
        gridData.widthHint = 60;
        textSpoolInfoStart.setLayoutData(gridData);

        Label lblSpoolInfoCurrent = new Label(groupSpoolInfo, SWT.NONE);
        lblSpoolInfoCurrent.setText("Current :");
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblSpoolInfoCurrent.setLayoutData(gridData);

        textSpoolInfoCurrent = new Text(groupSpoolInfo, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
        gridData.widthHint = 60;
        textSpoolInfoCurrent.setLayoutData(gridData);

        Label lblSpoolInfoIter = new Label(groupSpoolInfo, SWT.NONE);
        lblSpoolInfoIter.setText("Iteration :");
        gridData = new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1);
        lblSpoolInfoIter.setLayoutData(gridData);

        textSpoolInfoIter = new Text(groupSpoolInfo, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
        gridData.widthHint = 50;
        textSpoolInfoIter.setLayoutData(gridData);


        groupSpoolSum = new Group(groupSpool, SWT.RIGHT);
        groupSpoolSum.setText("Summary");
        groupSpoolSum.setLayout(new GridLayout(3,false));
        gridData = new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 2);
        groupSpoolSum.setLayoutData(gridData);

        Label lblCurrent = new Label(groupSpoolSum, SWT.NONE);
        lblCurrent.setText("Current");
        gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
        lblCurrent.setLayoutData(gridData);

        Label lblReal = new Label(groupSpoolSum, SWT.NONE);
        lblReal.setText("Real");
        gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
        lblReal.setLayoutData(gridData);

        Label lblSkewFactor = new Label(groupSpoolSum, SWT.NONE);
        lblSkewFactor.setText("Skew");
        gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
        lblSkewFactor.setLayoutData(gridData);

        textCurrentSum = new Text(groupSpoolSum, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
        gridData.widthHint = 100;
        textCurrentSum.setLayoutData(gridData);

        textRealSum = new Text(groupSpoolSum, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
        gridData.widthHint = 100;
        textRealSum.setLayoutData(gridData);

        textSkewFactor = new Text(groupSpoolSum, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
        gridData.widthHint = 100;
        textSkewFactor.setLayoutData(gridData);


        Label lblMaxCurrent = new Label(groupSpoolSum, SWT.NONE);
        lblMaxCurrent.setText("MaxCurrent");
        gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
        lblMaxCurrent.setLayoutData(gridData);

        Label lblMaxReal = new Label(groupSpoolSum, SWT.NONE);
        lblMaxReal.setText("MaxReal");
        gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
        lblMaxReal.setLayoutData(gridData);

        Label lblMaxSkewFactor = new Label(groupSpoolSum, SWT.NONE);
        lblMaxSkewFactor.setText("MaxSkew");
        gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
        lblMaxSkewFactor.setLayoutData(gridData);

        textMaxCurrentSum = new Text(groupSpoolSum, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
        gridData.widthHint = 100;
        textMaxCurrentSum.setLayoutData(gridData);

        textMaxRealSum = new Text(groupSpoolSum, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
        gridData.widthHint = 100;
        textMaxRealSum.setLayoutData(gridData);

        textMaxSkewFactor = new Text(groupSpoolSum, SWT.CENTER | SWT.BORDER | SWT.READ_ONLY);
        gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
        gridData.widthHint = 100;
        textMaxSkewFactor.setLayoutData(gridData);



        Group groupSpoolOption = new Group(groupSpool, SWT.NONE);
        groupSpoolOption.setText("Option");
        groupSpoolOption.setLayout(new GridLayout(5,false));
        groupSpoolOption.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));


        Label labSpoolUser = new Label(groupSpoolOption, SWT.NONE);
        labSpoolUser.setText("User :");
        labSpoolUser.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,1,1));
        textSpoolUser = new Text(groupSpoolOption, SWT.BORDER);
        textSpoolUser.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,false,false,4,1));
        textSpoolUser.setEnabled(false);

        Label labSpoolFreq = new Label(groupSpoolOption, SWT.NONE);
        labSpoolFreq.setText("Sleep (s):");
        labSpoolFreq.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,2,1));
        textSpoolFreq = new Text(groupSpoolOption, SWT.BORDER);
        textSpoolFreq.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,3,1));
        textSpoolFreq.setEnabled(false);

        Label lblSize = new Label(groupSpoolOption, SWT.NONE);
        lblSize.setText("Size :");
        lblSize.setLayoutData(new GridData(SWT.RIGHT,SWT.CENTER,false,false,1,1));


        btnRadioSizeTo = new Button(groupSpoolOption, SWT.RADIO);
        btnRadioSizeTo.setEnabled(false);
        btnRadioSizeTo.setSelection(false);
        btnRadioSizeTo.setBounds(163, 20, 42, 16);
        btnRadioSizeTo.setText("To");


        btnRadioSizeGo = new Button(groupSpoolOption, SWT.RADIO);
        btnRadioSizeGo.setEnabled(false);
        btnRadioSizeGo.setSelection(true);
        btnRadioSizeGo.setBounds(163, 20, 42, 16);
        btnRadioSizeGo.setText("Go");
        sizeValueSpool = "Mo";
        sizeValueSumSpool = "Go";

        btnRadioSizeMo = new Button(groupSpoolOption, SWT.RADIO);
        btnRadioSizeMo.setEnabled(false);
        btnRadioSizeMo.setSelection(false);
        btnRadioSizeMo.setBounds(211, 21, 42, 16);
        btnRadioSizeMo.setText("Mo");


        btnRadioSizeKo = new Button(groupSpoolOption, SWT.RADIO);
        btnRadioSizeKo.setEnabled(false);
        btnRadioSizeKo.setSelection(false);
        btnRadioSizeKo.setBounds(211, 21, 42, 16);
        btnRadioSizeKo.setText("Ko");


        groupSpoolDetail = new Group(groupSpool, SWT.NONE);
        groupSpoolDetail.setTouchEnabled(true);
        groupSpoolDetail.setText("Detail");
        groupSpoolDetail.setLayout(new GridLayout(1,false));
        gridData = new GridData(SWT.CENTER, SWT.TOP, true, true, 2, 1);
        groupSpoolDetail.setLayoutData(gridData);

        Composite ctmp = new Composite(groupSpoolDetail, SWT.NONE);
        gridData = new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1);
        gridData.horizontalAlignment = SWT.FILL;
        ctmp.setLayout(new GridLayout(3,false));
        ctmp.setLayoutData(gridData);

        Text lblDetVproc = new Text(ctmp, SWT.CENTER | SWT.READ_ONLY);
        lblDetVproc.setText("Vproc");
        gridData = new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1);
        gridData.widthHint = 60;
        lblDetVproc.setLayoutData(gridData);

        Text lblDetCurrent = new Text(ctmp, SWT.CENTER | SWT.READ_ONLY);
        lblDetCurrent.setText("CurrentSpool");
        gridData = new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1);
        gridData.widthHint = 110;
        lblDetCurrent.setLayoutData(gridData);

        Text lblDetMax = new Text(ctmp, SWT.CENTER | SWT.READ_ONLY);
        lblDetMax.setText("MaxSpool");
        gridData = new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1);
        gridData.widthHint = 110;
        lblDetMax.setLayoutData(gridData);



        groupSpoolDetailAgg = new Group(groupSpool, SWT.NONE);
        groupSpoolDetailAgg.setTouchEnabled(true);
        groupSpoolDetailAgg.setText("Max");
        groupSpoolDetailAgg.setLayout(new GridLayout(1,false));
        gridData = new GridData(SWT.CENTER, SWT.TOP, true, true, 1, 1);
        groupSpoolDetailAgg.setLayoutData(gridData);

        Composite ctmpAgg = new Composite(groupSpoolDetailAgg, SWT.NONE);
        gridData = new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1);
        gridData.horizontalAlignment = SWT.FILL;
        ctmpAgg.setLayout(new GridLayout(3,false));
        ctmpAgg.setLayoutData(gridData);

        Text lblDetAggVproc = new Text(ctmpAgg, SWT.CENTER | SWT.READ_ONLY);
        lblDetAggVproc.setText("Vproc");
        gridData = new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1);
        gridData.widthHint = 60;
        lblDetAggVproc.setLayoutData(gridData);

        Text lblDetAggCurrent = new Text(ctmpAgg, SWT.CENTER | SWT.READ_ONLY);
        lblDetAggCurrent.setText("CurrentSpool");
        gridData = new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1);
        gridData.widthHint = 110;
        lblDetAggCurrent.setLayoutData(gridData);

        Text lblDetAggMax = new Text(ctmpAgg, SWT.CENTER | SWT.READ_ONLY);
        lblDetAggMax.setText("MaxSpool");
        gridData = new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1);
        gridData.widthHint = 110;
        lblDetAggMax.setLayoutData(gridData);




        btnStartSpool.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                thSpoolActive = true;
                AggTextSpoolDet = null;
                textSpoolUserValue = textSpoolUser.getText();
                compteurIter = 0;

                try {
                    freqCalcSpool = Integer.parseInt(textSpoolFreq.getText());
                    if (freqCalcSpool <= 0) { throw new Exception("");}
                } catch (Exception err) {
                    majInformation(ecranPrincipal.MESSAGE_ERROR,"Sleep has to be an integer superior than 0 second (not ["+textSpoolFreq.getText()+"])");
                    thSpoolActive = false;
                    activatedContents();
                    return;
                }
                majDateText(textSpoolInfoStart);
                majDateText(textSpoolInfoCurrent);
                textSpoolInfoIter.setText(compteurIter+"");
                majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Start");
                new Thread(new Runnable() {
                    public void run() {
                        while (thSpoolActive) {
                            final int nbrAMP = ecranPrincipal.getNumberAmps();

                            if (!thSpoolActive) { return ;}
                            SpoolUsage spoolUsageSum;

                            try {
                                spoolUsageSum = ecranPrincipal.getTdConnexion().requestSpoolSum(textSpoolUserValue);
                                textCurrentSumValue = spoolUsageSum.formatSpoolWithSize(spoolUsageSum.getCurrentSpoolSum(),sizeValueSumSpool) + " " +sizeValueSumSpool;
                                textRealSumValue = spoolUsageSum.formatSpoolWithSize(spoolUsageSum.getRealSpoolSum(),sizeValueSumSpool) + " " + sizeValueSumSpool;
                                textSkewFactorValue = spoolUsageSum.getSkewFactor() + " %";

                                List<SpoolUsage> listSU;
                                listSU = ecranPrincipal.getTdConnexion().requestSpoolDet(textSpoolUserValue);

                                if ( AggTextSpoolDet == null || AggTextSpoolDet.isEmpty() ) {
                                    AggTextSpoolDet = new HashMap<>();
                                    for (int i = 0; i < nbrAMP; i++) {
                                        AggTextSpoolDet.put(i,new SpoolUsage(i,0.0,0.0));
                                    }
                                } else {
                                    for (int i = 0; i < ecranPrincipal.getNumberAmps(); i++) {
                                        SpoolUsage suTmp = listSU.get(i);
                                        AggTextSpoolDet.put(suTmp.getVproc(),suTmp.returnMaxSpoolUsage(AggTextSpoolDet.get(suTmp.getVproc())));
                                    }
                                }

                                tabTextSpoolDetValue = new String[3][nbrAMP];
                                tabTextSpoolDetAggValue = new String[3][nbrAMP];

                                for (int i = 0; i < nbrAMP; i++) {
                                    tabTextSpoolDetValue[0][i] = listSU.get(i).getVproc().toString();
                                    tabTextSpoolDetValue[1][i] = listSU.get(i).formatSpoolWithSize(listSU.get(i).getCurrentSpool(), sizeValueSpool) + " " + sizeValueSpool;
                                    tabTextSpoolDetValue[2][i] = listSU.get(i).formatSpoolWithSize(listSU.get(i).getMaxProfileSpool(), sizeValueSpool) + " " + sizeValueSpool;
                                    tabTextSpoolDetAggValue[0][i] = AggTextSpoolDet.get(i).getVproc().toString();
                                    tabTextSpoolDetAggValue[1][i] = AggTextSpoolDet.get(i).formatSpoolWithSize(AggTextSpoolDet.get(i).getCurrentSpool(), sizeValueSpool) + " " + sizeValueSpool;
                                    tabTextSpoolDetAggValue[2][i] = AggTextSpoolDet.get(i).formatSpoolWithSize(AggTextSpoolDet.get(i).getMaxProfileSpool(), sizeValueSpool) + " " + sizeValueSpool;
                                }

                            } catch (SQLException err) {
                                majInformation(ecranPrincipal.MESSAGE_ERROR,err.getMessage());
                                thSpoolActive = false;
                            } catch (NullPointerException err) {
                                thSpoolActive = false;
                            }

                            ecranPrincipal.getDisplay().asyncExec(new Runnable() {
                                public void run() {

                                 //   refreshInformation();

                                    if (!thSpoolActive || textCurrentSum.isDisposed()) { thSpoolActive = false; return ; }

                                    textCurrentSum.setText(textCurrentSumValue);
                                    textRealSum.setText(textRealSumValue);
                                    textSkewFactor.setText(textSkewFactorValue);

                                    for (int i = 0; i < nbrAMP; i++) {
                                        tabTextSpoolDet[0][i].setText(tabTextSpoolDetValue[0][i]);
                                        tabTextSpoolDet[1][i].setText(tabTextSpoolDetValue[1][i]);
                                        tabTextSpoolDet[2][i].setText(tabTextSpoolDetValue[2][i]);
                                        tabTextSpoolDetAgg[0][i].setText(tabTextSpoolDetAggValue[0][i]);
                                        tabTextSpoolDetAgg[1][i].setText(tabTextSpoolDetAggValue[1][i]);
                                        tabTextSpoolDetAgg[2][i].setText(tabTextSpoolDetAggValue[2][i]);
                                    }

                                    majDateText(textSpoolInfoCurrent);
                                    compteurIter++;
                                    textSpoolInfoIter.setText(compteurIter+"");

                                }
                            });

                            if (thSpoolActive) {
                                try {
                                    Thread.sleep(freqCalcSpool * 1000);
                                } catch (Exception e) {
                                    //nothing to do here
                                }
                            }
                        }
                    }
                }).start();

                btnStartSpool.setEnabled(false);
                btnResetSpool.setEnabled(false);
                btnStopSpool.setEnabled(true);
                textSpoolUser.setEnabled(false);
                textSpoolFreq.setEnabled(false);
                btnRadioSizeGo.setEnabled(false);
                btnRadioSizeMo.setEnabled(false);
                btnRadioSizeKo.setEnabled(false);
            }
        });


        btnStopSpool.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                activatedContents();
                thSpoolActive = false;
                majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Stop");

            }
        });

        btnResetSpool.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Reset");
                textCurrentSum.setText("");
                textRealSum.setText("");
                textSkewFactor.setText("");
                textSpoolInfoStart.setText("");
                textSpoolInfoCurrent.setText("");
                textSpoolInfoIter.setText("");

                int nbrAMP = ecranPrincipal.getNumberAmps();
                for (int i = 0; i < nbrAMP; i++) {
                    tabTextSpoolDet[0][i].setText(i+"");
                    tabTextSpoolDet[1][i].setText("");
                    tabTextSpoolDet[2][i].setText("");
                    tabTextSpoolDetAgg[0][i].setText(i+"");
                    tabTextSpoolDetAgg[1][i].setText("");
                    tabTextSpoolDetAgg[2][i].setText("");
                }

                AggTextSpoolDet = null;


            }
        });


        btnRadioSizeTo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                btnRadioSizeTo.setSelection(true);
                btnRadioSizeGo.setSelection(false);
                btnRadioSizeMo.setSelection(false);
                btnRadioSizeKo.setSelection(false);
                sizeValueSpool = "Go";
                sizeValueSumSpool = "To";
            }
        });


        btnRadioSizeGo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                btnRadioSizeTo.setSelection(false);
                btnRadioSizeGo.setSelection(true);
                btnRadioSizeMo.setSelection(false);
                btnRadioSizeKo.setSelection(false);
                sizeValueSpool = "Mo";
                sizeValueSumSpool = "Go";
            }
        });

        btnRadioSizeMo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                btnRadioSizeTo.setSelection(false);
                btnRadioSizeGo.setSelection(false);
                btnRadioSizeMo.setSelection(true);
                btnRadioSizeKo.setSelection(false);
                sizeValueSpool = "Ko";
                sizeValueSumSpool = "Mo";
            }
        });

        btnRadioSizeKo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                btnRadioSizeTo.setSelection(false);
                btnRadioSizeGo.setSelection(false);
                btnRadioSizeMo.setSelection(false);
                btnRadioSizeKo.setSelection(true);
                sizeValueSpool = "o";
                sizeValueSumSpool = "Ko";
            }
        });

        compositePrincipal.pack(true);
    }

    @Override
    public void deleteContents() {
        majInformation(ecranPrincipal.MESSAGE_INFORMATION,"Close Module");
        groupSpool.dispose();
        compositePrincipal.pack(true);
    }

    @Override
    public void activatedContents() {
        if (ScrollCompSpoolDetail == null || ScrollCompSpoolDetail.isDisposed()) {

            int nbrAMP = ecranPrincipal.getNumberAmps();
            ScrollCompSpoolDetail = new ScrolledComposite(groupSpoolDetail,SWT.H_SCROLL | SWT.V_SCROLL);
            ScrollCompSpoolDetail.setAlwaysShowScrollBars(true);
            ScrollCompSpoolDetail.setLayout(new GridLayout(3,false));
            gridData = new GridData(SWT.CENTER, SWT.TOP, false, true, 1, 1);
            ScrollCompSpoolDetail.setLayoutData(gridData);
            ScrollCompSpoolDetail.setExpandHorizontal(true);
            ScrollCompSpoolDetail.setExpandVertical(true);

            CompScSpoolDetail = new Composite(ScrollCompSpoolDetail, SWT.NONE);
            ScrollCompSpoolDetail.setContent(CompScSpoolDetail);
            CompScSpoolDetail.setLayout(new GridLayout(3,false));

            tabTextSpoolDet = new Text[3][nbrAMP];



            ScrollCompSpoolDetailAgg = new ScrolledComposite(groupSpoolDetailAgg,SWT.H_SCROLL | SWT.V_SCROLL);
            ScrollCompSpoolDetailAgg.setAlwaysShowScrollBars(true);
            ScrollCompSpoolDetailAgg.setLayout(new GridLayout(3,false));
            gridData = new GridData(SWT.CENTER, SWT.TOP, false, true, 1, 1);
            ScrollCompSpoolDetailAgg.setLayoutData(gridData);
            ScrollCompSpoolDetailAgg.setExpandHorizontal(true);
            ScrollCompSpoolDetailAgg.setExpandVertical(true);

            CompScSpoolDetailAgg = new Composite(ScrollCompSpoolDetailAgg, SWT.NONE);
            ScrollCompSpoolDetailAgg.setContent(CompScSpoolDetailAgg);
            CompScSpoolDetailAgg.setLayout(new GridLayout(3,false));

            tabTextSpoolDetAgg = new Text[3][nbrAMP];


            for (int i = 0; i < nbrAMP; i++) {
                gridData = new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1);
                gridData.widthHint = 50;

                tabTextSpoolDet[0][i] = new Text(CompScSpoolDetail, SWT.CENTER | SWT.READ_ONLY | SWT.BORDER );
                tabTextSpoolDet[0][i].setLayoutData(gridData);
                tabTextSpoolDet[0][i].setText(i+"");
                tabTextSpoolDetAgg[0][i] = new Text(CompScSpoolDetailAgg, SWT.CENTER | SWT.READ_ONLY | SWT.BORDER );
                tabTextSpoolDetAgg[0][i].setLayoutData(gridData);
                tabTextSpoolDetAgg[0][i].setText(i+"");

                gridData = new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1);
                gridData.widthHint = 100;
                tabTextSpoolDet[1][i] = new Text(CompScSpoolDetail, SWT.CENTER | SWT.READ_ONLY | SWT.BORDER );
                tabTextSpoolDet[1][i].setLayoutData(gridData);
                tabTextSpoolDetAgg[1][i] = new Text(CompScSpoolDetailAgg, SWT.CENTER | SWT.READ_ONLY | SWT.BORDER );
                tabTextSpoolDetAgg[1][i].setLayoutData(gridData);

                gridData = new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1);
                gridData.widthHint = 100;
                tabTextSpoolDet[2][i] = new Text(CompScSpoolDetail, SWT.CENTER | SWT.READ_ONLY | SWT.BORDER );
                tabTextSpoolDet[2][i].setLayoutData(gridData);
                tabTextSpoolDetAgg[2][i] = new Text(CompScSpoolDetailAgg, SWT.CENTER | SWT.READ_ONLY | SWT.BORDER );
                tabTextSpoolDetAgg[2][i].setLayoutData(gridData);
            }

            ScrollCompSpoolDetail.setMinSize(CompScSpoolDetail.computeSize(SWT.DEFAULT,SWT.DEFAULT));
            ScrollCompSpoolDetailAgg.setMinSize(CompScSpoolDetailAgg.computeSize(SWT.DEFAULT,SWT.DEFAULT));

            textSpoolUser.setText(ecranPrincipal.getTextUsername().getText());
            textSpoolFreq.setText(freqCalcSpool+"");

            compositePrincipal.pack(true);
            //shell.layout();
        }

        btnStartSpool.setEnabled(true);
        btnStopSpool.setEnabled(false);
        btnResetSpool.setEnabled(true);
        textSpoolUser.setEnabled(true);
        textSpoolFreq.setEnabled(true);
        btnRadioSizeTo.setEnabled(true);
        btnRadioSizeGo.setEnabled(true);
        btnRadioSizeMo.setEnabled(true);
        btnRadioSizeKo.setEnabled(true);


    }

    @Override
    public void desactivatedContents() {

        thSpoolActive = false;
        textSpoolUser.setText("");
        textSpoolFreq.setText("");
        textCurrentSum.setText("");
        textRealSum.setText("");
        textSkewFactor.setText("");
        textSpoolInfoStart.setText("");
        textSpoolInfoCurrent.setText("");
        textSpoolInfoIter.setText("");

        ScrollCompSpoolDetail.dispose();
        ScrollCompSpoolDetailAgg.dispose();

        btnStartSpool.setEnabled(false);
        btnStopSpool.setEnabled(false);
        btnResetSpool.setEnabled(false);
        textSpoolUser.setEnabled(false);
        textSpoolFreq.setEnabled(false);
        btnRadioSizeTo.setEnabled(false);
        btnRadioSizeGo.setEnabled(false);
        btnRadioSizeMo.setEnabled(false);
        btnRadioSizeKo.setEnabled(false);

        compositePrincipal.pack(true);


    }

    private void majDateText (Text textDate) {
        if (textDate != null) {
            Date date = new Date();
            textDate.setText(heureFormat.format(date));
        }
    }



}
