package com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Member;
import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.Retrofit.Requests.RequestsAPI;
import com.example.w2020skerdjan.jobmanager.Retrofit.RetrofitClient;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic.Fragments.EmployerTutorialPane;
import com.example.w2020skerdjan.jobmanager.Utils.CodesUtil;
import com.example.w2020skerdjan.jobmanager.Utils.MySharedPref;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmployerTutorialActivity extends AppCompatActivity {


    static final int NUM_PAGES = 5;
    private ViewPager pager;
    private ScreenSlidePagerAdapter pagerAdapter;
    private LinearLayout circles;
    private Button done;
    private ImageButton next;
    private FragmentTransaction fragmentTransaction;
    boolean isOpaque = true;
    private MySharedPref mySharedPref;
    private String name, last_name,mobile_number ,education, profession, experience_years;
    private boolean mobile_valid = false;
    private Member member;

    private RetrofitClient retrofitClient;
    private Retrofit retrofit;
    private RequestsAPI requestsAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        EmployerTutorialPane employerTutorialPane = EmployerTutorialPane.newInstance(R.layout.tutorial_pane_empty,-1);
        fragmentTransaction = getSupportFragmentManager().beginTransaction().add(employerTutorialPane,"employerTutorial");
        setContentView(R.layout.activity_employer_tutorial);
        mySharedPref = new MySharedPref(this);
        retrofitClient = new RetrofitClient();
        retrofit = retrofitClient.krijoRetrofit();
        requestsAPI = retrofit.create(RequestsAPI.class);
        requestsAPI.getAspNetUserIdByEmail(mySharedPref.getStringFromSharedPref(CodesUtil.USERNAME)).enqueue(getMemberDetails);
        setupViews();
    }

    private void setupViews(){
        next = ImageButton.class.cast(findViewById(R.id.next));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            }
        });

        done = Button.class.cast(findViewById(R.id.done));

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //marrim mobile validity nga fragmenti i trete qe ka inputin e numrit te telefonit
                mobile_valid = pagerAdapter.getFragmentByPosition(2).getMobileValidity();
                if(validate())
                    endTutorial();
            }
        });

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new CrossfadePageTransformer());
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //See note above for why this is needed
                if(position == NUM_PAGES - 1 && positionOffset > 0){
                    if(isOpaque) {
                        pager.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }

                } else {
                    if(!isOpaque) {
                        pager.setBackgroundColor(getResources().getColor(R.color.primary));
                        isOpaque = true;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
                if(position == NUM_PAGES - 1){
                    next.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                }else if(position < NUM_PAGES - 1){
                    next.setVisibility(View.VISIBLE);
                    done.setVisibility(View.GONE);
                }else if(position == NUM_PAGES){
                   endTutorial();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Unused
            }
        });

        buildCircles();
    }


    private void buildCircles(){
        circles = LinearLayout.class.cast(findViewById(R.id.circles));

        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (5 * scale + 0.5f);

        for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
            ImageView circle = new ImageView(this);
            circle.setImageResource(R.drawable.circle);
            circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            circle.setAdjustViewBounds(true);
            circle.setPadding(padding, 0, padding, 0);
            circles.addView(circle);
        }

        setIndicator(0);
    }



    private void setIndicator(int index){
        if(index < NUM_PAGES){
            for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
                ImageView circle = (ImageView) circles.getChildAt(i);
                if(i == index){
                    circle.setImageResource(R.drawable.circle_selected);
                }else {
                    circle.setImageResource(R.drawable.circle);
                }
            }
        }
    }


    private boolean validate(){
        boolean flag =true;
        if(name == null || name.length()==0){
            if(flag = true)
            Toast.makeText(this, "Please insert a name!", Toast.LENGTH_SHORT).show();
            flag=false;
        }
        if(last_name == null || last_name.length()==0){
            if(flag = true)
            Toast.makeText(this, "Please insert a last name!", Toast.LENGTH_SHORT).show();
            flag=false;
        }
        if(mobile_number == null || mobile_number.length()==0 || !mobile_valid){
            if(flag = true)
            Toast.makeText(this, "Please insert a valid mobile number!", Toast.LENGTH_SHORT).show();
            flag=false;
        }
        if(education == null || education.length()==0 || education.equals("Education")||pagerAdapter.getFragmentByPosition(2).returnIdFromEducation(education)==-1){
            if(flag = true)
            Toast.makeText(this, "Please insert an education info!", Toast.LENGTH_SHORT).show();
            flag=false;
        }
        if(profession == null || profession.length()==0 || profession.equals("Profession") || pagerAdapter.getFragmentByPosition(3).returnIdFromProfession(profession)==-1){
            if(flag = true)
            Toast.makeText(this, "Please insert a profession info!", Toast.LENGTH_SHORT).show();
            flag=false;
        }
        if(experience_years == null || experience_years.length()==0){
            if(flag = true)
            Toast.makeText(this, "Please insert the amount of experience you have for this profession!", Toast.LENGTH_SHORT).show();
            flag=false;
        }
        return flag;
    }

    private void endTutorial(){
        if(member!=null){
           member.setFirstName(name);
           member.setLastName(last_name);
           member.setPhone(mobile_number);
           member.setEducation(pagerAdapter.getFragmentByPosition(2).returnIdFromEducation(education));
           member.setProfessionId(pagerAdapter.getFragmentByPosition(3).returnIdFromProfession(profession));
           member.setExperienceYears(Integer.parseInt(experience_years));
           requestsAPI.updateMemberWithId(member.getMemberID(), member).enqueue(updateMemberCallback);
        }
        else{
            Toast.makeText(EmployerTutorialActivity.this, "There was a problem on the server side. Please contact the admins!", Toast.LENGTH_SHORT).show();
        }
    }

    Callback<Member> updateMemberCallback = new Callback<Member>() {
        @Override
        public void onResponse(Call<Member> call, Response<Member> response) {
            if(response.isSuccessful()){
                Intent intent = new Intent(EmployerTutorialActivity.this, CodesUtil.EMPLOYER_CLASS);
                startActivity(intent);
            }
            else {

            }
        }

        @Override
        public void onFailure(Call<Member> call, Throwable t) {

        }
    };


    Callback<Member> getMemberDetails =  new Callback<Member>() {
        @Override
        public void onResponse(Call<Member> call, Response<Member> response) {
            if(response.isSuccessful()){
                member = response.body();
            }
            else {

                try {
                    Log.d("Login", "getAspnNetuserId failed : " + response.message() + response.errorBody().string());
                    Toast.makeText(EmployerTutorialActivity.this, "There was a problem with extracting your role. Please contact the admins!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<Member> call, Throwable t) {
            String message = "";
            Toast.makeText(EmployerTutorialActivity.this, "Login failed: Network Error!", Toast.LENGTH_SHORT).show();
            if(t!=null && t.getMessage()!=null){
                message = t.getMessage();
            }
            Log.d("Login", "OnFailure : " + message);
        }
    };



    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<EmployerTutorialPane> employerTutorialPanes;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            employerTutorialPanes = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            EmployerTutorialPane tp = null;
            switch(position){
                case 0:
                    tp = EmployerTutorialPane.newInstance(R.layout.tutorial_employer_welcome,0);
                    employerTutorialPanes.add(tp);
                    break;
                case 1:
                    tp = EmployerTutorialPane.newInstance(R.layout.tutorial_employer_two,1);
                    employerTutorialPanes.add(tp);
                    break;
                case 2:
                    tp = EmployerTutorialPane.newInstance(R.layout.tutorial_employer_three,2);
                    employerTutorialPanes.add(tp);
                    break;
                case 3:
                    tp = EmployerTutorialPane.newInstance(R.layout.tutorial_employer_four,3);
                    employerTutorialPanes.add(tp);
                    break;
                case 4:
                    tp = EmployerTutorialPane.newInstance(R.layout.tutorial_employer_five,4);
                    employerTutorialPanes.add(tp);
                    break;
            }
            return tp;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        public EmployerTutorialPane getFragmentByPosition(int position){
            return employerTutorialPanes.get(position);
        }
    }

    public class CrossfadePageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setExperience_years(String experience_years) {
        this.experience_years = experience_years;
    }

    public void setMobile_valid(boolean mobile_valid) {
        this.mobile_valid = mobile_valid;
    }
}
