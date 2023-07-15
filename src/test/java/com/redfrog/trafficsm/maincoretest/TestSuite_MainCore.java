package com.redfrog.trafficsm.maincoretest;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ MoveAnimationTest.class, PathTest.class, TrafficDetectorTest.class })
public class TestSuite_MainCore {

}
