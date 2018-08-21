package com.maths.huim.models;

import java.util.List;
import java.util.Map;

public class ItemUtilityTable {

    private List<String> itemSet;
    private Map<Integer, ItemTransactionUtility> itemTransactionUtilities;

    public ItemUtilityTable(List<String> itemSet, Map<Integer, ItemTransactionUtility> itemTransactionUtilities) {
        this.itemSet = itemSet;
        this.itemTransactionUtilities = itemTransactionUtilities;
    }

    public ItemUtilityTable() { }


    public List<String> getItemSet() {
        return itemSet;
    }

    public void setItemSet(List<String> itemSet) {
        this.itemSet = itemSet;
    }

    public Map<Integer, ItemTransactionUtility> getItemTransactionUtilities() {
        return itemTransactionUtilities;
    }

    public void setItemTransactionUtilities(Map<Integer, ItemTransactionUtility> itemTransactionUtilities) {
        this.itemTransactionUtilities = itemTransactionUtilities;
    }

    @Override
    public String toString() {
        return "ItemUtilityTable{" +
                "itemSet='" + itemSet + '\'' +
                ", itemTransactionUtilities=" + itemTransactionUtilities +
                '}';
    }

}
