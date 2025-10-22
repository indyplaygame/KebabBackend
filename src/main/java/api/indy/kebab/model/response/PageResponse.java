package api.indy.kebab.model.response;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Represents a paginated response returned by the API.
 * Contains a list of items and pagination metadata.
 *
 * @param <T> The type of items in the paginated response.
 * @param items The list of items on the current page.
 * @param page The current page number (starting from 0).
 * @param size The number of items per page.
 * @param totalPages The total number of pages available.
 * @param totalItems The total number of items across all pages.
 * @param hasPrevious Indicates if there is a previous page.
 * @param hasNext Indicates if there is a next page.
 */
public record PageResponse<T> (
    List<T> items,
    int page,
    int size,
    int totalPages,
    long totalItems,
    boolean hasPrevious,
    boolean hasNext
) {

    /**
     * Creates a {@link PageResponse} from a Spring Data {@link Page}.
     *
     * <p>This factory method copies the content and pagination metadata from the given
     * Spring Data Page into a new PageResponse instance.
     *
     * @param page the source Spring Data {@link Page}
     * @param <T> the element type
     * @return a {@link PageResponse} containing the page content and metadata
     */
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
