package com.example.w2020skerdjan.jobmanager.RoleUserLogic.AdminLogic.Activities;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Member;
import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.Retrofit.Requests.RequestsAPI;
import com.example.w2020skerdjan.jobmanager.Retrofit.RetrofitClient;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.AdminLogic.Fragments.AdminHomeFragment;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.AdminLogic.Fragments.AdminJobsFragment;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.AdminLogic.Fragments.AdminPersonsFragment;
import com.example.w2020skerdjan.jobmanager.Utils.CodesUtil;
import com.example.w2020skerdjan.jobmanager.Utils.MySharedPref;
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

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminActivity extends AppCompatActivity {

    private Drawer result;
    private Toolbar toolbar;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private Member loggedMember;
    private RetrofitClient retrofitClient;
    private Retrofit retrofit;
    private RequestsAPI requestsAPI;
    private MySharedPref mySharedPref;
    private String memberEmail;
    private  AccountHeader headerResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setupRetrofit();
        setupViews();
        initAdminHomeFragment();
        getLoggedInMember();
    }

    private void getLoggedInMember() {
        memberEmail = mySharedPref.getStringFromSharedPref(CodesUtil.USERNAME);
        if (memberEmail != null)
            requestsAPI.getAspNetUserIdByEmail(memberEmail).enqueue(getMemberFromEmail);
    }

    Callback<Member> getMemberFromEmail = new Callback<Member>() {
        @Override
        public void onResponse(Call<Member> call, Response<Member> response) {
            if (response.isSuccessful()) {
                loggedMember = response.body();
                headerResult.addProfiles(new ProfileDrawerItem().withName(loggedMember.getFirstName()+""+loggedMember.getLastName()).withEmail(loggedMember.getEmail()).withIcon(getResources().getDrawable(R.drawable.ic_icon_profile)));
            } else {
                try {
                    Log.d("Login", "getAspnNetuserId failed : " + response.message() + response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<Member> call, Throwable t) {
            String message = "";
            Toast.makeText(AdminActivity.this, "Login failed: Network Error!", Toast.LENGTH_SHORT).show();
            if(t!=null && t.getMessage()!=null){
                message = t.getMessage();
            }
            Log.d("Login", "OnFailure : " + message);
        }
    };




    private void setupRetrofit(){
        retrofitClient = new RetrofitClient();
        retrofit = retrofitClient.krijoRetrofit();
        requestsAPI = retrofit.create(RequestsAPI.class);
        mySharedPref = new MySharedPref(this);
    }



    private void setupViews(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.md_white_1000));
        toolbar.setTitle("Home");
        setTitle("Home");
        setupNavigationDrawer();
    }

    private void setupNavigationDrawer(){
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem itemHome = new PrimaryDrawerItem().withIdentifier(0).withName("Home").withIcon(R.drawable.ic_icon_home_normal);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Jobs").withIcon(R.drawable.ic_icon_job);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(3).withName("Users").withIcon(R.drawable.ic_icon_users);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(2).withName("Managers").withIcon(R.drawable.ic_icon_manager);
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withIdentifier(5).withName("Settings").withIcon(R.drawable.ic_icon_settings_normal);
        //nis me Home Fragment ne OnCreate
        // Create the AccountHeader



       headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.nav_header)
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
                        itemHome, item1,item3,item2,
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
                                        initAdminHomeFragment();
                                        break;
                                    case 2:
                                        initJobsFragment();
                                        break;
                                    case 3:
                                        initUsersFragment();
                                        break;
                                    case 4:
                                        initUsersFragment();
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

    private void initAdminHomeFragment() {
        toolbar.setTitle("Home");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        AdminHomeFragment homeFragment = new AdminHomeFragment();
       // fragmentTransaction.addToBackStack("AdminHome");
        fragmentTransaction.replace(R.id.fragmentLogin, homeFragment, "AdminHome");
        fragmentTransaction.commitAllowingStateLoss();
    }

    private  void initJobsFragment(){
        toolbar.setTitle("Jobs");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        AdminJobsFragment adminJobsFragment = new AdminJobsFragment();
        // fragmentTransaction.addToBackStack("AdminHome");
        fragmentTransaction.replace(R.id.fragmentLogin, adminJobsFragment, "jobs");
        fragmentTransaction.commitAllowingStateLoss();
    }

    private  void initUsersFragment(){
        toolbar.setTitle("Members");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        AdminPersonsFragment adminPersonsFragment = new AdminPersonsFragment();
        // fragmentTransaction.addToBackStack("AdminHome");
        fragmentTransaction.replace(R.id.fragmentLogin, adminPersonsFragment, "persons");
        fragmentTransaction.commitAllowingStateLoss();
    }
}
