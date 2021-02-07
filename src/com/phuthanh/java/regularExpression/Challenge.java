package com.phuthanh.java.regularExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Challenge {
	
	private static final String challenge1 = "I want a bike.";
	private static final String challenge2 = "Replace all blanks with underscores.";
	private static final String challenge3 = "aaabccccccccdddefffg";
	private static final String challenge4 = "abcd.135uvqz.7tzik.999";
	private static final String challenge5 = "abcd.135\tuvqz.7\ttzik.999\n";
	private static final String challenge6 = "{0, 2}, {0, 5}, {1, 3}, {2, 4}";
	
	public static void main(String[] args) {
		sixthChallenge();
	}
	
	private static void firstChallenge() {
		
		
		String resultRegExp1 = "^I[ ]{1}want[ ]{1}a[ ]{1}bike.$";
		System.out.println("Result = " + challenge1.matches(resultRegExp1));
		
		String resultRegExp2 = "I want a \\w+.";
		System.out.println("Result = " + challenge1.matches(resultRegExp2));
	}
	
	private static void secondChallenge() {
		String regExp1 = "^[A-Z]{1}[ ]{1}[a-z]{4}[ ]{1}[a-z]{1}[ ]{1}[a-z]{4}.$";
		
		Pattern pattern = Pattern.compile(regExp1);
		Matcher matcher = pattern.matcher(challenge1);
		System.out.println(matcher.matches());
		
		
		/*
			The group with number 0 is always the whole regular expression. 
			To get access to a group marked by parentheses you should start with group numbers 1
		*/
		String regExpDigit = "[A-Za-z]+\\.(\\d+)";
		Pattern groupPattern = Pattern.compile(regExpDigit);
		Matcher groupMatcher = groupPattern.matcher(challenge4);
		while (groupMatcher.find()) {
			System.out.println("Occurrence: " + groupMatcher.group(1));
		}
		
	}
	
	private static void thirdChallenge() {
		String result = challenge2.replaceAll("\\s", "_");
		System.out.println(result);
		
		String regExp1 = "^a{3}b{1}c{8}d{3}e{1}f{3}g{1}$";
		String regExp2 = "[a-g]+";
		System.out.println(challenge3.matches(regExp1));
		
		String regExpDigit1 = "^[a-z]+\\.\\d+$";
		System.out.println("abcd.135".matches(regExpDigit1));
		System.out.println("kijsl.22".matches(regExpDigit1));
		System.out.println("f5.12a".matches(regExpDigit1));
	}
	
	private static void fourthChallenge() {
		String regExp1 = "[A-Za-z]+\\.(\\d+)[\t\n]+";
		String regExp2 = "[A-Za-z]+\\.(\\d+)\\s";
		
		Pattern groupPattern = Pattern.compile(regExp1);
		Matcher matcher = groupPattern.matcher(challenge5);
		
		while (matcher.find()) {
			System.out.println("Occurrence: " + matcher.group(1));
			System.out.println("Also " + matcher.start(1) + " to " + (matcher.end(1) -1));
		}
	}
	
	private static void fifthChallenge() {
		String regExp1 = "\\{(\\d+, \\d+)\\}";
		Pattern groupPattern = Pattern.compile(regExp1);
		Matcher matcher = groupPattern.matcher(challenge6);
		
		while (matcher.find()) {
			System.out.println("Occurrence: " + matcher.group(1));
		}
	}
	
	private static void sixthChallenge() {
		String test1 = "11111";
		String regExp1 = "^[0-9]{5}$";
		String regExp11 = "^\\d{5}$";
		System.out.println(test1.matches(regExp1));
		
		String test2 = "11111-1111";
		String regExp2 = "^[0-9]{5}[\\-]{1}[0-9]{4}$";
		System.out.println(test2.matches(regExp2));
	}
}











