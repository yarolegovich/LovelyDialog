package com.yarolegovich.lovelydialog;

import android.content.Context;

/**
 * Created by yarolegovich on 16.04.2016.
 */
public class LovelyInfoDialog extends AbsLovelyDialog<LovelyInfoDialog> {
    public LovelyInfoDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_info;
    }
}
