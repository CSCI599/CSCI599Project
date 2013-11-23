package com.csci599.project;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.generic.InstructionHandle;

public class MainClass {

	public static void main(String[] args) throws IOException {

		CFG cfg = new CFG();
		ArrayList<CFG_Graph> graphs = cfg.cfgMaker("bin/com/csci599/project/",
				"Condition");
		System.out.println("CFG Size: " + graphs.size());
		int i = 1;
		for (CFG_Graph graph : graphs) {
			LocalVariable[] localVariables = graph.localVariableTable
					.getLocalVariableTable();

			System.out.println("Graph " + i + " byte code mapping length: "
					+ graph.byteCode_to_sourceCode_mapping.size());
			i++;
			// cfg.traverseCFG(graph.edges);
			for (ArrayList<InstructionHandle> edge : graph.edges) {
				if (edge.get(0) != null) {
					System.out.print(edge.get(0).getPosition()
							+ " ("
							+ graph.byteCode_to_sourceCode_mapping
									.get((int) edge.get(0).getPosition())
							+ ") ----> ");
				}
				if (edge.get(1) != null) {
					System.out.print(edge.get(1).getPosition()
							+ " ("
							+ graph.byteCode_to_sourceCode_mapping
									.get((int) edge.get(1).getPosition())
							+ ")\n");
				} else {
					System.out.println("EXIT\n");
				}
			}

			System.out.println("Method: " + graph.method.getName());
			System.out
					.println("Code: " + graph.method.getCode().toString(true));
		}

		ArrayList<DependencyInformation> dependencyList = cfg
				.getConditionDependencyForLineNumber(graphs.get(1), "main", 189);
		System.out
				.println("Node at position 189 depends on the following conditions ");
		System.out.println("Total dependency conditions: "
				+ dependencyList.size());
		for (DependencyInformation dependency : dependencyList) {
			System.out.print("\n" + dependency.dependencyNode);
			if (dependency.if_else) {
				System.out.print("\tIf Part");
			} else {
				System.out.print("\tElse Part");
			}
		}

	}
}
