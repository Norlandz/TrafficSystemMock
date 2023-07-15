package com.redfrog.trafficsm;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.redfrog.trafficsm.maincoretest.TestSuite_MainCore;
import com.redfrog.trafficsm.mainrepotest.TestSuite_MainRepo;
import com.redfrog.trafficsm.webControllerTest.TestSuite_WebController;

@Suite
@SelectClasses({ TestSuite_MainCore.class, TestSuite_MainRepo.class, TestSuite_WebController.class })
//__________________
public class TestSuite_MainEssential {

}

//_________________________________