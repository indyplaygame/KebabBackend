package api.indy.kebab.model.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T> (
    List<T> items,
    int page,
    int size,
    int totalPages,
    long totalItems,
    boolean hasPrevious,
    boolean hasNext
) {
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalPages(),
            page.getTotalElements(),
            page.hasPrevious(),
            page.hasNext()
        );
    }
}
