package com.yarolegovich.lovelydialog;

import android.content.Context;

/**
 * Created by yarolegovich on 16.04.2016.
 */
public class LovelyDialogs {

    public static LovelyStandardDialog standardDialog(Context context) {
        return new LovelyStandardDialog(context);
    }

    public static LovelyChoiceDialog singleChoiceDialog(Context context) {
        return new LovelyChoiceDialog(context);
    }

    public static LovelyTextInputDialog textInputDialog(Context context) {
        return new LovelyTextInputDialog(context);
    }

    public static LovelyMultiChoiceDialog multiChoiceDialog(Context context) {
        return new LovelyMultiChoiceDialog(context);
    }

    public static LovelyCustomDialog customDialog(Context context) {
        return new LovelyCustomDialog(context);
    }

    public static LovelyProgressDialog progressDialog(Context context) {
        return new LovelyProgressDialog(context);
    }

    public static LovelyInfoDialog infoDialog(Context context) {
        return new LovelyInfoDialog(context);
    }
}
