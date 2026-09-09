package ru.yandex.practicum;

public class WordsValidator {
    public static boolean isValid(String word, int validLength) {
        if (validLength < 1) {
            return false;
        }
        return word != null && word.toLowerCase().matches("[а-я]{" + validLength + "}");
    }
}
