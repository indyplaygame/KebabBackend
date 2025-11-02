package api.indy.kebab.persistence.converter;

import api.indy.kebab.auth.Permission;
import api.indy.kebab.core.SpringContext;
import api.indy.kebab.model.MenuItem;
import api.indy.kebab.service.MenuService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * JPA attribute converter for converting a map of {@link MenuItem} with their quantities
 * to a semicolon-separated string for database storage and vice versa.
 */
@Converter
public class OrderItemsConverter implements AttributeConverter<Map<MenuItem, Integer>, String> {
    private static final String SEPARATOR = ";";

    private final MenuService _menuService;

    public OrderItemsConverter() {
        this._menuService = SpringContext.getBean(MenuService.class);
    }


    /**
     * Converts a map of {@link MenuItem} and their quantities to a semicolon-separated string
     * for storage in the database.
     *
     * @param items the map of {@link MenuItem} and their quantities to convert
     * @return a semicolon-separated string representation of the map
     */
    @Override
    public String convertToDatabaseColumn(Map<MenuItem, Integer> items) {
        StringBuilder str = new StringBuilder();

        for(Map.Entry<MenuItem, Integer> item : items.entrySet()) str.append(item.getKey().getMenuItemId())
            .append(":")
            .append(item.getValue())
            .append(SEPARATOR);

        return str.toString();
    }

    /**
     * Converts a semicolon-separated string from the database back into a map of {@link MenuItem}
     * and their quantities.
     *
     * @param str the semicolon-separated string to convert
     * @return a map of {@link MenuItem} and their quantities
     */
    @Override
    public Map<MenuItem, Integer> convertToEntityAttribute(String str) {
        Map<MenuItem, Integer> items = new HashMap<>();

        for(String itemStr : str.split(SEPARATOR)) {
            String[] parts = itemStr.split(":");

            long menuItemId = Long.parseLong(parts[0]);
            Integer quantity = Integer.parseInt(parts[1]);
            MenuItem menuItem = this._menuService.getMenuItem(menuItemId);

            items.put(menuItem, quantity);
        }

        return items;
    }
}
