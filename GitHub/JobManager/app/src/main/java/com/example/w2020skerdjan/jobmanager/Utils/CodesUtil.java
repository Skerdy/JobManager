package com.example.w2020skerdjan.jobmanager.Utils;

import com.example.w2020skerdjan.jobmanager.RoleUserLogic.AdminLogic.AdminActivity;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic.EmployerActivity;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.ManagerLogic.ManagerActivity;

public class CodesUtil {
    public static final String USERNAME = "username" ;
    public static final String PASSWORD = " password" ;
    public static final String ACCESS_TOKEN = " access_token" ;
    public static final Class ADMIN_CLASS = AdminActivity.class;
    //public static final Class EMPLOYEE_CLASS = EmployeeActivity.class;
    public static final Class EMPLOYER_CLASS = EmployerActivity.class;
    public static final Class MANAGER_CLASS = ManagerActivity.class;
}
