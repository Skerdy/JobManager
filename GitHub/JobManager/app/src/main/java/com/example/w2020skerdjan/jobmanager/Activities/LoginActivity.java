package com.example.w2020skerdjan.jobmanager.Activities;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.w2020skerdjan.jobmanager.Fragment.LoginFragment;
import com.example.w2020skerdjan.jobmanager.Fragment.RegisterFragment;
import com.example.w2020skerdjan.jobmanager.R;

public class LoginActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initLogin();
    }


    public void initLogin() {
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        LoginFragment homeFragment = new LoginFragment();
        fragmentTransaction.addToBackStack("Login");
        fragmentTransaction.replace(R.id.fragmentLogin, homeFragment, "Login");
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void initRegister(){
            fragmentTransaction=getSupportFragmentManager().beginTransaction();
            RegisterFragment registerFragment = new RegisterFragment();
            fragmentTransaction.addToBackStack("Register");
            fragmentTransaction.replace(R.id.fragmentLogin, registerFragment, "Register");
            fragmentTransaction.commitAllowingStateLoss();
    }

}
