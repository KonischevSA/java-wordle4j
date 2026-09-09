package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.EmptyWordListException;
import ru.yandex.practicum.log.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WordleDictionaryLoader {

    private Logger logger;

    public WordleDictionaryLoader(Logger logger) {

        this.logger = logger;
    }

    public WordleDictionary loadDictionary(String fileName) throws IOException, EmptyWordListException {
        ArrayList<String> words = new ArrayList<>();
        int totalWordsChecked = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName));) {

            String word;
            while (br.ready()) {
                word = br.readLine();
                totalWordsChecked++;

                if (WordsValidator.isValid(word, WordleGame.GAME_WORD_LENGTH)) {
                    words.add(word.toLowerCase().replace("ё", "е"));
                }
            }
            logger.writeLog("Словарь загружен. Обработано слов - " + totalWordsChecked + ", добавлено в игру - " + words.size() + ".");

            if(words.isEmpty())
            {
                throw new EmptyWordListException("В словаре нет подходящих для игры слов.");
            }

        } catch (IOException e) {
            throw new IOException("Не удалось загрузить словарь для игры.");
        }
        catch (EmptyWordListException e)
        {
            throw e;
        }

        return new WordleDictionary(words);
    }
}
