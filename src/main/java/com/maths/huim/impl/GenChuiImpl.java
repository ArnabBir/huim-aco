package com.maths.huim.impl;

import com.maths.huim.models.GenChui;
import com.maths.huim.models.ItemUtilityTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenChuiImpl {

    public void execute(GenChui genChui, Map<List<String>, ItemUtilityTable> itemUtilityTableMap) {

        List<String> itemSet = genChui.getItemSet();
        List<String> prevSet = genChui.getPrevSet();
        List<String> postSet = genChui.getPostSet();
        ItemUtilityTable itemUtilityTable = new ItemUtilityTable();
        List<String> newItemSet;

        while(!postSet.isEmpty()) {

            String item = postSet.remove(0);
            newItemSet = new ArrayList<>(itemSet);
            newItemSet.add(item);
            //itemSet.add(item);

        }
    }

}
