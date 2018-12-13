package org.moflon.tie.gt.testsuite;

import test2.MatchBoxTest;

import GraphOperations.ConstantUtilsTest;
import GraphOperations.GraphTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({ ConstantUtilsTest.class, GraphTest.class, MatchBoxTest.class })
public class EMoflonTieGtTestSuite {

}
