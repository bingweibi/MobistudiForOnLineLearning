package czd.mobistudi.mo.activity;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import chinaykc.mobistudi.R;
import czd.mobistudi.md.ui.fragment.FirstRecyclerViewFragment;
import czd.mobistudi.md.model.entity.Tab;
import czd.mobistudi.mo.activity.base.BaseActivity;
import czd.mobistudi.md.ui.fragment.SecondRecyclerViewFragment;

public class MobarActivity extends BaseActivity {

    private MaterialViewPager mViewPager;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private SecondRecyclerViewFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_mobar);
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            if(actionBar !=null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
//              actionBar.setHomeAsUpIndicator(R.drawable.???);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }

        }

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        return SecondRecyclerViewFragment.newInstance(Tab.dev);
                    case 1:
                        return SecondRecyclerViewFragment.newInstance(Tab.good);
                    case 2:
                        return SecondRecyclerViewFragment.newInstance(Tab.ask);
//                    case 3:
//                        return ThirdUserDetailFragment.newInstance();
                    default:
                        return FirstRecyclerViewFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "ALL";
                    case 1:
                        return "Recommend";
                    case 2:
                        return "Ask";
                    case 3:
                        return "Test";
//                    case 4:
//                        return "MyPosts";
                }
                return "";
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
               if(object instanceof SecondRecyclerViewFragment){
                   currentFragment = (SecondRecyclerViewFragment) object;
               }else{
                   currentFragment = null;
               }
                super.setPrimaryItem(container, position, object);
            }

        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.green,
                                ResourcesCompat.getDrawable(getResources(),R.drawable.mobaar_header_pic1_640x400,null)
                                );
                    case 1:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.blue,
                                ResourcesCompat.getDrawable(getResources(),R.drawable.mobaar_header_pic2_600x400,null)
                                );
                    case 2:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.cyan,
                                ResourcesCompat.getDrawable(getResources(),R.drawable.mobaar_header_pic3_901x750,null)
                                );
                    case 3:
                    return HeaderDesign.fromColorResAndDrawable(
                                R.color.viewpager_gray,
                                ResourcesCompat.getDrawable(getResources(),R.drawable.mobaar_header_pic4_900x700,null)
                                );
//                    case 4:
//                        return HeaderDesign.fromColorResAndDrawable(
//                                R.color.color_primary_dark,
//                                ResourcesCompat.getDrawable(getResources(),R.drawable.user_detail_header_bg,null)
//                        );
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mPagerSlidingTabStrip = mViewPager.getPagerTitleStrip();
        mPagerSlidingTabStrip.setViewPager(mViewPager.getViewPager());
        mPagerSlidingTabStrip.setOnTabReselectedListener(new PagerSlidingTabStrip.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                if(currentFragment!=null){
                    currentFragment.backToContentTop();
                }
            }
        });

        final View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    if(currentFragment!=null){
                        currentFragment.backToContentTop();
                    }
                    Toast.makeText(getApplicationContext(), "back to top", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
