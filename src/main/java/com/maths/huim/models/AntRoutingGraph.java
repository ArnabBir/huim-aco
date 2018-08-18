package com.maths.huim.models;

public class AntRoutingGraph {

    private AntRoutingGraphNode root;

    public AntRoutingGraph() {
        root = new AntRoutingGraphNode();
    }

    public AntRoutingGraph(AntRoutingGraphNode root) {
        this.root = root;
    }

    public AntRoutingGraphNode getRoot() {
        return root;
    }

    public void setRoot(AntRoutingGraphNode root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return "{ " + this.root.getKeyItem() + " -> {" + this.root + " } }";
    }
}