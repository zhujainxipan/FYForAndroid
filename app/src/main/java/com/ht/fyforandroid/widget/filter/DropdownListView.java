package com.ht.fyforandroid.widget.filter;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.ht.fyforandroid.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by niehongtao on 16/6/17.
 */
public class DropdownListView extends ScrollView {
    private Context mContext;
    private LinearLayout mLl;
    public DropdownItemObject current;
    List<? extends DropdownItemObject> list;
    public DropDownButton button;

    public DropdownListView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public DropdownListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        View.inflate(mContext, R.layout.dropdown_tab_list, this);
        mLl = (LinearLayout) findViewById(R.id.linearLayout);
    }

    public void flush() {
        for (int i = 0, n = mLl.getChildCount(); i < n; i++) {
            View view = mLl.getChildAt(i);
            if (view instanceof DropdownListItemView) {
                DropdownListItemView itemView = (DropdownListItemView) view;
                DropdownItemObject data = (DropdownItemObject) itemView.getTag();
                if (data == null) return;
                boolean checked = data == current;
                String suffix = data.getSuffix();
                itemView.bind(TextUtils.isEmpty(suffix) ? data.text : data.text + suffix, checked);
                if (checked) button.setText(data.text);
            }
        }
    }

    public void bind(List<? extends DropdownItemObject> list, DropDownButton button, final Container container, int selectedId) {
        current = null;
        this.list = list;
        this.button = button;

        LinkedList<View> cachedDividers = new LinkedList<>();
        LinkedList<DropdownListItemView> cachedViews = new LinkedList<>();
        for (int i = 0, n = mLl.getChildCount(); i < n; i++) {
            View view = mLl.getChildAt(i);
            if (view instanceof DropdownListItemView) {
                cachedViews.add((DropdownListItemView) view);
            } else {
                cachedDividers.add(view);
            }
        }
        mLl.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        boolean isFirst = true;
        for (DropdownItemObject item : list) {
            if (isFirst) {
                isFirst = false;
            } else {
                View divider = cachedDividers.poll();
                if (divider == null)
                    divider = inflater.inflate(R.layout.dropdown_tab_list_divider, mLl, false);
                mLl.addView(divider);
            }
            DropdownListItemView view = cachedViews.poll();
            if (view == null)
                view = (DropdownListItemView) inflater.inflate(R.layout.dropdown_tab_list_item, mLl, false);
            view.setTag(item);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DropdownItemObject data = (DropdownItemObject) v.getTag();
                    if (data == null) return;
                    DropdownItemObject oldOne = current;
                    current = data;
                    flush();
                    container.hide();
                    if (oldOne != current) container.onSelectionChanged(DropdownListView.this);
                }
            });
            mLl.addView(view);
            if (item.id == selectedId && current == null) current = item;
        }

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getVisibility() == View.VISIBLE) {
                    container.hide();
                } else {
                    container.show(DropdownListView.this);
                }
            }
        });
        if (current == null && list.size() > 0) {
            current = list.get(0);
        }
        flush();
    }

    public interface Container {
        void show(DropdownListView listView);

        void hide();

        void onSelectionChanged(DropdownListView view);
    }

}
