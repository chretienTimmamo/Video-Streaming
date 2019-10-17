package mshopmobile.app.com.videostreaming;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class StreamMultiviewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Activity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_multiview);
        this.mainActivity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // Set drawer Action Bar Color to red
        //  toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.red));

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            // Handle the camera action


        } else if (id == R.id.nav_gallery) {

            //Intent navCameraIntent = new Intent(getApplicationContext(), FlowVisualization.class);
         //   startActivity(navCameraIntent);
         //   finish();

        } else if (id == R.id.nav_slideshow) {
            AlertDialog.Builder newAlertDialog  = new AlertDialog.Builder(mainActivity);
            newAlertDialog.setTitle("Planning");
            newAlertDialog.setMessage("Use Plannification to record your video surveillance by camera at a precise moment!!!");
            newAlertDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i ) {
                    Toast.makeText(getApplicationContext(),"This functionnality is in progress...", Toast.LENGTH_LONG).show();

                }
            });

            newAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i ) {
                    Toast.makeText(getApplicationContext(),"Cancel ", Toast.LENGTH_LONG).show();

                }
            });

            newAlertDialog.show();

        } else if (id == R.id.nav_manage) {
            AlertDialog.Builder newAlertDialog  = new AlertDialog.Builder(mainActivity);
            newAlertDialog.setTitle("Tools");
            newAlertDialog.setMessage("Use Tools to set configuration to your camera!!!");
            newAlertDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i ) {
                    Toast.makeText(getApplicationContext(),"This functionnality is in progress...", Toast.LENGTH_LONG).show();

                }
            });

            newAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i ) {
                    Toast.makeText(getApplicationContext(),"Cancel ", Toast.LENGTH_LONG).show();

                }
            });

            newAlertDialog.show();
            /*
           CustomDialog  customdialog  = new CustomDialog(mainActivity);
           customdialog.setTitle("VideSecuritrix: Tools");
           customdialog.setSubTitle("Use Tools to set configuration to your camera!!!");
           customdialog.Build();
           */

        } else if (id == R.id.nav_send) {
            Toast.makeText(getApplicationContext(),"Logout successfully...", Toast.LENGTH_LONG).show();
            Log.e("BackPressed", "Login back pressed");
            Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(nextScreen);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
