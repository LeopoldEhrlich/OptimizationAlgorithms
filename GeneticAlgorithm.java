package com.example.genetic_algorithm;

class GeneticAlgorithm extends Algorithm
{

    //Contains the overall population of solutions
    private TravelingSalesmanSolution[] Population;

    //Contains the subset of the population that will be preserved to make the next generation
    private TravelingSalesmanSolution[] MatingPool;

    //The average number of solutions per mutated solution
    private int mutationRate;

    //The number of times to iterate the population
    private int iterations;

    public GeneticAlgorithm(int graphSize) {
        this(graphSize, 4000, 25, 4000);

    }

    public GeneticAlgorithm(int graphSize, int iterations, int mutationRate, int popSize){
        super(graphSize, "Genetic Algorithm");

        Population = new TravelingSalesmanSolution[popSize];
        MatingPool = new TravelingSalesmanSolution[popSize/2];

        this.mutationRate = mutationRate;
        this.iterations = iterations;
    }


    //Creates a new population of solutions, initialized randomly
    public void generate()
    {
        for (int i = 0; i < Population.length; i++)
        {
            Population[i] = new TravelingSalesmanSolution(graph.getSize());
        }
    }

    //evaluation and reproduction cycle
    void run()
    {
        generate();

        int count = 0;

        for(int i = 0; i < iterations; i++){
            nextPopulation();
        }
        drawBestPath();
    }

    //Returns the solution with the highest fitness
    private TravelingSalesmanSolution getBestPath(){
        TravelingSalesmanSolution bestPath = MatingPool[0];

        for (TravelingSalesmanSolution s : MatingPool)
        {
            if(bestPath.getLengthOfJourney() > s.getLengthOfJourney()){
                bestPath = s;
            }
        }

        return bestPath;
    }

    //selects using tournament selection
    private void selectTournament()
    {
        double[] absoluteFitness = new double[Population.length];

        //populates distance and adds up total of fitness score
        for (int i = 0; i < Population.length; i++)
        {
            absoluteFitness[i] = Population[i].distanceTravelled(graph.getWeights());
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

    //Iterates the genetic algorithm to get the next population
    private void nextPopulation()
    {
        //populates the mating pool
        selectTournament();

        //crosses over the mating pool
        for (int i = 0; i < MatingPool.length; i++) crossoverCycle(i);

        //Applies mutation to a random sampling of members
        int i = mutationRate+r.nextInt(-mutationRate,mutationRate);
        while (i < Population.length)
        {
            Population[i].mutateSwap();
            i += mutationRate+r.nextInt(-mutationRate/2,mutationRate/2);
        }

    }

    //Crosses over two solutions using cycle crossover
    private void crossoverCycle(int parent1Index){
        int[] parent1 = MatingPool[parent1Index].getGenotype();
        int[] parent2 = MatingPool[(parent1Index+1) % MatingPool.length].getGenotype();

        Population[parent1Index] = cycleGenChild(parent1,parent2);
        Population[parent1Index+MatingPool.length] = cycleGenChild(parent2,parent1);
    }

    private TravelingSalesmanSolution cycleGenChild(int[] parent1, int[] parent2){
        Integer[] child = new Integer[parent1.length];

        child[0] = parent1[0];
        int next = parent2[0];

        boolean cycle = true;
        //in the first cycle it's creating solutions that don't have valid cycles (missing bits)
        while(cycle){
            for (int i = 0; i < parent1.length; i++){
                if(parent1[i] == next){
                    if(i == 0){
                        cycle = false;
                        break;
                    }

                    child[i] = parent1[i];
                    next = parent2[i];
                }
            }
        }

        for (int i = 0; i < parent1.length; i++){
            if(child[i] == null){
                child[i] = parent2[i];
            }
        }

        return new TravelingSalesmanSolution(child);
    }

    private void drawBestPath(){
        drawPath(getBestPath());
    }

    //Returns the distance of the path of the best solution
    public double solutionLength() {
        return (getBestPath().getLengthOfJourney());
    }
}
