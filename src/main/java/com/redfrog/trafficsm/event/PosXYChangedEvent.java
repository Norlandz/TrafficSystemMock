package com.redfrog.trafficsm.event;

import com.redfrog.trafficsm.annotation.CanRemoveFromCode;
import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.shape.Point;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

@CanRemoveFromCode
@Deprecated
public class PosXYChangedEvent extends Event {

  @Messy
  public static final EventType<PosXYChangedEvent> PosXYChangedEvent_EventType = new EventType<>(PosXYChangedEvent.class.getSimpleName() + EventType.class.getSimpleName());

  @Getter
  private final Point point;

  public PosXYChangedEvent(Point point) {
    super(PosXYChangedEvent_EventType);
    this.point = point;
  }

}

//_________________________________________
//
//_______________________________________________
//__
//_________________________________________________________________________________
//
//______________________________________________________________________________________________
//
//_____________________________________
//
//_