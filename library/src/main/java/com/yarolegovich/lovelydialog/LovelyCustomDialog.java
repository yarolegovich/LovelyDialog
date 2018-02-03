package com.yarolegovich.lovelydialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yarolegovich on 16.04.2016.
 */
public class LovelyCustomDialog extends AbsLovelyDialog<LovelyCustomDialog> {

    private View addedView;
    private InstanceStateManager instanceStateManager;

    public LovelyCustomDialog(Context context) {
        super(context);
    }

    public LovelyCustomDialog(Context context, int theme) {
        super(context, theme);
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

    @Override
    public LovelyCustomDialog configureView(@NonNull ViewConfigurator<View> configurator) {
        if (addedView == null) {
            throw new IllegalStateException(string(R.string.ex_msg_dialog_view_not_set));
        }
        configurator.configureView(addedView);
        return this;
    }

    public LovelyCustomDialog setListener(int viewId, View.OnClickListener listener) {
        return setListener(viewId, false, listener);
    }

    public LovelyCustomDialog setListener(int viewId, boolean dismissOnClick, View.OnClickListener listener) {
        if (addedView == null) {
            throw new IllegalStateException(string(R.string.ex_msg_dialog_view_not_set));
        }
        View.OnClickListener clickListener = new ClickListenerDecorator(listener, dismissOnClick);
        findView(viewId).setOnClickListener(clickListener);
        return this;
    }

    public LovelyCustomDialog setInstanceStateManager(InstanceStateManager instanceStateManager) {
        this.instanceStateManager = instanceStateManager;
        return this;
    }

    @Override
    void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        instanceStateManager.saveInstanceState(outState);
    }

    @Override
    void restoreState(Bundle savedState) {
        super.restoreState(savedState);
        instanceStateManager.restoreInstanceState(savedState);
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_custom;
    }


    public interface InstanceStateManager {
        void saveInstanceState(Bundle outState);

        void restoreInstanceState(Bundle savedState);
    }
}
