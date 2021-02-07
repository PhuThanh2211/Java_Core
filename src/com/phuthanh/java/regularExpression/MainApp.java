package com.phuthanh.java.regularExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainApp {

	public static void main(String[] args) {
		matchVisaNumberPattern();
		
	}

	private static void basicRegularExpression() {
		String string = "I am a string. Yes, I am";
		System.out.println(string);
		
		String alphanumeric = "abcDeeeF12Ghhiiiijkl99z";
		System.out.println(alphanumeric.replaceAll(".", "Y")); // Match Any Character
		
		System.out.println(alphanumeric.replaceAll("^abcDeee", "YYY"));
		
		String secondString = "abcDeeeF12abcDeeeGhhiiiijkl99z";
		System.out.println(secondString.replaceAll("^abcDeee", "YYY")); // Just match at the start of the String
		
		System.out.println(alphanumeric.matches("^hello")); 					// False
		System.out.println(alphanumeric.matches("^abcDeee"));					// False (Should be entire the String)
		System.out.println(alphanumeric.matches("^abcDeeeF12Ghhiiiijkl99z"));	// True
		
		System.out.println(alphanumeric.replaceAll("ijkl99z$", "THE END")); // Just match at the end of the String
		System.out.println(alphanumeric.replaceAll("[aei]", "X"));
		System.out.println(alphanumeric.replaceAll("[aei][Fj]", "X"));
		
		System.out.println("harry".replaceAll("[Hh]arry", "Harry"));
		
		String newAlphanumeric = "abcDeeeF12Ghhiiiijkl99z";
		System.out.println(newAlphanumeric.replaceAll("[^ej]", "X")); // Replace character that not be 'e' or 'j'
		System.out.println(newAlphanumeric.replaceAll("[abcdef345678]", "X"));
		System.out.println(newAlphanumeric.replaceAll("[a-f3-8]", "X"));
		System.out.println(newAlphanumeric.replaceAll("(?i)[a-f3-8]", "X")); // Ignore case sensitive
		
		System.out.println(newAlphanumeric.replaceAll("[0-9]", "X"));
		System.out.println(newAlphanumeric.replaceAll("\\d", "X")); 	// As same the above statement
		System.out.println(newAlphanumeric.replaceAll("\\D", "X")); 	// Non digit
		
		String hasWhitespace = "I have blanks and \ta tab, and also a newline \n";
		System.out.println(hasWhitespace);
		System.out.println(hasWhitespace.replaceAll("\\s", "")); // Remove all whitespace
		System.out.println(hasWhitespace.replaceAll("\t", "X"));
		
		System.out.println(newAlphanumeric.replaceAll("\\w", "X")); // Replace all characters except whitespace
		System.out.println(hasWhitespace.replaceAll("\\w", "X"));
		
		System.out.println(hasWhitespace.replaceAll("\\b", "X"));
		
		String thirdAlphanumeric = "abcDeeeF12Ghhiiijkl99z";
		System.out.println(thirdAlphanumeric.replaceAll("^abcDe{3}", "YYY")); 	// Match exact 3 character "e"
		System.out.println(thirdAlphanumeric.replaceAll("^abcDe+", "YYY"));		// Match one or more character "e"
		System.out.println(thirdAlphanumeric.replaceAll("^abcDe*", "YYY"));		// Match none or more character "e"
		System.out.println(thirdAlphanumeric.replaceAll("^abcDe{4,6}", "YYY"));	// Match exact 4 to 6 character "e"
		
		
		// Match (one or more h) && (none or more i) && (j) 
		System.out.println(thirdAlphanumeric.replaceAll("h+i*j", "Y"));
	}
	
	private static void matchPattern() {
		
		/*
			1/ Pattern
			[abc]	Find one character from the options between the brackets
			[^abc]	Find one character NOT between the brackets
			[0-9]	Find one character from the range 0 to 9
			
			2/ Metacharacters
			|		Find a match for any one of the patterns separated by | as in: cat|dog|fish
			.		Find just one instance of any character
			^		Finds a match as the beginning of a string as in: ^Hello
			$		Finds a match at the end of the string as in: World$
			\d		Find a digit
			\s		Find a whitespace character
			\b		Find a match at the beginning of a word like this: \bWORD, or at the end of a word like this: WORD\b
			\w		Find a word character
			
			
			
			\\uxxxx	Find the Unicode character specified by the hexadecimal number xxxx
			
			3/ Quantifiers
			n+		Matches any string that contains at least one n
			n*		Matches any string that contains zero or more occurrences of n
			n?		Matches any string that contains zero or one occurrences of n
			n{x}	Matches any string that contains a sequence of X n's
			n{x,y}	Matches any string that contains a sequence of X to Y n's
			n{x,}	Matches any string that contains a sequence of at least X n's
			
			4/
			[] denotes a character class. 
			
			() denotes a capturing group.

			[a-z0-9] -- One character that is in the range of a-z OR 0-9
			
			(a-z0-9) -- Explicit capture of a-z0-9. No ranges. String "a-z0-9" will match
		
		*/
		StringBuilder htmlText = new StringBuilder();
		htmlText.append("<h1>My Heading</h1>");
		htmlText.append("<h2>Sub-heading</h2>");
		htmlText.append("<p>This is a paragraph about something.</p>");
		htmlText.append("<p>This is another paragraph about something else.</p>");
		htmlText.append("<h2>Summary</h2>");
		htmlText.append("<p>Here is the summary.</p>");
		
		String h2Pattern = ".*<h2>.*";
		Pattern pattern = Pattern.compile(h2Pattern);
		Matcher matcher = pattern.matcher(htmlText);
		System.out.println(matcher.matches());
		
		matcher.reset();
		int count = 0;
		while (matcher.find()) {
			count++;
			System.out.println("Occurrence " + count + " : " + matcher.start() + " to " + matcher.end());
		}
		
		String h2GroupPattern = "(<h2>.*?</h2>)";
		Pattern groupPattern = Pattern.compile(h2GroupPattern);
		Matcher groupMatcher = groupPattern.matcher(htmlText);
		System.out.println(groupMatcher.matches());
		
		groupMatcher.reset();
		while (groupMatcher.find()) {
			System.out.println("Occurrence: " + groupMatcher.group(1));
		}
		
		System.out.println("harry".replaceAll("[H|h]arry", "Larry"));
		System.out.println("Harry".replaceAll("[H|h]arry", "Larry"));
		
		String tvTest = "tstvtkt";
		String tNotVRegExp = "t[^v]";
		String tNotV2CharRegExp = "t(?!v)";
		Pattern tNotVPattern = Pattern.compile(tNotVRegExp);
		Matcher tNotVMatcher = tNotVPattern.matcher(tvTest);
		
		int count2 = 0;
		while (tNotVMatcher.find()) {
			count2++;
			System.out.println("Occurrence " + count2 + ": " + tNotVMatcher.start() + " to " + tNotVMatcher.end());
		}
	}
	
	private static void matchPhoneNumberPattern() {
		String phoneRegExp = "^([\\(]{1}[0-9]{3}[\\)]{1}[ ]{1}[0-9]{3}[\\-]{1}[0-9]{4})$";
		
		String phone1 = "123456789";		// Shouldn't match
		String phone2 = "(123) 456-7890";	// Match
		String phone3 = "123 456-7890";		// Shouldn't match
		String phone4 = "(123)456-7890";	// Shouldn't match
		
		System.out.println("Phone 1 = " + phone1.matches(phoneRegExp));
		System.out.println("Phone 2 = " + phone2.matches(phoneRegExp));
		System.out.println("Phone 3 = " + phone3.matches(phoneRegExp));
		System.out.println("Phone 4 = " + phone4.matches(phoneRegExp));
	}
	
	private static void matchVisaNumberPattern() {
		String visaReqExp = "^4[0-9]{12}([0-9]{3})?$";
		
		String visa1 = "4444444444444";			// Match
		String visa2 = "5444444444444";			// Shouldn't match
		String visa3 = "4444444444444444";		// Match
		String visa4 = "4444";					// Shouldn't match
		String visa5 = "4444444444444567";		// Match
		String visa6 = "4444444444444567890";	// Shouldn't match
		
		System.out.println("Visa 1 = " + visa1.matches(visaReqExp));
		System.out.println("Visa 2 = " + visa2.matches(visaReqExp));
		System.out.println("Visa 3 = " + visa3.matches(visaReqExp));
		System.out.println("Visa 4 = " + visa4.matches(visaReqExp));
		System.out.println("Visa 5 = " + visa5.matches(visaReqExp));
		System.out.println("Visa 6 = " + visa6.matches(visaReqExp));
	}
}












