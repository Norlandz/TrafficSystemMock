package com.redfrog.trafficsm.webControllerTest.randomTest;

import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testfx.framework.junit5.ApplicationTest;

import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.annotation.MultiThreadPb;
import com.redfrog.trafficsm.model.roadway.main.RoadwaySolidRoad;
import com.redfrog.trafficsm.service.MapBuilder;
import com.redfrog.trafficsm.service.TrafficAnalyzerBuilder;
import com.redfrog.trafficsm.service.WindowSessionJavafx;
import com.redfrog.trafficsm.shape.Point;
import com.redfrog.trafficsm.test.javafxBoot.JfxAppSimpleSetup;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class CreateRemoveMapFileWebControllerTest extends ApplicationTest {

  @Autowired
  private MapBuilder mapBuilder;

  @Autowired
  private TrafficAnalyzerBuilder trafficAnalyzerBuilder;

  @Autowired
  private WindowSessionJavafx windowSessionJavafx;

  //_________________________________________________________
  private AnchorPane panel_SemanticRoot;

  @Override
  public void start(Stage primaryStage) {
    panel_SemanticRoot                     = JfxAppSimpleSetup.startSetup(primaryStage);
    windowSessionJavafx.panel_SemanticRoot = panel_SemanticRoot;
  }
  //_______

  @Nested
  class Test_01 {

    @Test
    @Transactional
    @MultiThreadPb
    public void demo() {
      mapBuilder.createMapDemo01();
      mapBuilder.saveMapFile();

      trafficAnalyzerBuilder.createMapDemo02();
      mapBuilder.saveMapFile();

      //_________________________________________________

      sleep(999999);
    }

    @Test
    @Transactional
    public void demo02() {
      //________________________________________________________________

      sleep(999999);
    }

  }

}

//_____
