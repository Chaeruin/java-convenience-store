package store.domain;

import java.util.Date;

public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final Date startDate;
    private final Date endDate;

    public Promotion(String name, int buy, int get, Date startDate, Date endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return this.name;
    }

    public int getBuy() {
        return this.buy;
    }

    public int getGet() {
        return this.get;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

}
