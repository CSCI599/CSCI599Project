package com.csci599.project;

public class Condition {
	public static void main(String[] args) {
		int n = 10000;
		int a = 0, b = 1, c = 2, d = 9, e = 10, f = 11, g = 12, h = 13;

		if (b == 3 && c == 4) {
			if (d == 5) {
				if (a == 6) {
					System.out.println("A = 6");
				} if(a == 7){
					System.out.println("A != 6");
				}
			} else {
				if (a == 7) {
					System.out.println("A = 7");
				} else {
					System.out.println("A != 7");
				}
			}
		} else {
			if (e == 8) {
				System.out.println("E = 8");
			} else {
				System.out.println("E != 8");
			}
		}
		
		if (f == 3 && g == 4) {
			if (h == 5) {
				e=n+1*(a/4);
				if (e == 6) {
					System.out.println("E = 6");
				} else {
					System.out.println("E != 6");
					System.out.println("This is the line to execute");
				}
			} else {
				if (e == 7) {
					System.out.println("E = 7");
				} else {
					System.out.println("E != 7");
				}
			}
		} else {
			if (e == 8) {
				System.out.println("E = 8");
			} else {
				System.out.println("E != 8");
			}
		}

		Reporter.reporter();
	}
}
