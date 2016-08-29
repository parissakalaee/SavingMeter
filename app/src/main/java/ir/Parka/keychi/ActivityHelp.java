package ir.Parka.keychi;

import android.content.Context;
import android.os.Bundle;
import android.os.Trace;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ActivityHelp extends EnhancedAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ir.Parka.keychi.R.layout.activity_help);

        HelperUi.persianizer((ViewGroup) getWindow().getDecorView());
        final ViewPager viewPager = (ViewPager) findViewById(ir.Parka.keychi.R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(getApplicationContext()));

        TextView skipButton = (TextView) findViewById(ir.Parka.keychi.R.id.skipButton);
        final TextView nextButton = (TextView) findViewById(ir.Parka.keychi.R.id.nextButton);
        final TextView doneButton = (TextView) findViewById(ir.Parka.keychi.R.id.doneButton);

        final SelectionIndicator indicator = (SelectionIndicator) findViewById(ir.Parka.keychi.R.id.indicator);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                G.HANDLER.post(new Runnable() {

                    @Override
                    public void run() {
                        indicator.setActiveItem(position, true);

                        if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1) {
                            nextButton.setVisibility(View.GONE);
                            doneButton.setVisibility(View.VISIBLE);
                        } else {
                            nextButton.setVisibility(View.VISIBLE);
                            doneButton.setVisibility(View.GONE);
                        }
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        assert skipButton != null;
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                ActivityHelp.this.finish();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                ActivityHelp.this.finish();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                if (viewPager.getCurrentItem() != viewPager.getAdapter().getCount() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });
    }

    public enum CustomPagerEnum {

        INTRO1(ir.Parka.keychi.R.layout.intro1),
        INTRO2(ir.Parka.keychi.R.layout.intro2),
        INTRO3(ir.Parka.keychi.R.layout.intro3),
        INTRO4(ir.Parka.keychi.R.layout.intro4),
        INTRO5(ir.Parka.keychi.R.layout.intro5),
        INTRO6(ir.Parka.keychi.R.layout.intro6),
        INTRO7(ir.Parka.keychi.R.layout.intro7);

        private int mLayoutResId;

        CustomPagerEnum(int layoutResId) {
            mLayoutResId = layoutResId;
        }

        public int getLayoutResId() {
            return mLayoutResId;
        }
    }

    public class CustomPagerAdapter extends PagerAdapter {

        private Context mContext;

        public CustomPagerAdapter(Context context) {
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {

//            Trace.beginSection("test-trace"); // it need API 18+

                CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
                LayoutInflater inflater = LayoutInflater.from(mContext);
                ViewGroup layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), collection, false);
                collection.addView(layout);
                HelperUi.persianizer(layout);
//            Trace.endSection();
                return layout;


        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return CustomPagerEnum.values().length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
