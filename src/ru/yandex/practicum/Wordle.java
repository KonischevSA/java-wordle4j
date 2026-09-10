package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.*;
import ru.yandex.practicum.log.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class Wordle {

    private static String dictionaryFile = "words_ru.txt";
    private static String logFile = "log.txt";
    private static Logger logger;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            refreshLog();
            logger = new Logger(logFile, true);

            logger.writeLog("Подготовка к игре.");

            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(logger);
            WordleDictionary wordleDictionary = wordleDictionaryLoader.loadDictionary(dictionaryFile);
            WordleGame game = new WordleGame(logger, scanner, wordleDictionary);

            printGameRules();

            while (true) {
                printMenu();
                int command;

                try {
                    command = Integer.parseInt(scanner.nextLine());
                    if (command != 0 && command != 1) {
                        throw new WrongCommandException("Получена неизвестная команда от пользователя");
                    }
                } catch (NumberFormatException e) {
                    throw e;
                }

                switch (command) {
                    case 0:
                        return;
                    case 1:
                        logger.writeLog("Начало игры.");
                        game.playNewGame();
                        break;
                    default:
                        System.out.println("Неверная команда");
                }
            }
        } catch (WrongCommandException | NumberFormatException e) {
            try {
                logger.writeLog(e.getMessage(), LogMessageType.ERROR);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            System.out.println("Неизвестная команда");
        } catch (EmptyWordListException e) {
            try {
                logger.writeLog(e.getMessage(), LogMessageType.ERROR);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            System.out.println(e.getMessage());
        } catch (Throwable e) {
            try {
                logger.writeLog(e.getMessage(), LogMessageType.FATAL_ERROR);

            } catch (IOException | NullPointerException ex) {
                System.out.println(ex.getMessage());
            }

            System.out.println(e.getMessage());
        }
    }

    private static void refreshLog() throws IOException {
        Path log = Paths.get(logFile);

        try {

            if (Files.exists(log)) {
                Files.delete(log);
            }
            Files.createFile(log);

        } catch (IOException e) {
            throw new IOException("Не удалось пересоздать лог-файл");
        }
    }

    private static void printMenu() {
        System.out.println("Выберите, что хотите сделать:");
        System.out.println("0 - выйти;");
        System.out.println("1 - начать новую игру;");
    }

    public static void printGameRules() {
        System.out.println("------------------");
        System.out.println("Правила игры:");
        System.out.println(" - введите слово из 5 букв;");
        System.out.println(" - допустимы только буквы русского алфавита;");
        System.out.println(" - буквы е и ё равнозначны;");
        System.out.println(" - у вас 6 попыток;");
        System.out.println(" - для получения подсказки нажмите Enter (подсказка считается попыткой);");
        System.out.println("Удачной игры!");
        System.out.println("------------------");
    }
}
