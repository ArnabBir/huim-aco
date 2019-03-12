package com.maths.huim;

import com.maths.huim.dao.ItemUnitProfitMapDao;
import com.maths.huim.models.Constants;
import com.maths.huim.models.ItemUnitProfitMap;
import com.maths.huim.utils.MAUTableUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;
import java.util.Scanner;

public class runMAU {

    public static MAUTableUtils mauTableUtils = new MAUTableUtils();

    public static void main( String [] args) {

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
//        System.out.print("Enter the minUtil value : ");
//        long minUtil = sc.nextLong();
//        Constants.setMinUtil(minUtil);
//        System.out.print("Enter the delta value : ");
//        double delta = sc.nextDouble();
//        Constants.setDelta(delta);
//        System.out.print("Enter the ant count value : ");
//        long antCount = sc.nextLong();
//        Constants.setAntCount(antCount);
//        System.out.println("minUtil = " + Constants.minUtil + " delta = " + Constants.delta);

        ItemUnitProfitMap itemUnitProfitMap = (new ItemUnitProfitMapDao()).fetch(directories[index-1]);
        System.out.println(itemUnitProfitMap);
        Map<Integer, Double> tableMAU = mauTableUtils.calculateMAUTable(itemUnitProfitMap.getMap());
        System.out.println(tableMAU);

    }
}
