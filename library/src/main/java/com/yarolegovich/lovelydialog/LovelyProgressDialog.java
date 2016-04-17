package com.yarolegovich.lovelydialog;

import android.content.Context;

/**
 * Created by yarolegovich on 16.04.2016.
 */
public class LovelyProgressDialog extends AbsLovelyDialog<LovelyProgressDialog> {
    public LovelyProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return 0;
    }
}
