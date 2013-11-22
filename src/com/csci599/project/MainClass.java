package com.csci599.project;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.bcel.generic.InstructionHandle;

public class MainClass {
	public static void main(String[] args) throws IOException {
		CFG cfg = new CFG();
		ArrayList<CFG_Graph> graphs = cfg.cfgMaker("Example/", "Condition");
		System.out.println("CFG Size: " + graphs.size());
		int i = 1;
		for (CFG_Graph graph : graphs) {
			System.out.println("Graph " + i + " byte code mapping length: "
					+ graph.byteCode_to_sourceCode_mapping.size());
			i++;
			// cfg.traverseCFG(graph.edges);
			for (ArrayList<InstructionHandle> edge : graph.edges) {
				if (edge.get(0) != null) {
					System.out.print(edge.get(0).getPosition()+" ("+graph.byteCode_to_sourceCode_mapping
							.get((int) edge.get(0).getPosition()) + ") ----> ");
				}
				if (edge.get(1) != null) {
					System.out.print(edge.get(1).getPosition()+" ("+graph.byteCode_to_sourceCode_mapping
							.get((int) edge.get(1).getPosition()) + ")\n");
				} else {
					System.out.println("EXIT\n");
				}
			}

			System.out.println("Method: "+graph.method.getName());
			System.out.println("Code: "+graph.method.getCode().toString(true));
		}
	}
}
