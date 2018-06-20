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
    public static final String ADMINISTRATOR = "administrator" ;
    public static final String EMPLOYER = " employer" ;
    public static final String JOB_ID = "jobID";
    public static final String INIT_MAP = "initMap" ;
    public static final String FROM_JOB_CLICK = "from_job_click";
}
