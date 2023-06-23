package com.example.gradebackend.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {

    public static final String API_DEPARTMENT = "/api/v1/department";
    public static final String API_EMPLOYEE = "/api/v1/employee";
    public static final String API_PREMIUM = "/api/v1/premium";
    public static final String API_SALARY = "/api/v1/salary";
    public static final String API_TASK = "/api/v1/task";
    public static final String API_AUTH = "/api/v1/auth";
    public static final String API_REPORT = "/api/v1/report";

    public static final String API_DEPARTMENT_SECURITY = "/api/v1/department/**";
    public static final String API_EMPLOYEE_SECURITY = "/api/v1/employee/**";
    public static final String API_PREMIUM_SECURITY = "/api/v1/premium/**";
    public static final String API_SALARY_SECURITY = "/api/v1/salary/**";
    public static final String API_TASK_SECURITY = "/api/v1/task/**";
    public static final String API_AUTH_SECURITY = "/api/v1/auth/**";
    public static final String API_REPORT_SECURITY = "/api/v1/report/**";

    public static final String API_DOCS = "/v3/api-docs/**";
    public static final String URL_SWAGGER = "/swagger-ui/**";


    public static final int CONFLICT = 409;
    public static final String USER_ROLE_NAME = "user";
}
