package com.yarolegovich.lovelydialog;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yarolegovich on 16.04.2016.
 */
public class LovelyCustomDialog extends AbsLovelyDialog<LovelyCustomDialog> {

    private View addedView;

    public LovelyCustomDialog(Context context) {
        super(context);
    }

    public LovelyCustomDialog setView(@LayoutRes int layout) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        ViewGroup parent = findView(R.id.ld_custom_view_container);
        addedView = inflater.inflate(layout, parent, true);
        return this;
    }

    public LovelyCustomDialog setView(View customView) {
        ViewGroup container = findView(R.id.ld_custom_view_container);
        container.addView(customView);
        addedView = customView;
        return this;
    }

    public LovelyCustomDialog setListener(int viewId, View.OnClickListener listener) {
        return setListener(viewId, false, listener);
    }

    public LovelyCustomDialog configureView(ViewConfigurator configurator) {
        if (addedView == null) {
            throw new IllegalStateException("You must call method setView before calling configureView");
        }
        configurator.configureView(addedView);
        return this;
    }

    public LovelyCustomDialog setListener(int viewId, boolean dismissOnClick, View.OnClickListener listener) {
        View.OnClickListener clickListener = dismissOnClick ?
                new CloseOnClickDecorator(listener) :
                listener;
        findView(viewId).setOnClickListener(clickListener);
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_custom;
    }

    public interface ViewConfigurator {
        void configureView(View v);
    }
}
