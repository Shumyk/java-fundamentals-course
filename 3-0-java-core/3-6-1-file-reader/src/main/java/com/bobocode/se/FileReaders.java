package com.bobocode.se;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * {@link FileReaders} provides an API that allow to read whole file into a {@link String} by file name.
 */
public class FileReaders {

    /**
     * Returns a {@link String} that contains whole text from the file specified by name.
     *
     * @param fileName a name of a text file
     * @return string that holds whole file content
     */
    public static String readWholeFile(String fileName) {
        final var filePath = createPathFromFileName(fileName);
        try (var fileLinesStream = openFileLinesStream(filePath)) {
            return fileLinesStream.collect(joining("\n"));
        }
    }

    private static Path createPathFromFileName(String fileName) {
        requireNonNull(fileName);
        final var resourceUrl = Thread.currentThread().getContextClassLoader().getResource(fileName);
        try {
            return Paths.get(resourceUrl.toURI());
        } catch (NullPointerException | URISyntaxException e) {
            throw new FileReaderException("Invalid file URL", e);
        }
    }

    private static Stream<String> openFileLinesStream(Path filePath) {
        try {
            return Files.lines(filePath);
        } catch (IOException e) {
            throw new FileReaderException("Cannot create stream of file lines!", e);
        }
    }

    public static class FileReaderException extends RuntimeException {
        public FileReaderException(String msg, Exception e) {
            super(msg, e);
        }
    }
}
