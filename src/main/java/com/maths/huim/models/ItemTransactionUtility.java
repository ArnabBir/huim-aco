package com.maths.huim.models;

public class ItemTransactionUtility {

    private int tid;
    private long itemUtility;
    private long residualUtility;

    public ItemTransactionUtility(int tid, long itemUtility, long residualUtility) {
        this.tid = tid;
        this.itemUtility = itemUtility;
        this.residualUtility = residualUtility;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public long getItemUtility() {
        return itemUtility;
    }

    public void setItemUtility(long itemUtility) {
        this.itemUtility = itemUtility;
    }

    public long getResidualUtility() {
        return residualUtility;
    }

    public void setResidualUtility(long residualUtility) {
        this.residualUtility = residualUtility;
    }

    @Override
    public String toString() {
        return "ItemTransactionUtility{" +
                "tid=" + tid +
                ", itemUtility=" + itemUtility +
                ", residualUtility=" + residualUtility +
                '}';
    }
}
