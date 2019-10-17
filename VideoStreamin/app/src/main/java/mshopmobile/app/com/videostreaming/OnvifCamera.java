package mshopmobile.app.com.videostreaming;

import android.graphics.Color;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import util.ItemObject;
import util.ReturnedCode;

public class OnvifCamera extends  AppCompatActivity {

    private Button button;
    public  BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onvif_camera);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fourViewFragment, new FourViewFragment());
        ft.commit();


        // Define a Bottom NavigationView to the activity_onvif_camera
         bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        if (Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        SegmentedButtonGroup sbg = (SegmentedButtonGroup) findViewById(R.id.segmentedButtonGroup);
        sbg.setOnClickedButtonPosition(new SegmentedButtonGroup.OnClickedButtonPosition() {
            @Override
            public void onClickedButtonPosition(int position) {
                Log.e("Position value","position");
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch (position) {
                    case ReturnedCode.FIRSTPOSITION:
                        ft.replace(R.id.fourViewFragment, new OneViewFragment());
                        ft.commit();
                        break;
                    case ReturnedCode.SECONDPOSITION:
                        ft.replace(R.id.fourViewFragment, new FourViewFragment());
                        ft.commit();
                        break;
                    case ReturnedCode.THIRDPOSITION:
                        ft.replace(R.id.fourViewFragment, new NineViewFragment());
                        ft.commit();
                        break;
                    case ReturnedCode.FOURTHPOSITION:
                        ft.replace(R.id.fourViewFragment, new NineSecondViewFragment());
                        ft.commit();
                        break;
                    case ReturnedCode.SECONDPOSITIONVALUE:
                        ft.replace(R.id.fourViewFragment, new SixteenViewFragment());
                        ft.commit();
                        break;

                    default:
                        break;
                }
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_video:
                      //  Toast.makeText(OnvifCamera.this,
                          //      R.string.action_in_progress, Toast.LENGTH_LONG)
                            //    .show();
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.action_in_progress, Toast.LENGTH_LONG);
                        View view = toast.getView();
                        view.setBackgroundResource(R.drawable.cameraicon1);
                        TextView text = (TextView) view.findViewById(android.R.id.message);
                        text.setTextColor(Color.parseColor("#000000"));
                        /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                        toast.show();
                        break;
                    case R.id.navigation_ptz:
                        //Toast.makeText(OnvifCamera.this,
                           //     R.string.action_in_progress, Toast.LENGTH_LONG)
                           //     .show();
                        Toast toast1 = Toast.makeText(getApplicationContext(), R.string.action_in_progress, Toast.LENGTH_LONG);
                        View view1 = toast1.getView();
                        view1.setBackgroundResource(R.drawable.cameraicon1);
                        TextView text1 = (TextView) view1.findViewById(android.R.id.message);
                        text1.setTextColor(Color.parseColor("#000000"));
                        /*Here you can do anything with above textview1 like text.setTextColor(Color.parseColor("#000000"));*/
                        toast1.show();
                        break;
                    case R.id.navigation_reconnexion:
                       // Toast.makeText(OnvifCamera.this,
                            //    R.string.action_in_progress, Toast.LENGTH_LONG)
                             //   .show();
                        Toast toast2 = Toast.makeText(getApplicationContext(), R.string.action_in_progress, Toast.LENGTH_LONG);
                        View view2 = toast2.getView();
                        view2.setBackgroundResource(R.drawable.cameraicon1);
                        TextView text2 = (TextView) view2.findViewById(android.R.id.message);
                        text2.setTextColor(Color.parseColor("#000000"));
                        /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                        toast2.show();
                        break;
                    case R.id.navigation_reconnecter:

                        Toast toast3 = Toast.makeText(getApplicationContext(), R.string.action_in_progress, Toast.LENGTH_LONG);
                        View view3 = toast3.getView();
                        view3.setBackgroundResource(R.drawable.cameraicon1);
                        TextView text3 = (TextView) view3.findViewById(android.R.id.message);
                        text3.setTextColor(Color.parseColor("#000000"));
                        /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                        toast3.show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

 /*     GridView gridview = (GridView) findViewById(R.id.gridview);

        List<ItemObject> allItems = getAllItemObject();
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, allItems);
        gridview.setAdapter(customAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override3
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });*/



/*        final Button button = findViewById(R.id.call_service);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
*//*            SoapService soapService  = new  SoapService("admin", "admin","192.168.0.72");
                soapService.getStreamingInformation();*//*
            }
        });*/
    }

/*    public void callService(View v){
    Intent intent = new Intent(this, SecondActivity.class);
       startActivity(intent);
*//*        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface);
       for(int i=0; i<7;i++){
            ThreadInfo threadInfo = new ThreadInfo(surfaceView,this);
            new Thread(threadInfo).start();
        }*//*
    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private List<ItemObject> getAllItemObject(){
        ItemObject itemObject = null;
        List<ItemObject> items = new ArrayList<>();
        items.add(new ItemObject("Image One",  "one"));
        items.add(new ItemObject("Image Two",  "two"));
        return items;
    }

    @Override
    public void onResume(){
        super.onResume();
        if (getResources().getDisplayMetrics().widthPixels > getResources().getDisplayMetrics().
                heightPixels) {
            Toast.makeText(this, "Screen switched to Landscape mode", Toast.LENGTH_SHORT).
                    show();
        } else {
            Toast.makeText(this, "Screen switched to Portrait mode", Toast.LENGTH_SHORT).
                    show();
        }

    }


}


