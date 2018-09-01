package com.maths.huim.utils;

import com.maths.huim.models.*;

import java.util.*;

public class AntRoutingGraphUtils {

    public AntRoutingGraphUtils(){}

    public AntRoutingGraph init(ItemTwuMap itemTwuMap) {    // TODO MAKE COMPLETE GRAPH

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
            ItemTwuMap childTwuMap = itemTwuMap.getPairRemoved(pair.getKey());
            antRoutingGraphNode.addChild(childNode);
            initGraph(childNode, childTwuMap);
        }
    }

    public void localUpdatePheromone(AntRoutingGraphNode antRoutingGraphNode) {

        double pheromone = antRoutingGraphNode.getPheromone();
        pheromone = pheromone * (1 - Constants.rho) + Constants.rho * Constants.tau0;
        antRoutingGraphNode.setPheromone(pheromone);
    }

    public void antTraverse(AntRoutingGraphNode antRoutingGraphNode, Map<List<String>, Long> itemSetCountMap) {

        List<String> itemSet = new ArrayList<String>();
        long itemSetCount = 0;
        while (antRoutingGraphNode != null && !antRoutingGraphNode.getItemSet().equals("")) {
            String nextItem = selectNextNode(antRoutingGraphNode);
            //System.out.print(nextItem + " -> ");
            antRoutingGraphNode = antRoutingGraphNode.getChildren().get(nextItem);
            if(antRoutingGraphNode != null) {
                itemSet.add(nextItem);
                localUpdatePheromone(antRoutingGraphNode);
            }
        }
        if(itemSetCountMap.containsKey(itemSet)) {
            itemSetCount = itemSetCountMap.get(itemSet).longValue();
        }
        itemSetCountMap.put(itemSet, itemSetCount + 1);
    }

    public String selectNextNode(AntRoutingGraphNode antRoutingGraphNode) {

        String keyItem = "";
        double q = Math.random();
        double sumPropensity = 0;
        List<ItemPropensity> itemPropensityList = new ArrayList<ItemPropensity>();
        ItemPropensity maxItemPropensity = new ItemPropensity("", 0);

        for(Map.Entry<String, AntRoutingGraphNode> pair : antRoutingGraphNode.getChildren().entrySet()) {
            double propensity = Math.pow(pair.getValue().getPheromone(), Constants.alpha) * Math.pow(pair.getValue().getDesirability(), Constants.beta);
            sumPropensity += propensity;
            itemPropensityList.add(new ItemPropensity(pair.getKey(), propensity));
            if(propensity > maxItemPropensity.getPropensity()) {
                maxItemPropensity = new ItemPropensity(pair.getKey(), propensity);
            }
        }

        if(q < Constants.q0) {
            keyItem = maxItemPropensity.getItem();
        }
        else {
            double randFraction = Math.random();
            double randomPropensity = randFraction * sumPropensity;
            double initPropensity = 0.0;

            for(ItemPropensity itemPropensity : itemPropensityList) {
                initPropensity += itemPropensity.getPropensity();
                if(initPropensity >= randomPropensity) {
                    keyItem = itemPropensity.getItem();
                    break;
                }
            }
        }

        return keyItem;
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
