package com.perceivedev.xpskills.util;

/**
 * Generates a Progress String that looks nice.
 * <p>
 * i.e.: "["GREEN |||| RED |||| "]"
 */
public class ProgressPrinter {

    private String prefix, suffix;
    private String filledString, unfilledString;
    private String filledPrefix, unfilledPrefix;
    private int length;

    /**
     * @param prefix The prefix before the generated String
     * @param suffix The suffix after the generated String
     * @param filledString The string to use for filled "bars"
     * @param unfilledString The string to use for unfilled "bars"
     * @param filledPrefix The prefix before the filled parts. May be used for formatting.
     * @param unfilledPrefix The prefix before the unfilled parts. May be used for formatting.
     * @param length The length of the generated String
     */
    public ProgressPrinter(String prefix, String suffix
              , String filledString, String unfilledString,
              String filledPrefix, String unfilledPrefix,
              int length) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.filledString = filledString;
        this.unfilledString = unfilledString;
        this.filledPrefix = filledPrefix;
        this.unfilledPrefix = unfilledPrefix;
        this.length = length;
    }

    /**
     * Generates a String for the given Progress
     *
     * @param progress The progress. Between 0 and 1 inclusive.
     *
     * @return The generated String
     */
    public String generate(double progress) {
        int filledBars = Math.toIntExact(Math.round(length * progress));
        StringBuilder result = new StringBuilder();

        result.append(prefix);

        if (filledBars > 0) {
            result.append(filledPrefix);
            for (int i = 0; i < filledBars; i++) {
                result.append(filledString);
            }
        }
        if (length - filledBars > 0) {
            result.append(unfilledPrefix);
            for (int i = 0; i < length - filledBars; i++) {
                result.append(unfilledString);
            }
        }

        result.append(suffix);

        return result.toString();
    }

    /**
     * Generates a String for the given Progress
     *
     * @param current The current value
     * @param max The maximum value
     *
     * @return The generated String
     */
    public String generate(int current, int max) {
        return generate((double) current / max);
    }
}
