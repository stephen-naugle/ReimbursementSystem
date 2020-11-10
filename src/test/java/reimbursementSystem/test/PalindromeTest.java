package reimbursementSystem.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PalindromeTest {
	
	public boolean isPalindrome(String inputString) {
	    if (inputString.length() == 0) {
	        return true;
	    } else {
	        char firstChar = inputString.charAt(0);
	        char lastChar = inputString.charAt(inputString.length() - 1);
	        String mid = inputString.substring(1, inputString.length() - 1);
	        return (firstChar == lastChar) && isPalindrome(mid);
	    }
	}	

@Test
public void whenEmptyString_thenAccept() {
    PalindromeTest palindromeTester = new PalindromeTest();
    assertTrue(palindromeTester.isPalindrome(""));
}
}