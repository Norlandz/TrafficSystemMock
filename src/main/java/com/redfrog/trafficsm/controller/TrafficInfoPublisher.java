package com.redfrog.trafficsm.controller;

import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.redfrog.trafficsm.annotation.Config;
import com.redfrog.trafficsm.message.config.WebSocketConfig;
import com.redfrog.trafficsm.model.TrafficDetector;
import com.redfrog.trafficsm.model.Vehicle;
import com.redfrog.trafficsm.model.VehicleInfoTrafficDetectorDto;

import lombok.extern.log4j.Log4j2;

@Controller //___________
@Log4j2
public class TrafficInfoPublisher {

  private ThreadPoolExecutor executor_Pub = new ThreadPoolExecutor(1, 1,
                                                                   0L, TimeUnit.MILLISECONDS,
                                                                   new LinkedBlockingQueue<Runnable>(),
                                                                   new ThreadFactoryBuilder().setNameFormat("thd-trafficInfoPublisher-%d").build());

  //_________________________________________________
  //___
  //_______________________________________________________
  //___

  @Autowired
  private ObjectMapper objectMapper;
  //____________
  //________________________________________________________________________

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @Config
  private final static String topic_PosOfVehicle = "/topic_PosOfVehicle";

  @Config
  public final static long timeGap_pub_PosOfVehicle = 100; //___

  //_____________________________________________________________________
  //_______________________________________________________________________
  //_________________________________________________________________________________
  //______________________________________________________________________
  //_____________________________________
  //___________________________________________________________________________________________
  //
  //__________________________________________________________________________________________________
  //______________________________________________________________________________________________________________________________________________________________________
  //_______________________________________________
  //_____________________________
  //___
  //_______________________________________________________________________________________________
  //_________________________________________________________________________________________

  //_____________________
  //_____________________________________
  //______________________
  //___________________________
  //___
  //_________________________________________________________________________________________________________________________________
  //__________________________________________________________________________________________________
  //__________________________________________________________________________________________________________________________________
  //
  //___________________________
  //__________________________________

  public void pub_PosOfVehicle(Vehicle vehicle) {
    executor_Pub.execute(() -> {
      String json;
      try {
        json = objectMapper.writeValueAsString(vehicle);
      } catch (JsonProcessingException e) {
        throw new Error(e);
      }
      simpMessagingTemplate.convertAndSend(WebSocketConfig.ws_BrokerTopic_ServerPubClientSub + topic_PosOfVehicle, json);
    });
  }

  @Config
  private final static String topic_VehicleInfoTrafficDetectorDto = "/topic_VehicleInfoTrafficDetectorDto";

  public void pub_VehicleInfoTrafficDetectorDto(VehicleInfoTrafficDetectorDto vehicleInfoTrafficDetectorDto, Set<TrafficDetector> gpTrafficDetector) {
    //_____________________________________________________________________________________________________________________________________________________
    executor_Pub.execute(() -> {
      log.debug(">> pub_VehicleInfoTrafficDetectorDto :: " + gpTrafficDetector);
      String json;
      try {
        json = objectMapper.writeValueAsString(gpTrafficDetector);
      } catch (JsonProcessingException e) {
        throw new Error(e);
      }
      simpMessagingTemplate.convertAndSend(WebSocketConfig.ws_BrokerTopic_ServerPubClientSub + topic_VehicleInfoTrafficDetectorDto, json);
    });
  }

}
