package com.redfrog.trafficsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.redfrog.trafficsm.TrafficSystemMockAppJavafxBoot.StageReadyEvent;
import com.redfrog.trafficsm.annotation.Debug;
import com.redfrog.trafficsm.exception.AlreadyExistedException;
import com.redfrog.trafficsm.exception.FoundNoItemWithIdException;
import com.redfrog.trafficsm.model.Vehicle;
import com.redfrog.trafficsm.service.MapBuilder;
import com.redfrog.trafficsm.service.MoveController;
import com.redfrog.trafficsm.service.TrafficAnalyzerBuilder;
import com.redfrog.trafficsm.service.WindowSessionJavafx;
import com.redfrog.trafficsm.service.exception.PointIsNotNearAnyRoadwayException;
import com.redfrog.trafficsm.shape.Point;

import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class MoveVehicleJavafxSessionInit implements ApplicationListener<StageReadyEvent> {

  @Autowired
  private MoveController moveController_corr;

  @Autowired
  private MapBuilder mapBuilder_corr;

  @Autowired
  private WindowSessionJavafx windowSessionJavafx_corr;

  @Autowired
  private TrafficAnalyzerBuilder trafficAnalyzerBuilder;

  //__________________________________________________
  //___________________________________________________________________________
  //___________________________________________________________________________________________________________________________
  //__________________________________________________________________________________________________________________________________________________
  //____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //_______________________________________________
  //_________________________________________________________________________________________________
  //_________________________________________________________________________________________________
  //_______________________

  @Override
  public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
    @Debug byte dmy033; //____________________________________________________________
    //_________________________________
    enable_JavafxController();
  }

  private void createVehicleDemo02_Javafx() {
    trafficAnalyzerBuilder.createMapDemo02();
    trafficAnalyzerBuilder.createVehicleDemo01();
    @Debug byte dmy371; //____________
    //_____________________________________________________________________________
    Vehicle vehicle = mapBuilder_corr.getVehicleInventory().getMppVehicle().values().iterator().next();
    try {
      //________________________________________________________
      trafficAnalyzerBuilder.placeVehicleInMap(vehicle.getIdSql());
    } catch (AlreadyExistedException | FoundNoItemWithIdException e) {
      throw new Error(e);
    }
    vehicle.setPosActual(new Point(671, 453));
    mapBuilder_corr.setVehicleSelected(vehicle);
  }

  private void enable_JavafxController() {
    Platform.runLater(() -> {
      windowSessionJavafx_corr.panel_SemanticRoot.addEventHandler(MouseEvent.MOUSE_PRESSED, (event) -> {
        //____________________________
        if (!event.isConsumed()) {
          //______________________________
          if (!event.isControlDown() && !event.isAltDown() && !event.isShiftDown() && event.isSecondaryButtonDown()) {
            event.consume();
            //________________________________________________________________
            //___________________________________________________________
            try {
              Vehicle vehicleSelected_L = mapBuilder_corr.getVehicleSelected();
              if (vehicleSelected_L == null) {
                log.info(">> enable_JavafxController-> No Vehicle is Selected");
              }
              else {
                log.debug(">> enable_JavafxController-> " + vehicleSelected_L);
                trafficAnalyzerBuilder.gotoTarget(vehicleSelected_L.getIdSql(), event.getX(), event.getY());
              }
            } catch (FoundNoItemWithIdException e) {
              throw new Error(e);
            } catch (PointIsNotNearAnyRoadwayException e) {
              System.err.println(e);
              //________________________________
              return;
            }
          }
        }
      });
    });

  }

}
