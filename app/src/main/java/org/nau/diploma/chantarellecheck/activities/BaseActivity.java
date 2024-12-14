package org.nau.diploma.chantarellecheck.activities;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import org.nau.diploma.chantarellecheck.fragments.StaticAnalysisFragment;

import rg.nau.diploma.chantarellecheck.R;
import rg.nau.diploma.chantarellecheck.databinding.BaseActvityBinding;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       BaseActvityBinding collectionViewBinding
                = BaseActvityBinding.inflate(getLayoutInflater());
        setContentView(collectionViewBinding.getRoot());

        FragmentManager fm = getSupportFragmentManager();
        ViewStateAdapter sa = new ViewStateAdapter(fm, getLifecycle());
        final ViewPager2 pa = findViewById(R.id.pager);
        pa.setUserInputEnabled(false);
        pa.setOffscreenPageLimit(1);

        pa.setAdapter(sa);

        TabLayout tabLayout = findViewById(R.id.nav_panel);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pa.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pa.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            finishAfterTransition();
        } else {
            super.onBackPressed();
        }
    }


    private class ViewStateAdapter extends FragmentStateAdapter {

        public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

                return new StaticAnalysisFragment();


        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}