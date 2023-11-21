package com.example.genetic_algorithm;

import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.util.Random;

public class SA {
    Random r = new Random();

    private final Graph graph;
    private final Group group;

    private Group lines;

    private Member solution;



    public SA(Graph graph, Group group)
    {
        this.graph = graph;
        this.group = group;

        this.lines = new Group();
        solution = new Member(graph.getSize());
    }

    double temperature = 1;
    final double minTemp = 0.00001;

    final int numIterations = 200;

    public void run()
    {
        Member bestSolution = solution;

        for(int repetitions = 0; repetitions < 20; repetitions++){
            while (temperature > minTemp) {
                for (int i = 0; i < numIterations; i++) {
                    Member neighbour = new Member(solution);
                    neighbour.mutateSwap();

                    if (neighbour.Fitness(graph.getAdjacency()) < solution.Fitness(graph.getAdjacency())) {
                        solution = neighbour;

                        if (solution.Fitness(graph.getAdjacency()) < bestSolution.Fitness(graph.getAdjacency())) {
                            bestSolution = solution;
                        }
                    } else if (changeCheck(neighbour)) {
                        solution = neighbour;
                    }
                }
                temperature *= 0.99;
                //System.out.println(solution.Fitness(graph.getAdjacency()));
            }

            temperature = 1;
        }


        solution = bestSolution;
        drawPath();
    }

    private boolean changeCheck(Member neighbour){
        final double currentFitness = solution.Fitness(graph.getAdjacency());
        final double neighbourFitness = neighbour.Fitness(graph.getAdjacency());

        final double totalLen = currentFitness + neighbourFitness;

        final double deltaNormalized = (currentFitness - neighbourFitness)/totalLen;
        return Math.random() < Math.exp(deltaNormalized/temperature);
    }

    private void drawPath(){
        group.getChildren().removeAll(lines);
        lines = new Group();

        int[] genotype = solution.getGenotype();

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

    public double getLength(){
        return solution.getLengthOfJourney();
    }

}
