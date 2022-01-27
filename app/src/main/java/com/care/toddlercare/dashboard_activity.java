package com.care.toddlercare;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class dashboard_activity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    bottomNavigation = findViewById(R.id.bottom_nav);

    bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.home));
    bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.favorite));
    bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.person));


    bottomNavigation.setOnShowListener(item -> {
        Fragment fragment = null;

        switch (item.getId())
        {
            case 1:
                fragment = new HomeFragment();
                break;
            case 2:
                fragment = new FavouriteFragment();
                break;
            case 3:
                fragment = new Profile();
                break;
        }

        loadFragment(fragment);
    });
    //end

        //bottomNavigation.setCount(1,"10");
        bottomNavigation.show(1, true);

        //Both method are required for bottom nav bar
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(getApplicationContext(), "You clicked "+ item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(getApplicationContext(), "You reselected" + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }
}