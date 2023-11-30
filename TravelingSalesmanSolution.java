package com.example.genetic_algorithm;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

class TravelingSalesmanSolution
{
    private int[] genotype;
    private Random r = new Random();

    public TravelingSalesmanSolution(int length)
    {
        genotype = new int[length];

        for (int i = 0; i < genotype.length; i++) {
            genotype[i] = -1;
        }

        for (int i = 0; i < genotype.length; i++)
        {
            int nextLoc;


            do {
                nextLoc = r.nextInt(0, length);
            }while(hasLoc(nextLoc));

            genotype[i] = nextLoc;
        }
    }

    //Copies another solution
    public TravelingSalesmanSolution(TravelingSalesmanSolution s)
    {
        genotype = new int[s.getGenotype().length];

        for (int i = 0; i < genotype.length; i++) {
            genotype[i] = s.getGenotype()[i];
        }
    }

    public TravelingSalesmanSolution(int[] genotype) {
        this.genotype = genotype;
    }

    public TravelingSalesmanSolution(Integer[] genotype) {
        int[] primGenotype = new int[genotype.length];

        for(int i = 0; i < genotype.length; i++) primGenotype[i] = genotype[i].intValue();

        this.genotype = primGenotype;
    }

    private boolean hasLoc(int loc){
        for(int i = 0; i < genotype.length; i++){
            if(genotype[i] == loc){ return true;}
        }

        return false;
    }

    public void print()
    {
        for(int i:genotype){
            System.out.print(i + " ");
        }

        System.out.println();
    }

    private double lengthOfJourney;

    public double distanceTravelled(double[][] adjacency)
    {
        lengthOfJourney = 0;

        for (int i = 0; i < genotype.length - 1; i++)
        {

            lengthOfJourney += adjacency[genotype[i]][genotype[i+1]];
        }

        return lengthOfJourney;
    }

    public double getLengthOfJourney() {
        return lengthOfJourney;
    }

    public int[] getGenotype()
    {
        return genotype;
    }

    public void mutateSwap() {
        int index1 = r.nextInt(genotype.length);
        int index2 = r.nextInt(genotype.length);

        int temp = genotype[index1];

        genotype[index1] = genotype[index2];
        genotype[index2] = temp;
    }


    public void mutateReverseSubArray(){
        int index1 = r.nextInt(genotype.length-3);
        int index2 = index1+3;//r.nextInt(index1+2, genotype.length);



        for(int i = index1; i < (index1+index2)/2; i++){
            int temp = genotype[i];
            genotype[i] = genotype[index2-i];
            genotype[index2-i] = temp;
        }
    }

}
