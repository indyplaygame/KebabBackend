package api.indy.kebab.util;

import api.indy.kebab.auth.Permission;
import api.indy.kebab.exceptions.NoSuchPermissionsException;
import api.indy.kebab.model.User;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;

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

    /**
     * Uploads a file and returns its URL.
     *
     * @param file The {@link MultipartFile} representing the file to upload.
     * @param baseUrl The base URL format string where the file will be accessible. It should contain a placeholder for the file name, e.g., {@code uploads/%s}.
     * @return The URL of the uploaded file.
     * @throws IOException If an I/O error occurs during file upload.
     */
    public static String uploadFile(MultipartFile file, String baseUrl) throws IOException {
        String fileName = "%s.%s".formatted(UUID.randomUUID(), Util.getFileExtension(file.getOriginalFilename()));
        String fileUrl = baseUrl.formatted(fileName);

        Path baseDir = Paths.get(System.getProperty("user.dir"));
        Path uploadPath = baseDir.resolve(fileUrl);
        Files.createDirectories(uploadPath.getParent());

        file.transferTo(uploadPath.toFile());

        return fileUrl;
    }

    /**
     * Retrieves a file based on its URL.
     *
     * @param fileUrl The URL of the file to retrieve.
     * @return The {@link File} representing the file, or null if not found.
     */
    public static File retrieveFile(String fileUrl) {
        if(fileUrl == null || fileUrl.isEmpty()) return null;

        Path baseDir = Paths.get(System.getProperty("user.dir"));
        Path filePath = baseDir.resolve(fileUrl);
        File file = filePath.toFile();

        if(file.exists() && file.isFile()) return file;
        else return null;
    }

    /**
     * Deletes a file based on its URL.
     *
     * @param fileUrl The URL of the file to delete.
     * @return True if the icon was successfully deleted, false otherwise.
     */
    public static boolean deleteFile(String fileUrl) {
        if(fileUrl == null || fileUrl.isEmpty()) return false;

        Path baseDir = Paths.get(System.getProperty("user.dir"));
        Path filePath = baseDir.resolve(fileUrl);
        File iconFile = filePath.toFile();

        try {
            return iconFile.delete();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Creates a {@link ResponseEntity} containing the file as a byte array resource.
     *
     * @param file The {@link File} to be included in the response.
     * @return A {@link ResponseEntity} containing the file data and appropriate headers.
     *
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static ResponseEntity<Object> createResourceResponse(File file) throws IOException {
        Path path = file.toPath();
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(Files.probeContentType(path)));
        headers.setContentLength(data.length);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    /**
     * Capitalizes the first letter of the given string and converts the rest to lowercase.
     *
     * @param str the input string to be capitalized.
     * @return the capitalized string, or the original string if it is null or empty.
     */
    public static String capitalize(String str) {
        return str == null || str.isEmpty() ? str : str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * Returns the current timestamp in {@code ISO-8601} format.
     *
     * @return String representing the current time as text.
     */
    public static String getTimestamp() {
        return Instant.now().toString().substring(0, 19);
    }

    /**
     * Parses a list of permission strings into a set of {@link Permission} enums.
     *
     * <p>This method processes each string in the provided list, converting it into a corresponding
     * {@link Permission} enum. Strings ending with an asterisk (*) are treated as prefixes, and all
     * permissions starting with the given prefix are added to the result. If a string does not match
     * any valid permission, it is added to a set of invalid permissions.</p>
     *
     * @param permissionsString the list of permission strings to parse.
     * @return a set of {@link Permission} enums parsed from the input strings.
     *
     * @throws NoSuchPermissionsException if any of the provided strings are invalid permissions.
     */
    public static Set<Permission> parsePermissions(List<String> permissionsString) throws NoSuchPermissionsException {
        Set<Permission> permissions = new HashSet<>();
        Set<String> invalidPermissions = new HashSet<>();

        for(String str : permissionsString) {
            try {
                if(str.endsWith("*")) {
                    String prefix = str.substring(0, str.length() - 1).replace(".", "_").toUpperCase();
                    permissions.addAll(Arrays.stream(Permission.values()).filter(p -> p.name().startsWith(prefix)).toList());
                } else permissions.add(Permission.valueOf(str.replace(".", "_").toUpperCase()));
            } catch(IllegalArgumentException e) {
                invalidPermissions.add(str);
            }
        }

        if(!invalidPermissions.isEmpty()) throw new NoSuchPermissionsException(invalidPermissions);

        return permissions;
    }

    /**
     * Verifies if a user owns an entity or has a bypass permission.
     *
     * @param entityOwnerId the ID of the entity owner.
     * @param bypassPermission the permission that allows bypassing ownership check.
     * @param user the user to verify.
     * @return true if the user is the owner of the entity or has the bypass permission, false otherwise.
     */
    public static boolean verifyOwnership(long entityOwnerId, Permission bypassPermission, User user) {
        return user.getUserId() == entityOwnerId || (bypassPermission != Permission.NONE && user.hasPermission(bypassPermission));
    }
}
