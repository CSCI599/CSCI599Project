
package com.csci599.project;

import java.util.ArrayList;
import java.util.TreeSet;

import org.apache.bcel.generic.InstructionHandle;

public class Nodes {
	public InstructionHandle nodeName;
	public ArrayList<Nodes> parents;
	public ArrayList<Nodes> children;
	
	//used to compute reaching def. info
	public TreeSet<Definition> in;
	public TreeSet<Definition> out;
	public TreeSet<Definition> gen;
	public TreeSet<Definition> kill;


	Nodes(InstructionHandle nodeName) {
		this.nodeName = nodeName;
		this.parents = new ArrayList<Nodes>();
		this.children = new ArrayList<Nodes>();
		this.in = new TreeSet<Definition>();
		this.out = new TreeSet<Definition>();
		this.gen = new TreeSet<Definition>();
		this.kill = new TreeSet<Definition>();

	}
}
