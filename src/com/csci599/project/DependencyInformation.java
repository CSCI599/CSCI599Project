package com.csci599.project;

import org.apache.bcel.generic.InstructionHandle;

public class DependencyInformation {
public InstructionHandle dependencyNode;
public boolean if_else; //true means dependent on if part of instruction at dependency node

public DependencyInformation() {
	// TODO Auto-generated constructor stub
}
}
