package com.redfrog.trafficsm;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import com.redfrog.trafficsm.service.WindowSessionJavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

//________________________________________________________________________________________________________
public class TrafficSystemMockAppJavafxBoot extends Application {

  private ConfigurableApplicationContext applicationContext;

  @Override
  public void init() {
    applicationContext = new SpringApplicationBuilder(TrafficSystemMockAppSpringBoot.class).run(); //
  }

  @Override
  public void start(Stage primaryStage) {
    double w_PanelSemanticRoot = 900 + 50;
    double h_PanelSemanticRoot = 600 + 50;
    double w_Scene = w_PanelSemanticRoot + 50;
    double h_Scene = h_PanelSemanticRoot + 50;

    WindowSessionJavafx windowSessionJavafx = applicationContext.getBean(WindowSessionJavafx.class);
    windowSessionJavafx.javafxStage = primaryStage;

    //____
    AnchorPane pane_JavafxRoot = new AnchorPane();
    pane_JavafxRoot.setBackground(Background.EMPTY); //_____________________________________
    windowSessionJavafx.pane_JavafxRoot = pane_JavafxRoot;

    AnchorPane panel_SemanticRoot = new AnchorPane();
    windowSessionJavafx.panel_SemanticRoot = panel_SemanticRoot;
    panel_SemanticRoot.setLayoutX(10);
    panel_SemanticRoot.setLayoutY(10);
    panel_SemanticRoot.setPrefSize(w_PanelSemanticRoot, h_PanelSemanticRoot);
    //_______________________________________________________
    pane_JavafxRoot.getChildren().add(panel_SemanticRoot);

    Button button = new Button("click me!");
    button.setOnAction(actionEvent -> button.setText("clicked!"));
    panel_SemanticRoot.getChildren().add(button);

    moreSetup(panel_SemanticRoot);

    Scene scene = new Scene(pane_JavafxRoot, w_Scene, h_Scene);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Init Page");
    primaryStage.show();

    applicationContext.publishEvent(new StageReadyEvent(primaryStage));
  }

  public static class StageReadyEvent extends ApplicationEvent {
    public StageReadyEvent(Stage stage) { super(stage); }

    public Stage getStage() { return ((Stage) getSource()); }
  }

  public static void moreSetup(Pane panel_SemanticRoot) {
    panel_SemanticRoot.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
      if (!event.isConsumed()) {
        if (!event.isControlDown() && !event.isAltDown() && !event.isShiftDown() && event.isPrimaryButtonDown()) {
          //__________________________
          double posX = event.getX();
          double posY = event.getY();
          System.out.printf("new Point(%f, %f) %n", posX, posY);
        }
      }
    });
  }

  @Override
  public void stop() {
    applicationContext.close();
    Platform.exit();
  }

}

//_
//___________
//_______________________________________________________________________________________________
//_
//_________________
//__________________________________________
//_____
//_________________
//_______________________________________________________
//_
//__________
//_____________________________
//__________
//________________________________
//__________
//___________________________________
//_
//____________
//____________________________________________________________________
//___________________________________________________________
//__________________________________________________________
//_____________________________________
//_
//___________________________________________
//___________________________________________
//_______________________________________________
//_______________________________________________
//_
//___________
//___________________________________________________
//____________________________________________
//________________________________________________________________
//__________________________________
//_____________________________________________________________________________________________
//________________________________________
//_
//______________________________________________________
//__________________________________________________
//___________________________________________________________
//_______________________________________
//_______________________________________
//______________________________________________________________________________
//______________________________________________________________
//_
//_____________________________________________
//__________________________________________________
//___________________________________________________________________
//_
//_________________________________________________________________________________________________________
//______________________________________________________________________________________________________________________
//____________________________________________________________________________________________________________________________
//_________________________________________________________________
//_________________________________________________________________________________________________________________________________
//_________________________________
//_
//_________________________
//____
//_
//__