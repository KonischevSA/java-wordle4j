package ru.yandex.practicum;

import ru.yandex.practicum.log.*;

import java.util.List;
import java.util.Random;

public class WordleDictionary {

    private List<String> words;
    private Random rnd = new Random();

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public String getRandomWord() {
        return words.get(rnd.nextInt(0, words.size()));
    }

    public List<String> getWords() {
        return words;
    }
}
