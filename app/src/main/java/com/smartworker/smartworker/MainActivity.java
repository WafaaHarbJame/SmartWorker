package com.smartworker.smartworker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import com.smartworker.smartworker.account.Profile;
import com.smartworker.smartworker.db.DbOperation_Jops;
import com.smartworker.smartworker.db.DbOperation_Users;
import com.smartworker.smartworker.login.Login;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    int id ;
    SearchView sv;
    GridView gv;
    DbOperation_Jops db_j;
    DbOperation_Users db_u;
    Button btn_profile,btn_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = getIntent().getIntExtra("user_id",2);
        sv = (SearchView)findViewById(R.id.shearch_view);
        gv = (GridView)findViewById(R.id.list_jops);
        btn_profile = (Button)findViewById(R.id.profile);
        btn_menu = (Button)findViewById(R.id.menu);
        db_j = new DbOperation_Jops(this);

        final Main_Adapter m_ad = new Main_Adapter(this,db_j.getALLJops());
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
                in.putExtra("user_id",id);
                startActivity(in);
            }
        });



        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent im = new Intent(getApplicationContext(),Map.class);
                im.putExtra("user_id",id);
                startActivity(im);
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
        builder.setMessage("Are you sure you wont to Sign out ?").setCancelable(false)
                .setTitle("Worning")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent in = new Intent(getApplicationContext(), Login.class);
                        startActivity(in);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
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
                switch(item.getItemId()){
                    case R.id.profile:
                        Intent in = new Intent(getApplicationContext(), Profile.class);
                        in.putExtra("user_id",id);
                        startActivity(in);
                        return true;

                    case R.id.map:
                        Intent im = new Intent(getApplicationContext(), Map.class);
                        im.putExtra("user_id",id);
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
