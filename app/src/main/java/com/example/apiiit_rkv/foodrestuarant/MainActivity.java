package com.example.apiiit_rkv.foodrestuarant;


import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private ViewPager myviewPager;
    private TabLayout tabLayout;

    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateData();

        myviewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(myviewPager);

        tabLayout = (TabLayout)findViewById(R.id.tab);
        tabLayout.setupWithViewPager(myviewPager);


    }


    public void CreateData(){
        final DatabaseHelper mydb = new DatabaseHelper(this);
        Cursor result = mydb.getAllData();
        if(result.getCount() != 9){
            mydb.deleteTable();
            mydb.insertData("Biryani",0,200,0);
            mydb.insertData("Chapati",0,100,0);
            mydb.insertData("Dosa",0,100,0);

            mydb.insertData("Spinach\nNoodles",0,150,0);
            mydb.insertData("Fried\nMashi",0,90,0);
            mydb.insertData("Dumplings",0,60,0);

            mydb.insertData("Panzanella",0,200,0);
            mydb.insertData("Pasta",0,100,0);
            mydb.insertData("Margherita\nPizza",0,200,0);

        }
        mydb.close();
    }

    private void setupViewPager(ViewPager myviewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        String t="";
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            t = (String)bundle.get("tab");
        }

        if(t.equals("1")) {
            adapter.addFragment(new IndianFood(), "Indian");
            adapter.addFragment(new ChineseFood(), "Chinese");
            adapter.addFragment(new ItalianFood(), "Italian");
        }else if(t.equals("2")){
            adapter.addFragment(new ChineseFood(), "Chinese");
            adapter.addFragment(new ItalianFood(), "Italian");
            adapter.addFragment(new IndianFood(), "Indian");
        }else if(t.equals("3")){
            adapter.addFragment(new ItalianFood(), "Italian");
            adapter.addFragment(new IndianFood(), "Indian");
            adapter.addFragment(new ChineseFood(), "Chinese");
        }
        else {
            adapter.addFragment(new IndianFood(), "Indian");
            adapter.addFragment(new ChineseFood(), "Chinese");
            adapter.addFragment(new ItalianFood(), "Italian");
        }
        myviewPager.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.cart_action:
                showOrder();
                break;
            case R.id.notification:
                showOrder();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void showOrder() {
        Intent intent = new Intent(MainActivity.this,CartActivity.class);
        startActivity(intent);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        String t="";
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            if(!((String)bundle.get("main")==null))
            t = (String)bundle.get("main");
        }
        if(t.equals("main")){
            Intent intent_main = new Intent(MainActivity.this,MainActivity.class);
            intent_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent_main);
        }else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}



