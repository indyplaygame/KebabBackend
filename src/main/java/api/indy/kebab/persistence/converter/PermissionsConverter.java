package api.indy.kebab.persistence.converter;

import api.indy.kebab.auth.Permission;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JPA attribute converter for converting a set of {@link Permission} enums
 * to a semicolon-separated string for database storage and vice versa.
 */
@Converter
public class PermissionsConverter implements AttributeConverter<Set<Permission>, String> {
    private static final String SEPARATOR = ";";

    /**
     * Converts a set of {@link Permission} enums to a semicolon-separated string.
     *
     * <p>Each permission is converted to its lowercase string representation,
     * with underscores replaced by dots.</p>
     *
     * @param permissions the set of permissions to convert.
     * @return a semicolon-separated string representation of the permissions.
     */
    @Override
    public String convertToDatabaseColumn(Set<Permission> permissions) {
        return String.join(SEPARATOR, permissions.stream()
            .map(Enum::name)
            .map(s -> s.replace("_", ".").toLowerCase())
            .toArray(String[]::new)
        );
    }

    /**
     * Converts a semicolon-separated string to a set of {@link Permission} enums.
     *
     * <p>Each permission string is converted to uppercase, with dots replaced by underscores,
     * and then mapped to the corresponding {@link Permission} enum.</p>
     *
     * @param str the semicolon-separated string to convert.
     * @return a set of {@link Permission} enums.
     */
    @Override
    public Set<Permission> convertToEntityAttribute(String str) {
        return str.isEmpty() ? new HashSet<>() : Stream.of(str.split(SEPARATOR))
            .map(s -> s.replace(".", "_").toUpperCase())
            .map(Permission::valueOf)
            .collect(Collectors.toSet());
    }
}
