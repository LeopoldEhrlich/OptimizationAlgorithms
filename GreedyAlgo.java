package com.example.genetic_algorithm;

import javafx.scene.Group;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class GreedyAlgo extends Algorithm{
    private Member[] startingPoints;
    private Member path;

    GreedyAlgo(Graph graph, Group group) {
        super(graph, group);

        startingPoints = new Member[graph.getSize()];
    }

    public void run(){
        drawPath(generate());
    }

    public double getLength(){
        return path.getLengthOfJourney();
    }

    private Member generate(){
        Member fastest = genFromGreed(0);

        for(int i = 1; i < startingPoints.length; i++){
            startingPoints[i] = genFromGreed(i);

            if(startingPoints[i].Fitness(graph.getAdjacency()) < fastest.Fitness(graph.getAdjacency())){
                fastest = startingPoints[i];
            }
        }

        path = fastest;
        return fastest;
    }

    private Member genFromGreed(int startingPoint){
        int[] genotype = new int[startingPoints.length];

        for(int i = 0; i < genotype.length; i++){
            genotype[i] = -1;
        }

        genotype[0] = startingPoint;

        for(int i = 1; i < startingPoints.length; i++){
            int closest = -1;

            for(int x = 0; x < graph.getSize(); x++){
                boolean contains = false;

                int j = 0;
                while(genotype[j] != -1 && j++ < genotype.length){
                    if(genotype[j] == x){
                        contains = true;
                    }
                }

                if(closest == -1 || !contains && graph.getAdjacency()[genotype[i-1]][x] < graph.getAdjacency()[genotype[i-1]][closest]){
                    closest = x;
                }
            }

            genotype[i] = closest;
        }

        return new Member(genotype);
    }
}
