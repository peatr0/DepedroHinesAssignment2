package depedro.hines.n01455125;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    DepedroHome depedroHome=new DepedroHome();
    HinesDownload hinesDownload=new HinesDownload();
    n01455125Weather n01455125weather=new n01455125Weather();
    DBScreen DBScreen=new DBScreen();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {

            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContent, depedroHome).commit();


        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.nav_view);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.Open_Nav,R.string.Close_Nav);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Snackbar snackbar = Snackbar.make(drawerLayout, "Drawer opened", Snackbar.LENGTH_LONG);
        snackbar.setAction("DepedroHines", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                snackbar.show();
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                // Show a short duration Toast message with a text
                Toast.makeText(MainActivity.this,getText( R.string.pedro), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.home:{

                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContent, depedroHome).commit();
                        DrawerLayout drawer = findViewById(R.id.drawer);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                    case R.id.download:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContent,hinesDownload).commit();
                        DrawerLayout drawer = findViewById(R.id.drawer);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                    case R.id.n01455125Weather:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContent,n01455125weather).commit();
                        DrawerLayout drawer = findViewById(R.id.nav_view);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                    case R.id.DBScreen:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContent, DBScreen).commit();
                        DrawerLayout drawer = findViewById(R.id.drawer);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }


                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);
        }else super.onBackPressed();
    }

}