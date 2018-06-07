package com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic;

import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import com.example.w2020skerdjan.jobmanager.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;

public class EmployerMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Toolbar toolbar;
    private SupportMapFragment mapFragment;
    private View slideUpEmployer;
    private SlideUp slideUp;
    private RelativeLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        setupViews();
        mapFragment.getMapAsync(this);

    }


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
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void setupViews(){
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

       /* slideUp = new SlideUpBuilder(slideUpEmployer)
                .withStartState(SlideUp.State.SHOWED)
                .withStartGravity(Gravity.BOTTOM)
                //.withSlideFromOtherView(anotherView)
                .withGesturesEnabled(true)
                .withHideSoftInputWhenDisplayed(true)
                //.withInterpolator()
                //.withAutoSlideDuration()
                //.withLoggingEnabled()
                //.withTouchableAreaPx()
                //.withTouchableAreaDp()
                .withListeners(new SlideUp.Listener.Slide() {
                    @Override
                    public void onSlide(float percent) {
                        Log.d("SkerdiSlide", "Sliding");
                    }
                })
                //.withSavedState()
                .build();*/

       slideUp = new SlideUpBuilder(slideUpEmployer)
               .withListeners(new SlideUp.Listener.Events() {
                   @Override
                   public void onSlide(float percent) {
                   }

                   @Override
                   public void onVisibilityChanged(int visibility) {
                       if (visibility == View.GONE){
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

}
