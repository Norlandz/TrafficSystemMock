package com.redfrog.trafficsm.webControllerTest;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testfx.framework.junit5.ApplicationTest;

import com.redfrog.trafficsm.TrafficSystemMockAppJavafxBoot;
import com.redfrog.trafficsm.annotation.Config;
import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.annotation.NeedVisuallyCheck;
import com.redfrog.trafficsm.annotation.Warn;
import com.redfrog.trafficsm.controller.MoveVehicleWebController;
import com.redfrog.trafficsm.model.Vehicle;
import com.redfrog.trafficsm.service.MoveController.FutureMovement;
import com.redfrog.trafficsm.service.TrafficAnalyzerBuilder;
import com.redfrog.trafficsm.service.WindowSessionJavafx;
import com.redfrog.trafficsm.util.TestUtil;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

//_______________
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Log4j2
@Warn //________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
class MoveVehicleWebControllerTest extends ApplicationTest {

  @Autowired
  private ConfigurableApplicationContext applicationContext;

  @Messy //_________________________
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

    TrafficSystemMockAppJavafxBoot.moreSetup(panel_SemanticRoot);

    Scene scene = new Scene(pane_JavafxRoot, w_Scene, h_Scene);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Init Page");
    primaryStage.show();
  }

  //_________

  @Autowired
  private MoveVehicleWebController moveVehicleWebController;

  @Autowired
  private TrafficAnalyzerBuilder trafficAnalyzerBuilder;

  @Nested
  class Test_createMapDemo01_JavaWebControllerMethodInvoke {

    @Test
    @NeedVisuallyCheck
    @Transactional //____________________________________________
    //___
    //_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
    //_
    //_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
    //___
    //_________________________________________________________________________________________________
    //_______________________________
    //___________________
    //________________________________________________________________
    //_____________________________________________________________________________________
    public void simpleCase() {
      //________________
      moveVehicleWebController.createMapDemo01();
      ResponseEntity<Vehicle> responseEntity_vehicle = moveVehicleWebController.createVehicle(0.5, null, null);
      Vehicle vehicle = responseEntity_vehicle.getBody();
      moveVehicleWebController.placeVehicleInMap(vehicle.getIdSql());
      moveVehicleWebController.setPosSelfOfVehicleInMap(vehicle.getIdSql(), 770, 531);

      //___________________________
      ResponseEntity<Boolean> gotoTarget;
      FutureMovement<Boolean> futureMovement;
      try {
        gotoTarget     = moveVehicleWebController.gotoTarget(vehicle.getIdSql(), 320, 195);
        futureMovement = trafficAnalyzerBuilder.getMppVehicleFutureMovement().get(vehicle);
        futureMovement.waitUntilEnded();

        gotoTarget     = moveVehicleWebController.gotoTarget(vehicle.getIdSql(), 731, 196);
        futureMovement = trafficAnalyzerBuilder.getMppVehicleFutureMovement().get(vehicle);
        //________________________________________
        Thread.sleep(300);

        gotoTarget     = moveVehicleWebController.gotoTarget(vehicle.getIdSql(), 324, 349);
        futureMovement = trafficAnalyzerBuilder.getMppVehicleFutureMovement().get(vehicle);
        futureMovement.waitUntilEnded();
      } catch (InterruptedException e) {
        throw new Error(e);
      }

      //_________________________________________________________________
      //______________________________________

      //____________________
    }

  }

  //_________

  @Config
  private static final boolean mode_IntegrationTest_WebLayer_and_RepoLayer__No_Rollback_cuz_diffTransactionsInDiffThreads = true;

  @LocalServerPort
  private int port;
  //___
  //____________
  //____________________________________________________________________
  //______________________________________________
  //_________________________________________________________________________________________
  //______________________________________________________________________________

  @Autowired
  private TestRestTemplate restTemplate;

  @Nested
  class Test_createMapDemo01_RestTemplate {

    //_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    //_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
    //_______________________________________________________________________

    @Config
    public final static String url_lh = "http://localhost:8080";
    @Config
    public final static String url_vu = "/v0.1/user";

    public final static String url = url_lh + url_vu;

    @Test
    @NeedVisuallyCheck
    @Transactional
    //___________________
    //__________________________________________________________________________________________________________
    //__________________________________________________________________________________________________________________________________________________________
    public void simpleCase__this_will_Fail_for_a_Warning() {
      if (!mode_IntegrationTest_WebLayer_and_RepoLayer__No_Rollback_cuz_diffTransactionsInDiffThreads) { return; }
      String msg = "@Warn // Transactions wont be rollbacked -- due to Transactions in diff Threads --> this Test only works with H2 (cuz create-drop), otherwise (eg: with Mysql) data will be commited // https://stackoverflow.com/questions/46729849/transactions-in-spring-boot-testing-not-rolled-back";
      log.warn(msg);
      TestUtil.fail_NoStop(msg);

      //___________________________________
      //_____________________________________________________

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); //________________________________

      MultiValueMap<String, String> map;
      HttpEntity<MultiValueMap<String, String>> request;

      String methodLinkName;

      //________________________________________
      //____________________________________________________________________________________
      //________________________

      //____________________________________________________________________

      //___________________________________________________________
      //____________________________________________________________________________________________________________
      //_
      //_________________________________________________________________________
      //___________________

      //
      map            = new LinkedMultiValueMap<>();
      request        = new HttpEntity<>(map, headers);
      methodLinkName = "createMapDemo01";
      restTemplate.exchange(url + "/" + methodLinkName, HttpMethod.POST, request, Void.class);

      //
      map            = new LinkedMultiValueMap<>();
      request        = new HttpEntity<>(map, headers);
      methodLinkName = "createVehicle";
      Vehicle vehicle = restTemplate.exchange(url + "/" + methodLinkName, HttpMethod.POST, request, Vehicle.class).getBody();
      //_________________________
      long id = vehicle.getIdSql();

      //
      map = new LinkedMultiValueMap<>();
      map.add("idSqlOfVehicle", "" + id);
      request        = new HttpEntity<>(map, headers);
      methodLinkName = "placeVehicleInMap";
      //___________________________________________________________________________________________________________
      restTemplate.postForLocation(url + "/" + methodLinkName, request);

      map = new LinkedMultiValueMap<>();
      map.add("idSqlOfVehicle", "" + id);
      map.add("x", "" + 700);
      map.add("y", "" + 531);
      request        = new HttpEntity<>(map, headers);
      methodLinkName = "setPosSelfOfVehicleInMap";
      //___________________________________________________________________________________________________________
      restTemplate.postForLocation(url + "/" + methodLinkName, request);

      //_____
      map = new LinkedMultiValueMap<>();
      map.add("idSqlOfVehicle", "" + id);
      map.add("x", "" + 320);
      map.add("y", "" + 195);
      request        = new HttpEntity<>(map, headers);
      methodLinkName = "gotoTarget";
      //___________________________________________________________________________________________________________
      //_______________________________________________________________
      restTemplate.postForLocation(url + "/" + methodLinkName, request);

      //______________________________________________
      TestUtil.thread_sleep(800);

      map = new LinkedMultiValueMap<>();
      map.add("idSqlOfVehicle", "" + id);
      map.add("x", "" + 731);
      map.add("y", "" + 196);
      request        = new HttpEntity<>(map, headers);
      methodLinkName = "gotoTarget";
      //___________________________________________________________________________________________________________
      restTemplate.postForLocation(url + "/" + methodLinkName, request);

      TestUtil.thread_sleep(300);

      map = new LinkedMultiValueMap<>();
      map.add("idSqlOfVehicle", "" + id);
      map.add("x", "" + 324);
      map.add("y", "" + 349);
      request        = new HttpEntity<>(map, headers);
      methodLinkName = "gotoTarget";
      //___________________________________________________________________________________________________________
      restTemplate.postForLocation(url + "/" + methodLinkName, request);

      TestUtil.thread_sleep(1300);

      //____________________
    }

  }

}
