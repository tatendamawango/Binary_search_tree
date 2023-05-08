package edu.ktu.ds.lab2.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

public class ParsableAvlSet<E extends Parsable<E>> extends AvlSet<E>
        implements ParsableSortedSet<E> {

    private final Function<String, E> createFunction; // Function for creating a base object

    /**
     * Constructor with a function for creating a base object
     *
     * @param createFunction
     */
    public ParsableAvlSet(Function<String, E> createFunction) {
        super();
        this.createFunction = createFunction;
    }

    /**
     * Constructor with function for base object creation and comparator
     *
     * @param createFunction
     * @param c
     */
    public ParsableAvlSet(Function<String, E> createFunction, Comparator<? super E> c) {
        super(c);
        this.createFunction = createFunction;
    }

    /**
     * Creates an element from a String string and appends it to the end
     *
     * @param dataString
     */
    @Override
    public void add(String dataString) {
        add(createElement(dataString));
    }

    /**
     * Creates a list from a filePath file
     *
     * @param filePath
     */
    @Override
    public void load(String filePath) {
        if (filePath == null || filePath.length() == 0) {
            return;
        }

        clear();
        try (BufferedReader fReader = Files.newBufferedReader(Paths.get(filePath), Charset.defaultCharset())) {
            fReader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .forEach(this::add);
        } catch (FileNotFoundException e) {
            Ks.ern("Data file " + filePath + " not found");
        } catch (IOException e) {
            Ks.ern("File  " + filePath + " read error");
        }
    }

    protected E createElement(String data) {
        return Optional.ofNullable(createFunction)
                .map(f -> f.apply(data))
                .orElseThrow(() -> new IllegalStateException("No feature creation function set"));
    }
}
