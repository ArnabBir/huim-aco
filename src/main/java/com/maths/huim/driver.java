package com.maths.huim;

import com.maths.huim.dao.TransactionDao;
import com.maths.huim.impl.ItemTwuMapImpl;
import com.maths.huim.impl.ItemUtilityTableImpl;
import com.maths.huim.models.*;
import com.maths.huim.utils.AntRoutingGraphUtils;
import com.maths.huim.utils.ItemTwuMapUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

public class driver {

    public static void main(String [] args) {

        File file = new File("data");
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        for (int i = 0; i < directories.length; ++i) {
            System.out.println(i + 1 + ". " + directories[i]);
        }

        System.out.print("\nSelect the dataset : ");
        Scanner sc = new Scanner(System.in);
        int index = sc.nextInt();
        // Fetching transactions
        Set<Integer> itemSet = new HashSet<Integer>();
        List<Transaction> transactions = (new TransactionDao()).fetch(directories[index - 1], itemSet);

        // Calculating item twu map
        ItemTwuMapImpl itemTwuMapImpl = new ItemTwuMapImpl();
        ItemTwuMap itemTwuMap = itemTwuMapImpl.calculate(transactions, itemSet);
        itemTwuMapImpl = null;

        System.out.println("Hello Ji 1!");
        //Sorting Itemset
        ItemTwuMapUtils itemTwuMapUtils = new ItemTwuMapUtils();
        itemTwuMapUtils.sortDesc(itemTwuMap);

        //Pruning Itemset
        itemTwuMapUtils.prune(transactions, itemTwuMap, itemSet);
        itemSet = null;
        itemTwuMapUtils = null;
        System.out.println("Hello Ji 2!");
        // Calculating Item Utility Mapping
        ItemUtilityTableImpl itemUtilityTableImpl = new ItemUtilityTableImpl();
        Map<List<Integer>, ItemUtilityTable> itemUtilityTableMap = itemUtilityTableImpl.init(transactions, itemTwuMap);
        transactions = null;
        itemUtilityTableImpl = null;
        System.out.println("Hello Ji 3!");

        AntRoutingGraphUtils antRoutingGraphUtils = new AntRoutingGraphUtils();
        //AntRoutingGraph antRoutingGraph = antRoutingGraphUtils.init(itemTwuMap);
        AntRoutingGraph antRoutingGraph = antRoutingGraphUtils.bootstrapAntGraph(itemTwuMap);
        //System.out.println(antRoutingGraph);
        System.out.println("Hello Ji 4!");

        Map<List<Integer>, Long> itemSetCountMap = new HashMap<List<Integer>, Long>();
        long countNodes = 0;

        long keyCount = itemTwuMap.getMap().keySet().size();
        itemTwuMap = null;

        System.out.println("Total node count : " + keyCount);
        int x = 0;
        for (int g = 0; g < Constants.maxG /*&& countNodes < keyCount*/; ++g) {
            PathUtil maxPathUtil = new PathUtil();
            for (int i = 0; i < Constants.antCount /*&& countNodes < keyCount*/; ++i) {
                countNodes += antRoutingGraphUtils.antTraverse(antRoutingGraph.getRoot(), itemUtilityTableMap, itemSetCountMap, maxPathUtil, 0);
                ++x;
            }
            //System.out.println(maxPathUtil);
            if (maxPathUtil.getUtil() > 0) {     // GLOBAL UPDATE IS NOT CONVERGING
                antRoutingGraphUtils.globalUpdatePheromone(antRoutingGraph, maxPathUtil);
            }
            System.out.println("Nodes covered : " + countNodes);
            System.out.println("Iterations : " + x);
            System.out.println(itemSetCountMap);
        }

        System.out.println(keyCount + " -> " + countNodes);
        System.out.println(itemSetCountMap);
    }
}
