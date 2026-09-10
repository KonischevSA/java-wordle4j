package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.EmptyWordListException;
import ru.yandex.practicum.log.Logger;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WordleDictionaryTest {
    private static WordleDictionaryLoader wordleDictionaryLoader;
    private String goodFile_1 = "test/ru/yandex/practicum/words_ru_1_good.txt";
    private String goodFile_10 = "test/ru/yandex/practicum/words_ru_10_good.txt";

    @BeforeAll
    public static void beforeAll() {
        Logger logger = new Logger("tests_log.txt", false);
        wordleDictionaryLoader = new WordleDictionaryLoader(logger);
    }

    @Test
    public void test_1GoodRowFileMake1RowDictionary() throws EmptyWordListException, IOException {
        WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(goodFile_1);

        assertEquals(1, wordleDictionary.getWords().size());
    }

    @Test
    public void test_10GoodRowFileMake1RowDictionary() throws EmptyWordListException, IOException {
        WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(goodFile_10);

        assertEquals(10, wordleDictionary.getWords().size());
    }

    @Test
    public void test_1RowDictionaryGenerateIsPredeterminedWord() throws EmptyWordListException, IOException {
        WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(goodFile_1);

        assertEquals("аббат", wordleDictionary.getRandomWord());
    }
}
