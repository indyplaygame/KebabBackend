package api.indy.kebab.auth;

/**
 * Enum representing various permissions within the application.
 * These permissions are used to control access to different features and actions.
 *
 * @see AuthInterceptor
 * @see AuthRequired
 */
public enum Permission {
    NONE,

    PERMISSIONS_GRANT,
    PERMISSIONS_REVOKE,

    CATEGORIES_CREATE,
    CATEGORIES_UPDATE,
    CATEGORIES_DELETE,

    MENU_CREATE,
    MENU_UPDATE,
    MENU_DELETE,

    RESTAURANTS_CREATE,
    RESTAURANTS_UPDATE,
    RESTAURANTS_DELETE,

    REVIEWS_UPDATE,
    REVIEWS_DELETE,

    ORDERS_READ,
    ORDERS_UPDATE
}
