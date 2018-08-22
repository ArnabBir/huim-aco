package com.maths.huim.impl;

import com.maths.huim.models.Constants;
import com.maths.huim.models.GenChui;
import com.maths.huim.models.ItemUtilityTable;

import java.lang.reflect.Array;
import java.util.*;

public class GenChuiImpl {

    public boolean subSumCheck(List<String> itemSetY, List<String> prevSetY, Map<List<String>, ItemUtilityTable> itemUtilityTableMap) {

        for(String itemJ : prevSetY) {
            Set<Integer> tidJ = itemUtilityTableMap.get(Arrays.asList(itemJ)).getItemTransactionUtilities().keySet();
            Set<Integer> tidY = itemUtilityTableMap.get(itemSetY).getItemTransactionUtilities().keySet();
            if(tidJ.contains(tidY)) return true;
        }
        return false;
    }

    public List<String> computeClosure(List<String> itemSetY, List<String> postSetYC, List<String> itemSetX, List<String> postSetX, Map<List<String>, ItemUtilityTable> itemUtilityTableMap) {

        ItemUtilityTableImpl itemUtilityTableImpl = new ItemUtilityTableImpl();
        List<String> itemSetYC = new ArrayList<>(itemSetY);


        for(String itemZ : postSetX) {

            System.out.println(itemSetX);

            if(itemSetX.isEmpty()) {
                postSetYC.add(itemZ);
            }
            else {
                Set<Integer> tidX = itemUtilityTableMap.get(new ArrayList<>(itemSetX)).getItemTransactionUtilities().keySet();
                Set<Integer> tidYC = itemUtilityTableMap.get(new ArrayList<>(itemSetYC)).getItemTransactionUtilities().keySet();
//                if (tidX.contains(tidYC)) {
                    System.out.println(itemUtilityTableMap.get(itemSetYC) + "   ----->" + itemUtilityTableMap.get(Arrays.asList(itemZ)));
                    ItemUtilityTable itemUtilityTable = itemUtilityTableImpl.union(itemUtilityTableMap.get(itemSetYC), itemUtilityTableMap.get(Arrays.asList(itemZ)));
                    itemSetYC.add(itemZ);
                    System.out.println("itemSetYC = " + itemSetYC);
                    itemUtilityTableMap.put(itemSetYC, itemUtilityTable);
//                } else {
//                    postSetYC.add(itemZ);
//                }
            }
        }

        return itemSetYC;
    }

    public void execute(GenChui genChui, Map<List<String>, ItemUtilityTable> itemUtilityTableMap, List<List<String>> closedItemSets) {

        System.out.println("HELLO !!!!");

        List<String> itemSetX = new ArrayList<String>(genChui.getItemSet());
        List<String> prevSetX = new ArrayList<String>(genChui.getPrevSet());
        List<String> postSetX = new ArrayList<String>(genChui.getPostSet());

        List<String> itemSetY;
        List<String> prevSetY;
        List<String> postSetY;

        List<String> itemSetYC;
        List<String> prevSetYC;
        List<String> postSetYC;

        ItemUtilityTableImpl itemUtilityTableImpl = new ItemUtilityTableImpl();

        while(!postSetX.isEmpty()) {

            String item = postSetX.remove(0);
            itemSetY = new ArrayList<>(itemSetX);
            itemSetY.add(item);
            if(itemSetX.size() > 0) {
                ItemUtilityTable itemUtilityTableY = itemUtilityTableImpl.union(itemUtilityTableMap.get(itemSetX), itemUtilityTableMap.get(Arrays.asList(item)));
                itemUtilityTableMap.put(itemSetY, itemUtilityTableY);
            }
            prevSetY = new ArrayList<>(prevSetX);
            System.out.println("itemSetY = " + itemSetY);
            long sumItemUtilityY = itemUtilityTableImpl.sumItemUtility(itemUtilityTableMap.get(itemSetY));
            long sumResidualUtilityY = itemUtilityTableImpl.sumResidualUtility(itemUtilityTableMap.get(itemSetY));
            System.out.println("sum = " + (sumItemUtilityY + sumResidualUtilityY));
            if(sumItemUtilityY + sumResidualUtilityY >= Constants.minUtil) {
                if(!subSumCheck(itemSetY, prevSetY, itemUtilityTableMap)) {
                    prevSetYC = prevSetY;
                    postSetYC = new ArrayList<>();
                    itemSetYC = computeClosure(itemSetY, postSetYC, itemSetX, postSetX, itemUtilityTableMap);
                    long sumItemUtilityYC = itemUtilityTableImpl.sumItemUtility(itemUtilityTableMap.get(itemSetYC));
                    System.out.println(":: " + itemSetYC + " : " + sumItemUtilityYC);
                    if(sumItemUtilityYC >= Constants.minUtil) {
                        closedItemSets.add(itemSetYC);
                        System.out.println("closedItemSets = " + closedItemSets);
                        return;
                    }
                    execute(new GenChui(itemSetYC, prevSetYC, postSetYC, Constants.minUtil), itemUtilityTableMap, closedItemSets);
                }
            }
        }
    }

}
