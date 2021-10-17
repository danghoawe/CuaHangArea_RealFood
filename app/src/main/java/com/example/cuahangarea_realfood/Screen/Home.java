package com.example.cuahangarea_realfood.Screen;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.cuahangarea_realfood.Fragment.HomeFragment;
import com.example.cuahangarea_realfood.Fragment.NotificationFragment;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.Fragment.SettingFragment;
import com.example.cuahangarea_realfood.Fragment.StoreFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

public class Home extends AppCompatActivity {
FrameLayout frameLayout;
BottomBar bottomBar ;
public static Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_home);
        this.getSupportActionBar().hide();
        setControl();
        setEvent();
        BottomBarTab nearby = bottomBar.getTabWithId(R.id.tab_notification);
        nearby.setBadgeCount(5);
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setEvent() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId)
                {
                    case R.id.tab_home:
                        HomeFragment homeFragment = new HomeFragment();
                        loadFragment(homeFragment);
                        break;
                    case R.id.tab_notification:
                        NotificationFragment notificationFragment = new NotificationFragment();
                        loadFragment(notificationFragment);
                        break;
                    case R.id.tab_setting:
                        SettingFragment settingFragment = new SettingFragment();
                        loadFragment(settingFragment);
                        break;
                    case R.id.tab_store:
                        StoreFragment storeFragment = new StoreFragment();
                        loadFragment(storeFragment);
                        break;
                }
            }
        });
    }

    private void setControl() {
         bottomBar = (BottomBar) findViewById(R.id.bottomBar);
         frameLayout = findViewById(R.id.fragment);
    }

}