package com.maths.huim.utils;

import com.maths.huim.models.AntRoutingGraph;
import com.maths.huim.models.AntRoutingGraphNode;
import com.maths.huim.models.Constants;
import com.maths.huim.models.ItemTwuMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AntRoutingGraphUtils {

    public AntRoutingGraphUtils(){}

    public AntRoutingGraph init(ItemTwuMap itemTwuMap) {    // TODO MAKE COMPLETE GRAPH

//        Map<String, AntRoutingGraphNode> map = new HashMap<String, AntRoutingGraphNode>();
//        AntRoutingGraphNode antRoutingGraphNode;
//
//        for(Map.Entry<String, Long> pair: itemTwuMap.getMap().entrySet()) {
//            antRoutingGraphNode = new AntRoutingGraphNode();
//            antRoutingGraphNode.setKeyItem(pair.getKey());
//            antRoutingGraphNode.setPheromone(Constants.tauBefore);
//            antRoutingGraphNode.setDesirability(pair.getValue());
//            map.put(pair.getKey(), antRoutingGraphNode);
//        }
//
//        AntRoutingGraphNode rootNode = new AntRoutingGraphNode();
//        rootNode.setChildren(map);
//        return new AntRoutingGraph(rootNode);
        AntRoutingGraphNode antRoutingGraphNode = new AntRoutingGraphNode();
        initGraph(antRoutingGraphNode, itemTwuMap);
        return new AntRoutingGraph(antRoutingGraphNode);
    }

    public void initGraph(AntRoutingGraphNode antRoutingGraphNode, ItemTwuMap itemTwuMap) {


        AntRoutingGraphNode childNode;
        for(Map.Entry<String, Long> pair: itemTwuMap.getMap().entrySet()) {

            childNode = new AntRoutingGraphNode();
            childNode.setKeyItem(pair.getKey());
            childNode.setPheromone(Constants.tauBefore);
            childNode.setDesirability(pair.getValue());

            //System.out.println(pair.getKey());
            //System.out.println(itemTwuMap);
            ItemTwuMap childTwuMap = itemTwuMap.getPairRemoved(pair.getKey());
            System.out.println(childNode.getKeyItem());
            antRoutingGraphNode.addChild(childNode);
            initGraph(childNode, childTwuMap);
        }
    }

    public void insert(AntRoutingGraph antRoutingGraph, List<String> itemSet) {

        Map<String, AntRoutingGraphNode> children = antRoutingGraph.getRoot().getChildren();

        for(int i = 0; i < itemSet.size(); ++i) {

            String item = itemSet.get(i);
            AntRoutingGraphNode t;

            if(children.containsKey(item)){
                t = children.get(item);
            }else{
                t = new AntRoutingGraphNode(item);
                children.put(item, t);
            }

            children = t.getChildren();

            //set leaf node
            if(i==itemSet.size()-1) {
                t.setLeaf(true);
                t.setItemSet(itemSet);
            }
        }
    }

    // Returns if there is any word in the antRoutingGraph
    // that starts with the given prefix.
    public boolean startsWith(AntRoutingGraph antRoutingGraph, List<String> itemSets) {

        if(searchNode(antRoutingGraph, itemSets) == null)
            return false;
        else
            return true;
    }

    public AntRoutingGraphNode searchNode(AntRoutingGraph antRoutingGraph, List<String> itemSet) {

        Map<String, AntRoutingGraphNode> children = antRoutingGraph.getRoot().getChildren();
        AntRoutingGraphNode t = null;
        for(int i = 0; i < itemSet.size(); ++i) {
            String item = itemSet.get(i);
            if(children.containsKey(item)) {
                t = children.get(item);
                children = t.getChildren();
            }else{
                return null;
            }
        }
        return t;
    }

    // Returns if the word is in the antRoutingGraph.
    public boolean search(AntRoutingGraph antRoutingGraph, List<String> itemSets) {

        AntRoutingGraphNode t = searchNode(antRoutingGraph, itemSets);
        if(t != null && t.isLeaf()) return true;
        else    return false;
    }

    public void delete(AntRoutingGraph antRoutingGraph, List<String> itemSets) {
        delete( antRoutingGraph.getRoot(), itemSets, 0);
    }

    private boolean delete(AntRoutingGraphNode current, List<String> word, int index) {

        if (index == word.size()) {
            if (!current.isLeaf()) {
                return false;
            }
            current.setLeaf(false);
            return current.getChildren().isEmpty();
        }

        String item = word.get(index);
        AntRoutingGraphNode node = current.getChildren().get(item);
        if (node == null)   return false;

        boolean shouldDeleteCurrentNode = delete( node, word, index + 1) && !node.isLeaf();

        if (shouldDeleteCurrentNode) {
            current.getChildren().remove(item);
            return current.getChildren().isEmpty();
        }

        return false;
    }
}
