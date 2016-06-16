package com.ht.fyforandroid;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ht.fyforandroid.base.BaseFragment;

/**
 * Created by niehongtao on 16/6/16.
 */
public class MainFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isOpenEventBus() {
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId_ = item.getItemId();

        if (itemId_ == R.id.action_create_maopao) {
            action_create_maopao();
            return true;
        }
        if (itemId_ == R.id.action_scan) {
            action_scan();
            return true;
        }
        if (itemId_ == R.id.action_create_task) {
            action_create_task();
            return true;
        }
        if (itemId_ == R.id.action_2fa) {
            action_2fa();
            return true;
        }
        if (itemId_ == R.id.action_create) {
            action_create();
            return true;
        }
        if (itemId_ == R.id.action_create_friend) {
            action_create_friend();
            return true;
        }
        if (itemId_ == R.id.action_search) {
            action_search();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void action_search() {

    }

    private void action_create_friend() {

    }

    private void action_create() {

    }

    private void action_2fa() {

    }

    private void action_create_task() {

    }

    private void action_scan() {

    }

    private void action_create_maopao() {

    }
}
