package com.ishabaev.testapp.presenter.currency;

import android.support.v7.util.DiffUtil;

import com.ishabaev.testapp.model.Currency;

import java.util.List;

public class MyDiffCallback extends DiffUtil.Callback {

    List<Currency> oldPersons;
    List<Currency> newPersons;

    public MyDiffCallback(List<Currency> newPersons, List<Currency> oldPersons) {
        this.newPersons = newPersons;
        this.oldPersons = oldPersons;
    }

    @Override
    public int getOldListSize() {
        return oldPersons.size();
    }

    @Override
    public int getNewListSize() {
        return newPersons.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).getName().equals(newPersons.get(newItemPosition).getName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).getValue() == newPersons.get(newItemPosition).getValue();
    }

    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
