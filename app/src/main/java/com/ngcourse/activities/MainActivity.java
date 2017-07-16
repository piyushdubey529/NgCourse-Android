package com.ngcourse.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngcourse.Fragments.FragmentCourseList;
import com.ngcourse.Fragments.FragmentEventList;
import com.ngcourse.Fragments.FragmentHomePage;
import com.ngcourse.Fragments.FragmentUploadVideo;
import com.ngcourse.Fragments.FragmentVideoList;
import com.ngcourse.R;
import com.ngcourse.utilities.FontAwesome;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView searchIcon;
    private LinearLayout searchLayout;
    private EditText searchInput;
    private FontAwesome filterIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.inflateHeaderView(R.layout.headerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        displayView(R.id.home_page);
        setListener();
    }

    private void setListener() {
        searchIcon.setOnClickListener(this);
    }

    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchIcon = (TextView) findViewById(R.id.search_icon);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        searchInput = (EditText) findViewById(R.id.searchInput);
        filterIcon = (FontAwesome) findViewById(R.id.filter_icon);
        filterIcon.setVisibility(View.GONE);
    }

    private void displayView(int itemId) {
        Fragment fragment = null;
        switch (itemId){
            case R.id.home_page:
                fragment = new FragmentHomePage();
                break;
            case R.id.videos:
                fragment = new FragmentVideoList();
                break;
            case R.id.courses:
                fragment = new FragmentCourseList();
                break;
            case R.id.upload:
                fragment = new FragmentUploadVideo();
                break;
            case R.id.events:
                fragment = new FragmentEventList();
                break;
            default:
              //  fragment = new Fragmentone();
        }
        if(fragment != null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
        }
        drawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displayView(item.getItemId());
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_page:
                drawerLayout.openDrawer(Gravity.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflowmenu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
     }
    }
}
