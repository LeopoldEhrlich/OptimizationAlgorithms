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
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(100);

        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(100);

        GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm(100);

        runAlgorithm(geneticAlgorithm);
        runAlgorithm(simulatedAnnealing);
        runAlgorithm(greedyAlgorithm);
    }

    public static void runAlgorithm(Algorithm algorithm){
        Group root = algorithm.getGroup();
        Graph graph = algorithm.getGraph();

        root.setAutoSizeChildren(false);

        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 300);
        stage.setScene(scene);
        stage.setTitle(algorithm.getAlgorithmName());
        stage.show();

        algorithm.run();
        graph.draw(root);

        System.out.println("Path Length of " + algorithm.getAlgorithmName() + ": " + algorithm.solutionLength() );
    }

    public static void main(String[] args) {
        launch();
    }
}
