package com.ngcourse.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ngcourse.R;

import java.util.ArrayList;

/**
 * Created by piyush on 12/6/17.
 */

public class SlidingImageAdapter extends PagerAdapter {

    private ArrayList<Integer> imageList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public SlidingImageAdapter(Context mContext, ArrayList<Integer> imageList){
        this.mContext = mContext;
        this.imageList = imageList;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      View view = layoutInflater.inflate(R.layout.image_layout, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(imageList.get(position));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(view, 0);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
