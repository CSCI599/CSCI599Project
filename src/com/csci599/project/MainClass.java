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
			// LocalVariable[] localVariables = graph.localVariableTable
			// .getLocalVariableTable();

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

		int node = 79;
		
		System.out.println("Total Nodes: " + graphs.get(1).nodes.size());
		ArrayList<InstructionHandle> dependencyList = cfg
				.getDependencyInformation(graphs.get(1), node);
		System.out.println(node + " can be reached by "
				+ graphs.get(1).reachabilityList.get(node).size()
				+ " other nodes");
		System.out.println("Node at position " + node
				+ " depends on the following conditions ");
		System.out.println("Total dependency conditions: "
				+ dependencyList.size());
		for (InstructionHandle dependency : dependencyList) {
			System.out.print("\n" + dependency);
		}
		
		/*
		 * LocalVariable[] localVariables = graphs.get(1).localVariableTable
		 * .getLocalVariableTable(); System.out.println();
		 * System.out.println("Local Variables used in main method: "); for (int
		 * j = 0; j < localVariables.length; j++) { System.out.println("Name: "
		 * + localVariables[j].getName() + " Index: " +
		 * localVariables[j].getIndex() + " Signature: " +
		 * localVariables[j].getSignature() + " Start PC: " +
		 * localVariables[j].getStartPC()); }
		 * 
		 * 
		 * System.out.println("New Analysis");
		 * cfg.getPath(graphs.get(1).nodesMap, node);
		 */
		// System.out.println("Nodes: ");
		// for(Nodes nodes : graphs.get(1).nodes){
		// System.out.println(nodes.nodeName);
		// }
		
		System.out.println();
		System.out.println("Conditions: ");
		ArrayList<DependencyInformation> depList = cfg.dependencyAdapter(
				dependencyList, node, graphs.get(1).localVariableTable,
				graphs.get(1).nodes);

		for (DependencyInformation dep : depList) {
			System.out.println();
			System.out.println("Instruction: " + dep.dependencyNode);
			System.out.println("Must evaluate to : " + dep.true_false);
			System.out.println("Depends on variable: "
					+ dep.varVal.variableName);
			System.out.println("For instruction to be TRUE, "
					+ dep.varVal.variableName + " must be : "
					+ dep.varVal.value);
			System.out.println();
		}

		//System.out.println();
		//System.out.println("==================CONDITION CHECK===================");
		//System.out.println(cfg.checkTargetOnEveryPath(graphs.get(1).edges, 0, 111));
	}
	
	
	

}
