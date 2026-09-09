package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.EmptyWordListException;
import ru.yandex.practicum.log.Logger;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WordleDictionaryLoaderTest {
    private static WordleDictionaryLoader wordleDictionaryLoader;
    private String emptyFile = "test\\ru\\yandex\\practicum\\words_ru_empty.txt";
    private String badFile_1 = "test\\ru\\yandex\\practicum\\words_ru_1_bad.txt";
    private String badFile_10 = "test\\ru\\yandex\\practicum\\words_ru_10_bad.txt";
    private String goodFile_1 = "test\\ru\\yandex\\practicum\\words_ru_1_good.txt";
    private String goodFile_10 = "test\\ru\\yandex\\practicum\\words_ru_10_good.txt";
    private String noSuchFile = "test\\ru\\yandex\\practicum\\no_such_file.txt";

    @BeforeAll
    public static void beforeAll() {
        Logger logger = new Logger("tests_log.txt", false);
        wordleDictionaryLoader = new WordleDictionaryLoader(logger);
    }

    @Test
    public void test_emptyFileGenerateEmptyWordListException() {
        boolean emptyWordExGenerated = false;
        try {
            WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(emptyFile);
        } catch (EmptyWordListException e) {
            emptyWordExGenerated = true;
        } catch (IOException e) {
            emptyWordExGenerated = false;
        }

        assertTrue(emptyWordExGenerated);
    }

    @Test
    public void test_1BadRowFileGenerateEmptyWordListException() {
        boolean emptyWordExGenerated = false;
        try {
            WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(badFile_1);
        } catch (EmptyWordListException e) {
            emptyWordExGenerated = true;
        } catch (IOException e) {
            emptyWordExGenerated = false;
        }

        assertTrue(emptyWordExGenerated);
    }

    @Test
    public void test_10BadRowsFileGenerateEmptyWordListException() {
        boolean emptyWordExGenerated = false;
        try {
            WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(badFile_10);
        } catch (EmptyWordListException e) {
            emptyWordExGenerated = true;
        } catch (IOException e) {
            emptyWordExGenerated = false;
        }

        assertTrue(emptyWordExGenerated);
    }

    @Test
    public void test_1GoodRowFileDontGenerateEnyException() {
        boolean emptyWordExGenerated = true;
        try {
            WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(goodFile_1);
        } catch (EmptyWordListException | IOException e) {
            emptyWordExGenerated = false;
        }

        assertTrue(emptyWordExGenerated);
    }

    @Test
    public void test_10GoodRowFileDontGenerateEnyException() {
        boolean emptyWordExGenerated = true;
        try {
            WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(goodFile_10);
        } catch (EmptyWordListException | IOException e) {
            emptyWordExGenerated = false;
        }

        assertTrue(emptyWordExGenerated);
    }

    @Test
    public void test_noSuchFileGenerateIOException() {
        boolean emptyWordExGenerated = false;
        try {
            WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(noSuchFile);
        } catch (EmptyWordListException e) {
            emptyWordExGenerated = false;
        } catch (IOException e) {
            emptyWordExGenerated = true;
        }

        assertTrue(emptyWordExGenerated);
    }
}
