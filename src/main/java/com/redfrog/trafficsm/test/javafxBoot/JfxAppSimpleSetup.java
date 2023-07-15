package com.redfrog.trafficsm.test.javafxBoot;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

public class JfxAppSimpleSetup {

  public static AnchorPane startSetup(Stage primaryStage) {

    double w_PanelSemanticRoot = 900 + 50;
    double h_PanelSemanticRoot = 600 + 50;
    double w_Scene = w_PanelSemanticRoot + 50;
    double h_Scene = h_PanelSemanticRoot + 50;

    //____
    AnchorPane pane_JavafxRoot = new AnchorPane();
    //_________________________________________________________
    pane_JavafxRoot.setBackground(Background.EMPTY); //_____________________________________

    AnchorPane panel_SemanticRoot = new AnchorPane();
    //_______________________________________________________________
    panel_SemanticRoot.setLayoutX(10);
    panel_SemanticRoot.setLayoutY(10);
    panel_SemanticRoot.setPrefSize(w_PanelSemanticRoot, h_PanelSemanticRoot);
    //_______________________________________________________
    pane_JavafxRoot.getChildren().add(panel_SemanticRoot);

    Button button = new Button("click me!");
    button.setOnAction(actionEvent -> button.setText("clicked!"));
    panel_SemanticRoot.getChildren().add(button);

    Scene scene = new Scene(pane_JavafxRoot, w_Scene, h_Scene);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Init Page");
    primaryStage.show();

    return panel_SemanticRoot;
  }

}
