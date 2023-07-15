package com.redfrog.trafficsm.mainrepotest;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ MapBuilderCreateRoadwayRepoMethodSubUnitTest.class, MapBuilderTest.class, MapFileCreateRemoveSaveLoadTest.class })
public class TestSuite_MainRepo {

}
