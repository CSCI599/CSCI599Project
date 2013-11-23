package com.csci599.project;

import java.util.ArrayList;

import org.apache.bcel.generic.InstructionHandle;

public class Nodes {
	public InstructionHandle nodeName;
	public ArrayList<Nodes> parents;
	public ArrayList<Nodes> children;

	Nodes(InstructionHandle nodeName) {
		this.nodeName = nodeName;
		this.parents = new ArrayList<Nodes>();
		this.children = new ArrayList<Nodes>();
	}
}
