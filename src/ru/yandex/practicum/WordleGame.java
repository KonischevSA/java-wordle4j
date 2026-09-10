package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.*;
import ru.yandex.practicum.log.*;

import java.io.IOException;
import java.util.*;

/*
 * В моем представлении класс игры должен быть черным ящиком и замкнут в себе.
 * Все, что с ним можно сделать извне - создать объект (конструктор) и запустить (playNewGame()).
 * Все остальные методы должны быть private/protected.
 * Но т.к. есть требование в ТЗ написать тесты, то часть функционала сделал public, чтобы хоть что-то можно было тестировать.
 * */

public class WordleGame {

    private static final int TOTAL_GAME_ROUNDS = 6;
    public static final int GAME_WORD_LENGTH = 5;

    private Logger logger;
    private String answer;
    private WordleDictionary dictionary;
    private int steps;
    private List<String> usedWords = new ArrayList<>();

    private HashSet<String> helpList = new HashSet<>();
    private HashSet<Character> badLetters = new HashSet<>();
    private HashSet<Character> goodLetters = new HashSet<>();
    private char[] fullWordMask = new char[GAME_WORD_LENGTH];
    private boolean someLetterGuessed = false;
    private Random rnd = new Random();

    Scanner scanner;

    public WordleGame(Logger logger, Scanner scanner, WordleDictionary dictionary) {
        this.logger = logger;
        this.dictionary = dictionary;
        this.scanner = scanner;
    }

    public void generateAnswer() throws IOException {
        answer = dictionary.getRandomWord();
        logger.writeLog("Загаданное слово: " + answer + ".");
    }

    private void makeBasicHelpList() {
        HashSet<Character> uniqueAnswerSet = new HashSet<>();

        for (int i = 0; i < GAME_WORD_LENGTH; i++) {
            uniqueAnswerSet.add((answer.charAt(i)));
        }

        helpList.clear();
        for (String word : dictionary.getWords()) {
            for (char letter : uniqueAnswerSet) {
                if (word.indexOf(letter) >= 0) {
                    helpList.add(word);
                    break;
                }
            }
        }
    }

    public void playNewGame() throws IOException, EmptyWordListException {

        restartGame();
        String playersWord;

        do {
            logger.writeLog("Раунд: " + steps);

            playersWord = askNewWord();
            if (playersWord.equals(answer)) {
                System.out.println("Поздравляем! Вы угадали!");
                logger.writeLog("Игра закончена, слово угадано.");
                return;
            }

            System.out.println(makeWordMask(playersWord));

            steps++;
        } while (steps <= TOTAL_GAME_ROUNDS);

        System.out.println("К сожалению, вы не угадали. Было загадано слово \"" + answer + "\"");
        logger.writeLog("Игра закончена, слово не угадано.");
    }

    public String askNewWord() throws IOException, EmptyWordListException {
        String newWord;

        do {
            System.out.println("Введите слово: ");
            newWord = scanner.nextLine();

            if (newWord.isEmpty()) {
                logger.writeLog("Использована подсказка.");
                newWord = generateNewWord();
                System.out.println(newWord);
            }
        } while (!checkNewWord(newWord));

        newWord = newWord.toLowerCase().replace("ё", "е");
        usedWords.add(newWord);

        return newWord;
    }

    public boolean checkNewWord(String word) throws IOException {

        if (WordsValidator.isValid(word, GAME_WORD_LENGTH)) {
            logger.writeLog("Проверка слова \"" + word + "\" на соответствие формату. Успешно!");

            if (usedWords.contains(word)) {
                logger.writeLog("Слово \"" + word + "\" уже использовалось.", LogMessageType.WARNING);
                System.out.println("Данное слово уже использовалось");
                return false;
            }
            return true;
        }

        System.out.println("Слово не соответствует формату.");
        logger.writeLog("Проверка слова " + word + " на соответствие формату. Не успешно!", LogMessageType.WARNING);
        return false;
    }

    private String generateNewWord() throws EmptyWordListException {
        String newWord;

        if (someLetterGuessed) {
            actualHelpList();
        }
        newWord = helpList.toArray()[(rnd.nextInt(0, helpList.size()))].toString();

        return newWord;
    }

    private void actualHelpList() throws EmptyWordListException {
        Set<String> tmpSet = new HashSet<>();

        for (String word : helpList) {
            if (badLetters.contains(word.charAt(0))
                    || badLetters.contains(word.charAt(1))
                    || badLetters.contains(word.charAt(2))
                    || badLetters.contains(word.charAt(3))
                    || badLetters.contains(word.charAt(4))) {
                tmpSet.add(word);
                continue;
            }

            for (Character character : goodLetters) {
                if (word.indexOf(character) < 0) {
                    tmpSet.add(word);
                    break;
                }
            }

            if ((fullWordMask[0] != '*' && fullWordMask[0] != word.charAt(0))
                    || (fullWordMask[1] != '*' && fullWordMask[1] != word.charAt(1))
                    || (fullWordMask[2] != '*' && fullWordMask[2] != word.charAt(2))
                    || (fullWordMask[3] != '*' && fullWordMask[3] != word.charAt(3))
                    || (fullWordMask[4] != '*' && fullWordMask[4] != word.charAt(4))) {
                tmpSet.add(word);
            }
        }

        helpList.removeAll(tmpSet);

        if (helpList.isEmpty()) {
            throw new EmptyWordListException("В словаре-помощнике не осталось слов для генерации подсказок.");
        }
    }

    public String makeWordMask(String word) {
        String[] mask = new String[GAME_WORD_LENGTH];
        badLetters.clear();

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == answer.charAt(i)) {
                mask[i] = "+";
                fullWordMask[i] = word.charAt(i);
                someLetterGuessed = true;
                goodLetters.add(word.charAt(i));
            } else if (answer.indexOf(word.charAt(i)) >= 0) {
                mask[i] = "^";
                goodLetters.add(word.charAt(i));
                someLetterGuessed = true;
            } else {
                mask[i] = "-";
                badLetters.add(word.charAt(i));
            }
        }

        return String.join("", mask);
    }

    private void restartGame() throws IOException {
        generateAnswer();
        makeBasicHelpList();

        steps = 1;
        usedWords.clear();
        badLetters.clear();
        goodLetters.clear();
        someLetterGuessed = false;

        Arrays.fill(fullWordMask, '*');
    }

    public String getAnswer() {
        return answer;
    }

    public String getFullWordMask() {
        return new String(fullWordMask);
    }
}
