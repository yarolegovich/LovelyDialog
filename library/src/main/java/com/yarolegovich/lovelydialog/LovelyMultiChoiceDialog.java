package com.yarolegovich.lovelydialog;

import android.content.Context;

/**
 * Created by yarolegovich on 16.04.2016.
 */
public class LovelyMultiChoiceDialog extends AbsLovelyDialog<LovelyMultiChoiceDialog> {
    public LovelyMultiChoiceDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return 0;
    }
}
