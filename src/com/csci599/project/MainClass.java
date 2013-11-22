package com.csci599.project;

import java.io.IOException;
import java.util.ArrayList;

public class MainClass {
	public static void main(String[] args) throws IOException {
		CFG cfg = new CFG();
		ArrayList<CFG_Graph> graphs = cfg.cfgMaker("Example/", "Condition");
		System.out.println("CFG Size: " + graphs.size());
		int i = 1;
		for (CFG_Graph graph : graphs) {
			System.out.println("Graph " + i);
			i++;
			cfg.traverseCFG(graph.edges);
		}
	}
}
