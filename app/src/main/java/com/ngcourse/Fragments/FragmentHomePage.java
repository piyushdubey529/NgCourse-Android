package com.ngcourse.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.R;
import com.ngcourse.ResponseInterfaces.ResponseVideoList;
import com.ngcourse.Webservices.CourseVideoListApi;
import com.ngcourse.adapter.AndroidVideoListAdapter;
import com.ngcourse.adapter.Angular2VideoListAdapter;
import com.ngcourse.adapter.AngularVideoListAdapter;
import com.ngcourse.adapter.HorizontalListAdapter;
import com.ngcourse.adapter.IonicVideoListAdapter;
import com.ngcourse.adapter.MongodbVideoListAdapter;
import com.ngcourse.adapter.NodeJsVideoListAdapter;
import com.ngcourse.adapter.SlidingImageAdapter;
import com.ngcourse.beans.HorizantalView;
import com.ngcourse.beans.Video;
import com.ngcourse.utilities.AppProgress;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by piyush on 12/6/17.
 */

public class FragmentHomePage extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener,
        NetworkCallResponse, ResponseVideoList {
    private static String API_TAG;
    private ViewPager viewPager;
    private FragmentActivity mContext;
    private ArrayList<Integer> imageList = new ArrayList<>();
    private static final Integer[] Images = {R.drawable.firstimage, R.drawable.sliderimage1, R.drawable.sliderimage2,
            R.drawable.sliderimage4, R.drawable.sliderimage5, R.drawable.sliderimage6, R.drawable.sliderimage7};
    private int NUM_PAGES = Images.length;
    private int currentPage = 0;
    private ImageView imageViewMiddle;
    private ImageView imageViewBottom;
    private TextView textviewFirst;
    private TextView textviewSecond;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerViewAngularCourse;
    private RecyclerView recyclerViewMongodbCourse;
    private RecyclerView recyclerViewAngular2Course;
    private RecyclerView recyclerViewAndroidCourse;
    private RecyclerView recyclerViewNodeJsCourse;
    private RecyclerView recyclerViewIonicCourse;
    private RecyclerView recyclerViewHorizontal;
    private RecyclerView.LayoutManager mLayoutManager;
    private AngularVideoListAdapter angularVideoListAdapter;
    private MongodbVideoListAdapter mongodbVideoListAdapter;
    private Angular2VideoListAdapter angular2VideoListAdapter;
    private AndroidVideoListAdapter androidVideoListAdapter;
    private NodeJsVideoListAdapter nodeJsVideoListAdapter;
    private HorizontalListAdapter horizontalListAdapter;
    private IonicVideoListAdapter ionicVideoListAdapter;
    private ArrayList<Video> videoList = new ArrayList<>();
    private ArrayList<HorizantalView> horizontalList = new ArrayList<>();
    private ProgressBar progressBar;
    private LinearLayout borderMongodb;
    private LinearLayout borderAngularcourse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_viewpager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        initView();
        setView();
        setTimer();
    }

    private void setTimer() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPage == NUM_PAGES) {
                            currentPage = 0;
                        }
                        viewPager.setCurrentItem(currentPage++, true);
                    }
                });
            }
        }, 1000, 3000);
    }

    private void setView() {
        for (int i = 0; i < Images.length; i++) {
            imageList.add(Images[i]);
        }
        viewPager.setAdapter(new SlidingImageAdapter(mContext, imageList));
        imageViewMiddle.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViewBottom.setScaleType(ImageView.ScaleType.FIT_XY);
        textviewFirst.setText(R.string.text_first);
        textviewSecond.setText(R.string.text_second);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        setHorizontalView();
        setCourseView("angular", recyclerViewAngularCourse);
        setCourseView("MongoDB", recyclerViewMongodbCourse);
        setCourseView("angular2", recyclerViewAngular2Course);
    }

    private void setHorizontalView() {
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(mLayoutManager);
      /*  DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewHorizontal.getContext(), LinearLayoutManager.HORIZONTAL);
        recyclerViewHorizontal.addItemDecoration(dividerItemDecoration);
        recyclerViewHorizontal.setHasFixedSize(false);
        recyclerViewHorizontal.setItemAnimator(new DefaultItemAnimator());*/
        prepareHorizontalList();
        horizontalListAdapter = new HorizontalListAdapter(mContext, horizontalList);
        recyclerViewHorizontal.setAdapter(horizontalListAdapter);
    }

    private void prepareHorizontalList() {
        HorizantalView horizantalView = new HorizantalView(R.drawable.videohorizontal, "Videos");
        HorizantalView horizantalView1 = new HorizantalView(R.drawable.bloghorizontal, "Courses");
        HorizantalView horizantalView2 = new HorizantalView(R.drawable.eventhorizontal, "Events");
        HorizantalView horizantalView3 = new HorizantalView(R.drawable.bloghorizontal1, "Blogs");
        HorizantalView horizantalView4 = new HorizantalView(R.drawable.earming, "Earning");
        HorizantalView horizantalView5 = new HorizantalView(R.drawable.uploadhorizontal, "Upload");
        horizontalList.add(horizantalView);
        horizontalList.add(horizantalView1);
        horizontalList.add(horizantalView2);
        horizontalList.add(horizantalView3);
        horizontalList.add(horizantalView4);
        horizontalList.add(horizantalView5);
    }

    private void setCourseView(String keyword, RecyclerView recyclerView) {
        AppProgress.showProgress(mContext, progressBar);
        CourseVideoListApi courseVideoListApi = new CourseVideoListApi(keyword, "0", "10", mContext);
        courseVideoListApi.getCourseVideoListApi(this, this);
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
      /*  DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
*/        if (keyword.equals("angular")) {
            angularVideoListAdapter = new AngularVideoListAdapter(mContext, videoList);
            recyclerView.setAdapter(angularVideoListAdapter);
        } else if (keyword.equals("MongoDB")) {
            mongodbVideoListAdapter = new MongodbVideoListAdapter(mContext, videoList);
            recyclerView.setAdapter(mongodbVideoListAdapter);
        } else if (keyword.equals("angular2")) {
            angular2VideoListAdapter = new Angular2VideoListAdapter(mContext, videoList);
            recyclerView.setAdapter(angular2VideoListAdapter);
        } else if (keyword.equals("android")) {
            androidVideoListAdapter = new AndroidVideoListAdapter(mContext, videoList);
            recyclerView.setAdapter(androidVideoListAdapter);
        } else if (keyword.equals("nodejs")) {
            nodeJsVideoListAdapter = new NodeJsVideoListAdapter(mContext, videoList);
            recyclerView.setAdapter(nodeJsVideoListAdapter);
        } else if (keyword.equals("ionic")) {
            ionicVideoListAdapter = new IonicVideoListAdapter(mContext, videoList);
            recyclerView.setAdapter(ionicVideoListAdapter);
        }
    }

    private void initView() {
        viewPager = (ViewPager) mContext.findViewById(R.id.viewPager);
        imageViewMiddle = (ImageView) mContext.findViewById(R.id.imageView1);
        imageViewBottom = (ImageView) mContext.findViewById(R.id.imageView2);
        textviewFirst = (TextView) mContext.findViewById(R.id.textview1);
        textviewSecond = (TextView) mContext.findViewById(R.id.textview2);
        bottomNavigationView = (BottomNavigationView) mContext.findViewById(R.id.bottomnavigation);
        recyclerViewAngularCourse = (RecyclerView) mContext.findViewById(R.id.angularcourserecyclerview);
        recyclerViewMongodbCourse = (RecyclerView) mContext.findViewById(R.id.mongodbcourserecyclerview);
        recyclerViewAngular2Course = (RecyclerView) mContext.findViewById(R.id.angular2courserecyclerview);
        recyclerViewAndroidCourse = (RecyclerView) mContext.findViewById(R.id.androidcourserecyclerview);
        recyclerViewNodeJsCourse = (RecyclerView) mContext.findViewById(R.id.nodejscourserecyclerview);
        recyclerViewIonicCourse = (RecyclerView) mContext.findViewById(R.id.ioniccourserecyclerview);
        recyclerViewHorizontal = (RecyclerView) mContext.findViewById(R.id.horizontalrecyclerview);
        progressBar = (ProgressBar) mContext.findViewById(R.id.progress_bar);
        borderMongodb = (LinearLayout) mContext.findViewById(R.id.border_mongodbcourse);
        borderAngularcourse = (LinearLayout) mContext.findViewById(R.id.border_angularcourse);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.action_home:
                fragment = new FragmentHomePage();
                break;
            case R.id.action_videos:
                fragment = new FragmentVideoList();
                break;
            case R.id.action_course:
                fragment = new FragmentCourseList();
                break;
        }
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = mContext.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
        }
        return false;
    }

    @Override
    public void callResponse(Boolean response, String API_TAG) {
        if (response){
            switch (API_TAG){
                case "angular":
                    borderAngularcourse.setVisibility(View.VISIBLE);
                    break;
                case "mongodb":
                    borderMongodb.setVisibility(View.VISIBLE);
                    break;
            }
            borderMongodb.setVisibility(View.VISIBLE);
        }
        this.API_TAG = API_TAG;
        AppProgress.hideProgress(mContext, progressBar);
    }

    @Override
    public void responseVideos(ArrayList<Video> videoList) {
        if (this.API_TAG.equals("angular")) {
            angularVideoListAdapter.videoList = videoList;
            angularVideoListAdapter.notifyDataSetChanged();
        } else if (this.API_TAG.equals("mongodb")) {
            mongodbVideoListAdapter.videoList = videoList;
            mongodbVideoListAdapter.notifyDataSetChanged();
        } else if (this.API_TAG.equals("angular2")) {
            angular2VideoListAdapter.videoList = videoList;
            angular2VideoListAdapter.notifyDataSetChanged();
        } else if (this.API_TAG.equals("android")) {
            androidVideoListAdapter.videoList = videoList;
            androidVideoListAdapter.notifyDataSetChanged();
        } else if (this.API_TAG.equals("nodejs")) {
            nodeJsVideoListAdapter.videoList = videoList;
            nodeJsVideoListAdapter.notifyDataSetChanged();
        } else if (this.API_TAG.equals("ionic")) {

        }
    }
}
