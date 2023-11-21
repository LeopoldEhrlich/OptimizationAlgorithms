package com.example.genetic_algorithm;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

class Member
{
    private int[] genotype;
    private Random r = new Random();

    public Member(int length)
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

    public Member(Member m)
    {
        genotype = new int[m.getGenotype().length];

        for (int i = 0; i < genotype.length; i++) {
            genotype[i] = m.getGenotype()[i];
        }
    }

    public Member(int[] genotype)
    {
        this.genotype = genotype;
    }

    public void newFromCrossover(int[] parent1, int[] parent2)
    {
        genotype = new int[parent1.length];

        for (int i = 0; i < genotype.length; i++) {
            genotype[i] = -1;
        }

        int startingPoint = r.nextInt(parent1.length-1);

        int i = startingPoint+1;

        for(int count = 0; count < genotype.length; count++){
            i = i % (parent1.length);

            if(count  < parent1.length/2) {
                genotype[i] = parent1[i];
            }

            else{
                int p2i = i;
                while(hasLoc(parent2[p2i])) {
                    p2i++;
                    p2i = p2i % (parent1.length);
                    //if(i == startingPoint){break;}
                }

                genotype[i] = parent2[p2i];
            }

            i++;
        }
    }

    public void newFromCycleCrossover(int[] parent1, int[] parent2)
    {
        HashMap<Integer, Integer> values = new HashMap<Integer, Integer>();
        values.put(parent1[0], 0);
        boolean cycle = true;

        while(cycle){
            for (int i = 0; i < genotype.length; i++){
                if(values.containsKey(parent2[i])){
                    if(parent1[i] == parent1[0]){
                        cycle = false;
                        break;
                    }

                    values.put(parent1[i], i);
                }
            }
        }

        for (int i = 1; i < genotype.length; i++){
            if(!values.containsKey(parent1[i])){
                values.put(parent2[i], i);
            }
        }

        /*
        for (int i = 1; i < genotype.length; i++){
            if(parent1[i] == parent2[i] && !values.containsKey(parent2)){
                values.put(parent2[i], i);
            }
        }
        */

        for(int i = 0; i < genotype.length; i++){
            try {
                genotype[values.get(i)] = i;
            }
            catch (NullPointerException e){
            }
        }


    }

    private boolean hasLoc(int loc){
        for(int i = 0; i < genotype.length; i++){
            if(genotype[i] == loc){ return true;}
        }

        return false;
    }

    public void Print()
    {
        System.out.println(genotype);
    }

    private double lengthOfJourney;

    public double Fitness(double[][] adjacency)
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

    public void mutate(){
        int index1 = r.nextInt(genotype.length-3);
        int index2 = index1+3;//r.nextInt(index1+2, genotype.length);



        for(int i = index1; i < (index1+index2)/2; i++){
            int temp = genotype[i];
            genotype[i] = genotype[index2-i];
            genotype[index2-i] = temp;
        }
    }

}
