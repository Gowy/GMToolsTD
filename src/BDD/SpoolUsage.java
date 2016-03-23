package BDD;

import java.text.DecimalFormat;

/**
 * This Class is used to manage Spool Usage information
 * @author GMA
 * @version 0.1
 */
public class SpoolUsage {

    private Integer vproc = 0;
    private Double currentSpool = 0.0;
    private Double maxProfileSpool = 0.0;

    private Double currentSpoolSum = 0.0;
    private Double realSpoolSum = 0.0;
    private Double skewFactor = 0.0;

    private Double divTo = (1000.0*1000*1000*1000);
    private Double divGo = (1000.0*1000*1000);
    private Double divMo = (1000.0*1000);
    private Double divKo = (1000.0);

    private DecimalFormat decimalFormat = new DecimalFormat( "###,###,###,###,###,##0.00");

    /**
     * Constructor of the Class SpoolUsage
     * @param vproc vproc from Teradata
     * @param currentSpool CurrentSpool from Teradata
     * @param maxProfileSpool MaxProfileSpool from Teradata
     */
    public SpoolUsage (Integer vproc, Double currentSpool, Double maxProfileSpool) {
        this.vproc = vproc;
        this.currentSpool = currentSpool;
        this.maxProfileSpool = maxProfileSpool;
    }

    /**
     * Constructor of the Class SpoolUsage for Summary Data
     * @param currentSpoolSum Sum of the CurrentSpool from Teradata
     * @param realSpoolSum Max(currentspool) * max(vproc)+1 from Teradata
     * @param skewFactor skewFactor of the CurrentSpool from Teradata
     */
    public SpoolUsage (Double currentSpoolSum, Double realSpoolSum, Double skewFactor) {
        this.currentSpoolSum = currentSpoolSum;
        this.realSpoolSum = realSpoolSum;
        this.skewFactor = skewFactor;
    }

    /**
     * getVproc is used to get the Vproc Value
     * @return Vproc value
     */
    public Integer getVproc () {
        return vproc;
    }

    /**
     * getCurrentSpool is used to get the CurrentSpool Value
     * @return CurrentSpool value
     */
    public Double getCurrentSpool () {
        return currentSpool;
    }

    /**
     * getMaxProfileSpool is used to get the MaxProfileSpool Value
     * @return MaxProfileSpool value
     */
    public Double getMaxProfileSpool () {
        return maxProfileSpool;
    }

    /**
     * getCurrentSpoolSum is used to get the Sum of all the AMP's CurrentSpool Value
     * @return Sum of all the AMP's CurrentSpool Value
     */
    public Double getCurrentSpoolSum () {
        return currentSpoolSum;
    }

    /**
     * getRealSpoolSum is used to get the real spool of all the AMP's CurrentSpool Value
     * @return Max(currentspool) * max(vproc)+1 Value
     */
    public Double getRealSpoolSum () {
        return realSpoolSum;
    }

    /**
     * getSkewFactor is used to get the skew factor of all the AMP's CurrentSpool Value
     * @return skew factor of all the AMP's CurrentSpool Value
     */
    public Double getSkewFactor () {
        return skewFactor;
    }

    /**
     * formatSpoolWithSize is used to format the value (To, Go, Mo, Ko)
     * @param spoolValue spool value for format
     * @param sizeValue size to apply for the format
     * @return SpoolValue in the good format
     */
    public String formatSpoolWithSize (Double spoolValue, String sizeValue) {
        String result;
        if (sizeValue.equals("To")) {
            result = decimalFormat.format(spoolValue / divTo);
        } else if (sizeValue.equals("Go")) {
            result = decimalFormat.format(spoolValue / divGo);
        } else if (sizeValue.equals("Mo")) {
            result = decimalFormat.format(spoolValue / divMo);
        } else if (sizeValue.equals("Ko")) {
            result = decimalFormat.format(spoolValue / divKo);
        } else {
            result = decimalFormat.format(spoolValue);
        }
        return result;
    }

    /**
     * returnMaxSpoolUsage is used to get the max between two SpoolUsage object
     * @param su SpoolUsage object to compare with the current instance
     * @return max of the SpoolUsage
     */
    public SpoolUsage returnMaxSpoolUsage( SpoolUsage su) {
        Double tmpCurrentSpool;
        Double tmpMaxProfileSpool;

        if (su.getCurrentSpool() > this.currentSpool) { tmpCurrentSpool = su.getCurrentSpool(); } else { tmpCurrentSpool = this.currentSpool; }
        if (su.getMaxProfileSpool() > this.maxProfileSpool) { tmpMaxProfileSpool = su.getMaxProfileSpool(); } else { tmpMaxProfileSpool = this.maxProfileSpool; }

        return new SpoolUsage(this.vproc, tmpCurrentSpool, tmpMaxProfileSpool);
    }


    /**
     * returnMaxSpoolSum is used to get the max of summary data between two SpoolUsage object
     * @param su SpoolUsage object to compare with the current instance
     * @return max summary data of the SpoolUsage
     */
    public SpoolUsage returnMaxSpoolSum( SpoolUsage su) {
        Double tmpCurrentSpoolSum;
        Double tmpRealSpoolSum;
        Double tmpSkewFactor;

        if (su.getCurrentSpoolSum() > this.currentSpoolSum) { tmpCurrentSpoolSum = su.getCurrentSpoolSum(); } else { tmpCurrentSpoolSum = this.currentSpoolSum; }
        if (su.getRealSpoolSum() > this.realSpoolSum) { tmpRealSpoolSum = su.getRealSpoolSum(); } else { tmpRealSpoolSum = this.realSpoolSum; }
        if (su.getSkewFactor() > this.skewFactor) { tmpSkewFactor = su.getSkewFactor(); } else { tmpSkewFactor = this.skewFactor; }

        return new SpoolUsage(tmpCurrentSpoolSum, tmpRealSpoolSum, tmpSkewFactor);
    }


    /**
     * main is used to test the Class SpoolUsage
     * @param args args of the test
     */
    public static void main(String[] args) {
        SpoolUsage su = new SpoolUsage(1000000000.0,1.1,2.2);
        System.out.println(su.formatSpoolWithSize(su.getCurrentSpoolSum(),"o"));
    }


}
