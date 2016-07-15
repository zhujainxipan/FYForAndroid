package com.ht.fyforandroid.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by niehongtao on 16/7/15.
 */
public class CommonFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public CommonFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment ret = null;
        if (fragments != null) {
            ret = fragments.get(position);
        }
        return ret;
    }
    @Override
    public int getCount() {
        int ret = 0;
        if (fragments != null) {
            ret = fragments.size();
        }
        return ret;
    }
}
