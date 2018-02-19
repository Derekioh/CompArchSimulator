package com.csci6461.team13.simulator.ui.controllers;

import com.csci6461.team13.simulator.Simulator;
import com.csci6461.team13.simulator.util.FXMLLoadResult;
import com.csci6461.team13.simulator.util.FXMLUtil;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StartPanelController {

    @FXML
    Button sStart;

    /**
     * automatically executed by application after loading
     */
    @FXML
    void initialize() {

    }

    /**
     * act to clicks on sStart
     * <p>
     * switch scene to main scene
     */
    @FXML
    void handleStartBtn(MouseEvent event) {
        System.out.println("Simulation Started");

        Stage primaryStage = Simulator.getPrimaryStage();
        sStart.setDisable(true);
        try {
            // load the main scene, then current scene will dismiss
            FXMLLoadResult result = FXMLUtil.loadAsNode("main.fxml");
            FXMLUtil.addStylesheets(result.getNode(), "static/bootstrap3.css");
            primaryStage.setScene(new Scene(result.getNode()));
            primaryStage.setResizable(true);
//            primaryStage.setMaximized(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
