package api.indy.kebab.decorators.pagination;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class PaginationAspect {

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
