package com.ngliaxl.tab;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.ngliaxl.tab.fragment.MainFragment;
import com.ngliaxl.tab.fragment.MeFragment;
import com.ngliaxl.tab.fragment.ViewFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Fragment mainFragment;
    private Fragment viewFragment;
    private Fragment meFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();
        viewFragment = new ViewFragment();
        meFragment = new MeFragment();

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

    }


}
