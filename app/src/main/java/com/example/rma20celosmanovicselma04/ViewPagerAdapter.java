package com.example.rma20celosmanovicselma04;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        i++;
        if(i % 2 == 1) {
            return new TransactionListFragment();
        }
        else if(i % 2 == 0) {
            return new BudgetFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
