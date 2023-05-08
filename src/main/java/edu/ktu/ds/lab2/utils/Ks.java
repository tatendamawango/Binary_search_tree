package edu.ktu.ds.lab2.utils;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;

/*
 * The class is designed for convenient data acquisition
 * from the keyboard and efficient presentation of results to sout and serr streams.
 * All methods are static and for a single data type.
 * Students independently prepare methods for short and byte number types.

 * @author eimutis
 */
public class Ks { // KTU system - simulated Java System class

    private static final BufferedReader keyboard
            = new BufferedReader(new InputStreamReader(System.in));
    private static String dataFolder = "data";

    static public String giveString(String prompt) {
        Ks.ou(prompt);
        try {
            return keyboard.readLine();
        } catch (IOException e) {
            Ks.ern("Keyboard flow not working, job completed");
            System.exit(0);
        }
        return "";
    }

    static public long giveLong(String prompt) {
        while (true) {
            String s = giveString(prompt);
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                Ks.ern("Invalid number format, repeat");
            }
        }
    }

    static public long giveLong(String prompt, long bound1, long bound2) {
        while (true) {
            long a = giveLong(prompt + " between boundaries [" + bound1 + ":" + bound2 + "]=");
            if (a < bound1) {
                Ks.ern("Number lower than allowed, repeat");
            } else if (a > bound2) {
                Ks.ern("Number greater than allowed, repeat");
            } else {
                return a;
            }
        }
    }

    static public int giveInt(String prompt) {
        while (true) {
            long a = giveLong(prompt);
            if (a < Integer.MIN_VALUE) {
                Ks.ern("Number less than Integer.MIN_VALUE "
                        + ", repeat");
            } else if (a > Integer.MAX_VALUE) {
                Ks.ern("Number greater than Integer.MAX_VALUE"
                        + ", repeat");
            } else {
                return (int) a;
            }
        }
    }

    static public int giveInt(String prompt, int bound1, int bound2) {
        return (int) giveLong(prompt, bound1, bound2);
    }

    static public double giveDouble(String prompt) {
        while (true) {
            String s = giveString(prompt);
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                if (s.contains(",")) {
                    Ks.ern("Use a period instead of a comma"
                            + ", repeat");
                } else {
                    Ks.ern("Invalid number format"
                            + ", repeat");
                }
            }
        }
    }

    static public double giveDouble(String prompt, double bound1, double bound2) {
        while (true) {
            double a = giveDouble(prompt + " between boundaries [" + bound1 + ":" + bound2 + "]=");
            if (a < bound1) {
                Ks.ern("Number lower than allowed, repeat");
            } else if (a > bound2) {
                Ks.ern("Number greater than allowed, repeat");
            } else {
                return a;
            }
        }
    }

    static public String giveFileName() {
        File dir = new File(dataFolder);
        dir.mkdir();
        oun("Files available to you " + Arrays.toString(dir.list()));
        String fn = giveString("Specify the selected data file name: ");
        return (fn);
    }

    static public String getDataFolder() {
        return dataFolder;
    }

    static public void setDataFolder(String folderName) {
        dataFolder = folderName;
    }

    private static final PrintStream sout = System.out;
    private static final PrintStream serr = System.out;
    private static int lineNr;
    private static int errorNr;
    private static final boolean formatStartOfLine = true;

    static public void ou(Object obj) {
        if (formatStartOfLine) {
            sout.printf("%2d| %s", ++lineNr, obj);
        } else {
            sout.println(obj);
        }
    }

    static public void oun(Object obj) {
        if (formatStartOfLine) {
            sout.printf("%2d| %s\n", ++lineNr, obj);
        } else {
            sout.println(obj);
        }
    }

    static public void ounn(Object obj) { // *****new
        if (formatStartOfLine) {
            sout.printf("%2d|\n", ++lineNr);
            sout.printf("%s\n", obj);
        } else {
            sout.println(obj);
        }
    }

    static public void out(Object obj) {
        sout.print(obj);
    }

    static public void ouf(String format, Object... args) {
        sout.printf(format, args);
    }

    static public void er(Object obj) {
        serr.printf("***Error %d: %s", ++errorNr, obj);
    }

    static public void ern(Object obj) {
        serr.printf("***Error %d: %s\n", ++errorNr, obj);
    }

    static public void erf(String format, Object... args) {
        serr.printf(format, args);
    }

    static public LocalDate getDate() {
        return LocalDate.now();
    }
}
