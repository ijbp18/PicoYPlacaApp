package com.home.frvajoao.picoyplacaapp.listarBitacora;

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

import com.home.frvajoao.picoyplacaapp.R;
import com.home.frvajoao.picoyplacaapp.adapter.AdapterBitacoraItem;
import com.home.frvajoao.picoyplacaapp.common.Common;
import com.home.frvajoao.picoyplacaapp.consultarPlaca.ConsultarPlacaPresenter;
import com.home.frvajoao.picoyplacaapp.database.MyDbHelper;
import com.home.frvajoao.picoyplacaapp.model.Bitacora;
import com.home.frvajoao.picoyplacaapp.model.BitacoraContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormBitacoraActivty extends AppCompatActivity implements ICargarBitacoraContract.View{

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

    AdapterBitacoraItem adapter;
    List registroList;

    //declaramos el presenter
    private ICargarBitacoraContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_bitacora_activty);
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


    private void initView() {
        ButterKnife.bind(this);

        toolbar.setTitle("BITÁCORA");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new CargarBitacoraPresenter(this);

        registroList = new ArrayList<Bitacora>();
        recycler_bitacora.setLayoutManager(new LinearLayoutManager(this));
        buscarBitacoraList();



    }

    private void buscarBitacoraList() {

        presenter.buscarBitacoraList(this);


    }

    private void inflarRecycler(List registroList) {

        adapter= new AdapterBitacoraItem(this, registroList);
        recycler_bitacora.setAdapter(adapter);
    }



    @Override
    public void showBitacoraList(List<Bitacora> resultado) {

        registroList = resultado;

        if(registroList != null && registroList.size() > 0 )
            inflarRecycler(registroList);
        else
            Toast.makeText(this, "No existen registros en la bitácora.", Toast.LENGTH_SHORT).show();

    }
}
