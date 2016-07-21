package com.akari.quark.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akari.quark.R;
import com.akari.quark.entity.Infomation;
import com.akari.quark.ui.fragment.FocusFragment;
import com.akari.quark.ui.fragment.MainFragment;
import com.akari.quark.ui.fragment.QuestionsAskAndAnswerFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView mAvatar;
    TextView mUsername;
    TextView mIntroduction;
    FragmentManager fragmentManager;
    private Context context;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        fragmentManager = getSupportFragmentManager();
        sharedPreferences = getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        updateUsernameAndIntroduction();
    }


    private void init() {
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle("发现");
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        mAvatar = (ImageView) headerView.findViewById(R.id.header_avatar);
        mUsername = (TextView) headerView.findViewById(R.id.header_username);
        mIntroduction = (TextView) headerView.findViewById(R.id.header_introduction);

        ViewGroup followee = (ViewGroup) headerView.findViewById(R.id.followee);
        ViewGroup follower = (ViewGroup) headerView.findViewById(R.id.follower);

        followee.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FollowActivity.class);
                intent.putExtra("followType", FollowActivity.FollowType.Followee.name());
                startActivity(intent);
            }
        });

        follower.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FollowActivity.class);
                intent.putExtra("followType", FollowActivity.FollowType.Follower.name());
                startActivity(intent);
            }
        });

        fragmentManager.beginTransaction().replace(R.id.content, new MainFragment()).commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_main);
        if (fab != null) {
            fab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Intent intent = new Intent(context, CreatQuestionActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        switch (menuItem.getItemId()) {
            case R.id.nav_find:
                toolbar.setTitle("发现");
                fragmentManager.beginTransaction().replace(R.id.content, new MainFragment()).commit();
                drawerLayout.closeDrawer(navigationView);
                break;
            case R.id.nav_message:
                toolbar.setTitle("消息");
                Intent intent = new Intent(context, MessageActivity.class);
                context.startActivity(intent);
                drawerLayout.closeDrawer(navigationView);
                break;
            case R.id.nav_logout:
                drawerLayout.closeDrawer(navigationView);
                MainActivity.this.finish();
                menuItem.setChecked(true); // 改变item选中状态
                sharedPreferences.edit().putBoolean("ischecked", false).apply();
                Intent intent2 = new Intent(context, LoginActivity.class);
                context.startActivity(intent2);
                break;
            case R.id.nav_focus:
                toolbar.setTitle("关注");
                fragmentManager.beginTransaction().replace(R.id.content, new FocusFragment()).commit();
                drawerLayout.closeDrawer(navigationView);
                break;
            case R.id.nav_question:
                toolbar.setTitle("提问");
                fragmentManager.beginTransaction().replace(R.id.content, QuestionsAskAndAnswerFragment.newInstance(1)).commit();
                drawerLayout.closeDrawer(navigationView);
                break;
            case R.id.nav_answer:
                toolbar.setTitle("回答");
                fragmentManager.beginTransaction().replace(R.id.content, QuestionsAskAndAnswerFragment.newInstance(2)).commit();
                drawerLayout.closeDrawer(navigationView);
                break;
            default:
                drawerLayout.closeDrawer(navigationView);
                break;
        }
        return true;
    }

    private void updateUsernameAndIntroduction() {
//        if (sharedPreferences.getBoolean("ischecked",false)){//未登陆情况
//            Toast.makeText(context, "未登陆", Toast.LENGTH_SHORT).show();
//            mAvatar.setVisibility(View.INVISIBLE);
//            return;
//        }
        Toast.makeText(context, Infomation.getName(), Toast.LENGTH_SHORT).show();
        mUsername.setText(Infomation.getName());
        mIntroduction.setText(Infomation.getIntroduction());
    }
}
