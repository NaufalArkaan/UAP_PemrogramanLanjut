package model;

/**
 * DATA RINGKASAN DASHBOARD
 *
 * Dummy → dihitung dari List
 * MySQL → hasil query COUNT
 */
public class SummaryData {

    private int total;
    private int processing;
    private int done;

    public SummaryData(int total, int processing, int done) {
        this.total = total;
        this.processing = processing;
        this.done = done;
    }

    public int getTotal() { return total; }
    public int getProcessing() { return processing; }
    public int getDone() { return done; }
}
