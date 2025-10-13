package api.indy.kebab.model.request;

import org.springframework.web.multipart.MultipartFile;

public record CreateCategoryRequest(String name, MultipartFile icon, String description) {}
