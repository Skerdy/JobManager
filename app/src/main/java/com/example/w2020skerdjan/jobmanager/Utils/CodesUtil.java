package com.example.w2020skerdjan.jobmanager.Utils;

import com.example.w2020skerdjan.jobmanager.Fragment.LoginFragment;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.AdminLogic.Activities.AdminActivity;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployeeLogic.EmployeeActivity;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic.Activities.EmployerActivity;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic.Activities.EmployerTutorialActivity;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.ManagerLogic.ManagerActivity;

public class CodesUtil {
    public static final String USERNAME = "username" ;
    public static final String PASSWORD = " password" ;
    public static final String ACCESS_TOKEN = " access_token" ;
    
    public static final Class ADMIN_CLASS = AdminActivity.class;
    public static final Class EMPLOYER_CLASS = EmployerActivity.class;
    public static final Class MANAGER_CLASS = ManagerActivity.class;
    
    public static final String ADMINISTRATOR_ROLE = "admin" ;
    public static final String EMPLOYER_ROLE = "employer" ;
    public static final String MANAGER_ROLE ="manager" ;
    public static final String EMPLOYEE_ROLE ="employee" ;
    
    public static final String JOB_ID = "jobID";
    public static final String INIT_MAP = "initMap" ;
    public static final String FROM_JOB_CLICK = "from_job_click";

    public static final Class EMPLOYEE_CLASS = EmployeeActivity.class;
    public static final String ASP_NET_USER_ID = "asp_net_user_id_job_finder";
    public static final Class TUTORIAL_EMPLOYER_CLASS = EmployerTutorialActivity.class;
}
