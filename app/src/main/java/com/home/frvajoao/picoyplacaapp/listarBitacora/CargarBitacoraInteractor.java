package com.home.frvajoao.picoyplacaapp.listarBitacora;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.home.frvajoao.picoyplacaapp.common.Common;
import com.home.frvajoao.picoyplacaapp.database.MyDbHelper;
import com.home.frvajoao.picoyplacaapp.model.Bitacora;
import com.home.frvajoao.picoyplacaapp.model.BitacoraContract;
import com.home.frvajoao.picoyplacaapp.model.HorarioContract;
import com.home.frvajoao.picoyplacaapp.model.Multa;
import com.home.frvajoao.picoyplacaapp.model.MultaContract;

import java.util.ArrayList;
import java.util.List;

public class CargarBitacoraInteractor implements ICargarBitacoraContract.InteractorBase{
    ICargarBitacoraContract.CompleteListener listener;
    MyDbHelper helper;
    Context context;
    int totalReincidencia;

    public CargarBitacoraInteractor(ICargarBitacoraContract.CompleteListener listener) {

        this.listener = listener;
    }


    @Override
    public void buscarBitacora(Context context) {

        helper = new MyDbHelper(context, Common.DB_NAME, null, 1);

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
            listener.showMessageFailed("Ocurrió un problema al traer la información");
        }
        finally {
            if(cursor!=null) cursor.close();

            if(db.isOpen())db.close();
        }

        listener.getBitacoraList(resultado);


    }
}
