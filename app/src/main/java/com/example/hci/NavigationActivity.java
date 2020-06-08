package com.example.hci;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.hci.ui.analytics.AdvancedAnalytics;
import com.example.hci.ui.analytics.HouseGraphs;
import com.example.hci.ui.documents.DocumentVisualization;
import com.example.hci.ui.documents.InsertNewDocument;
import com.example.hci.ui.documents.NotifyPerson;
import com.example.hci.ui.events.InsertNewEvent;
import com.example.hci.ui.home.HomeFragment;
import com.example.hci.ui.events.EventsFragment;
import com.example.hci.ui.documents.DocumentsFragment;
import com.example.hci.ui.analytics.AnalyticsFragment;
import com.example.hci.ui.numbers.InsertNumberFragment;
import com.example.hci.ui.numbers.NumbersFragment;
import com.example.hci.ui.numbers.ShowUsefulNumber;
import com.example.hci.ui.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import android.os.Handler;
import java.util.Objects;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    public static NavigationView navigationView;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_events, R.id.nav_numbers,
                R.id.nav_documents, R.id.nav_analytics, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FrameLayout frameLayout = findViewById(R.id.nav_host_fragment);
        switch (item.getItemId()) {
            case R.id.nav_home:
                frameLayout.removeAllViews();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new HomeFragment(),"fragment_home")
                        .addToBackStack(null)
                        .commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
                break;

            case R.id.nav_profile:
                frameLayout.removeAllViews();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new ProfileFragment(),"fragment_profile")
                        .addToBackStack(null)
                        .commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle("Profile");
                break;

            case R.id.nav_events:
                frameLayout.removeAllViews();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new EventsFragment(),"fragment_events")
                        .addToBackStack(null)
                        .commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle("Events");
                break;

            case R.id.nav_numbers:
                frameLayout.removeAllViews();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new NumbersFragment(),"fragment_numbers")
                        .addToBackStack(null)
                        .commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle("Useful Numbers");
                break;

            case R.id.nav_documents:
                frameLayout.removeAllViews();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new DocumentsFragment(),"fragment_documents")
                        .addToBackStack(null)
                        .commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle("Documents");
                break;

            case R.id.nav_analytics:
                frameLayout.removeAllViews();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new AnalyticsFragment(),"fragment_analytics")
                        .addToBackStack(null)
                        .commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle("Analytics");
                break;

            case R.id.nav_logout:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (fragment instanceof ProfileFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment(),"fragment_home")
                    .commit();

            navigationView.getMenu().getItem(1).setChecked(false);
            navigationView.getMenu().getItem(0).setChecked(true);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (fragment instanceof EventsFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment(),"fragment_home")
                    .commit();

            navigationView.getMenu().getItem(2).setChecked(false);
            navigationView.getMenu().getItem(0).setChecked(true);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (fragment instanceof InsertNewEvent) {
            Dialog dialog = new AlertDialog.Builder(this,R.style.PrevDialogTheme)
                    .setTitle("Attention")
                    .setMessage("You will lose all inserted information.\nDo you really want to go back?")
                    .setIcon(R.drawable.yellow_alert)
                    .setPositiveButton(android.R.string.yes, (dialog12, whichButton) -> {
                        getSupportFragmentManager().popBackStackImmediate();
                        InsertNewEvent.from_hour_event = "";
                        InsertNewEvent.to_hour_event = "";
                        InsertNewEvent.button_event_popup.setText(getResources().getString(R.string.enter_days_and_hours));
                        InsertNewEvent.button_event_popup.setTextSize(15);
                    })
                    .setNegativeButton(android.R.string.no, null).show();
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.border_layout_calendar);
            dialog.setCanceledOnTouchOutside(false);
        }
        else if (fragment instanceof NumbersFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment(),"fragment_home")
                    .commit();

            navigationView.getMenu().getItem(3).setChecked(false);
            navigationView.getMenu().getItem(0).setChecked(true);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (fragment instanceof InsertNumberFragment || fragment instanceof ShowUsefulNumber) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new NumbersFragment(),"fragment_numbers")
                    .commit();
        }
        else if (fragment instanceof DocumentsFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment(),"fragment_home")
                    .commit();

            navigationView.getMenu().getItem(4).setChecked(false);
            navigationView.getMenu().getItem(0).setChecked(true);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (fragment instanceof InsertNewDocument){
            Dialog dialog = new AlertDialog.Builder(this,R.style.PrevDialogTheme)
                    .setTitle("Attention")
                    .setMessage("You will lose all inserted information.\nDo you really want to go back?")
                    .setIcon(R.drawable.yellow_alert)
                    .setPositiveButton(android.R.string.yes, (dialog1, whichButton) -> getSupportFragmentManager().popBackStackImmediate())
                    .setNegativeButton(android.R.string.no, null).show();
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.border_layout_calendar);
            dialog.setCanceledOnTouchOutside(false);
        }
        else if (fragment instanceof NotifyPerson || fragment instanceof DocumentVisualization) {
            getSupportFragmentManager().popBackStackImmediate();
        }
        else if (fragment instanceof AnalyticsFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment(),"fragment_home")
                    .commit();

            navigationView.getMenu().getItem(5).setChecked(false);
            navigationView.getMenu().getItem(0).setChecked(true);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (fragment instanceof HouseGraphs) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new AnalyticsFragment(),"fragment_analytics")
                    .commit();
        }
        else if (fragment instanceof AdvancedAnalytics) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new HouseGraphs(),"fragment_house_graphs")
                    .commit();
        }
        else {  //HomeFragment
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finish();
            } else {
                doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit!", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
            }
        }
    }

    public static void change_nav_info(String name, String surname) {
        View nav_header_main = navigationView.getHeaderView(0);

        TextView global_name = nav_header_main.findViewById(R.id.nameTextView);
        TextView global_email = nav_header_main.findViewById(R.id.emailTextView);

        if (!name.equals("") && !surname.equals("")) {
            String newName = name + " " + surname;
            String newEmail = name + "." + surname + "@gmail.com";

            global_name.setText(newName);
            global_email.setText(newEmail.toLowerCase());
        }
    }
}
