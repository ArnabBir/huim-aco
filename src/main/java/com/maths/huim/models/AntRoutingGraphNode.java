package com.maths.huim.models;

import java.util.*;

public class AntRoutingGraphNode {

    private String keyItem;
    private List<String> itemSet;
    private List<AntRoutingGraphNode> children;
    private double weight;
    private double pheromone;
    private double desirability;
    private boolean visited;

    public AntRoutingGraphNode() {

        this.keyItem = "";
        this.itemSet = new ArrayList<String>();
        this.children = new ArrayList<AntRoutingGraphNode>();
        this.visited = false;
        this.pheromone = Constants.tauBefore;
        this.desirability = 0.0;
    }

    public AntRoutingGraphNode(String keyItem){
        this.keyItem = keyItem;
        this.setItemSet(Arrays.asList(keyItem));
    }

    public void addChild(AntRoutingGraphNode antRoutingGraphNode) {
        this.children.add(antRoutingGraphNode);
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

    public List<AntRoutingGraphNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<AntRoutingGraphNode> children) {
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
        for(AntRoutingGraphNode node : this.children) {
            trieString += node.getKeyItem() + " -> {" + node.getChildren() + " } ";
        }
        return trieString;
    }
}
