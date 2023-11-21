package com.example.genetic_algorithm;

import javafx.scene.Group;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class GeneticAlgorithm extends Algorithm
{

    //Contains the overall population of solutions
    private Member[] Population = new Member[4000];

    //Contains the subset of the population that will be preserved to make the next generation
    private Member[] MatingPool = new Member[Population.length/2];

    //The average number of solutions per mutated solution
    private int mutationRate = 25;

    public GeneticAlgorithm(Graph graph, Group group) {
        super(graph, group);
    }


    //generate members with some preprocessing
    public void GeneratePreprocess()
    {
        //SimulatedAnnealing sa = new SimulatedAnnealing(graph,group);
        ExecutorService taskList =
                Executors.newFixedThreadPool(8);

        for (int i = 0; i < Population.length; i++)
        {
            taskList.execute(new SimulatedAnnealing(graph,group, Population, i));
        }
        taskList.shutdown();

        try {
            taskList.awaitTermination(5, TimeUnit.MINUTES);
        }
        catch(InterruptedException interruptedException){
            System.out.println("Interrupted");
            interruptedException.printStackTrace();
        }

        System.out.println("Done generating");
        Run();
    }

    //Creates a new population of solutions, initialized randomly
    public void Generate()
    {
        for (int i = 0; i < Population.length; i++)
        {
            Population[i] = new Member(graph.getSize());
        }

        Run();
    }

    //evaluation and reproduction cycle
    boolean go;
    public void Run()
    {
        go = true;
        int count = 0;

        //double[] bests = new double[500];

        do{
            selectTournament();
            nextPopulation();
            //bests[count] = getBestPath();

            count++;
        }while (count < 2000);

        //System.out.println("Found in " + count + " iterations");

        drawBestPath();

    }

    //Returns the solution with the highest fitness
    private Member getBestPath(){
        Member bestPath = MatingPool[0];

        for (Member m : MatingPool)
        {
            if(bestPath.getLengthOfJourney() > m.getLengthOfJourney()){
                bestPath = m;
            }
        }

        return bestPath;
    }

    private void drawBestPath(){
        drawPath(getBestPath());
    }

    //Returns the distance of the path of the best solution
    public double getBestPathLength() {
        return (getBestPath().getLengthOfJourney());
    }

    //selects using tournament selection
    private void selectTournament()
    {
        double[] absoluteFitness = new double[Population.length];

        //populates distance and adds up total of fitness score
        for (int i = 0; i < Population.length; i++)
        {
            absoluteFitness[i] = Population[i].Fitness(graph.getAdjacency());
        }

        //populates mating pool based on tournament selection
        for (int i = 0; i < MatingPool.length; i++)
        {
            int currentWinner = r.nextInt(Population.length);
            for(int k = 1; k < 20; k++){
                int currentContender = r.nextInt(Population.length);
                if(absoluteFitness[currentWinner] > absoluteFitness[currentContender]){
                    currentWinner = currentContender;
                }
            }

            MatingPool[i] = Population[currentWinner];
        }
    }

    //selects the next generation with stochastic universal sampling
    private void selectSUS()
    {
        double[] absoluteFitness = new double[Population.length];


        //populates distance and adds up total of fitness score
        for (int i = 0; i < Population.length; i++)
        {
            absoluteFitness[i] = Population[i].Fitness(graph.getAdjacency());
        }


        //populates mating pool based on tournament selection
        for (int i = 0; i < MatingPool.length; i++)
        {
            int currentWinner = r.nextInt(Population.length);
            for(int k = 1; k < 20; k++){
                int currentContender = r.nextInt(Population.length);
                if(absoluteFitness[currentWinner] > absoluteFitness[currentContender]){
                    currentWinner = currentContender;
                }
            }

            MatingPool[i] = Population[currentWinner];
        }
    }

    //Iterates the genetic algorithm - Multithreaded
    private void nextPopulation()
    {
        ExecutorService taskList = Executors.newFixedThreadPool(8);

        for (int i = 0; i < Population.length; i++) {

        }

        //Applies mutation to a random sampling of members
        int i = mutationRate+r.nextInt(-mutationRate,mutationRate);
        while (i < Population.length)
        {
            Population[i].mutateSwap();
            i += mutationRate+r.nextInt(-mutationRate/2,mutationRate/2);
        }

    }

    //Crosses over two
    private void crossover(int i, int shift){
            Population[i].newFromCrossover(MatingPool[i/2].getGenotype(), MatingPool[(i/2)+shift % MatingPool.length].getGenotype());
    }

    private void randomCrossover(){
        for (int i = 0; i < Population.length; i++)
        {
            Population[i].newFromCrossover(MatingPool[i/2].getGenotype(), MatingPool[r.nextInt(MatingPool.length)].getGenotype());
        }
    }

    private void mutate(int frame){

    }

    void end(int i)
    {
        go = false;
        StringBuilder s = new StringBuilder();
        s.append(Population[i].getGenotype()[0]);

        for(int x = 1; x < Population[i].getGenotype().length; x++){
            s.append(", ");
            s.append(Population[i].getGenotype()[x]);
        }

        System.out.println("The phrase is " + s);
    }
}


