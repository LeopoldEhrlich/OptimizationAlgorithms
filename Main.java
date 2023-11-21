package com.example.genetic_algorithm;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Boolean.FALSE;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));

        Graph graph = new Graph(50);

        Group root = new Group();
        //Group r2 = new Group();
        //Group r3 = new Group();

        GeneticAlgorithm ga = new GeneticAlgorithm(graph, root);
        //SA sa = new SA(graph,r2);
        //GreedyAlgo greedyAlgo = new GreedyAlgo(graph, r3);

        root.setAutoSizeChildren(false);

        Scene scene = new Scene(root, 600, 300);
        stage.setScene(scene);
        stage.setTitle("Genetic Algorithm");
        stage.show();

        /*
        Stage stage2 = new Stage();
        Scene scene2 = new Scene(r2, 600, 300);
        stage2.setScene(scene2);
        stage2.setTitle("Simulated Annealing");
        stage2.show();
        */

        /*Stage stage3 = new Stage();
        Scene scene3 = new Scene(r3, 600, 300);
        stage3.setScene(scene3);
        stage3.setTitle("Greedy Algorithm");
        //stage3.show();*/


        ga.Generate();
        //greedyAlgo.run();

        graph.draw(root);
        //graph.draw(r2);
        //graph.draw(r3);

        System.out.println("Genetic Algorithm Length: " + ga.getBestPathLength() );
        //System.out.println("Simulated Annealing Length: " + sa.getLength() );
        //System.out.println("Greedy Algorithm Length: " + greedyAlgo.getLength() );
    }

    public static void main(String[] args) {
        launch();
    }
}