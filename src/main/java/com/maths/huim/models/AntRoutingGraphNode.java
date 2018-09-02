package com.maths.huim.models;

import java.util.*;

public class AntRoutingGraphNode {

    private String keyItem;
    private List<String> itemSet;
    private Map<String, AntRoutingGraphNode> children;
    private double weight;
    private double pheromone;
    private double desirability;
    private boolean visited;

    public AntRoutingGraphNode() {

        this.keyItem = "";
        this.itemSet = new ArrayList<String>();
        this.children = new LinkedHashMap<String, AntRoutingGraphNode>();
        this.visited = false;
        this.weight = 0;
        this.pheromone = Constants.tauBefore;
        this.desirability = 0.0;
    }

    public AntRoutingGraphNode(String keyItem){
        this.keyItem = keyItem;
        this.setItemSet(Arrays.asList(keyItem));
    }

    public void addChild(AntRoutingGraphNode antRoutingGraphNode) {
        this.children.put(antRoutingGraphNode.getKeyItem(), antRoutingGraphNode);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getKeyItem() {
        return this.keyItem;
    }

    public void setKeyItem(String keyItem) {
        this.keyItem = keyItem;
    }

    public List<String> getItemSet() {
        return itemSet;
    }

    public void setItemSet(List<String> itemSet) {
        this.itemSet = itemSet;
    }

    public Map<String, AntRoutingGraphNode> getChildren() {
        return this.children;
    }

    public void setChildren(Map<String, AntRoutingGraphNode> children) {
        this.children = children;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public double getPheromone() {
        return pheromone;
    }

    public void setPheromone(double pheromone) {
        this.pheromone = pheromone;
    }

    public double getDesirability() {
        return desirability;
    }

    public void setDesirability(double desirability) {
        this.desirability = desirability;
    }

    @Override
    public String toString() {

        String trieString = "";
        for(Map.Entry<String, AntRoutingGraphNode> node : this.children.entrySet()) {
            trieString += node.getKey() + " -> {" + node.getValue() + " } ";
        }
        return trieString;
    }
}
