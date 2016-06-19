package com.ht.fyforandroid;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ht.fyforandroid.base.BaseActivity;
import com.ht.fyforandroid.widget.filter.DropDownButton;
import com.ht.fyforandroid.widget.filter.DropdownItemObject;
import com.ht.fyforandroid.widget.filter.DropdownListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by niehongtao on 16/6/16.
 */
public class MainActivity extends BaseActivity {
    @InjectView(R.id.btn_close)
    Button mBtnClose;
    @InjectView(R.id.chooseType)
    DropDownButton mChooseType;
    @InjectView(R.id.chooseLabel)
    DropDownButton mChooseLabel;
    @InjectView(R.id.chooseOrder)
    DropDownButton mChooseOrder;
    @InjectView(R.id.tabs)
    LinearLayout mTabs;
    @InjectView(R.id.mask)
    View mMask;
    @InjectView(R.id.dropdownType)
    DropdownListView mDropdownType;
    @InjectView(R.id.dropdownLabel)
    DropdownListView mDropdownLabel;
    @InjectView(R.id.dropdownOrder)
    DropdownListView mDropdownOrder;

    private static final int ID_TYPE_ALL = 0;
    private static final int ID_TYPE_MY = 1;
    private static final String TYPE_ALL = "全部讨论";
    private static final String TYPE_MY = "我的讨论";
    private static final int ID_LABEL_ALL = -1;
    private static final String LABEL_ALL = "全部标签";
    private static final String ORDER_REPLY_TIME = "最后评论排序";
    private static final String ORDER_PUBLISH_TIME = "发布时间排序";
    private static final String ORDER_HOT = "热门排序";
    private static final int ID_ORDER_REPLY_TIME = 51;
    private static final int ID_ORDER_PUBLISH_TIME = 49;
    private static final int ID_ORDER_HOT = 53;


    private DropdownButtonsController dropdownButtonsController = new DropdownButtonsController();

    Animation dropdown_in, dropdown_out, dropdown_mask_out;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.mLoadingDialog.hideLoading();
    }

    @Override
    protected void initView() {
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        dropdown_mask_out = AnimationUtils.loadAnimation(this, R.anim.dropdown_mask_out);
        dropdown_out = AnimationUtils.loadAnimation(this, R.anim.dropdown_out);
        dropdown_in = AnimationUtils.loadAnimation(this, R.anim.dropdown_in);


        mMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownButtonsController.hide();
            }
        });

        dropdownButtonsController.init();
//        dropdownButtonsController.flushAll(true);


    }

    @Override
    protected void initData() {

    }

    private class DropdownButtonsController implements DropdownListView.Container {
        private DropdownListView currentDropdownList;
        private List<DropdownItemObject> datasetType = new ArrayList<>(2);
        private List<DropdownItemObject> datasetAllLabel = new ArrayList<>();
        private List<DropdownItemObject> datasetMyLabel = new ArrayList<>();
        private List<DropdownItemObject> datasetLabel = datasetAllLabel;
        private List<DropdownItemObject> datasetOrder = new ArrayList<>(3);

        @Override
        public void show(DropdownListView view) {
            if (currentDropdownList != null) {
                currentDropdownList.clearAnimation();
                currentDropdownList.startAnimation(dropdown_out);
                currentDropdownList.setVisibility(View.GONE);
                currentDropdownList.button.setChecked(false);
            }
            currentDropdownList = view;
            mMask.clearAnimation();
            mMask.setVisibility(View.VISIBLE);
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_in);
            currentDropdownList.setVisibility(View.VISIBLE);
            currentDropdownList.button.setChecked(true);
        }

        @Override
        public void hide() {
            if (currentDropdownList != null) {
                currentDropdownList.clearAnimation();
                currentDropdownList.startAnimation(dropdown_out);
                currentDropdownList.button.setChecked(false);
                mMask.clearAnimation();
                mMask.startAnimation(dropdown_mask_out);
            }
            currentDropdownList = null;
        }

        @Override
        public void onSelectionChanged(DropdownListView view) {
            if (view == mDropdownType) {
                updateLabels(getCurrentLabels());
            }
//            onRefresh();
        }

        void reset() {
            mChooseType.setChecked(false);
            mChooseLabel.setChecked(false);
            mChooseOrder.setChecked(false);

            mDropdownType.setVisibility(View.GONE);
            mDropdownLabel.setVisibility(View.GONE);
            mDropdownOrder.setVisibility(View.GONE);
            mMask.setVisibility(View.GONE);

            mDropdownType.clearAnimation();
            mDropdownLabel.clearAnimation();
            mDropdownOrder.clearAnimation();
            mMask.clearAnimation();
        }

        void init() {
            reset();
            datasetType.add(new DropdownItemObject(TYPE_ALL, ID_TYPE_ALL, "all"));
            datasetType.add(new DropdownItemObject(TYPE_MY, ID_TYPE_MY, "my"));
            mDropdownType.bind(datasetType, mChooseType, this, ID_TYPE_ALL);

            datasetAllLabel.add(new DropdownItemObject(LABEL_ALL, ID_LABEL_ALL, null) {
                @Override
                public String getSuffix() {
                    return mDropdownType.current == null ? "" : mDropdownType.current.getSuffix();
                }
            });
            datasetMyLabel.add(new DropdownItemObject(LABEL_ALL, ID_LABEL_ALL, null));
            datasetLabel = datasetAllLabel;
            mDropdownLabel.bind(datasetLabel, mChooseLabel, this, ID_LABEL_ALL);

            datasetOrder.add(new DropdownItemObject(ORDER_REPLY_TIME, ID_ORDER_REPLY_TIME, "51"));
            datasetOrder.add(new DropdownItemObject(ORDER_PUBLISH_TIME, ID_ORDER_PUBLISH_TIME, "49"));
            datasetOrder.add(new DropdownItemObject(ORDER_HOT, ID_ORDER_HOT, "53"));
            mDropdownOrder.bind(datasetOrder, mChooseOrder, this, ID_ORDER_REPLY_TIME);

            dropdown_mask_out.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (currentDropdownList == null) {
                        reset();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        private List<DropdownItemObject> getCurrentLabels() {
            return mDropdownType.current != null && mDropdownType.current.id == ID_TYPE_MY ? datasetMyLabel : datasetAllLabel;
        }

        void updateLabels(List<DropdownItemObject> targetList) {
            if (targetList == getCurrentLabels()) {
                datasetLabel = targetList;
                mDropdownLabel.bind(datasetLabel, mChooseLabel, this, mDropdownLabel.current.id);
            }
        }
    }
}
