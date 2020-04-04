package com.smartworker.smartworker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.smartworker.smartworker.account.Profile;
import com.smartworker.smartworker.db.DbOperation_Jops;
import com.smartworker.smartworker.db.DbOperation_Users;
import com.smartworker.smartworker.login.Jop;
import com.smartworker.smartworker.login.Login;
import com.smartworker.smartworker.login.User;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int userId;
    SearchView sv;
    GridView gv;
    DbOperation_Jops db_j;
    DbOperation_Users db_u;
    Button btn_profile, btn_menu;
    List<Jop> jops = new ArrayList<>();
    List<User> workers_list = new ArrayList<>();
    DbOperation_Users db_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sv = (SearchView) findViewById(R.id.shearch_view);
        gv = (GridView) findViewById(R.id.list_jops);
        btn_profile = (Button) findViewById(R.id.profile);
        btn_menu = (Button) findViewById(R.id.menu);
        db_j = new DbOperation_Jops(this);
        jops = db_j.getALLJops();
        db_user = new DbOperation_Users(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userId = bundle.getInt("user_id");
        }

        final Main_Adapter m_ad = new Main_Adapter(this, jops);
        gv.setAdapter(m_ad);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                m_ad.getFilter().filter(newText);
                return false;
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Profile.class);
                in.putExtra("user_id", userId);
                startActivity(in);
            }
        });

        workers_list = db_user.getAllUSERS();
        for (int i = 0; i < workers_list.size(); i++) {
            User worker = workers_list.get(i);
            Log.e("workers_list", "data" + " --- " + worker.getFARST_NAME() + " --- " + worker.getLatitude() + " | " + worker.getLongitude() + " --- " + worker.getJOP_ID() + " --- " + worker.getCity_id());

        }

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent im = new Intent(getApplicationContext(), MapsActivity.class);
                im.putExtra("user_id", userId);
                im.putExtra("job_id", jops.get(position).getId());
                im.putExtra("jop_name", jops.get(position).getName());
                startActivity(im);
                System.out.println("Log main jobId " + jops.get(position).getId());
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });


    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you wont to Sign out ?").setCancelable(false).setTitle("Worning").setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent in = new Intent(getApplicationContext(), Login.class);
                startActivity(in);
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("RestrictedApi")
    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.main_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        Intent in = new Intent(getApplicationContext(), Profile.class);
                        in.putExtra("user_id", userId);
                        startActivity(in);
                        return true;

                    case R.id.map:
                        Intent im = new Intent(getApplicationContext(), MapsActivity.class);
                        im.putExtra("user_id", userId);
                        startActivity(im);
                        return true;

                    case R.id.about:
                        Intent iin = new Intent(getApplicationContext(), AboutUs.class);
                        startActivity(iin);
                        return false;

                    case R.id.sign_out:
                        onBackPressed();
                        return false;
                    default:
                        return false;
                }

            }
        });
        popup.show();
    }
}
