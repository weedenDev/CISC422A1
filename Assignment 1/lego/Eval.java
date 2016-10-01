// Evaluator for Lego formulas
package lego;

import lego.parser.*;

// data structure to store values of free variables
class Env {
	
}

public class Eval {

	public static boolean eval(Formula f) {
		
		//System.out.println("atomic");

		if (f instanceof Atomic) {
			//System.out.println("atomic");
			// Variables
			int x = 0;
			int y = 0;
			// handle Atomic
			Atomic a = (Atomic) f;
			if (a.e1 instanceof Int) {
				x = Integer.parseInt(a.e1.toString());
			}
			if (a.e2 instanceof Int) {
				y = Integer.parseInt(a.e2.toString());
			}
			if (a.rel_op.toString() == ">") {
				if (x > y)
					return true;
				else
					return false;

			}
			if (a.rel_op.toString() == ">=") {
				if (x >= y)
					return true;
				else
					return false;

			}
			if (a.rel_op.toString() == "=") {
				if (x == y){
					return true;
				}
				else{
					return false;
				}
			}
		}
		
		if (f instanceof Binary) {
			//System.out.println("Binary");
			return binEval(f);
		}
		if (f instanceof Unary) {
			//System.out.println("Unary");
			return unEval(f);
		}else {
			System.out.println("Error 01: Formula given not recognized as Unary, " + "Binary, or Atomic.");
			return false;
		}
		// return eval(f, new Env());
	}

	public static boolean eval(Formula f, Env e) {
		return true;
	}
	public static boolean unEval(Formula f){
		boolean result;
		if (f instanceof Unary){
			Unary u = (Unary) f;
			if(u.f instanceof Unary){
				result = unEval(u.f);
			}
			if(u.f instanceof Binary){
				result = binEval(u.f);
			}
			if (u.f instanceof Atomic){
				result = eval(u.f);
			}
			else{//result isnt initalized bad...
				System.out.println("Error 02:Result didn't have a value in unEval setting it to true so it fails");
				result = true;// will return false (safer assumption)
			}
			return !result;
		}
		
		else{
			//Pretty much unreachable in normal operation
			System.out.println("Error 03: unEval got an equation that was not Binary, Unary, or Atomic");
			return false;
		}
	}
	public static boolean binEval(Formula f) {
		if (f instanceof Binary) {
			boolean result = false;
			boolean result2 = false;
			Binary b = (Binary) f;
			if (b.f1 instanceof Binary) {
				//System.out.println(b.f1.toString());
				result = binEval(b.f1);
			} else {
				//System.out.println(b.f1.toString()+" "+"atomic");
				result = eval(b.f1);
			}

			if (b.f2 instanceof Binary) {
				//System.out.println(b.f2.toString());
				result2 = binEval(b.f2);
			} else { // it will be atomic
				//System.out.println(b.f2.toString()+" "+"atomic");
				result2 = eval(b.f2);
			}
			
			/*
			//For Result Type Error Checking
			System.out.println(result);
			System.out.println(result2);
		 	*/
			
			if (b.bin_conn.toString() == "&&") {
				return result && result2;
			}
			if (b.bin_conn.toString() == "||") {
				return result || result2;
			}
			if (b.bin_conn.toString() == "->") {
				return !(result && (!result2));
			}
			if (b.bin_conn.toString() == "<->") {
				return !(result && !result2) && !(result2 && !result);
			} else {
				return false;
			}
		}
		else{
			//pretty much an unreachable state. in normal operation
			System.out.println("Error 04: There was a Non Binary argument passed to binary eval");
			return false;
		}
	}
}
