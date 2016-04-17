package com.yarolegovich.lovelydialog;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yarolegovich on 16.04.2016.
 */
public class LovelyChoiceDialog extends AbsLovelyDialog<LovelyChoiceDialog> {

    public LovelyChoiceDialog(Context context) {
        super(context);
    }

    public <T> LovelyChoiceDialog setItems(T[] items, OnItemSelectedListener<T> itemSelectedListener) {
        return setItems(Arrays.asList(items), itemSelectedListener);
    }

    public <T> LovelyChoiceDialog setItems(List<T> items, OnItemSelectedListener<T> itemSelectedListener) {
        ArrayAdapter<T> adapter = new ArrayAdapter<T>(getContext(),
                android.R.layout.simple_list_item_1,
                items);
        return setItems(adapter, itemSelectedListener);
    }

    public <T> LovelyChoiceDialog setItems(ArrayAdapter<T> adapter, OnItemSelectedListener<T> itemSelectedListener) {
        ListView choicesList = findView(R.id.ld_choices);
        choicesList.setOnItemClickListener(new ItemSelectedAdapter<>(itemSelectedListener));
        choicesList.setAdapter(adapter);
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_choice;
    }

    private class ItemSelectedAdapter<T> implements AdapterView.OnItemClickListener {

        private OnItemSelectedListener<T> adaptee;

        private ItemSelectedAdapter(OnItemSelectedListener<T> adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (adaptee != null) {
                adaptee.onItemSelected(position, (T) parent.getItemAtPosition(position));
            }
            dismiss();
        }
    }

    public interface OnItemSelectedListener<T> {
        void onItemSelected(int position, T item);
    }
}
