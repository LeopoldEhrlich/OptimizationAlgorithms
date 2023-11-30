

package com.example.genetic_algorithm;

import javafx.scene.Group;

public class SimulatedAnnealing extends Algorithm {
    private TravelingSalesmanSolution solution;



    public SimulatedAnnealing(Graph graph, Group group)
    {
        super(graph,group, "Simulated Annealing");
        solution = new TravelingSalesmanSolution(graph.getSize());
    }

    public SimulatedAnnealing(int graphSize)
    {
        super(graphSize, "Simulated Annealing");
        solution = new TravelingSalesmanSolution(graphSize);
    }

    double temperature = 1;
    final double minTemp = 0.000001;

    final int numIterations = 200;

    public void run()
    {
        TravelingSalesmanSolution bestSolution = solution;

        for(int repetitions = 0; repetitions < 20; repetitions++){
            while (temperature > minTemp) {
                for (int i = 0; i < numIterations; i++) {
                    TravelingSalesmanSolution neighbour = new TravelingSalesmanSolution(solution);
                    neighbour.mutateSwap();

                    if (neighbour.distanceTravelled(graph.getWeights()) < solution.distanceTravelled(graph.getWeights())) {
                        solution = neighbour;

                        if (solution.distanceTravelled(graph.getWeights()) < bestSolution.distanceTravelled(graph.getWeights())) {
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

    private boolean changeCheck(TravelingSalesmanSolution neighbour){
        final double currentFitness = solution.distanceTravelled(graph.getWeights());
        final double neighbourFitness = neighbour.distanceTravelled(graph.getWeights());

        final double totalLen = currentFitness + neighbourFitness;

        final double deltaNormalized = (currentFitness - neighbourFitness)/totalLen;
        return Math.random() < Math.exp(deltaNormalized/temperature);
    }

    private void drawPath(){
        super.drawPath(solution);
    }

    public double solutionLength(){
        return solution.getLengthOfJourney();
    }

}
