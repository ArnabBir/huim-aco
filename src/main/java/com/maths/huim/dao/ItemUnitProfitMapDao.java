package com.maths.huim.dao;

import com.maths.huim.api.InputDao;
import com.maths.huim.models.ItemUnitProfitMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ItemUnitProfitMapDao implements InputDao {

    public ItemUnitProfitMap fetch(String scenario) {
//
//        Map<Character, Long> map = new HashMap<Character, Long>();
//        BufferedReader reader;
//        String filePath = "data/" + scenario + "/item_unit_profit.txt";
//
//        try {
//            reader = new BufferedReader(new FileReader(filePath));
//            String line = reader.readLine();
//            List<Character> items = line.split(":")[0].split(" ");
//            List<Character> unitProfits = line.split(":")[1].split(" ");
//            for(int j = 0; j < items.size(); ++j) {
//                map.put(items.get(j), Long.parseLong(unitProfits.get(j)));
//            }
//
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return new ItemUnitProfitMap(map);
        return new ItemUnitProfitMap(new HashMap<Integer, Long>());
    }
}
