package com.flowz.clarigojobtaskapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.flowz.clarigojobtaskapp.ui.AddFragment;
import com.flowz.clarigojobtaskapp.ui.ListFragment;

public class ViewPagerAdapter  extends FragmentStateAdapter {

    public ViewPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {

            case 0:
                return new ListFragment();

            case 1:
                return new AddFragment();
        }
      return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
