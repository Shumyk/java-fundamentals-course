package com.bobocode.se;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link FileStats} provides an API that allow to get character statistic based on text file. All whitespace characters
 * are ignored.
 */
@RequiredArgsConstructor
public class FileStats {

    private final Map<Character, Long> characterCountMap;
    private final char mostPopularCharacter;

    /**
     * Creates a new immutable {@link FileStats} objects using data from text file received as a parameter.
     *
     * @param fileName input text file name
     * @return new FileStats object created from text file
     */
    public static FileStats from(String fileName) {
        final var fileUri = getUriForFile(fileName);
        final var lines = getFileLinesForUri(fileUri);

        final var characterCountMap = formCharacterCountMap(lines);
        final var mostPopularCharacter = findMostPopularCharacter(characterCountMap);

        return new FileStats(characterCountMap, mostPopularCharacter);
    }

    @SneakyThrows
    private static URI getUriForFile(String fileName) {
        final var fileUrl = Thread.currentThread().getContextClassLoader().getResource(fileName);
        if (null == fileUrl) throw new FileStatsException("Wrong file name");
        return fileUrl.toURI();
    }

    @SneakyThrows
    private static Stream<String> getFileLinesForUri(URI uri) {
        final var path = Path.of(uri);
        return Files.lines(path);
    }

    private static Map<Character, Long> formCharacterCountMap(Stream<String> lines) {
        return lines.flatMapToInt(String::chars)
                .mapToObj(i -> (char) i)
                .filter(c -> !Character.isWhitespace(c))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private static char findMostPopularCharacter(Map<Character, Long> countMap) {
        return countMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow()
                .getKey();
    }

    /**
     * Returns a number of occurrences of the particular character.
     *
     * @param character a specific character
     * @return a number that shows how many times this character appeared in a text file
     */
    public int getCharCount(char character) {
        return characterCountMap.getOrDefault(character, 0L).intValue();
    }

    /**
     * Returns a character that appeared most often in the text.
     *
     * @return the most frequently appeared character
     */
    public char getMostPopularCharacter() {
        return mostPopularCharacter;
    }

    /**
     * Returns {@code true} if this character has appeared in the text, and {@code false} otherwise
     *
     * @param character a specific character to check
     * @return {@code true} if this character has appeared in the text, and {@code false} otherwise
     */
    public boolean containsCharacter(char character) {
        return characterCountMap.containsKey(character);
    }
}
