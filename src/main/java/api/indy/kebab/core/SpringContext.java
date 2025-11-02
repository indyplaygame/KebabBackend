package api.indy.kebab.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * A utility class to provide access to the Spring application context.
 * This class implements {@link ApplicationContextAware} to capture the application context
 * and provides a static method to retrieve beans from the context.
 */
@Component
public class SpringContext implements ApplicationContextAware {
    private static ApplicationContext context;

    /**
     * Sets the application context. This method is called by the Spring framework
     * during the initialization of the application context.
     *
     * @param context the Spring application context
     */
    @Override
    public void setApplicationContext(ApplicationContext context) {
        SpringContext.context = context;
    }

    /**
     * Retrieves a bean from the Spring application context by its class type.
     *
     * @param <T>       the type of the bean to retrieve
     * @param beanClass the class of the bean to retrieve
     * @return the bean instance of the specified type
     */
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
