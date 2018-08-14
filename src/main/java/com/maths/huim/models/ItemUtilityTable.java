package com.maths.huim.models;

import java.util.Map;

public class ItemUtilityTable {

    private String item;
    private Map<Integer, ItemTransactionUtility> itemTransactionUtilities;

    public ItemUtilityTable(String item, Map<Integer, ItemTransactionUtility> itemTransactionUtilities) {
        this.item = item;
        this.itemTransactionUtilities = itemTransactionUtilities;
    }

    public ItemUtilityTable() { }


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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
                "item='" + item + '\'' +
                ", itemTransactionUtilities=" + itemTransactionUtilities +
                '}';
    }
}
