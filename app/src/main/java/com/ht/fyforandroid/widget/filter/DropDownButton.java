package com.ht.fyforandroid.widget.filter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ht.fyforandroid.R;

/**
 * Created by niehongtao on 16/6/17.
 */
public class DropDownButton extends RelativeLayout {
    private Context mContext;
    private TextView mTextView;
    private View bottomLine;

    public DropDownButton(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public DropDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        View.inflate(mContext, R.layout.dropdown_tab_button, this);
        mTextView = (TextView) findViewById(R.id.textView);
        bottomLine = findViewById(R.id.bottomLine);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public void setChecked(boolean checked) {
        Drawable icon;
        if (checked) {
            icon = getResources().getDrawable(R.mipmap.ic_dropdown_actived);
            mTextView.setTextColor(getResources().getColor(R.color.green));
            bottomLine.setVisibility(VISIBLE);
        } else {
            icon = getResources().getDrawable(R.mipmap.ic_dropdown_normal);
            mTextView.setTextColor(getResources().getColor(R.color.font_black_content));
            bottomLine.setVisibility(GONE);
        }
        mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }
}
