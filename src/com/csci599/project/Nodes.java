package com.csci599.project;

import java.util.ArrayList;

import org.apache.bcel.generic.InstructionHandle;

public class Nodes {
	public InstructionHandle nodeName;
	public InstructionHandle parent;
	public ArrayList<Integer> visitedNodes;

	Nodes(InstructionHandle nodeName, InstructionHandle parent) {
		this.nodeName = nodeName;
		this.parent = parent;
		visitedNodes = new ArrayList<Integer>();
	}
}
