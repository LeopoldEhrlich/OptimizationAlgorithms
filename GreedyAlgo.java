package com.example.genetic_algorithm;

import javafx.scene.Group;

import java.util.HashSet;

public class GreedyAlgorithm extends Algorithm{
    private TravelingSalesmanSolution[] startingPoints;
    private TravelingSalesmanSolution path;

    GreedyAlgorithm(Graph graph, Group group) {
        super(graph, group, "Greedy Algorithm");

        startingPoints = new TravelingSalesmanSolution[graph.getSize()];
    }

    GreedyAlgorithm(int graphSize) {
        super(graphSize, "Greedy Algorithm");

        startingPoints = new TravelingSalesmanSolution[graphSize];
    }

    public void run(){
        drawPath(generate());
    }

    public double getLength(){
        return path.getLengthOfJourney();
    }

    private TravelingSalesmanSolution generate(){
        TravelingSalesmanSolution fastest = genFromGreed(0);

        for(int i = 1; i < startingPoints.length; i++){
            startingPoints[i] = genFromGreed(i);

            if(startingPoints[i].distanceTravelled(graph.getWeights()) < fastest.distanceTravelled(graph.getWeights())){
                fastest = startingPoints[i];
            }
        }

        path = fastest;
        return fastest;
    }

    private TravelingSalesmanSolution genFromGreed(int startingPoint){
        int[] genotype = new int[startingPoints.length];

        for(int i = 0; i < genotype.length; i++){
            genotype[i] = -1;
        }

        var usedVertices = new HashSet<Integer>();
        genotype[0] = startingPoint;
        usedVertices.add(startingPoint);

        for(int i = 0; i < startingPoints.length-1; i++){
            int closest = -1;

            for(int x = 0; x < graph.getSize(); x++){

                if(genotype[i] != x && !usedVertices.contains(x) && (closest == -1 || graph.getWeights()[genotype[i]][x] < graph.getWeights()[genotype[i]][closest])){
                    closest = x;
                }
            }

            genotype[i+1] = closest;
            usedVertices.add(closest);
        }

        return new TravelingSalesmanSolution(genotype);
    }

    public double solutionLength(){
        return path.getLengthOfJourney();
    }
}

