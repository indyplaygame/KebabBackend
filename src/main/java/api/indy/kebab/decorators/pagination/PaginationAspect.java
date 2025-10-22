package api.indy.kebab.decorators.pagination;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Aspect for handling pagination logic in methods annotated with {@link Paginated}.
 *
 * <p>This aspect intercepts methods annotated with {@link Paginated} and adjusts
 * the {@link Pageable} argument to enforce default and maximum page sizes as defined
 * in the annotation. It ensures that pagination parameters are within acceptable bounds
 * before proceeding with the method execution.</p>
 *
 * @see Paginated
 */
@Aspect
@Component
public class PaginationAspect {

    /**
     * Intercepts methods annotated with {@link Paginated} and adjusts the {@link Pageable}
     * argument based on the annotation's configuration.
     *
     * <p>If the page size is not specified or exceeds the maximum allowed size, it is
     * adjusted to the default or maximum size defined in the {@link Paginated} annotation.</p>
     *
     * @param joinPoint the join point representing the intercepted method
     * @return the result of the method execution with adjusted pagination parameters
     *
     * @throws Throwable if the intercepted method throws an exception
     */
    @Around("@annotation(api.indy.kebab.decorators.pagination.Paginated)")
    public Object handlePagination(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Paginated annotation = method.getAnnotation(Paginated.class);

        Object[] args = joinPoint.getArgs();
        Object[] newArgs = args.clone();

        for(int i = 0; i < args.length; i++) {
            if(!(args[i] instanceof Pageable)) continue;

            Pageable pageable = (Pageable) args[i];
            int page = pageable.getPageNumber();
            int size = pageable.getPageSize();

            if(size <= 0) size = annotation.defaultSize();
            if(size > annotation.maxSize()) size = annotation.maxSize();

            newArgs[i] = PageRequest.of(page, size, pageable.getSort());
            break;
        }

        return joinPoint.proceed(newArgs);
    }
}
