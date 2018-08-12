package com.maths.huim.models;

import java.util.HashMap;
import java.util.Map;

public class Transaction {

    private long tid;
    private Map<String, Long> itemCountMap;
    private long totalUtil;




    public Transaction(long tid, Map<String, Long> itemCountMap, long totalUtil) {
        this.tid = tid;
        this.itemCountMap = itemCountMap;
        this.totalUtil = totalUtil;
    }

    public Transaction(long tid) {
        this.tid = tid;
        this.itemCountMap = new HashMap<String, Long>();
        totalUtil = 0;
    }


    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public Map<String, Long> getItemCountMap() {
        return itemCountMap;
    }

    public void setItemCountMap(Map<String, Long> itemCountMap) {
        this.itemCountMap = itemCountMap;
    }

    public long getTotalUtil() {
        return totalUtil;
    }

    public void setTotalUtil(long totalUtil) {
        this.totalUtil = totalUtil;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "tid=" + tid +
                ", itemCountMap=" + itemCountMap +
                ", totalUtil=" + totalUtil +
                '}';
    }
}
