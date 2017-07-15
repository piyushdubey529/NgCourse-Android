package com.ngcourse.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ngcourse.R;

/**
 * Created by piyush on 6/7/17.
 */

public class FragmentUpload extends Fragment implements View.OnClickListener{
    private FragmentActivity mContext;
    private Button uploadBlog;
    private Button uploadVideo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.upload_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        setListener();
    }

    private void setListener() {
       uploadVideo.setOnClickListener(this);
        uploadBlog.setOnClickListener(this);
    }

    private void initView() {
        uploadBlog = (Button) mContext.findViewById(R.id.uploadBlog);
        uploadVideo = (Button) mContext.findViewById(R.id.uploadVideo);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()){
            case R.id.uploadBlog:
              fragment = new FragmentUploadBlog();
                break;
            case R.id.uploadVideo:
                fragment = new FragmentUploadVideo();
                break;
        }
        if(fragment != null){
            FragmentTransaction fragmentTransaction = mContext.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
        }
    }
}
