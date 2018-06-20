package com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.w2020skerdjan.jobmanager.Fragment.LoginFragment;
import com.example.w2020skerdjan.jobmanager.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class EmployerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Drawer result;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);
        setupViews();
       // initAdminHomeFragment();
    }

    private void setupViews(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.md_white_1000));
        toolbar.setTitle("Home");
        setTitle("Home");
        setupNavigationDrawer();
        initHomeFragment();
    }

    private void setupNavigationDrawer(){
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem itemHome = new PrimaryDrawerItem().withIdentifier(0).withName("Home").withIcon(R.drawable.ic_icon_home_normal);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("My Jobs").withIcon(R.drawable.ic_icon_job);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(3).withName("Find Employees").withIcon(R.drawable.ic_icon_users);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(4).withName("My Profile").withIcon(R.drawable.ic_icon_manager);
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withIdentifier(5).withName("Settings").withIcon(R.drawable.ic_icon_settings_normal);
        //nis me Home Fragment ne OnCreate
        // Create the AccountHeader



        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.nav_header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Admin Admin").withEmail("admin@w2020.com").withIcon(getResources().getDrawable(R.drawable.ic_icon_profile))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

//create the drawer and remember the `Drawer` result object
        result= new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        itemHome, item1,item2,item3,
                        new DividerDrawerItem(),
                        settings
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, final int position, IDrawerItem drawerItem) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                switch (position){
                                    case 1:
                                     initHomeFragment();
                                        break;
                                    case 2:
                                        initSearchJobsEmployer();
                                        break;
                                    case 3:
                                        initfindEmployesActivity();
                                        break;
                                    case 4:
                                        initEmployerProfile();
                                        break;
                                    case 5 :
                                        break;
                                    case 7 :
                                        break;
                                    case -1:
                                        break;
                                }
                            }
                        }, 200);
                        Log.d("position", " "+position);
                        return false;
                    }
                }).withAccountHeader(headerResult)
                .build();
      /*  result.addStickyFooterItem(new PrimaryDrawerItem().withName("Log out").withIconColor(Color.parseColor("#ffffff")).withIcon(R.drawable.ic_exit_to_app_white_24dp).withIdentifier(6).withTextColor(Color.parseColor("#ffffff")));
        result.getStickyFooter().setBackgroundResource(R.color.nav_yellow);*/
    }

    public void initfindEmployesActivity(){
        Intent intent = new Intent(EmployerActivity.this, EmployerMap.class);
        startActivity(intent);
    }

    public void initHomeFragment(){
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        EmployerHomeFragment homeFragment = new EmployerHomeFragment();
        fragmentTransaction.addToBackStack("employer_home");
        fragmentTransaction.replace(R.id.fragmentLogin, homeFragment, "employer_home");
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void initEmployerProfile(){
        /*fragmentTransaction=getSupportFragmentManager().beginTransaction();
        EmployerProfileFragment profileFragment = new EmployerProfileFragment();
        fragmentTransaction.addToBackStack("employer_profile");
        fragmentTransaction.replace(R.id.fragmentLogin, profileFragment, "employer_profile");
        fragmentTransaction.commitAllowingStateLoss();*/
        Intent intent = new Intent(EmployerActivity.this,EmployerProfile.class);
        startActivity(intent);
    }

    public void initSearchJobsEmployer(){
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        EmployeeSearchJobsFragment employeeSearchJobsFragment = new EmployeeSearchJobsFragment();
        fragmentTransaction.addToBackStack("employeeSearchJobsFragment");
        fragmentTransaction.replace(R.id.fragmentLogin, employeeSearchJobsFragment, "employeeSearchJobsFragment");
        fragmentTransaction.commitAllowingStateLoss();
    }
}
