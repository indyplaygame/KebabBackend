package api.indy.kebab.persistence.converter;

import api.indy.kebab.auth.Permission;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Converter
public class PermissionsConverter implements AttributeConverter<List<Permission>, String> {
    private static final String SEPARATOR = ";";

    @Override
    public String convertToDatabaseColumn(List<Permission> permissions) {
        return String.join(SEPARATOR, permissions.stream()
            .map(Enum::name)
            .map(s -> s.replace("_", ".").toLowerCase())
            .toArray(String[]::new)
        );
    }

    @Override
    public List<Permission> convertToEntityAttribute(String str) {
        return str.isEmpty() ? new ArrayList<>() : Stream.of(str.split(SEPARATOR))
            .map(s -> s.replace(".", "_").toUpperCase())
            .map(Permission::valueOf)
            .toList();
    }
}
