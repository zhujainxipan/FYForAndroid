package com.ht.fyforandroid.widget.filter;

/**
 * Created by niehongtao on 16/6/17.
 */
public class DropdownItemObject {
    public DropdownItemObject(String text, int id, String value) {
        this.text = text;
        this.id = id;
        this.value = value;
    }

    public int id;
    public String text;
    public String value;
    private String suffix;

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
