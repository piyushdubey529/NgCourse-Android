package com.ngcourse.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.R;
import com.ngcourse.utilities.AppProgress;
import java.io.File;

/**
 * Created by piyush on 6/7/17.
 */

public class FragmentUploadBlog extends Fragment implements View.OnClickListener, NetworkCallResponse{

    private Button upload;
    private ImageView browse;
    private TextView selected;
    private FragmentActivity mContext;
    private int FILE_REQUEST_CODE = 101;
    private File selectedFile;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.upload_video_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        setListener();
    }

    private void setListener() {
        browse.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    private void initView() {
        browse = (ImageView) mContext.findViewById(R.id.browse);
        selected = (TextView) mContext.findViewById(R.id.selected);
        upload = (Button) mContext.findViewById(R.id.upload);
        progressBar = (ProgressBar) mContext.findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.browse:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, FILE_REQUEST_CODE);
                break;

            case R.id.upload:
               /* UploadVideoApi uploadVideoApi = new UploadVideoApi(mContext, selectedFile);
                uploadVideoApi.uploadVideoApi(this);*/
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == FILE_REQUEST_CODE){
            String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            if(data != null){
                String fileName = data.getData().getPath();
                String[] paths = fileName.split(":");
                fileName = absolutePath + "/" + paths[1];
                selectedFile = new File(fileName);
                selected.setText("Selected: "+fileName);
                Toast.makeText(mContext, fileName, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void callResponse(Boolean response, String API_TAG) {
        AppProgress.hideProgress(mContext, progressBar);
    }
}
