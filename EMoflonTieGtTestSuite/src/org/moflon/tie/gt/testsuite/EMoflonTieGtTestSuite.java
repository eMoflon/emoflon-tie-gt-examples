package org.moflon.tie.gt.testsuite;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import GraphOperations.ConstantUtilsTest;
import GraphOperations.EIntContainerTest;
import GraphOperations.GraphTest;
import test2.MatchBoxTest;

@RunWith(JUnitPlatform.class)
@SelectClasses({ ConstantUtilsTest.class, EIntContainerTest.class, GraphTest.class, MatchBoxTest.class })
public class EMoflonTieGtTestSuite {

}
