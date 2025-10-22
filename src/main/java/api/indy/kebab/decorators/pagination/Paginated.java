package api.indy.kebab.decorators.pagination;

import java.lang.annotation.*;

/**
 * Annotation to indicate that a request supports pagination.
 *
 * <p>This annotation can be used to define default and maximum page sizes
 * for methods that return paginated results.</p>
 *
 * @see PaginationAspect
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Paginated {

    /**
     * Specifies the default number of items per page.
     *
     * @return The default page size
     */
    int defaultSize() default 10;

    /**
     * Specifies the maximum number of items allowed per page.
     *
     * @return The maximum page size
     */
    int maxSize() default 50;
}
