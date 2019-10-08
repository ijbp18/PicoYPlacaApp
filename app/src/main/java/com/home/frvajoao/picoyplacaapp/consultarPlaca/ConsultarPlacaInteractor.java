package com.home.frvajoao.picoyplacaapp.consultarPlaca;

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

public class ConsultarPlacaInteractor implements IConsultarPlacaContract.InteractorBase{
    IConsultarPlacaContract.CompleteListener listener;
    MyDbHelper helper;
    Context context;
    int totalReincidencia;

    public ConsultarPlacaInteractor(IConsultarPlacaContract.CompleteListener listener) {

        this.listener = listener;
    }

    @Override
    public void realizarRegistroPlaca(String placa) {

    }

    @Override
    public void saveRegPlacaSuccessfully() {

    }

    @Override
    public void validarFechaPicoYPlaca(String id, String ultimoDigito, Context context) {
        helper = new MyDbHelper(context, Common.DB_NAME, null, 1);
        Cursor cursor = null;
        int total = 0;

        SQLiteDatabase db = helper.getReadableDatabase();

        try {

            cursor = db.rawQuery("SELECT COUNT(*) AS total FROM " + HorarioContract.HorarioEntry.TABLE_NAME + " " +
                    " WHERE " + HorarioContract.HorarioEntry.COLUMN_ID + "=" + id + " " +
                    " AND (" + HorarioContract.HorarioEntry.COLUMN_PLACA_INI + "='" + ultimoDigito + "' OR " + HorarioContract.HorarioEntry.COLUMN_PLACA_FIN + "='" + ultimoDigito + "') ", null);


            if (cursor.moveToFirst()) {
                total = cursor.getInt(0);
            }

            cursor.close();
            db.close();


        } catch (Exception e) {
            listener.showMessageFailed("Error al validar la fecha");

        } finally {
            if (cursor != null)
                cursor.close();

        }

        listener.validateSuccessfully(total > 0);




    }

    @Override
    public void saveRegistroSancion(String placalSel) {

        Multa multaSel = new Multa(0, placalSel, Common.getFechaActual("dd/MM/yyyy HH:mm:ss"));
        SQLiteDatabase db = null;

        try {

            //abrimos bd
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MultaContract.MultaEntry.COLUMN_FECHA_REGISTRO, multaSel.getFechaRegistro());
            values.put(MultaContract.MultaEntry.COLUMN_PLACA, multaSel.getPlaca());

            db.insert(MultaContract.MultaEntry.TABLE_NAME, MultaContract.MultaEntry.COLUMN_ID, values);

            totalReincidencia = validarReincidencia(db, placalSel);
            listener.mostrarNumeroReincidencia(totalReincidencia);
            listener.saveSuccessfullySancion(placalSel);


        } catch (Exception e) {

            listener.showMessageFailed("No se pudo guardar el registro");
        } finally {
            if (db.isOpen())
                db.close();
        }

    }

    @Override
    public void guardarRegConsultaBitacora(Bitacora regBitacora) {

        SQLiteDatabase db = null;


        try {

            //abrimos bd
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(BitacoraContract.BitacoraEntry.COLUMN_FECHA_REGISTRO, regBitacora.getFechaRegistro());
            values.put(BitacoraContract.BitacoraEntry.COLUMN_PLACA, regBitacora.getPlaca());
            values.put(BitacoraContract.BitacoraEntry.COLUMN_FECHA_CONSULTA, regBitacora.getFechaConsulta());
            values.put(BitacoraContract.BitacoraEntry.COLUMN_INFRACCION, regBitacora.getInfraccion());

            db.insert(BitacoraContract.BitacoraEntry.TABLE_NAME, BitacoraContract.BitacoraEntry.COLUMN_ID, values);

            listener.saveSuccessfullyBitacora();


        } catch (Exception e) {
            listener.showMessageFailed("No se puedo guardar el registro");

        } finally {
            if (db.isOpen())
                db.close();
        }

    }

    private int validarReincidencia(SQLiteDatabase db, String placalSel) {

        Cursor cursor = null;
        int total = 0;


        try {

            cursor = db.rawQuery("SELECT COUNT(*) AS total FROM " + MultaContract.MultaEntry.TABLE_NAME + " " +
                    " WHERE " + MultaContract.MultaEntry.COLUMN_PLACA + "='" + placalSel + "' ", null);


            if (cursor.moveToFirst()) {
                total = cursor.getInt(0);
            }


        } catch (Exception e) {
            listener.showMessageFailed("No se pudo validar la placa");

        } finally {
            if (cursor != null)
                cursor.close();

        }

        return total;
    }
}
