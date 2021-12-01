package Bertolino.valutaExp;
import java.util.Stack;
import java.util.StringTokenizer;

public class Algoritmo {
	
	protected static String risultato;

	public static String valutaEspressione(StringTokenizer s) {
		Stack<String> operandi = new Stack<>();
		Stack<Character> operatori = new Stack<>();

		while (s.hasMoreTokens()) {
			String token = s.nextToken();
			
			if (Regex.isNumero(token))
				operandi.add(token);
			else if (token.equals("(")) {
				operandi.push(valutaEspressione(s));
			}
			else if (token.equals(")")) {
				while (!operatori.isEmpty()) {
					String o1 = operandi.pop();
					String o2 = operandi.pop();
					char op = operatori.pop();
					operandi.push(calcola(o2,o1,op));
				}
				return operandi.pop();
			}
			else if (s.hasMoreTokens()) { //caso in cui è sicuramente un operatore
				char op1 = token.charAt(0);

				if(operatori.isEmpty())
					operatori.push(op1);
				else {
					char op2 = operatori.pop();
					
					if (prioritario(op1,op2)) {
						operatori.push(op2);
						operatori.push(op1);
					}
					else {
						String o2 = operandi.pop();
						String o1 = operandi.pop();
						operandi.push(calcola(o1,o2,op2));
						
						if (!operatori.isEmpty()) {
							op2 = operatori.pop();
							
							if (!prioritario(op1,op2)) {
								o2 = operandi.pop();
								o1 = operandi.pop();
								operandi.push(calcola(o1,o2,op2));
								operatori.push(op1);
							}
						}
						else
							operatori.push(op1);
					}
				}	

			}
		}
		while (!operatori.isEmpty()) {
			String o1 = operandi.pop();
			String o2 = operandi.pop();
			char op = operatori.pop();
			operandi.push(calcola(o2,o1,op));
		}
		if (operandi.size() == 1)
			risultato = operandi.pop();
		else
			throw new IllegalArgumentException();
		
		return risultato;
	}//valutaEspressione

	
	private static String calcola (String o1, String o2, char op) {
		String ris = "";
		
		if (op == '^')
			ris += (Integer.parseInt(o1)^Integer.parseInt(o2));
		
		else if (op == '*')
			ris += (Integer.parseInt(o1)*Integer.parseInt(o2));
		else if (op == '/')
			ris += (Integer.parseInt(o1)/Integer.parseInt(o2));
		if (op == '%')
			ris += (Integer.parseInt(o1)%Integer.parseInt(o2));
		
		if (op == '+')
			ris += (Integer.parseInt(o1)+Integer.parseInt(o2));
		if (op == '-')
			ris += (Integer.parseInt(o1)-Integer.parseInt(o2));
		return ris;
	}//calcola

	
	private static boolean prioritario(char op1, char op2) {
		if (op1 == op2)
			return false;
		if (op1 == '^')
			return true;
		if (op1 == '+' && op2 == '-')
			return false;
		if (op1 == '*' || op1 == '/' || op1 == '%') {
			if (op2 == '+' || op2 == '-')
				return true;
			else
				return false;
		}
		return false;
	}//prioritario

}//Algoritmo
