package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.EmptyWordListException;
import ru.yandex.practicum.log.Logger;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    private static Logger logger;
    private static WordleDictionaryLoader wordleDictionaryLoader;
    private String goodFile_1 = "test/ru/yandex/practicum/words_ru_1_good.txt";
    private String goodFile_10 = "test/ru/yandex/practicum/words_ru_10_good.txt";

    @BeforeAll
    public static void beforeAll() {
        logger = new Logger("tests_log.txt", false);
        wordleDictionaryLoader = new WordleDictionaryLoader(logger);
    }

    @Test
    public void test_answerIsGeneratedIfDictionaryIsGood() throws IOException, EmptyWordListException {
        WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(goodFile_10);

        WordleGame game = new WordleGame(logger, new Scanner(System.in), wordleDictionary);
        game.generateAnswer();

        assertNotNull(game.getAnswer());
    }

    @Test
    public void test_answerIsPredeterminedIfDictionaryIsGoodAndOneRow() throws IOException, EmptyWordListException {
        WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(goodFile_1);

        WordleGame game = new WordleGame(logger, new Scanner(System.in), wordleDictionary);
        game.generateAnswer();

        assertEquals("аббат", game.getAnswer());
    }

    @Test
    public void test_wordMapForAnswerIsPredeterminedWord() throws EmptyWordListException, IOException {
        WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(goodFile_1);
        WordleGame game = new WordleGame(logger, new Scanner(System.in), wordleDictionary);
        game.generateAnswer();

        assertEquals("+++++", game.makeWordMask(game.getAnswer()));
    }

    @Test
    public void test_wordMapForWrongWordIsPredeterminedWord() throws EmptyWordListException, IOException {
        WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(goodFile_1);
        WordleGame game = new WordleGame(logger, new Scanner(System.in), wordleDictionary);
        game.generateAnswer();

        assertEquals("-----", game.makeWordMask("укроп"));
    }

    @Test
    public void test_fullWordMapForAnswerIsPredeterminedWord() throws EmptyWordListException, IOException {
        WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(goodFile_1);
        WordleGame game = new WordleGame(logger, new Scanner(System.in), wordleDictionary);
        game.generateAnswer();
        game.makeWordMask(game.getAnswer());

        assertEquals("аббат", game.getFullWordMask());
    }
}
