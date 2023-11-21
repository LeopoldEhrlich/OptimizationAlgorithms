package com.example.genetic_algorithm;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Graph {
    private double[][] adjacency;
    private double[][] positions;

    private int size;

    public Graph(int size) {
        adjacency = new double[size][size];

        this.size = size;

        generate(size);
    }

    //Initializes a new graph with random positions
    private void generate(int size) {
        Random r = new Random();
        positions = new double[this.size][2];

        for (int i = 0; i < this.size; i++) {
            positions[i][0] = r.nextDouble(5, 595);
            positions[i][1] = r.nextDouble(5, 295);
        }

        for(int i = 0; i < this.size; i++){
            for (int x = 0; x < this.size; x++) {
                adjacency[i][x] = distance(positions[i], positions[x]);
            }
        }
    }

    //Returns the distance between two nodes
    private double distance(double[] p1, double[] p2)
    {
        return Math.sqrt((p1[0] - p2[0])*(p1[0] - p2[0]) + (p1[1] - p2[1])*(p1[1] - p2[1]));
    }

    //Adds each node of the graph to the group to be displayed
    public void draw(Group group){
        for(int i = 0; i < positions.length; i++){
            Circle node = new Circle();

            node.setCenterX(positions[i][0]);
            node.setCenterY(positions[i][1]);

            node.setRadius(5);
            node.setFill(Color.DARKSLATEGREY);

            group.getChildren().add(node);
        }
    }

    //Getters
    public int getSize(){return size;}
    public double[][] getAdjacency(){ return adjacency; }
    public double[][] getPositions() { return positions; }
}




