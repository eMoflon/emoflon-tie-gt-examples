<?xml version="1.0" encoding="ASCII"?>
<specification:Pattern xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore" xmlns:emf="http://gervarro.org/democles/constraint/emf.ecore" xmlns:specification="http://gervarro.org/democles/specification.ecore" name="pattern_Graph_2_1_removeEdge_red">
  <symbolicParameters xsi:type="emf:EMFVariable" name="edge">
    <eClassifier xsi:type="ecore:EClass" href="GraphOperations.ecore#//Edge"/>
  </symbolicParameters>
  <symbolicParameters xsi:type="emf:EMFVariable" name="graph">
    <eClassifier xsi:type="ecore:EClass" href="GraphOperations.ecore#//Graph"/>
  </symbolicParameters>
  <bodies>
    <constraints xsi:type="emf:Reference">
      <parameters reference="//@symbolicParameters.0"/>
      <parameters reference="//@symbolicParameters.1"/>
      <eModelElement xsi:type="ecore:EReference" href="GraphOperations.ecore#//Edge/graph"/>
    </constraints>
  </bodies>
</specification:Pattern>
