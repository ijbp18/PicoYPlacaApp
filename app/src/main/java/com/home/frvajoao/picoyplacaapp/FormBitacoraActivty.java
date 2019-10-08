package com.home.frvajoao.picoyplacaapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.home.frvajoao.picoyplacaapp.adapter.AdapterBitacoraItem;
import com.home.frvajoao.picoyplacaapp.common.Common;
import com.home.frvajoao.picoyplacaapp.database.MyDbHelper;
import com.home.frvajoao.picoyplacaapp.model.Bitacora;
import com.home.frvajoao.picoyplacaapp.model.BitacoraContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormBitacoraActivty extends AppCompatActivity {

    @BindView(R.id.btn_regresar)
    Button btn_regresar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_bitacora)
    RecyclerView recycler_bitacora;
    @OnClick(R.id.btn_regresar)
    public void onClickRegresar(){
        finish();
    }


    MyDbHelper helper;
    AdapterBitacoraItem adapter;
    List registroList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_bitacora_activty);
        init();
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        helper = new MyDbHelper(this, Common.DB_NAME, null, 1);
    }

    private void initView() {
        ButterKnife.bind(this);

        toolbar.setTitle("BITÁCORA");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        registroList = new ArrayList<>();
        recycler_bitacora.setLayoutManager(new LinearLayoutManager(this));
        registroList = cargarListaConsultas();

        if(registroList != null && registroList.size() > 0 )
            inflarRecycler(registroList);
        else
            Toast.makeText(this, "No existen registros en la bitácora.", Toast.LENGTH_SHORT).show();

    }

    private void inflarRecycler(List registroList) {

        adapter= new AdapterBitacoraItem(this, registroList);
        recycler_bitacora.setAdapter(adapter);
    }

    private List<Bitacora> cargarListaConsultas() {

        Bitacora bitacora;
        List<Bitacora> resultado = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase db = null;


        try{
            db = helper.getReadableDatabase();

            cursor = db.rawQuery("SELECT " + BitacoraContract.BitacoraEntry.COLUMN_ID + ", " + BitacoraContract.BitacoraEntry.COLUMN_FECHA_REGISTRO + ", " + BitacoraContract.BitacoraEntry.COLUMN_PLACA + ", " + BitacoraContract.BitacoraEntry.COLUMN_FECHA_CONSULTA + ", " + BitacoraContract.BitacoraEntry.COLUMN_INFRACCION + " FROM " + BitacoraContract.BitacoraEntry.TABLE_NAME + " ORDER BY " + BitacoraContract.BitacoraEntry.COLUMN_ID + " DESC", null);

            while (cursor.moveToNext()){

                bitacora = new Bitacora();

                bitacora.setId(cursor.getInt(0));
                bitacora.setFechaRegistro(cursor.getString(1));
                bitacora.setPlaca(cursor.getString(2));
                bitacora.setFechaConsulta(cursor.getString(3));
                bitacora.setInfraccion(cursor.getInt(4));
                resultado.add(bitacora);

            }


        }catch(Exception e){
            System.out.println("error: *** "+ e.getMessage());
            Toast.makeText(this, "El documento no existe", Toast.LENGTH_SHORT).show();
        }
        finally {
            if(cursor!=null) cursor.close();

            if(db.isOpen())db.close();
        }


        return resultado;


    }


}
