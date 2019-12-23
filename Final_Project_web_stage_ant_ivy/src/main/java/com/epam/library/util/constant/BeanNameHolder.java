package com.epam.library.util.constant;

public class BeanNameHolder {

    public final static String BASE_PACKAGE = "com.epam.library";

    public static final String DATA_SOURCE_BEAN = "dataSource";
    public static final String TRANSACTION_MANAGER_BEAN = "transactionManager";
    public static final String ENTITY_MANAGER_FACTORY_BEAN = "entityManagerFactory";
    public static final String FLYWAY_BEAN = "flyway";
    public static final String DEVELOPING_PROFILE = "dev";
    public static final String TESTING_PROFILE = "test";

    //Controller
    public static final String WEB_SERVLET_BEAN = "/controller";

    // Repository beans
    public static final String ABSTRACT_DAO_BEAN = "abstractDao";
    public static final String USER_DAO_BEAN = "userDao";
    public static final String BOOK_DAO_BEAN = "bookDao";
    public static final String ORDER_DAO_BEAN = "orderDao";

    // Service beans

    public static final String USER_SERVICE_BEAN = "userService";
    public static final String BOOK_SERVICE_BEAN = "bookService";
    public static final String ORDER_SERVICE_BEAN = "orderService";

    private BeanNameHolder() {

    }
}
