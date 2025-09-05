package com.Wisdom_Training.config;

public class APIURL {

    // Không cần token (PUBLIC)
    public static final String[] URL_ANONYMOUS_GET = {
            "/api/products/{id}",       // Lấy chi tiết 1 product
            "/api/products",            // Lấy danh sách product
            "/api/categories",          // Lấy danh sách category
            "/api/categories/{id}",     // Lấy chi tiết 1 category
            "/api/categories/{categoryId}/products",    // Lấy danh sách produt theo categoryId
    };
    public static final String[] URL_ANONYMOUS_POST = {
            "/api/accounts",            // Đăng ký
            "/api/accounts/login",      // Đăng nhập
    };
    public static final String[] URL_ANONYMOUS_PUT = {};
    public static final String[] URL_ANONYMOUS_DELETE = {};

    // Role USER
    public static final String[] URL_USER_GET = {};
    public static final String[] URL_USER_POST = {};
    public static final String[] URL_USER_PUT = {};
    public static final String[] URL_USER_DELETE = {};

    // Role ADMIN
    public static final String[] URL_ADMIN_GET = {};
    public static final String[] URL_ADMIN_POST = {
            "/api/products",            // Tạo product
            "/api/categories",          // Tạo category
            "/api/categories/{categoryId}/products", // Tạo product theo categoryId
    };
    public static final String[] URL_ADMIN_PUT = {
            "/api/products/{id}",       // Cập nhật 1 product
            "/api/categories/{id}",     // Cập nhật 1 category
    };
    public static final String[] URL_ADMIN_PATCH = {
            "/api/products/{productId}/category/{categoryId}",  // Cập nhật categoryId cho product
    };
    public static final String[] URL_ADMIN_DELETE = {
            "/api/products/{id}",       // Xóa 1 product
            "/api/categories/{id}",     // Xóa 1 category

    };
}
