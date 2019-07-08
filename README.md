# tab-fragment
tab fragment

### 使用方式

```
       ArrayList<Fragment> fragments = new ArrayList<Fragment>() {
            {
                add(mainFragment);
                add(viewFragment);
                add(meFragment);

            }
        };
        ArrayList<View> views = new ArrayList<View>() {
            {
                add(findViewById(R.id.tab_main));
                add(findViewById(R.id.tab_view));
                add(findViewById(R.id.tab_me));
            }
        };


       TabViewBuilder builder = new TabViewBuilder(getSupportFragmentManager());
        builder.setFragments(fragments)
                .setTabViews(views)
                .setContainerLayoutId(R.id.fragment_container);
        builder.build();
```


### 代码

```
public class TabViewBuilder {

    private Fragment[] fragments;
    private View[] tabViews;

    private int currentIndex;
    private FragmentManager fragmentManager;
    @IdRes
    private int containerLayoutId;

    private int defaultSelectedIndex = 0;

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

        for (Fragment fragment : fragments) {
            tran.add(containerLayoutId, fragment, fragment.getClass().getSimpleName()).hide(fragment);
        }

        tran.show(fragments[defaultSelectedIndex]).commitAllowingStateLoss();
        tabViews[defaultSelectedIndex].setSelected(true);

        // for click
        for (int i = 0; i < tabViews.length; i++) {
            tabViews[i].setTag(i);
        }
        for (View v : tabViews) {
            v.setOnClickListener(mOnTabClickListener);
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
```
