package wxw.com.androiddemo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private MainFragment mainFragment;
    private LocalBroadcastManager localBroadcastManager;//
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;
//    private TextView left_tv;
    private CursorLoaderListFragment cursorLoaderListFragment;
    private NavigationView navigation;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        left_tv = (TextView) findViewById(R.id.left_tv);
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        toolbar.setTitle("Home");
//        toolbar.setSubtitle("Sub Title");
//        toolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);
        initViews();
        initFragment();
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("test");
//        broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if ("test".equals(intent.getAction())) {
//                    left_tv.setText("广播接收到了");
//                }
//            }
//        };
//        localBroadcastManager = LocalBroadcastManager.getInstance(this);
//
//        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
//        MainActivity.this.sendBroadcast(new Intent("test"));
        navigation = (NavigationView) findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(menuItem.isChecked()){
                    return true;
                }
                ft = getFragmentManager().beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
//                        toolbar.setTitle(menuItem.getTitle());
                        ft.replace(R.id.id_content_container,new MainFragment());
                        ft.commit();
                    break;
                    case R.id.nav_messages:
//                        toolbar.setTitle("Messages");
                        ft.replace(R.id.id_content_container,new MessageFragment());
                        ft.commit();
                        break;
                    case R.id.nav_friends:
//                        toolbar.setTitle("Friends");
                        ft.replace(R.id.id_content_container,new FriendFragment());
                        ft.commit();
                        break;
                    case R.id.nav_discussion:
//                        toolbar.setTitle("Discussion");
                        ft.replace(R.id.id_content_container,new DiscussionFragment());
                        ft.commit();
                        break;
                    case R.id.sub1:
//                        toolbar.setTitle("Sub1");
                        break;
                    case R.id.sub2:
//                        toolbar.setTitle("Sub2");
                        break;

                }
                toolbar.setTitle(menuItem.getTitle());
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view,"FAB",Snackbar.LENGTH_LONG).show();
                Snackbar.make(view,"FAB",Snackbar.LENGTH_LONG).setAction("cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    private void initFragment() {

         fm = getFragmentManager();
         ft = fm.beginTransaction();
        mainFragment = new MainFragment();
        cursorLoaderListFragment = new CursorLoaderListFragment();
        ft.replace(R.id.id_content_container, mainFragment);
        ft.commit();

    }

    private void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button®, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_edit) {
            Toast.makeText(this, "Edit", Toast.LENGTH_LONG).show();
        } else if (id == R.id.action_share) {
            Toast.makeText(this, "Share", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
