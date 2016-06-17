package com.ht.fyforandroid.widget.filter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ht.fyforandroid.R;

/**
 * Created by niehongtao on 16/6/17.
 */
public class DropdownListItemView extends TextView {
    public DropdownListItemView(Context context) {
        super(context);
    }

    public DropdownListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DropdownListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DropdownListItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(CharSequence text, boolean checked) {
        setText(text);
        setChecked(checked);
    }

    public void setChecked(boolean checked) {
        if (checked) {
            Drawable icon = getResources().getDrawable(R.mipmap.ic_task_status_list_check);
            setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }


}