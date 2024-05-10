package application;

public class ArgumentParser {

    /**
     * Parses the command-line arguments and extracts the file name.
     *
     * @param args The command-line arguments.
     * @return The file name specified in the arguments, or null if invalid.
     * @throws NullPointerException if the `args` parameter is null.
     */
    public String parseArguments(String[] args) {
        if (args.length != 2 || !args[0].equals("-file") || args[1].isBlank()) {
                throw new IllegalArgumentException("Invalid arguments. Usage: -file <filename>");
        }
        return args[1];
    }
}