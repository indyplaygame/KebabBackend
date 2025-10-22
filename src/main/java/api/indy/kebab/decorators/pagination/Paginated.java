package api.indy.kebab.decorators.pagination;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Paginated {
    int defaultSize() default 10;
    int maxSize() default 50;
}
