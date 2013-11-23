package com.csci599.project;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.InstructionHandle;

class CFG_Graph {
	String servletName;
	Method method;
	ArrayList<Nodes> nodes;
	ArrayList<ArrayList<InstructionHandle>> edges;
	SortedMap<Integer, ArrayList<Nodes> > edgesMap;

	SortedMap<Integer, Integer> byteCode_to_sourceCode_mapping;
	ArrayList<LineHitsForEachServlet> servletStats;
	ArrayList<EdgesHit> edgesTraversed;
	ArrayList<Integer> testCases;
	ArrayList<Integer> newTestCases;
	ArrayList<Nodes> dangerousEdges;
	ArrayList<TestCaseToEdges> testCaseToEdge;
	LineNumberTable lineNumberTable;
	LocalVariableTable localVariableTable;

	public CFG_Graph() {
		servletName = "";
		method = new Method();
		nodes = new ArrayList<Nodes>();
		edges = new ArrayList<ArrayList<InstructionHandle>>();
		edgesMap = new TreeMap<Integer, ArrayList<Nodes>>();
		byteCode_to_sourceCode_mapping = new TreeMap<Integer, Integer>();
		servletStats = new ArrayList<LineHitsForEachServlet>();
		edgesTraversed = new ArrayList<EdgesHit>();
		testCases = new ArrayList<Integer>();
		newTestCases = new ArrayList<Integer>();
		dangerousEdges = new ArrayList<Nodes>();
		testCaseToEdge = new ArrayList<TestCaseToEdges>();
	}

}