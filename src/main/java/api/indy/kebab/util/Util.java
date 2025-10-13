package api.indy.kebab.util;

/**
 * Utility class providing helper methods for the application.
 */
public class Util {

    /**
     * Retrieves the file extension from a given file name.
     *
     * @param fileName The name of the file from which to extract the extension.
     *                 If the file name is null or does not contain a valid extension,
     *                 an empty string is returned.
     * @return The file extension as a string, or an empty string if no valid extension is found.
     */
    public static String getFileExtension(String fileName) {
        if(fileName == null) return "";

        int i = fileName.lastIndexOf('.');
        if (i == -1 || i == fileName.length() - 1) return "";

        return fileName.substring(i + 1);
    }
}
