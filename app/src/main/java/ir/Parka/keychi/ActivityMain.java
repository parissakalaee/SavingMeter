package ir.Parka.keychi;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends EnhancedAppCompatActivity {

    public static Typeface typeFaceDefault;
    public static Typeface typeFaceFont;
    public static Typeface typeFaceMoon;
    DrawerLayout drawerLayout;
    long back_pressed;

    @Override
    public void onBackPressed(){
        if(back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            G.currentActivity.finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            G.currentActivity.startActivity(intent);
            System.exit(0);
        }else{
            showToastMessage(getString(ir.Parka.keychi.R.string.msg_exit));
        }

        back_pressed = System.currentTimeMillis();
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(ir.Parka.keychi.R.layout.activity_main);

        typeFaceDefault = Typeface.createFromAsset(getAssets(), "BYekan.ttf");
        typeFaceFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        typeFaceMoon = Typeface.createFromAsset(getAssets(), "icomoon.ttf");
        drawerLayout = (DrawerLayout) findViewById(ir.Parka.keychi.R.id.drawer_layout);

        HelperUi.persianizer((ViewGroup) getWindow().getDecorView());

        Toolbar toolbar = (Toolbar) findViewById(ir.Parka.keychi.R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView ic_menu = (TextView) findViewById(ir.Parka.keychi.R.id.ic_menu);
        ic_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }else{
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        RecyclerView recViewDrawerMenu = (RecyclerView) findViewById(ir.Parka.keychi.R.id.recview_drawer_menu);
        recViewDrawerMenu.setHasFixedSize(true);
        LinearLayoutManager recViewLayoutManager = new LinearLayoutManager(this);
        recViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recViewDrawerMenu.setLayoutManager(recViewLayoutManager);
        recViewDrawerMenu.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recViewDrawerMenu.setItemAnimator(new DefaultItemAnimator());

        String[] itemsTitle = getResources().getStringArray(ir.Parka.keychi.R.array.drawer_titles);
        String[] itemsIcon = getResources().getStringArray(ir.Parka.keychi.R.array.drawer_icons);
        int[] itemsColor = getResources().getIntArray(ir.Parka.keychi.R.array.drawer_colors);

        List<DrawerItem> drawerItems = new ArrayList<DrawerItem>();
        for(int i = 0; i < itemsTitle.length; i++){
            drawerItems.add(new DrawerItem(itemsTitle[i], itemsIcon[i], itemsColor[i]));
        }

        DrawerItemAdapter ad = new DrawerItemAdapter(drawerItems);
        recViewDrawerMenu.setAdapter(ad);

        viewPager = (ViewPager) findViewById(ir.Parka.keychi.R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setCurrentItem(1);

        tabLayout = (TabLayout) findViewById(ir.Parka.keychi.R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for(int j = 0; j < tabsCount; j++){
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for(int i = 0; i < tabChildsCount; i++){
                View tabViewChild = vgTab.getChildAt(i);
                if(tabViewChild instanceof TextView){
                    ((TextView) tabViewChild).setTypeface(typeFaceFont);
                }
            }
        }
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentBank(), getString(ir.Parka.keychi.R.string.ic_bank));
        adapter.addFragment(new FragmentCalculate(), getString(ir.Parka.keychi.R.string.ic_calculator));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int position){
            return mFragmentList.get(position);
        }

        @Override
        public int getCount(){
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }

    }

    private class DrawerItem {

        String itemTitle;
        String itemIcon;
        int itemColor;

        public DrawerItem(String itemTitle, String imgIcon, int itemColor){
            this.itemTitle = itemTitle;
            this.itemIcon = imgIcon;
            this.itemColor = itemColor;
        }

        public String getTitle(){
            return this.itemTitle;
        }

        public String getIcon(){
            return this.itemIcon;
        }

        public int getColor(){
            return this.itemColor;
        }
    }

    private class DrawerItemHolder extends RecyclerView.ViewHolder {

        public TextView itemIcon;
        public TextView itemTitle;
        public ViewGroup itemLayout;

        public DrawerItemHolder(View itemView){
            super(itemView);
            itemIcon = (TextView) itemView.findViewById(ir.Parka.keychi.R.id.drawer_icon);
            itemTitle = (TextView) itemView.findViewById(ir.Parka.keychi.R.id.drawer_text);
            itemLayout = (ViewGroup) itemView.findViewById(ir.Parka.keychi.R.id.lyt_item);
        }
    }

    private class DrawerItemAdapter extends RecyclerView.Adapter<DrawerItemHolder> {

        private List<DrawerItem> items;
        public DrawerItemAdapter(List<DrawerItem> items){
            super();
            this.items = items;
        }

        @Override
        public DrawerItemHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(ir.Parka.keychi.R.layout.item_drawer, parent, false);

            return new DrawerItemHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final DrawerItemHolder holder, final int position){
            holder.itemIcon.setTypeface(ActivityMain.typeFaceFont);
            holder.itemTitle.setTypeface(ActivityMain.typeFaceDefault);

            holder.itemIcon.setText(items.get(position).getIcon());
            holder.itemIcon.setTextColor(items.get(position).getColor());
            holder.itemTitle.setText(items.get(position).getTitle());
            holder.itemTitle.setTextColor(Color.DKGRAY);

            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view){

                    switch(position){
                        case 0:
                            holder.itemTitle.setSelected(true);
                            aboutDialog();
                            break;
                        case 1:
                            openCafeBazaarFeedBack(G.currentActivity.getPackageName(), "rating");
                            //                            openCandoFeedBack(G.currentActivity.getPackageName());
                            //                            openMyketFeedBack(G.currentActivity.getPackageName());
                            //                            openIranAppsFeedBack(G.currentActivity.getPackageName());
                            break;
                        case 2:
                            sendEmailFeedback(G.currentActivity.getPackageName());
                            break;
                        case 3:
                            Intent myIntent = new Intent(ActivityMain.this, ActivityHelp.class);
                            ActivityMain.this.startActivity(myIntent);
                            break;
                        case 4:
                            ActivityMain.this.finish();
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ActivityMain.this.startActivity(intent);
                            System.exit(0);
                            break;
                    }

                    DrawerLayout drawer = (DrawerLayout) findViewById(ir.Parka.keychi.R.id.drawer_layout);
                    drawer.closeDrawer(Gravity.RIGHT);
                }
            });
        }

        @Override
        public int getItemCount(){
            return items.size();
        }
    }

    private void openCafeBazaarFeedBack(final String paramStringPackage, final String paramStringAction){
        if(isPackageInstalled("com.farsitel.bazaar")){
            Intent intent;
            if(paramStringAction.equals("rating"))
                intent = new Intent(Intent.ACTION_EDIT); // submit rate for app
            else if(paramStringAction.equals("detail"))
                intent = new Intent(Intent.ACTION_VIEW); // view detail of app
            else if(paramStringAction.equals("setup"))
                intent = new Intent(Intent.ACTION_INSERT); // setup app directly
            else
                intent = new Intent(Intent.ACTION_VIEW); // view detail of app

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try{
                intent.setData(Uri.parse("bazaar://details?id=" + paramStringPackage));
                G.currentActivity.startActivity(intent);
            }catch(android.content.ActivityNotFoundException anfe){
                intent.setData(Uri.parse("http://cafebazaar.ir/app/?id=" + paramStringPackage));
                G.currentActivity.startActivity(intent);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            showToastMessage(getString(ir.Parka.keychi.R.string.msg_bazzar_not_installed));
        }
    }

    private void openCandoFeedBack(final String paramStringPackage){
        Intent intent = new Intent(Intent.ACTION_VIEW); // view detail of app
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try{
            intent.setData(Uri.parse("cando://leave-review?id=" + paramStringPackage));
            G.currentActivity.startActivity(intent);
        }catch(android.content.ActivityNotFoundException anfe){
            intent.setData(Uri.parse("http://cando.asr24.com/app.jsp?package=" + paramStringPackage));
            G.currentActivity.startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void openMyketFeedBack(final String paramStringPackage){
        Intent intent = new Intent(Intent.ACTION_VIEW); // view detail of app
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try{
            intent.setData(Uri.parse("myket://comment/#Intent;scheme=comment;package=" + paramStringPackage + ";end"));
            G.currentActivity.startActivity(intent);
        }catch(android.content.ActivityNotFoundException anfe){
            intent.setData(Uri.parse("http://myket.ir/app/" + paramStringPackage));
            G.currentActivity.startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void openIranAppsFeedBack(final String paramStringPackage){
        Intent intent = new Intent(Intent.ACTION_VIEW); // view detail of app
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try{
            intent.setData(Uri.parse("iranapps://app/" + paramStringPackage + "?a=comment&r=5"));
            G.currentActivity.startActivity(intent);
        }catch(android.content.ActivityNotFoundException anfe){
            intent.setData(Uri.parse("http://iranapps.ir/app/" + paramStringPackage + "?a=comment&r=5"));
            G.currentActivity.startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void sendEmailFeedback(String paramStringPackage){
        //        throw new RuntimeException("This is a crash");
        Intent Email = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:info@parkagroup.ir"));
        try{
            String str = getPackageManager().getPackageInfo(G.currentActivity.getPackageName(), 0).versionName;
            Email.putExtra(Intent.EXTRA_EMAIL, "info@parkagroup.ir");
            Email.putExtra(Intent.EXTRA_SUBJECT, paramStringPackage + str + " بازخورد");
            startActivity(Intent.createChooser(Email, "تماس با ما"));
        }catch(PackageManager.NameNotFoundException localNameNotFoundException){
            localNameNotFoundException.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean isPackageInstalled(String PackageName){
        PackageManager manager = getPackageManager();
        boolean isAppInstalled = false;
        try{
            manager.getPackageInfo(PackageName, PackageManager.GET_ACTIVITIES);
            isAppInstalled = true;
        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return isAppInstalled;
    }

    private void aboutDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, ir.Parka.keychi.R.style.MyAlertDialogStyle);

        alertDialogBuilder
                .setMessage(Html.fromHtml(getString(ir.Parka.keychi.R.string.txt_about)))
                .setCancelable(true)
                .setPositiveButton(getString(ir.Parka.keychi.R.string.ic_contact), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                        sendEmailFeedback(G.currentActivity.getPackageName());
                        dialog.cancel();
                    }
                })
                .setNegativeButton(getString(ir.Parka.keychi.R.string.ic_link),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id){
                                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.parkagroup.ir"));
                                Bundle b = new Bundle();
                                b.putBoolean("new_window", true); //sets new window
                                intent.putExtras(b);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        })
                .setNeutralButton(getString(ir.Parka.keychi.R.string.ic_exit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id){
                                dialog.cancel();
                            }
                        });


        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();

        alertD.getButton(DialogInterface.BUTTON_POSITIVE).setTextSize(24);
        alertD.getButton(DialogInterface.BUTTON_POSITIVE).setTypeface(ActivityMain.typeFaceFont);
        alertD.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(24);
        alertD.getButton(DialogInterface.BUTTON_NEGATIVE).setTypeface(ActivityMain.typeFaceFont);
        alertD.getButton(DialogInterface.BUTTON_NEUTRAL).setTextSize(24);
        alertD.getButton(DialogInterface.BUTTON_NEUTRAL).setTypeface(ActivityMain.typeFaceFont);

        // Make the textview clickable. Must be called after show()
        ((TextView) alertD.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) alertD.findViewById(android.R.id.message)).setTypeface(ActivityMain.typeFaceDefault);
        ((TextView) alertD.findViewById(android.R.id.message)).setLineSpacing(4, 1.1f);
    }

    void showToastMessage(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTypeface(ActivityMain.typeFaceDefault);
        toast.show();
    }
}
