package api.indy.kebab.util;

public class Util {
    public static String getFileExtension(String fileName) {
        if(fileName == null) return "";

        int i = fileName.lastIndexOf('.');
        if (i == -1 || i == fileName.length() - 1) return "";

        return fileName.substring(i + 1);
    }
}
