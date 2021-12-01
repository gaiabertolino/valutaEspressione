package Bertolino.valutaExp;

public class Regex {

	private static String ESPRESSIONE = "(\\(?(\\d*([\\-\\+\\*\\%\\/\\^]{1}\\d*)*)+\\)?)*";
	
	protected static boolean corretta(String s) {
		try {
			if (s.substring(s.length()-1).matches(("[\\+\\-\\*\\+\\/\\%]")))
				return false;
			int parentesi = 0;
			for (int i=0; i<s.length(); i++) {
				if (s.charAt(i) == '(')
					parentesi++;
				if (s.charAt(i) == ')')
					parentesi--;
			}
			if (parentesi != 0) 
				return false;
			if (s.matches(ESPRESSIONE))
				return true;
			return false;
		}
		catch (Exception e) { return false; }
	}//corretta
	
	protected static boolean isNumero(String s) {
		if (s.matches("\\d+"))
			return true;
		return false;
	}//isNumero
}//Regex
