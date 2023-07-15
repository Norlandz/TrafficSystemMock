package com.redfrog.trafficsm.mainrepotest.randomTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testfx.framework.junit5.ApplicationTest;

import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.service.MapBuilder;
import com.redfrog.trafficsm.service.TrafficAnalyzerBuilder;
import com.redfrog.trafficsm.service.WindowSessionJavafx;
import com.redfrog.trafficsm.test.javafxBoot.JfxAppSimpleSetup;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
//______________________
class DbSpeedCreateMapDemo01RandomTest extends ApplicationTest {

  @Autowired
  private MapBuilder mapBuilder;

  @Autowired
  private TrafficAnalyzerBuilder trafficAnalyzerBuilder;

  @Messy
  @Autowired
  private WindowSessionJavafx windowSessionJavafx;

  //_____________
  //_______________________________________________________

  //_____

  //____________
  @PersistenceContext
  private EntityManager em;

  //_____

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

    //_____________________________________________________________________
    //_______________________________
    //____________________________________________________
    //___________________________________________________________________________
    //______________________________________________
    //____________________________
    //________________

    @Test
    @Transactional
    @Rollback(false)
    public void demo_01() {
      System.out.println(">> DbSpeedCreateMapDemo01RandomTest");
      mapBuilder.createMapDemo01();

      //____________________

      //______________________________________________________________
      //____________________________________________________________________________________________________________________________
      //______________________________________________________________________
      //____________________________________________________________________________________________
      //______________
      //________________________________________________
      //______________________________________________________
      //__________________________________________________________
      //________________________
      //________________________________________________________________________________________________________
      //________________________________________________________________________________________________________
      //_____________________________________________________
      //______________
      //________________________________________________________________________________________________________
      //________________________________________________________________________________________________________
      //_______________________________________________________________
      //_______________________________________
      //______________
      //________________________________________________________________________________________________________
      //________________________________________________________________________________________________________
      //_____________________________________________________
      //_____________________________________________________
      //________________________________________
      //____________________

    }

  }

  @Nested
  class Test_02 {

    @Test
    @Transactional
    //____________________
    public void demo_02() {
      trafficAnalyzerBuilder.createMapDemo02();

      sleep(999999);
    }

  }

}
