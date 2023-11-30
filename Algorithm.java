package com.example.genetic_algorithm;

import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.util.Random;

public abstract class Algorithm {
    Random r = new Random();

    protected Graph graph;
    protected Group group;

    protected Group lines;

    abstract void run();

    private String AlgorithmName;

    public Algorithm(Graph graph, Group group, String algorithmName)
    {
        this.graph = graph;
        this.group = group;

        this.lines = new Group();

        this.AlgorithmName = algorithmName;
    }

    public Algorithm(int size, String algorithmName)
    {
        this.graph = new Graph(size);
        this.group = new Group();

        this.AlgorithmName = algorithmName;
    }

    protected void drawPath(TravelingSalesmanSolution s){
        group.getChildren().removeAll(lines);
        lines = new Group();

        int[] genotype = s.getGenotype();

        for(int i = 0; i < genotype.length-1; i++){
            Line line = new Line();

            line.setStartX(graph.getPositions()[genotype[i]][0]);
            line.setStartY(graph.getPositions()[genotype[i]][1]);

            line.setEndX(graph.getPositions()[genotype[i+1]][0]);
            line.setEndY(graph.getPositions()[genotype[i+1]][1]);

            lines.getChildren().add(line);
        }

        group.getChildren().add(lines);
    }

    protected abstract double solutionLength();

    public Graph getGraph() {
        return graph;
    }

    public Group getGroup() {
        return group;
    }

    public String getAlgorithmName(){
        return AlgorithmName;
    }
}
