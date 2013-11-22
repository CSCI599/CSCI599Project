package com.csci599.project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.TreeMap;


import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.BranchInstruction;
import org.apache.bcel.generic.IfInstruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.RETURN;
import org.apache.bcel.generic.Select;

public class CFG {
	protected static final String header = "digraph control_flow_graph {\n\n\tnode [shape = rectangle]; entry exit;\n\tnode [shape = circle];\n\n";
	protected static final String footer = "\n}";
	protected InstructionList instList;

	public CFG(InstructionList instructions) {
		instList = instructions;
	}

	public CFG() {

	}

	public ArrayList<Nodes> getChildren(Nodes node,
			ArrayList<ArrayList<InstructionHandle>> edges) {
		InstructionHandle nodeName = node.nodeName;
		ArrayList<Nodes> children = new ArrayList<Nodes>();

		for (int i = 0; i < edges.size(); i++) {
			ArrayList<InstructionHandle> edge = edges.get(i);
			
			if (edge.get(0).equals(nodeName)) {
				Nodes newNode = new Nodes(edge.get(1), nodeName);
				children.add(newNode);
			}

		}

		return children;
	}

	public ArrayList<CFG_Graph> cfgMaker(String dir, String inputClassFilename)
			throws IOException {

		SortedMap<Integer, InstructionHandle> g_statements = new TreeMap<Integer, InstructionHandle>();
		String path = dir + inputClassFilename + ".class";
		// System.out.println("Parsing " + path + ".");

		JavaClass cls = null;
		try {
			cls = (new ClassParser(path)).parse();
		} catch (IOException e) {
			// e.printStackTrace(debug);
			System.out.println("Error while parsing " + inputClassFilename
					+ ".");
			System.exit(1);
		}

		// Search for main method.
		// System.out.println("Searching for entry method:");
		Method mainMethod = null;
		ArrayList<CFG_Graph> cfg_graphList = new ArrayList<CFG_Graph>();

		for (Method m : cls.getMethods()) {
			// if ("_jspService".equals(m.getName())) {
			mainMethod = m;
			// break;
			// }
			// }
			if (mainMethod == null) {
				System.out.println("No entry method found in "
						+ inputClassFilename + ".");
				break;// System.exit(1);
			}

			// Create CFG.
			// System.out.println("Creating CFG object.");
		
			// System.out.println("CODE: \n\n");

			String code = mainMethod.getCode().toString();

			InstructionList instList = new InstructionList(mainMethod.getCode()
					.getCode());

			// byte[] byteCode = mainMethod.getCode().getCode();
			// Instruction[] inst = instList.getInstructions();
			// System.out.println("Number of instructions: "+inst.length);
			// System.out.println(code);

			ArrayList<String> statements = getArrayListFromCode(code);
			ArrayList<Integer> lineNumbers = new ArrayList<Integer>();
			for (int i = 1; i < statements.size(); i++) {

				String line = statements.get(i);
				String[] parts = line.split("\t");
				String[] subPart = parts[0].split(":");
				// System.out.println("Adding "+subPart[0]);
				try {
					if (!subPart[0].equalsIgnoreCase("")) {
						lineNumbers.add(Integer.parseInt(subPart[0]));
					}
				} catch (NumberFormatException ex) {
					// System.out.println(subPart[0] + " is not a number");
				}
			}

			for (int i = 0; i < instList.size(); i++) {
				InstructionHandle handle = instList.findHandle(lineNumbers
						.get(i));
				Integer lineNum = handle.getPosition();

				g_statements.put(lineNum, handle);

			}

			// Map<InstructionHandle, InstructionHandle> internalCFG = new
			// TreeMap<InstructionHandle, InstructionHandle>();
			FileWriter fwr = new FileWriter(dir + "/Dotty/"
					+ inputClassFilename + "-" + mainMethod.getName()
					+ ".dotty", false);
			fwr.write(header);
			// ArrayList<InstructionHandle> edge = new
			// ArrayList<InstructionHandle>();
			ArrayList<ArrayList<InstructionHandle>> graph = new ArrayList<ArrayList<InstructionHandle>>();
			ArrayList<InstructionHandle> nodes = new ArrayList<InstructionHandle>();
			// System.out.println("entry -> 0;");
			fwr.write("\n" + "entry -> 0;");

			for (SortedMap.Entry<Integer, InstructionHandle> entry : g_statements
					.entrySet()) {
				nodes.add(entry.getValue());
				if (entry.getValue().getNext() != null) {
					nodes.add(entry.getValue().getNext());
				}
				if (entry.getValue().getNext() != null
						&& !(entry.getValue().getInstruction() instanceof RETURN)) {
					if (entry.getValue().getInstruction() instanceof BranchInstruction) {
						BranchInstruction branchInstruction = (BranchInstruction) entry
								.getValue().getInstruction();

						// System.out.println(entry.getValue().getPosition() +
						// " -> " +
						// ifInstruction.getTarget().getPosition()+";");
						fwr.write("\n" + entry.getValue().getPosition()
								+ " -> "
								+ branchInstruction.getTarget().getPosition()
								+ ";");
						graph.add(new ArrayList<InstructionHandle>(Arrays
								.asList(entry.getValue(),
										branchInstruction.getTarget())));
						// internalCFG.put(entry.getValue(),
						// ifInstruction.getTarget());
						if (branchInstruction instanceof IfInstruction) {
							// System.out.println(entry.getValue().getPosition()
							// +
							// " -> " + entry.getValue().getNext().getPosition()
							// +
							// ";");
							fwr.write("\n" + entry.getValue().getPosition()
									+ " -> "
									+ entry.getValue().getNext().getPosition()
									+ ";");
							// internalCFG.put(entry.getValue(),
							// entry.getValue().getNext());

							graph.add(new ArrayList<InstructionHandle>(Arrays
									.asList(entry.getValue(), entry.getValue()
											.getNext())));
						} else if (branchInstruction instanceof Select) {

							Select selectInst = (Select) branchInstruction;
							InstructionHandle[] instHandleList = selectInst
									.getTargets();

							for (int i = 0; i < instHandleList.length; i++) {
								fwr.write("\n" + entry.getValue().getPosition()
										+ " -> "
										+ instHandleList[i].getPosition() + ";");
								// internalCFG.put(entry.getValue(),
								// entry.getValue().getNext());

								graph.add(new ArrayList<InstructionHandle>(
										Arrays.asList(entry.getValue(),
												instHandleList[i])));
							}

						}
					} else {
						// System.out.println(entry.getValue().getPosition()+
						// " -> "+entry.getValue().getNext().getPosition()+";");
						fwr.write("\n" + entry.getValue().getPosition()
								+ " -> "
								+ entry.getValue().getNext().getPosition()
								+ ";");

						InstructionHandle han = entry.getValue();
						InstructionHandle han2 = entry.getValue();

						// System.out.println(han.getClass());
						// internalCFG.put(han, han2);

						graph.add(new ArrayList<InstructionHandle>(Arrays
								.asList(entry.getValue(), entry.getValue()
										.getNext())));

					}
				} else {
					// System.out.println(entry.getValue().getPosition()+
					// " -> exit;");
					fwr.write("\n" + entry.getValue().getPosition()
							+ " -> exit;");
					// internalCFG.put(entry.getValue(), null);

					graph.add(new ArrayList<InstructionHandle>(Arrays.asList(
							entry.getValue(), null)));

				}
			}
			fwr.write(footer);

			fwr.close();

			// remove duplicate nodes
			// System.out.println("Size before removing duplicates: " +
			// nodes.size());
			HashSet<InstructionHandle> hs = new HashSet<InstructionHandle>();
			hs.addAll(nodes);
			nodes.clear();
			nodes.addAll(hs);

			CFG_Graph cfg_graph = new CFG_Graph();
			cfg_graph.edges = graph;
			nodes = sortNodeList(nodes);
			cfg_graph.nodes = nodes;
			cfg_graph.servletName = inputClassFilename;
			cfg_graph.method = mainMethod;

			SortedMap<Integer, Integer> byteCode_to_sourceCode_mapping = new TreeMap<Integer, Integer>();
			LineNumberTable table = mainMethod.getCode().getLineNumberTable();

			cfg_graph.lineNumberTable = table;
			for (InstructionHandle node : nodes) {
				// System.out.println("table Size: " + table.getLength());
				// System.out.println(table.getSourceLine(2));

				// System.out.println("Position " + node
				// + " corresponds to line number "
				// + table.getSourceLine(7));
				try {
					byteCode_to_sourceCode_mapping.put(node.getPosition(),
							table.getSourceLine(node.getPosition()));
				} catch (Exception ex) {

				}
			}
			cfg_graph.byteCode_to_sourceCode_mapping = byteCode_to_sourceCode_mapping;
			cfg_graphList.add(cfg_graph);

		}
		return cfg_graphList;

	}

	public static ArrayList<String> getArrayListFromCode(String line) {
		ArrayList<String> statements = new ArrayList<String>();
		String temp = "";
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == '\n') {
				if (temp.equalsIgnoreCase("Attribute(s) = ")) {
					break;
				}
				statements.add(temp);
				temp = "";
			} else {
				temp += line.charAt(i);
			}
		}
		return statements;
	}

	public ArrayList<InstructionHandle> sortNodeList(
			ArrayList<InstructionHandle> nodes) {

		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 1; j < (nodes.size()); j++) {
				if (nodes.get(j - 1).getPosition() >= nodes.get(j)
						.getPosition()) {

					InstructionHandle temp = nodes.get(j - 1);
					nodes.remove(j - 1);
					nodes.add(j, temp);
				}

			}
		}

		return nodes;

	}

	public void traverseCFG(ArrayList<ArrayList<InstructionHandle>> internalCFG) {
		for (int i = 0; i < internalCFG.size(); i++) {
			ArrayList<InstructionHandle> edge = internalCFG.get(i);
			System.out.println(edge.get(0) + " ----> " + edge.get(1));
		}
	}
}
