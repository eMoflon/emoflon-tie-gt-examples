package org.moflon.tie.gt.testsuite;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import GraphOperations.ConstantUtilsTest;
import GraphOperations.EIntContainerTest;
import GraphOperations.GraphTest;
import SokobanTests.SokobanTutorialAppendixTest;
import SokobanTests.SokobanValidatorTest;
import de.tud.es.dyve.model.NetworkTest;
import de.tud.es.dyve.model.RootTest;
import test2.MatchBoxTest;

@RunWith(JUnitPlatform.class)
@SelectClasses({ ConstantUtilsTest.class, EIntContainerTest.class, GraphTest.class, MatchBoxTest.class,
		NetworkTest.class, RootTest.class, SokobanValidatorTest.class, SokobanTutorialAppendixTest.class })
public class EMoflonTieGtTestSuite {

}
