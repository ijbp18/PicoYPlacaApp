package com.home.frvajoao.picoyplacaapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.home.frvajoao.picoyplacaapp.consultarPlaca.FormValidarPlacaActivity;
import com.home.frvajoao.picoyplacaapp.listarBitacora.FormBitacoraActivty;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @OnClick(R.id.btnConsultarPlaca)
    public void validarPlacaSel() {
        startActivity(new Intent(this, FormValidarPlacaActivity.class));

    }

    @OnClick(R.id.btnBitacora)
    public void onClickRegistroBitacora() {
        startActivity(new Intent(this, FormBitacoraActivty.class));

    }

    @OnClick(R.id.btnExit)
    public void onClickExit() {
        signOut();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_log_out) {
            signOut();
        } else if (id == R.id.nav_consultar_placa) {
            startActivity(new Intent(this, FormValidarPlacaActivity.class));
        } else if (id == R.id.nav_bitacora) {
            startActivity(new Intent(this, FormBitacoraActivty.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {

        showAlert("ATENCIÓN", "Desea salir de Pico y Placa APP", (dialog, which) -> {
            dialog.dismiss();
            finish();

        }, (dialog, which) -> dialog.dismiss());

//        AlertDialog confirmDialog = new AlertDialog.Builder(this)
//                .setTitle("Salir")
//                .setMessage("Desea salir de Pico y Placa APP?")
//                .setNegativeButton("CANCELAR", (dialog, which) -> dialog.dismiss())
//                .setPositiveButton("ACEPTAR", (dialog, which) -> {
//
//
//                    finish();
//                }).create();
//
//
//        confirmDialog.show();

    }


    private void initView() {
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.inicio));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

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


    private void showAlert(String title, String message, DialogInterface.OnClickListener actionPositive, DialogInterface.OnClickListener actionNegative) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("ACEPTAR", actionPositive);
        builder.setNegativeButton("CANCELAR", actionNegative);
        AlertDialog alert = builder.create();
        alert.show();
    }


}

