package com.zf.ihome;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.deahu.activity.TrafficViolationActivity;
import com.zf.common.app.BaseActivity;
import com.zf.common.widget.dragdrop.DragdropView;
import com.zf.fastshare.FastShareActivity;
import com.zf.ihome.id.IdentityQueryActivity;
import com.zf.lottery.LotteriesActivity;
import com.zf.measurement.MeasurementActivity;
import com.zf.translation.TranslateActivity;
import com.zf.weather.WeatherForecastActivity;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        WebView weatherView = new WebView(this);
//        toolbar.addView(weatherView, new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar
// .LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER_VERTICAL));
//        weatherView.setBackgroundColor(0);
//        weatherView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
//        String data = "<iframe name=\"weather_inc\" align=\"right\" src=\"http://i.tianqi.com/index
// .php?c=code&id=52&icon=1&num=3\"" +
//                " width=\"150\" height=\"25\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\"
// scrolling=\"no\"></iframe>";
//        weatherView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
        //weatherView.loadUrl("http://i.tianqi.com/index.php?c=code&id=52&icon=1&num=3");
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null)
//                        .show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string
                .navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View nameView = navigationView.getHeaderView(0).findViewById(R.id.nameView);
        nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        DragdropView homeView = (DragdropView) findViewById(R.id.homeView);
        RecyclerListAdapter adapter = new RecyclerListAdapter(this, homeView);
        homeView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_lottery) {
            startActivity(new Intent(this, LotteriesActivity.class));
        } else if (id == R.id.nav_violation) {
            startActivity(new Intent(this, TrafficViolationActivity.class));
        } else if (id == R.id.nav_weather) {
            startActivity(new Intent(this, WeatherForecastActivity.class));
        } else if (id == R.id.nav_identity) {
            startActivity(new Intent(this, IdentityQueryActivity.class));
        } else if (id == R.id.nav_measurement) {
            startActivity(new Intent(this, MeasurementActivity.class));
        } else if (id == R.id.nav_translation) {
            startActivity(new Intent(this, TranslateActivity.class));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(this, FastShareActivity.class));
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
