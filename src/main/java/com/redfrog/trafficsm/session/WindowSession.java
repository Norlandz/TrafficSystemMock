package com.redfrog.trafficsm.session;

import org.springframework.stereotype.Component;

import com.redfrog.trafficsm.annotation.UseWithoutSpring;
import com.redfrog.trafficsm.service.MapBuilder;
import com.redfrog.trafficsm.service.MoveController;
import com.redfrog.trafficsm.service.PathFinder;
import com.redfrog.trafficsm.service.TrafficAnalyzerBuilder;
import com.redfrog.trafficsm.service.WindowSessionJavafx;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@UseWithoutSpring
//__________
public class WindowSession {

  private static int seqNum = 0;

  public final String name;

  public WindowSession() {
    seqNum++;
    this.name = "Init";
  }

  public final WindowSessionJavafx windowSessionJavafx = new WindowSessionJavafx();

  public final MapBuilder mapBuilder = new MapBuilder(this);
  public final PathFinder pathFinder = new PathFinder(this);
  public final TrafficAnalyzerBuilder trafficAnalyzerBuilder = new TrafficAnalyzerBuilder(this);
  public final MoveController moveController = new MoveController(this);

}
