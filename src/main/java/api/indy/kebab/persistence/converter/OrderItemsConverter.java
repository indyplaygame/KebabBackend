package api.indy.kebab.persistence.converter;

import api.indy.kebab.core.SpringContext;
import api.indy.kebab.model.MenuItem;
import api.indy.kebab.service.MenuService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;

@Converter
public class OrderItemsConverter implements AttributeConverter<Map<MenuItem, Integer>, String> {
    private static final String SEPARATOR = ";";

    private final MenuService _menuService;

    public OrderItemsConverter() {
        this._menuService = SpringContext.getBean(MenuService.class);
    }

    @Override
    public String convertToDatabaseColumn(Map<MenuItem, Integer> items) {
        StringBuilder str = new StringBuilder();

        for(Map.Entry<MenuItem, Integer> item : items.entrySet()) str.append(item.getKey().getMenuItemId())
            .append(":")
            .append(item.getValue())
            .append(SEPARATOR);

        return str.toString();
    }


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
