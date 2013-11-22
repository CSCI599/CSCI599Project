package com.csci599.project;

public class Condition {
	public static void main(String[] args) {
		int a = 0, b = 0, c = 0, d = 1;

		if (b == 0 && c == 0) {
			if (d == 0) {
				if (a == 0) {
					System.out.println("A = 0");
				} else {
					System.out.println("A != 0");
				}
			} else {
				if (a == 4) {
					System.out.println("A = 4");
				} else {
					System.out.println("A != 4");
				}
			}
		} else {
			if (a == 3) {
				System.out.println("A = 3");
			} else {
				System.out.println("A != 3");
			}
		}

		Reporter.reporter();
	}
}
