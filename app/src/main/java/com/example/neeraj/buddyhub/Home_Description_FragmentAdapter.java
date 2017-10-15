package com.example.neeraj.buddyhub;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ankitgarg on 14/10/17.
 */

public class Home_Description_FragmentAdapter extends FragmentPagerAdapter {
    List<Image_Fragment_Object> list;

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page" +position;
    }

    Context context;
    public Home_Description_FragmentAdapter(FragmentManager fm, Context mcontext, List<Image_Fragment_Object> mlist) {
        super(fm);
        context=mcontext;
        list=mlist;
    }

    @Override
    public Fragment getItem(int position) {
        ImageFragment f = ImageFragment.newInstance();
        Bundle args = new Bundle();
        args.putInt("page_position", position + 1);
        f.setArguments(args);
        f.setImageList(list.get(position));
        return f;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
    @Override
    public int getCount() {
        return list.size();
    }
}
