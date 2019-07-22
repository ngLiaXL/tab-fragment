package com.ngliaxl.tab;

import android.view.View;
import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;
import java.util.Objects;

public class TabViewBuilder {

    private Fragment[] fragments;
    private View[] tabViews;

    private int currentIndex;
    private FragmentManager fragmentManager;
    @IdRes
    private int containerLayoutId;

    private int defaultSelectedIndex = 0;

    private boolean lazyCreateView;

    public TabViewBuilder(FragmentManager fm) {
        this.fragmentManager = fm;
    }

    public TabViewBuilder setFragments(List<Fragment> fragmentList) {
        if (fragmentList != null) {
            fragments = fragmentList.toArray(new Fragment[0]);
        }
        return this;
    }

    public TabViewBuilder setTabViews(List<View> viewList) {
        if (viewList != null) {
            tabViews = viewList.toArray(new View[0]);
        }
        return this;
    }

    public TabViewBuilder setContainerLayoutId(@IdRes int containerLayoutId) {
        this.containerLayoutId = containerLayoutId;
        return this;
    }

    public TabViewBuilder setDefaultSelectedIndex(int index) {
        this.defaultSelectedIndex = index;
        return this;
    }

    public TabViewBuilder setLazyCreateView(boolean lazyCreateView) {
        this.lazyCreateView = lazyCreateView;
        return this;
    }

    public void build() {
        if (fragments == null || tabViews == null) {
            throw new RuntimeException("fragments and tab views have not be setup");
        }

        FragmentTransaction tran = fragmentManager.beginTransaction();
        for (Fragment fg : fragments) {
            if (fg.isAdded()) {
                tran.remove(Objects.requireNonNull(fragmentManager.findFragmentByTag(fg.getClass().getSimpleName())));
            }
        }

        if (lazyCreateView) {
            Fragment fragment = fragments[defaultSelectedIndex];
            tran.add(containerLayoutId, fragment, fragment.getClass().getSimpleName());
        } else {
            for (Fragment fragment : fragments) {
                tran.add(containerLayoutId, fragment, fragment.getClass().getSimpleName()).hide(fragment);
            }
        }


        tran.show(fragments[defaultSelectedIndex]).commitAllowingStateLoss();
        tabViews[defaultSelectedIndex].setSelected(true);

        // for click
        for (int i = 0; i < tabViews.length; i++) {
            tabViews[i].setTag(i);
            tabViews[i].setOnClickListener(mOnTabClickListener);
        }
    }

    public void showFragmentWithIndex(int index) {
        if (currentIndex != index) {
            FragmentTransaction trx = fragmentManager.beginTransaction();
            trx.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(containerLayoutId, fragments[index], fragments[index].getClass().getSimpleName());
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        tabViews[currentIndex].setSelected(false);
        tabViews[index].setSelected(true);
        currentIndex = index;
    }


    private View.OnClickListener mOnTabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showFragmentWithIndex(Integer.parseInt(String.valueOf(v.getTag())));
        }
    };

}
