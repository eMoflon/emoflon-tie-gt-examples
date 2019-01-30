<?xml version="1.0" encoding="ASCII"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore" xmlns:emf="http://gervarro.org/democles/constraint/emf.ecore" xmlns:specification="http://gervarro.org/democles/specification.ecore">
  <specification:Pattern name="pattern_SokobanValidator_move_2_moveSokobanUp_red">
    <symbolicParameters xsi:type="emf:EMFVariable" name="sok">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Sokoban"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="from">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Field"/>
    </symbolicParameters>
    <bodies>
      <constraints xsi:type="emf:Reference">
        <parameters reference="/0/@symbolicParameters.0"/>
        <parameters reference="/0/@symbolicParameters.1"/>
        <eModelElement xsi:type="ecore:EReference" href="SokobanLanguage.ecore#//Figure/field"/>
      </constraints>
    </bodies>
  </specification:Pattern>
  <specification:Pattern name="pattern_SokobanValidator_move_4_moveSokobanDown_red">
    <symbolicParameters xsi:type="emf:EMFVariable" name="sok">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Sokoban"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="from">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Field"/>
    </symbolicParameters>
    <bodies>
      <constraints xsi:type="emf:Reference">
        <parameters reference="/1/@symbolicParameters.0"/>
        <parameters reference="/1/@symbolicParameters.1"/>
        <eModelElement xsi:type="ecore:EReference" href="SokobanLanguage.ecore#//Figure/field"/>
      </constraints>
    </bodies>
  </specification:Pattern>
  <specification:Pattern name="pattern_SokobanValidator_move_6_moveSokobanLeft_red">
    <symbolicParameters xsi:type="emf:EMFVariable" name="sok">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Sokoban"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="from">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Field"/>
    </symbolicParameters>
    <bodies>
      <constraints xsi:type="emf:Reference">
        <parameters reference="/2/@symbolicParameters.0"/>
        <parameters reference="/2/@symbolicParameters.1"/>
        <eModelElement xsi:type="ecore:EReference" href="SokobanLanguage.ecore#//Figure/field"/>
      </constraints>
    </bodies>
  </specification:Pattern>
  <specification:Pattern name="pattern_SokobanValidator_move_8_moveSokobanRight_red">
    <symbolicParameters xsi:type="emf:EMFVariable" name="sok">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Sokoban"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="from">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Field"/>
    </symbolicParameters>
    <bodies>
      <constraints xsi:type="emf:Reference">
        <parameters reference="/3/@symbolicParameters.0"/>
        <parameters reference="/3/@symbolicParameters.1"/>
        <eModelElement xsi:type="ecore:EReference" href="SokobanLanguage.ecore#//Figure/field"/>
      </constraints>
    </bodies>
  </specification:Pattern>
  <specification:Pattern name="pattern_SokobanValidator_move_10_pushBlockRight_red">
    <symbolicParameters xsi:type="emf:EMFVariable" name="block">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Block"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="to">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Field"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="sok">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Sokoban"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="from">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Field"/>
    </symbolicParameters>
    <bodies>
      <constraints xsi:type="emf:Reference">
        <parameters reference="/4/@symbolicParameters.0"/>
        <parameters reference="/4/@symbolicParameters.1"/>
        <eModelElement xsi:type="ecore:EReference" href="SokobanLanguage.ecore#//Figure/field"/>
      </constraints>
      <constraints xsi:type="emf:Reference">
        <parameters reference="/4/@symbolicParameters.2"/>
        <parameters reference="/4/@symbolicParameters.3"/>
        <eModelElement xsi:type="ecore:EReference" href="SokobanLanguage.ecore#//Figure/field"/>
      </constraints>
    </bodies>
  </specification:Pattern>
  <specification:Pattern name="pattern_SokobanValidator_move_12_pushBlockLeft_red">
    <symbolicParameters xsi:type="emf:EMFVariable" name="block">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Block"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="to">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Field"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="sok">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Sokoban"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="from">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Field"/>
    </symbolicParameters>
    <bodies>
      <constraints xsi:type="emf:Reference">
        <parameters reference="/5/@symbolicParameters.0"/>
        <parameters reference="/5/@symbolicParameters.1"/>
        <eModelElement xsi:type="ecore:EReference" href="SokobanLanguage.ecore#//Figure/field"/>
      </constraints>
      <constraints xsi:type="emf:Reference">
        <parameters reference="/5/@symbolicParameters.2"/>
        <parameters reference="/5/@symbolicParameters.3"/>
        <eModelElement xsi:type="ecore:EReference" href="SokobanLanguage.ecore#//Figure/field"/>
      </constraints>
    </bodies>
  </specification:Pattern>
  <specification:Pattern name="pattern_SokobanValidator_move_14_pushBlockUp_red">
    <symbolicParameters xsi:type="emf:EMFVariable" name="block">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Block"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="to">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Field"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="sok">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Sokoban"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="from">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Field"/>
    </symbolicParameters>
    <bodies>
      <constraints xsi:type="emf:Reference">
        <parameters reference="/6/@symbolicParameters.0"/>
        <parameters reference="/6/@symbolicParameters.1"/>
        <eModelElement xsi:type="ecore:EReference" href="SokobanLanguage.ecore#//Figure/field"/>
      </constraints>
      <constraints xsi:type="emf:Reference">
        <parameters reference="/6/@symbolicParameters.2"/>
        <parameters reference="/6/@symbolicParameters.3"/>
        <eModelElement xsi:type="ecore:EReference" href="SokobanLanguage.ecore#//Figure/field"/>
      </constraints>
    </bodies>
  </specification:Pattern>
  <specification:Pattern name="pattern_SokobanValidator_move_16_pushBlockDown_red">
    <symbolicParameters xsi:type="emf:EMFVariable" name="block">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Block"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="to">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Field"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="sok">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Sokoban"/>
    </symbolicParameters>
    <symbolicParameters xsi:type="emf:EMFVariable" name="from">
      <eClassifier xsi:type="ecore:EClass" href="SokobanLanguage.ecore#//Field"/>
    </symbolicParameters>
    <bodies>
      <constraints xsi:type="emf:Reference">
        <parameters reference="/7/@symbolicParameters.0"/>
        <parameters reference="/7/@symbolicParameters.1"/>
        <eModelElement xsi:type="ecore:EReference" href="SokobanLanguage.ecore#//Figure/field"/>
      </constraints>
      <constraints xsi:type="emf:Reference">
        <parameters reference="/7/@symbolicParameters.2"/>
        <parameters reference="/7/@symbolicParameters.3"/>
        <eModelElement xsi:type="ecore:EReference" href="SokobanLanguage.ecore#//Figure/field"/>
      </constraints>
    </bodies>
  </specification:Pattern>
</xmi:XMI>
