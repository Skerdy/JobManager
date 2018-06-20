package com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.w2020skerdjan.jobmanager.Adapters.JobPickerAdapter;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.EmployeeMap;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.JobType;
import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.Retrofit.Requests.RequestsAPI;
import com.example.w2020skerdjan.jobmanager.Retrofit.RetrofitClient;
import com.example.w2020skerdjan.jobmanager.Utils.CodesUtil;
import com.example.w2020skerdjan.jobmanager.Utils.Utils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager;

public class EmployerMap extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Toolbar toolbar;
    private SupportMapFragment mapFragment;
    private View slideUpEmployer;
    private SlideUp slideUp;
    private RelativeLayout rootView, topHeader;
    private RecyclerView rv;
    private JobPickerAdapter jobPickerAdapter;
    private Button findEmployee;
    private RetrofitClient retrofitClient;
    private Retrofit retrofit;
    private RequestsAPI requestsAPI;
    private ImageView leftArrow, rightArrow;
    private ArrayList<Marker> markers;
    private PickerLayoutManager pickerLayoutManager;
    private int selectedJobId;
    private List<JobType> jobTypes;
    private MaterialDialog dialog;
    private CardView cardView;
    private int positionOfRecycler = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Intent intent = getIntent();


        setupViews();
        setupRetrofit();
        markers = new ArrayList<>();
        jobTypes = new ArrayList<>();
        requestsAPI.getAllJobsTypes().enqueue(jobTypesCallback);
        findEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestsAPI.getUsersForJob("15026256-6c63-4bea-b816-d5abbb37a3c0", ""+selectedJobId).enqueue(usersCallback);
                slideUp.hide();

            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

         dialog = new MaterialDialog.Builder(EmployerMap.this)
                .title("Not Found")
                .content("No Employees were found for that job. Please try again later, or try another job below !")
                .positiveText("Ok")
                .build();

        setupArrowNavigationLogic();

        if(intent.getStringExtra(CodesUtil.INIT_MAP)!=null && intent.getStringExtra(CodesUtil.INIT_MAP).equals(CodesUtil.FROM_JOB_CLICK)){
            //kemi ardhur tek acitivity i map duke klikuar tek nje job nga lista e tere puneve prandaj bejme direkt thirrjen call dhe ulim panelin e search .
            Log.d("EmployeeMap", "Erdhem nga JobClick");
            slideUp.hide();
            requestsAPI.getUsersForJob("15026256-6c63-4bea-b816-d5abbb37a3c0", ""+ intent.getIntExtra(CodesUtil.JOB_ID,1)).enqueue(usersCallback);
        }
    }

    private int  getSelectedJobId(String jobName) {
        for(JobType jobType : jobTypes){
            if(jobType.getJobTitle().equals(jobName)){
               return (int) jobType.getJobId();
            }
        }
        return -1;
    }

    Callback<List<EmployeeMap>> usersCallback = new Callback<List<EmployeeMap>>() {
        @Override
        public void onResponse(Call<List<EmployeeMap>> call, Response<List<EmployeeMap>> response) {
            if(response.isSuccessful()){
                    slideUp.hide();
                    putMarkersFromCall(response.body());
                    if(response.body().isEmpty()){
                    dialog.show();
                    }
            }
            else {
                try {
                    dialog.show();
                    Log.d("Login", "Get All Employees Failed : " + response.message() + response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<List<EmployeeMap>> call, Throwable t) {
            String message = "";
            Toast.makeText(getBaseContext(), "Login failed: Network Error!", Toast.LENGTH_SHORT).show();
            if(t!=null && t.getMessage()!=null){
                message = t.getMessage();
            }
            Log.d("Login", "OnFailure : " + message);
        }
    };


    Callback<List<JobType>> jobTypesCallback = new Callback<List<JobType>>() {
        @Override
        public void onResponse(Call<List<JobType>> call, Response<List<JobType>> response) {
            if(response.isSuccessful()){
                jobTypes.clear();
                jobTypes.addAll(response.body());
                jobPickerAdapter.setDataList(Utils.getJobsTypesFromResponse(response.body()));
            }
            else {
                try {

                    Log.d("Login", "Get All Job Types Failed : " + response.message() + response.errorBody().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }


               // showDialogWithMessage("Not Found", "No Employees were found for that job. Please try again later, or try another job below !");
            }
        }

        @Override
        public void onFailure(Call<List<JobType>> call, Throwable t) {
            String message = "";
            Toast.makeText(EmployerMap.this, "Login failed: Network Error!", Toast.LENGTH_SHORT).show();
            if(t!=null && t.getMessage()!=null){
                message = t.getMessage();
            }
            Log.d("Login", "OnFailure : " + message);
        }
    };


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void putMarkersFromCall(List<EmployeeMap> employeeMaps){
        LatLng latLng;
        LatLngBounds.Builder builder;
        Marker markertmp;
        removeMarkers();
        for(EmployeeMap employeeMap :employeeMaps){
            latLng = new LatLng(employeeMap.getGeoLatMap(),employeeMap.getGeoLongMap());
            markertmp = mMap.addMarker(new MarkerOptions().position(latLng).title(employeeMap.getUserNameMap()));
            markers.add(markertmp);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        builder = new LatLngBounds.Builder();

        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);
    }

    private void removeMarkers(){
        for(Marker marker: markers){
            marker.remove();
        }
        markers.clear();
    }

    private void setupViews(){
        cardView = findViewById(R.id.refreshButton);
        topHeader = findViewById(R.id.topHeaderPanel);
        leftArrow = findViewById(R.id.leftArrow);
        rightArrow = findViewById(R.id.rightArrow);
        rootView = findViewById(R.id.fragmentLogin);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        toolbar = findViewById(R.id.toolbar);
        slideUpEmployer = findViewById(R.id.slideUpEmployer);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setActionBar(toolbar);
            toolbar.setTitleTextColor(getResources().getColor(R.color.md_white_1000));
            toolbar.setTitle("Employees");
        }

        setTitle("Home");
        getActionBar().setDisplayHomeAsUpEnabled(true);

       slideUp = new SlideUpBuilder(slideUpEmployer)
               .withListeners(new SlideUp.Listener.Events() {
                   @Override
                   public void onSlide(float percent) {
                   }

                   @Override
                   public void onVisibilityChanged(int visibility) {
                       if (visibility == View.GONE){
                           topHeader.setVisibility(View.VISIBLE);
                       }
                   }
               })
               .withStartGravity(Gravity.BOTTOM)
               .withLoggingEnabled(true)
               .withGesturesEnabled(true)
               .withStartState(SlideUp.State.SHOWED
               )
               .withSlideFromOtherView(findViewById(R.id.fragmentLogin))
               .build();
        slideUp.show();

        topHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topHeader.setVisibility(View.GONE);
                slideUp.show();
            }
        });

        mapFragment.getMapAsync(this);
        rv = (RecyclerView) findViewById(R.id.rv);
        findEmployee = findViewById(R.id.findEmployee);
        pickerLayoutManager = new PickerLayoutManager(this, PickerLayoutManager.HORIZONTAL, false);
        pickerLayoutManager.setChangeAlpha(true);
        pickerLayoutManager.setScaleDownBy(0.99f);
        pickerLayoutManager.setScaleDownDistance(0.8f);

        jobPickerAdapter = new JobPickerAdapter(this, new ArrayList<String>(), rv);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rv);
        rv.setLayoutManager(pickerLayoutManager);
        rv.setAdapter(jobPickerAdapter);
        rv.smoothScrollToPosition(1);

        pickerLayoutManager.setOnScrollStopListener(new PickerLayoutManager.onScrollStopListener() {
            @Override
            public void selectedView(View view) {
                if(rv.getAdapter().getItemCount()>0) {
                    TextView textView = (TextView) view.findViewById(R.id.job_item);
                    selectedJobId = getSelectedJobId(textView.getText().toString());
                    reCalculateRecyclerViewPosition(textView.getText().toString());
                }
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRetrofit(){
        retrofitClient = new RetrofitClient();
        retrofit = retrofitClient.krijoRetrofit();
        requestsAPI = retrofit.create(RequestsAPI.class);
    }

    private void showDialogWithMessage(String title , String message){
       /* new MaterialDialog.Builder(EmployerMap.this)
                .title(title)
                .content(message)
                .positiveText("OK")
                .build()
                .show();*/
       Log.d("Login", "initializing dialog");

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(title)
                .content(message)
                .positiveText("Ok")
                .show();
    }

    private void setupArrowNavigationLogic(){
        positionOfRecycler = 1;
      //  final LinearSmoothScroller smoothScroller = new LinearSmoothScroller(EmployerMap.this);
        final LinearSmoothScroller smoothScroller = new LinearSmoothScroller(rv.getContext()) {

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 500 / displayMetrics.densityDpi;
            }
        };

        smoothScroller.setTargetPosition(positionOfRecycler);
        rv.getLayoutManager().startSmoothScroll(smoothScroller);
        leftArrow.setVisibility(View.INVISIBLE);
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ArrowPressed" , "Pressed Left");
                if(positionOfRecycler-1<=-1)
                    positionOfRecycler=-1;
                else
                    positionOfRecycler--;
               // rv.getLayoutManager().scrollToPosition(positionOfRecycler);

                smoothScroller.setTargetPosition(positionOfRecycler);
                rv.getLayoutManager().startSmoothScroll(smoothScroller);
              //  rv.getLayoutManager().smoothScrollToPosition(rv,null,positionOfRecycler);
                reCalculateVisibilityOfArrows(positionOfRecycler);
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ArrowPressed" , "Pressed Right");

                if(positionOfRecycler+1>jobTypes.size())
                    positionOfRecycler=jobTypes.size();
                else{
                    positionOfRecycler++;
                }
                smoothScroller.setTargetPosition(positionOfRecycler);
                rv.getLayoutManager().startSmoothScroll(smoothScroller);
              //  rv.getLayoutManager().smoothScrollToPosition(rv,null,positionOfRecycler);
                reCalculateVisibilityOfArrows(positionOfRecycler);
            }
        });
    }

    private void reCalculateRecyclerViewPosition(String jobName){
        for(int i =0 ; i<jobTypes.size(); i++){
            if(jobTypes.get(i).getJobTitle().equals(jobName)){
                this.positionOfRecycler = i;
                reCalculateVisibilityOfArrows(i);
                break;
            }
        }
    }

    //i eshte position aktual i sapollogaritur
    private void reCalculateVisibilityOfArrows(int i){
        if(i<=0){
            leftArrow.setVisibility(View.INVISIBLE);
        }
        else {
            leftArrow.setVisibility(View.VISIBLE);
        }

        if(i>=jobTypes.size()-1)
        {
            rightArrow.setVisibility(View.INVISIBLE);
        }
        else{
            rightArrow.setVisibility(View.VISIBLE);
        }
    }

}
