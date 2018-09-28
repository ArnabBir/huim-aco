package com.maths.huim.utils;

import com.maths.huim.impl.ItemUtilityTableImpl;
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

    public AntRoutingGraph bootstrapAntGraph(ItemTwuMap itemTwuMap) {

        AntRoutingGraphNode antRoutingGraphNode = new AntRoutingGraphNode();
        List<AntRoutingGraphNode> nodeList = new ArrayList<AntRoutingGraphNode>();
        List<String> keyList = new ArrayList<String>(itemTwuMap.getMap().keySet());

        for(int i = keyList.size()-1; i >= 0; --i) {
            AntRoutingGraphNode childNode = new AntRoutingGraphNode();
            childNode.setKeyItem(keyList.get(i));
            childNode.setPheromone(Constants.tauBefore);
            childNode.setDesirability(itemTwuMap.getMap().get(keyList.get(i)));
            for(AntRoutingGraphNode node : antRoutingGraphNode.getChildren()) {
                AntRoutingGraphNode tempNode = node;
                childNode.addChild(tempNode);
            }
            antRoutingGraphNode.addChild(childNode);
        }
        return new AntRoutingGraph(antRoutingGraphNode);
    }

    public void localUpdatePheromone(AntRoutingGraphNode antRoutingGraphNode) {

        double pheromone = antRoutingGraphNode.getPheromone();
        pheromone = pheromone * (1 - Constants.rho) + Constants.rho * Constants.tau0;
        antRoutingGraphNode.setPheromone(pheromone);
    }

    public void globalUpdatePheromone(AntRoutingGraph antRoutingGraph, PathUtil maxPathUtil) {

        AntRoutingGraphNode antRoutingGraphNode = antRoutingGraph.getRoot();
        double delta = (double) maxPathUtil.getUtil() / Constants.minUtil;
        for(Integer item : maxPathUtil.getPath()) {
            antRoutingGraphNode = antRoutingGraphNode.getChildren().get(item.intValue());
            double pheromone = antRoutingGraphNode.getPheromone();
            pheromone += delta;
            antRoutingGraphNode.setPheromone(pheromone);
        }
    }

    public long getRemainingUnvisitedPathLength(AntRoutingGraphNode antRoutingGraphNode) {

        long countNodes = 0;
        for(AntRoutingGraphNode node : antRoutingGraphNode.getChildren()) {

            if(node == null) return countNodes;
            if(node.isVisited()) {
                continue;
            }
            node.setVisited(true);
            //++countNodes;
            countNodes += 1 + getRemainingUnvisitedPathLength(node);
        }
        return countNodes;
    }

    // Check the closure property and remove the unnecessary instances
    public void removeItemSetCout(Map<List<String>, ItemUtilityTable> itemUtilityTableMap, List<String> itemSet, String nextItem, Map<List<String>, Long> itemSetCountMap, ItemUtilityTableImpl itemUtilityTableImpl) {

        if(itemUtilityTableImpl.isClosureCheck(itemUtilityTableMap.get(itemSet), itemUtilityTableMap.get(Arrays.asList(nextItem)))) {

            itemSetCountMap.remove(new ArrayList<>(itemSet));
        }
    }

    public void incrementItemSetCountMap(Map<List<String>, Long> itemSetCountMap, List<String> itemSet) {

        long itemSetCount = 0;
        if(itemSetCountMap.containsKey(new ArrayList<>(itemSet))) {
            itemSetCount = itemSetCountMap.get(new ArrayList<String>(itemSet)).longValue();
        }
        itemSetCountMap.put(new ArrayList<>(itemSet), itemSetCount + 1);
    }

    public long antTraverse(AntRoutingGraphNode antRoutingGraphNode, Map<List<String>, ItemUtilityTable> itemUtilityTableMap,
                            Map<List<String>, Long> itemSetCountMap, PathUtil maxPathUtil, long countNodes) {

        List<String> itemSet = new ArrayList<String>();
        List<Integer> indexList = new ArrayList<Integer>();
        ItemUtilityTableImpl itemUtilityTableImpl = new ItemUtilityTableImpl();

        while (antRoutingGraphNode != null && (antRoutingGraphNode.getChildren().size() > 0)) {

            int nextNodeIndex = selectNextNode(antRoutingGraphNode);
            antRoutingGraphNode = antRoutingGraphNode.getChildren().get(nextNodeIndex);
            //System.out.println(nextNodeIndex + " -> " + antRoutingGraphNode.getKeyItem());

            if(antRoutingGraphNode != null) {

                if(!antRoutingGraphNode.isVisited()) {
                    antRoutingGraphNode.setVisited(true);
                    ++countNodes;
                }

                if(itemSet.size() == 0) {
                    indexList.add(nextNodeIndex);
                    itemSet.add(antRoutingGraphNode.getKeyItem());
                }
                else {

                    ItemUtilityTable itemUtilityTable = itemUtilityTableImpl.computeClosure(itemUtilityTableMap.get(itemSet), itemUtilityTableMap.get(Arrays.asList(antRoutingGraphNode.getKeyItem())));
                    removeItemSetCout(itemUtilityTableMap, itemSet, antRoutingGraphNode.getKeyItem(), itemSetCountMap, itemUtilityTableImpl);
                    itemSet.add(antRoutingGraphNode.getKeyItem());
                    indexList.add(nextNodeIndex);
                    itemUtilityTableMap.put(itemSet, itemUtilityTable);
                    localUpdatePheromone(antRoutingGraphNode);

                    long sumItemUtility = itemUtilityTableImpl.sumItemUtility(itemUtilityTable);
                    long sumResidualUtility = itemUtilityTableImpl.sumResidualUtility(itemUtilityTable);

                    if(sumItemUtility > Constants.minUtil) {

                        if(sumItemUtility > maxPathUtil.getUtil()) {
                            maxPathUtil.setUtil(sumItemUtility);
                            maxPathUtil.setPath(indexList);
                        }
                        incrementItemSetCountMap(itemSetCountMap, itemSet);
                    }
                    else if(sumItemUtility + sumResidualUtility < Constants.minUtil) {
                        //countNodes += getRemainingUnvisitedPathLength(antRoutingGraphNode);
                        return countNodes;
                    }
                }
            }
        }

        return countNodes;
    }

    public int selectNextNode(AntRoutingGraphNode antRoutingGraphNode) {

        int keyNode = 0;
        double q = Math.random();
        double sumPropensity = 0;
        List<ItemPropensity> itemPropensityList = new ArrayList<ItemPropensity>();
        ItemPropensity maxItemPropensity = new ItemPropensity(0, 0);

        List<AntRoutingGraphNode> childNodeList = antRoutingGraphNode.getChildren();

        for(int i = 0; i < childNodeList.size(); ++i) {
            double propensity = Math.pow(childNodeList.get(i).getPheromone(), Constants.alpha) * Math.pow(childNodeList.get(i).getDesirability(), Constants.beta);
            sumPropensity += propensity;
            itemPropensityList.add(new ItemPropensity(i, propensity));
            if(propensity > maxItemPropensity.getPropensity()) {
                maxItemPropensity = new ItemPropensity(i, propensity);
            }
        }

        if(q < Constants.q0) {
            keyNode = maxItemPropensity.getIndex();
        }
        else {
            double randFraction = Math.random();
            double randomPropensity = randFraction * sumPropensity;
            double initPropensity = 0.0;

            for(ItemPropensity itemPropensity : itemPropensityList) {
                initPropensity += itemPropensity.getPropensity();
                if(initPropensity >= randomPropensity) {
                    keyNode = itemPropensity.getIndex();
                    break;
                }
            }
        }

        return keyNode;
    }

    public void insert(AntRoutingGraph antRoutingGraph, List<String> itemSet) {

        List<AntRoutingGraphNode> children = antRoutingGraph.getRoot().getChildren();

        for(int i = 0; i < itemSet.size(); ++i) {

            String item = itemSet.get(i);
            AntRoutingGraphNode t;

            if(children.get(i).equals(item)){
                t = children.get(i);
            }else{
                t = new AntRoutingGraphNode(item);
                children.add(t);
            }

            children = t.getChildren();

            //set leaf node
            if(i==itemSet.size()-1) {
                t.setVisited(true);
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

        List<AntRoutingGraphNode> children = antRoutingGraph.getRoot().getChildren();
        AntRoutingGraphNode t = null;
        for(int i = 0; i < itemSet.size(); ++i) {
            String item = itemSet.get(i);
            if(children.get(i).equals(item)) {
                t = children.get(i);
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
        if(t != null && t.isVisited()) return true;
        else    return false;
    }

    public void delete(AntRoutingGraph antRoutingGraph, List<String> itemSets) {
        delete( antRoutingGraph.getRoot(), itemSets, 0);
    }

    private boolean delete(AntRoutingGraphNode current, List<String> word, int index) {

        if (index == word.size()) {
            if (!current.isVisited()) {
                return false;
            }
            current.setVisited(false);
            return current.getChildren().isEmpty();
        }

        String item = word.get(index);
        AntRoutingGraphNode node = current.getChildren().get(0);
        if (node == null)   return false;

        boolean shouldDeleteCurrentNode = delete( node, word, index + 1) && !node.isVisited();

        if (shouldDeleteCurrentNode) {
            current.getChildren().remove(item);
            return current.getChildren().isEmpty();
        }

        return false;
    }
}
