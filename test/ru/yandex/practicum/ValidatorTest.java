package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    public void test_emptyStringWithZeroValidLengthShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("", 0));
    }

    @Test
    public void test_emptyStringNegativeValidLengthShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("", -1));
    }

    @Test
    public void test_emptyStringPositiveValidLengthShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("", 1));
    }

    @Test
    public void test_nullStringWithZeroValidLengthShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("", 0));
    }

    @Test
    public void test_nullStringNegativeValidLengthShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("", -1));
    }

    @Test
    public void test_nullStringPositiveValidLengthShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("", 1));
    }

    @Test
    public void test_WordLengthLessThenValidLengthShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("слово", 6));
    }

    @Test
    public void test_WordLengthMoreThenValidLengthShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("слово", 4));
    }

    @Test
    public void test_5SpacesWordShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("     ", 5));
    }

    @Test
    public void test_WordWithSpacesShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("сло о", 5));
    }

    @Test
    public void test_WordWithNumericShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("сло1о", 5));
    }

    @Test
    public void test_FullNumericWordShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("12345", 5));
    }

    @Test
    public void test_WordWithEnglishLettersShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("слоvо", 5));
    }

    @Test
    public void test_FullEnglishWordShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("words", 5));
    }

    @Test
    public void test_FullEnglishUpperCaseWordShouldReturnFalse()
    {
        assertFalse(WordsValidator.isValid("WORDS", 5));
    }

    @Test
    public void test_FullCyrillicWordShouldReturnTrue()
    {
        assertTrue(WordsValidator.isValid("слово", 5));
    }

    @Test
    public void test_FullCyrillicUpperCaseWordShouldReturnTrue()
    {
        assertTrue(WordsValidator.isValid("СЛОВО", 5));
    }

    @Test
    public void test_FullCyrillicSomeUpperCaseWordShouldReturnTrue()
    {
        assertTrue(WordsValidator.isValid("СлОвО", 5));
    }
}
