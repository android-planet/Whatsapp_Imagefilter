package com.example.whatsapp_imagefilter.filepicker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.whatsapp_imagefilter.R;
import com.example.whatsapp_imagefilter.filepicker.FilePickerConst;
import com.example.whatsapp_imagefilter.filepicker.PickerManager;
import com.example.whatsapp_imagefilter.filepicker.adapters.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class MediaPickerFragment extends BaseFragment{

    TabLayout tabLayout;

    ViewPager viewPager;

    private MediaPickerFragmentListener mListener;

    public MediaPickerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media_picker, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MediaPickerFragmentListener) {
            mListener = (MediaPickerFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MediaPickerFragment");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static MediaPickerFragment newInstance() {
        MediaPickerFragment photoPickerFragment = new MediaPickerFragment();
        return  photoPickerFragment;
    }

    public interface MediaPickerFragmentListener {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        tabLayout =  view.findViewById(R.id.tabs);
        viewPager =  view.findViewById(R.id.viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager());

        if(PickerManager.getInstance().showImages()) {
            if (PickerManager.getInstance().isShowFolderView())
                adapter.addFragment(MediaFolderPickerFragment.newInstance(FilePickerConst.MEDIA_TYPE_IMAGE), getString(R.string.images));
            else
                adapter.addFragment(MediaDetailPickerFragment.newInstance(FilePickerConst.MEDIA_TYPE_IMAGE), getString(R.string.images));
        }
        else
            tabLayout.setVisibility(View.GONE);

        if(PickerManager.getInstance().showVideo())
        {
            if(PickerManager.getInstance().isShowFolderView())
                adapter.addFragment(MediaFolderPickerFragment.newInstance(FilePickerConst.MEDIA_TYPE_VIDEO), getString(R.string.videos));
            else
                adapter.addFragment(MediaDetailPickerFragment.newInstance(FilePickerConst.MEDIA_TYPE_VIDEO), getString(R.string.videos));
        }
        else
            tabLayout.setVisibility(View.GONE);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
