package edu.ktu.ds.lab2.demo;

import edu.ktu.ds.lab2.gui.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;

/*
 * Order of work - here is the primary class.
 */
public class DemoExecution extends Application {

    public static void main(String[] args) {
        DemoExecution.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(Locale.US); // We unify number formats
        MainWindow.createAndShowGui(primaryStage);
    }
}
