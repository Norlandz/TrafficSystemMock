package com.redfrog.trafficsm.message.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.redfrog.trafficsm.annotation.Config;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Config
  public final static String ws_BrokerTopic_ServerPubClientSub = "/topic";
  @Config
  public final static String ws_BrokerTopic_ClientPubServerSub = "/app";
  @Config
  public final static String ws_to_connect = "/ws_to_connect";

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker(ws_BrokerTopic_ServerPubClientSub);
    config.setApplicationDestinationPrefixes(ws_BrokerTopic_ClientPubServerSub);
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    //____________
    //___________________________________________________________________________________
    //_______________
    registry.addEndpoint(ws_to_connect).setAllowedOriginPatterns("*");
  }

}