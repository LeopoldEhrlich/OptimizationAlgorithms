package com.example.genetic_algorithm;

import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.util.Random;

public class SimulatedAnnealing implements Runnable {
    Random r = new Random();

    private final Graph graph;
    private final Group group;

    private Group lines;

    private Member solution;

    Member[] Population;
    int insertionIndex;


    public SimulatedAnnealing(Graph graph, Group group, Member[] Population, int insertionIndex)
    {
        this.graph = graph;
        this.group = group;

        this.lines = new Group();
        solution = new Member(graph.getSize());

        this.Population = Population;
        this.insertionIndex = insertionIndex;
    }

    double temperature = 1;
    final double minTemp = 0.0001;

    final int numIterations = 100;

    public void go()
    {
        Member bestSolution = solution;

        for(int repetitions = 0; repetitions < 10; repetitions++){
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
                temperature *= 0.985;
                //System.out.println(solution.Fitness(graph.getAdjacency()));
            }

            temperature = 1;
        }


        solution = bestSolution;
    }

    private boolean changeCheck(Member neighbour){
        final double currentFitness = solution.Fitness(graph.getAdjacency());
        final double neighbourFitness = neighbour.Fitness(graph.getAdjacency());

        final double totalLen = currentFitness + neighbourFitness;

        final double deltaNormalized = (currentFitness/totalLen) - (neighbourFitness/totalLen);
        return Math.random() < Math.exp(deltaNormalized/temperature);
    }

    public void run(){
        go();

        Member returnSolution = solution;
        solution = new Member(graph.getSize());

        Population[insertionIndex] = returnSolution;
    }
}
