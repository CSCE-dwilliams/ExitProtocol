package com.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Comprehensive test suite for the CipherChallenge class.
 * Tests cover encryption logic, answer validation, edge cases, and potential bugs.
 */
public class CipherChallengeTest {

    private CipherChallenge basicChallenge;
    private CipherChallenge challengeWithHints;
    private ArrayList<String> hints;

    @Before
    public void setUp() {
        hints = new ArrayList<>();
        hints.add("Hint 1: Think about leadership");
        hints.add("Hint 2: Someone who sees the future");

        basicChallenge = new CipherChallenge("hello", 3, new ArrayList<>(), null);
        challengeWithHints = new CipherChallenge("visionary", 3, hints, null);
    }

    // ==================== Constructor Tests ====================

    @Test
    public void testConstructorBasic() {
        assertNotNull("CipherChallenge should be created successfully", basicChallenge);
    }

    @Test
    public void testConstructorWithHints() {
        assertNotNull("CipherChallenge with hints should be created successfully", challengeWithHints);
    }

    @Test
    public void testConstructorWithNullHints() {
        CipherChallenge challenge = new CipherChallenge("test", 1, null, null);
        assertNotNull("CipherChallenge should handle null hints", challenge);
    }

    @Test
    public void testConstructorWithEmptyString() {
        CipherChallenge challenge = new CipherChallenge("", 3, new ArrayList<>(), null);
        assertNotNull("CipherChallenge should handle empty string", challenge);
    }

    // ==================== isCorrect() Tests ====================

    @Test
    public void testIsCorrectWithCorrectAnswer() {
        assertTrue("Should return true for correct answer", basicChallenge.isCorrect("hello"));
    }

    @Test
    public void testIsCorrectWithIncorrectAnswer() {
        assertFalse("Should return false for incorrect answer", basicChallenge.isCorrect("world"));
    }

    @Test
    public void testIsCorrectWithEncryptedAnswer() {
        // "hello" with shift 3 becomes "khoor"
        assertFalse("Should return false when given encrypted answer instead of plain text",
                    basicChallenge.isCorrect("khoor"));
    }

    @Test
    public void testIsCorrectCaseSensitive() {
        assertFalse("Should be case sensitive - 'Hello' != 'hello'",
                    basicChallenge.isCorrect("Hello"));
    }

    @Test
    public void testIsCorrectWithWhitespace() {
        assertFalse("Should not trim whitespace", basicChallenge.isCorrect(" hello "));
    }

    @Test
    public void testIsCorrectWithEmptyString() {
        CipherChallenge emptyChallenge = new CipherChallenge("", 3, new ArrayList<>(), null);
        assertTrue("Empty answer should match empty challenge", emptyChallenge.isCorrect(""));
    }

    @Test
    public void testIsCorrectWithNull() {
        assertFalse("Should return false for null answer", basicChallenge.isCorrect(null));
    }

    @Test
    public void testIsCorrectWithSpecialCharacters() {
        CipherChallenge specialChallenge = new CipherChallenge("hello!", 3, new ArrayList<>(), null);
        assertTrue("Should correctly match answer with special characters",
                   specialChallenge.isCorrect("hello!"));
    }

    @Test
    public void testIsCorrectWithNumbers() {
        CipherChallenge numberChallenge = new CipherChallenge("test123", 3, new ArrayList<>(), null);
        assertTrue("Should correctly match answer with numbers",
                   numberChallenge.isCorrect("test123"));
    }

    @Test
    public void testIsCorrectWithMixedCase() {
        CipherChallenge mixedChallenge = new CipherChallenge("HeLLo", 3, new ArrayList<>(), null);
        assertTrue("Should correctly match mixed case answer",
                   mixedChallenge.isCorrect("HeLLo"));
    }

    // ==================== Encryption Logic Tests ====================

    @Test
    public void testEncryptionBasicShift() {
        // "hello" with shift 3 should become "khoor"
        String encrypted = basicChallenge.toString();
        assertTrue("Encrypted text should contain 'khoor'", encrypted.contains("khoor"));
    }

    @Test
    public void testEncryptionPreservesCase() {
        CipherChallenge upperChallenge = new CipherChallenge("HELLO", 3, new ArrayList<>(), null);
        String encrypted = upperChallenge.toString();
        assertTrue("Uppercase should remain uppercase in encryption", encrypted.contains("KHOOR"));
    }

    @Test
    public void testEncryptionMixedCase() {
        CipherChallenge mixedChallenge = new CipherChallenge("HeLLo", 3, new ArrayList<>(), null);
        String encrypted = mixedChallenge.toString();
        assertTrue("Mixed case should be preserved", encrypted.contains("KhOOr"));
    }

    @Test
    public void testEncryptionWithSpaces() {
        CipherChallenge spaceChallenge = new CipherChallenge("hello world", 3, new ArrayList<>(), null);
        String encrypted = spaceChallenge.toString();
        assertTrue("Spaces should be preserved", encrypted.contains("khoor zruog"));
    }

    @Test
    public void testEncryptionWithPunctuation() {
        CipherChallenge punctChallenge = new CipherChallenge("hello, world!", 3, new ArrayList<>(), null);
        String encrypted = punctChallenge.toString();
        assertTrue("Punctuation should be preserved", encrypted.contains("khoor, zruog!"));
    }

    @Test
    public void testEncryptionWithNumbers() {
        CipherChallenge numberChallenge = new CipherChallenge("abc123xyz", 3, new ArrayList<>(), null);
        String encrypted = numberChallenge.toString();
        assertTrue("Numbers should not be shifted", encrypted.contains("def123abc"));
    }

    @Test
    public void testEncryptionWrapAroundLowercase() {
        // "xyz" with shift 3 should wrap to "abc"
        CipherChallenge wrapChallenge = new CipherChallenge("xyz", 3, new ArrayList<>(), null);
        String encrypted = wrapChallenge.toString();
        assertTrue("Should wrap around alphabet (xyz -> abc)", encrypted.contains("abc"));
    }

    @Test
    public void testEncryptionWrapAroundUppercase() {
        // "XYZ" with shift 3 should wrap to "ABC"
        CipherChallenge wrapChallenge = new CipherChallenge("XYZ", 3, new ArrayList<>(), null);
        String encrypted = wrapChallenge.toString();
        assertTrue("Should wrap around alphabet (XYZ -> ABC)", encrypted.contains("ABC"));
    }

    @Test
    public void testEncryptionZeroShift() {
        CipherChallenge zeroShift = new CipherChallenge("hello", 0, new ArrayList<>(), null);
        String encrypted = zeroShift.toString();
        assertTrue("Zero shift should leave text unchanged", encrypted.contains("hello"));
    }

    @Test
    public void testEncryptionLargeShift() {
        // Shift of 26 should be equivalent to shift of 0
        CipherChallenge largeShift = new CipherChallenge("hello", 26, new ArrayList<>(), null);
        String encrypted = largeShift.toString();
        assertTrue("Shift of 26 should wrap around", encrypted.contains("hello"));
    }

    @Test
    public void testEncryptionShift52() {
        // Shift of 52 (2 full rotations) should equal shift of 0
        CipherChallenge largeShift = new CipherChallenge("hello", 52, new ArrayList<>(), null);
        String encrypted = largeShift.toString();
        assertTrue("Shift of 52 should wrap around to original", encrypted.contains("hello"));
    }

    @Test
    public void testEncryptionNegativeShift() {
        // Negative shift should work (shift -3 is same as shift 23)
        CipherChallenge negativeShift = new CipherChallenge("hello", -3, new ArrayList<>(), null);
        String encrypted = negativeShift.toString();
        // "hello" with shift -3 should become "ebiil" (h->e, e->b, l->i, l->i, o->l)
        assertTrue("Negative shift should work correctly", encrypted.contains("ebiil"));
    }

    @Test
    public void testEncryptionShift1() {
        // Simple shift by 1: a->b, b->c, etc.
        CipherChallenge shift1 = new CipherChallenge("abc", 1, new ArrayList<>(), null);
        String encrypted = shift1.toString();
        assertTrue("Shift by 1 should work", encrypted.contains("bcd"));
    }

    @Test
    public void testEncryptionShift25() {
        // Shift by 25 (almost full rotation): a->z, b->a, etc.
        CipherChallenge shift25 = new CipherChallenge("abc", 25, new ArrayList<>(), null);
        String encrypted = shift25.toString();
        assertTrue("Shift by 25 should work", encrypted.contains("zab"));
    }

    @Test
    public void testEncryptionEmptyString() {
        CipherChallenge emptyChallenge = new CipherChallenge("", 3, new ArrayList<>(), null);
        String output = emptyChallenge.toString();
        assertNotNull("toString should not return null for empty string", output);
    }

    @Test
    public void testEncryptionOnlySpaces() {
        CipherChallenge spaceChallenge = new CipherChallenge("   ", 3, new ArrayList<>(), null);
        String encrypted = spaceChallenge.toString();
        assertTrue("Spaces only should remain spaces", encrypted.contains("   "));
    }

    @Test
    public void testEncryptionOnlyPunctuation() {
        CipherChallenge punctChallenge = new CipherChallenge("!@#$%", 3, new ArrayList<>(), null);
        String encrypted = punctChallenge.toString();
        assertTrue("Punctuation should not be encrypted", encrypted.contains("!@#$%"));
    }

    @Test
    public void testEncryptionOnlyNumbers() {
        CipherChallenge numberChallenge = new CipherChallenge("12345", 3, new ArrayList<>(), null);
        String encrypted = numberChallenge.toString();
        assertTrue("Numbers should not be encrypted", encrypted.contains("12345"));
    }

    @Test
    public void testEncryptionSingleLetter() {
        CipherChallenge singleChallenge = new CipherChallenge("a", 1, new ArrayList<>(), null);
        String encrypted = singleChallenge.toString();
        assertTrue("Single letter 'a' with shift 1 should become 'b'", encrypted.contains("b"));
    }

    @Test
    public void testEncryptionAllLetters() {
        // Test full alphabet
        CipherChallenge alphabetChallenge = new CipherChallenge("abcdefghijklmnopqrstuvwxyz", 1,
                                                                new ArrayList<>(), null);
        String encrypted = alphabetChallenge.toString();
        assertTrue("Full alphabet shifted by 1",
                   encrypted.contains("bcdefghijklmnopqrstuvwxyza"));
    }

    // ==================== toString() Tests ====================

    @Test
    public void testToStringFormat() {
        String output = basicChallenge.toString();
        assertTrue("toString should contain instruction text",
                   output.contains("Reverse-engineer this encrypted phrase:"));
    }

    @Test
    public void testToStringNotNull() {
        assertNotNull("toString should never return null", basicChallenge.toString());
    }

    @Test
    public void testToStringContainsEncryptedText() {
        String output = challengeWithHints.toString();
        // "visionary" with shift 3 becomes "ylvlrqdub"
        assertTrue("toString should contain encrypted answer", output.contains("ylvlrqdub"));
    }

    @Test
    public void testToStringDoesNotContainPlaintext() {
        String output = challengeWithHints.toString();
        assertFalse("toString should not reveal the plaintext answer",
                    output.toLowerCase().contains("visionary"));
    }

    // ==================== Edge Cases and Boundary Tests ====================

    @Test
    public void testVeryLongString() {
        StringBuilder longString = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longString.append("test");
        }
        CipherChallenge longChallenge = new CipherChallenge(longString.toString(), 3,
                                                            new ArrayList<>(), null);
        assertNotNull("Should handle very long strings", longChallenge);
        assertTrue("Should correctly identify long answer",
                   longChallenge.isCorrect(longString.toString()));
    }

    @Test
    public void testUnicodeCharacters() {
        // Test with unicode characters that aren't standard English letters
        CipherChallenge unicodeChallenge = new CipherChallenge("café", 3, new ArrayList<>(), null);
        assertNotNull("Should handle unicode characters", unicodeChallenge);
        assertTrue("Should correctly match unicode answer", unicodeChallenge.isCorrect("café"));
    }

    @Test
    public void testNewlineCharacters() {
        CipherChallenge newlineChallenge = new CipherChallenge("hello\nworld", 3,
                                                               new ArrayList<>(), null);
        assertTrue("Should preserve newlines in answer",
                   newlineChallenge.isCorrect("hello\nworld"));
    }

    @Test
    public void testTabCharacters() {
        CipherChallenge tabChallenge = new CipherChallenge("hello\tworld", 3,
                                                           new ArrayList<>(), null);
        assertTrue("Should preserve tabs in answer", tabChallenge.isCorrect("hello\tworld"));
    }

    @Test
    public void testMultipleSpaces() {
        CipherChallenge multiSpaceChallenge = new CipherChallenge("hello    world", 3,
                                                                  new ArrayList<>(), null);
        assertTrue("Should preserve multiple spaces",
                   multiSpaceChallenge.isCorrect("hello    world"));
    }

    // ==================== Consistency Tests ====================

    @Test
    public void testEncryptionConsistency() {
        // Calling toString multiple times should return the same result
        String first = basicChallenge.toString();
        String second = basicChallenge.toString();
        assertEquals("toString should return consistent results", first, second);
    }

    @Test
    public void testIsCorrectConsistency() {
        // Calling isCorrect multiple times with same answer should return same result
        boolean first = basicChallenge.isCorrect("hello");
        boolean second = basicChallenge.isCorrect("hello");
        assertEquals("isCorrect should return consistent results", first, second);
    }

    @Test
    public void testDifferentInstancesSameParameters() {
        CipherChallenge challenge1 = new CipherChallenge("test", 5, new ArrayList<>(), null);
        CipherChallenge challenge2 = new CipherChallenge("test", 5, new ArrayList<>(), null);

        assertEquals("Same parameters should produce same encryption",
                     challenge1.toString(), challenge2.toString());
    }

    // ==================== Integration Tests ====================

    @Test
    public void testCompleteWorkflow() {
        // Create challenge
        CipherChallenge challenge = new CipherChallenge("secret", 7, new ArrayList<>(), null);

        // Get encrypted version
        String encrypted = challenge.toString();
        assertNotNull("Encrypted output should not be null", encrypted);

        // Verify correct answer works
        assertTrue("Correct answer should be accepted", challenge.isCorrect("secret"));

        // Verify incorrect answer fails
        assertFalse("Incorrect answer should be rejected", challenge.isCorrect("wrong"));
    }

    @Test
    public void testCaesarCipherExample() {
        // Classic Caesar cipher with shift 3
        CipherChallenge caesar = new CipherChallenge("CAESAR", 3, new ArrayList<>(), null);

        // CAESAR with shift 3 becomes FDHVDU
        String encrypted = caesar.toString();
        assertTrue("Caesar cipher encryption should work", encrypted.contains("FDHVDU"));

        assertTrue("Should accept correct answer", caesar.isCorrect("CAESAR"));
        assertFalse("Should reject encrypted version as answer", caesar.isCorrect("FDHVDU"));
    }

    @Test
    public void testAlphabetBoundaries() {
        // Test letters at alphabet boundaries
        CipherChallenge boundaryChallenge = new CipherChallenge("az", 1, new ArrayList<>(), null);
        String encrypted = boundaryChallenge.toString();

        // 'a' with shift 1 -> 'b', 'z' with shift 1 -> 'a' (wrap around)
        assertTrue("Boundary shift should work", encrypted.contains("ba"));
    }

    @Test
    public void testReverseShift() {
        // Test that shift and reverse shift are complementary
        CipherChallenge forward = new CipherChallenge("hello", 3, new ArrayList<>(), null);
        CipherChallenge backward = new CipherChallenge("hello", -3, new ArrayList<>(), null);

        String forwardEncrypted = forward.toString();
        String backwardEncrypted = backward.toString();

        assertFalse("Forward and backward shifts should produce different results",
                    forwardEncrypted.equals(backwardEncrypted));
    }

    @Test
    public void testShiftEquivalence() {
        // shift 3 and shift 29 should be equivalent (29 = 3 + 26)
        CipherChallenge shift3 = new CipherChallenge("test", 3, new ArrayList<>(), null);
        CipherChallenge shift29 = new CipherChallenge("test", 29, new ArrayList<>(), null);

        assertEquals("Shifts differing by 26 should be equivalent",
                     shift3.toString(), shift29.toString());
    }
}
