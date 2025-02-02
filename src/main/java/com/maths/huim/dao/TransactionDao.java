package com.maths.huim.dao;

import com.maths.huim.models.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class TransactionDao {

    public List<Transaction> fetch(String scenario, Set<String> itemSet) {

        List<Transaction> transactions = new ArrayList<Transaction>();

        BufferedReader reader;
        String filePath = "data/" + scenario + "/transactions.txt";

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            int lineCount = 1;
            while (line != null) {

                Map<String, Long> itemCountMap = new HashMap<String, Long>();
                List<String> components = Arrays.asList(line.split(":"));
                List<String> itemset = Arrays.asList(components.get(0).split(" "));
                List<String> counts = Arrays.asList(components.get(2).split(" "));

                itemSet.addAll(itemset);

                for(int j = 0; j < itemset.size(); ++j) {
                    itemCountMap.put(itemset.get(j), Long.parseLong(counts.get(j)));
                }

                transactions.add(new Transaction( lineCount++, itemCountMap, Long.parseLong(components.get(1))));

                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
