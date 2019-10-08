package com.home.frvajoao.picoyplacaapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.home.frvajoao.picoyplacaapp.common.Common;
import com.home.frvajoao.picoyplacaapp.database.MyDbHelper;
import com.home.frvajoao.picoyplacaapp.model.Bitacora;
import com.home.frvajoao.picoyplacaapp.model.BitacoraContract;
import com.home.frvajoao.picoyplacaapp.model.DiaSemana;
import com.home.frvajoao.picoyplacaapp.model.HorarioContract;
import com.home.frvajoao.picoyplacaapp.model.Multa;
import com.home.frvajoao.picoyplacaapp.model.MultaContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class FormValidarPlacaActivity extends AppCompatActivity {

    private int mYear, mMonth, mDay, mHour, mMinute;
    private String dateSel;
    private String horaSel;
    private int timeSel;
    private DiaSemana diaSel;
    private String fechaRegistro;
    private String fechaConsulta;
    MyDbHelper helper;
    int totalReincidencia = 0;
    StringBuilder message ;

    boolean isPrimeraVez = true;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txt_placa)
    EditText txt_placa;
    @BindView(R.id.btnValidarPlaca)
    CardView btnValidarPlaca;
    @BindView(R.id.lytSelDate)
    LinearLayout lytSelDate;
    @BindView(R.id.lytSelTime)
    LinearLayout lytSelTime;
    @BindView(R.id.lytParametrosAdd)
    LinearLayout lytParametrosAdd;
    @BindView(R.id.txt_time)
    TextView txt_time;
    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.btn_consultar)
    Button btn_consultar;
    private String placalSel;

    @OnClick(R.id.btn_consultar)
    public void validarPlacaSel() {
        validarTexto();

    }


    @BindView(R.id.cbx_fecha_actual)
    CheckBox cbx_fecha_actual;

    @OnCheckedChanged(R.id.cbx_fecha_actual)
    public void onRadioButtonCheckChanged(boolean checked) {
        if (checked) {
            lytParametrosAdd.setVisibility(View.GONE);
            cargarFechaActual();
        } else
            lytParametrosAdd.setVisibility(View.VISIBLE);
    }


    @Nullable
    @OnClick(R.id.lytSelDate)
    public void dateSelection() {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {

                    String dayOfWeek;
                    String monthStr;

                    if (year == mYear && monthOfYear == mMonth && dayOfMonth == mDay) {
                        txt_date.setText("Hoy");
                        dateSel = Common.getDayFormat(dayOfMonth) + "-" + (monthOfYear + 1) + "-" + year;
                        dayOfWeek = Common.getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
                        diaSel = new DiaSemana(String.valueOf(c.get(Calendar.DAY_OF_WEEK)), dayOfWeek);
                    } else if (year == mYear && monthOfYear == mMonth && dayOfMonth == (mDay + 1)) {

                        try {
                            SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
                            Date myDate = inFormat.parse(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            c.setTime(myDate);
                            dayOfWeek = Common.getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));

                            txt_date.setText("Mañana");
                            dateSel = Common.getDayFormat(dayOfMonth) + "-" + (monthOfYear + 1) + "-" + year;


                            diaSel = new DiaSemana(String.valueOf(c.get(Calendar.DAY_OF_WEEK)), dayOfWeek);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    } else {

                        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date myDate = inFormat.parse(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            c.setTime(myDate);
                            monthStr = Common.getMonthString((monthOfYear + 1));
                            dayOfWeek = Common.getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));

                            txt_date.setText(dayOfWeek + ", " + dayOfMonth + " de " + monthStr);
                            diaSel = new DiaSemana(String.valueOf(c.get(Calendar.DAY_OF_WEEK)), dayOfWeek);
                            dateSel = Common.getDayFormat(dayOfMonth) + "-" + (monthOfYear + 1) + "-" + year;

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }


                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }


    @Nullable
    @OnClick(R.id.lytSelTime)
    public void timeSelection() {

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {

                    Calendar datetime = Calendar.getInstance();
                    datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    datetime.set(Calendar.MINUTE, minute);

                    horaSel =  Common.getMinuteFormat(hourOfDay) + ":" + Common.getMinuteFormat(minute) + ":" + Common.getMinuteFormat(c.get(Calendar.SECOND));

                    txt_time.setText(hourOfDay + ":" + Common.getMinuteFormat(minute) + " " + Common.getAMPM(hourOfDay));
                    timeSel = Integer.parseInt(hourOfDay + Common.getMinuteFormat(minute));


                }, mHour, mMinute, true);

        timePickerDialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_validar_placa);

        init();
        initView();

        if(isPrimeraVez){
            cargarFechaActual();
            isPrimeraVez = false;
        }
    }

    private void cargarFechaActual() {


        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        dateSel = Common.getDayFormat(mDay) + "-" + (mMonth + 1) + "-" + mYear;
        txt_date.setText("Hoy");


        horaSel = Common.getMinuteFormat(c.get(Calendar.HOUR_OF_DAY)) + ":" + Common.getMinuteFormat(c.get(Calendar.MINUTE)) + ":" + Common.getMinuteFormat(c.get(Calendar.SECOND));
        timeSel = Integer.parseInt(c.get(Calendar.HOUR_OF_DAY) + Common.getMinuteFormat(c.get(Calendar.MINUTE)));


        txt_time.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + Common.getMinuteFormat(c.get(Calendar.MINUTE)) + " " + Common.getAMPM(c.get(Calendar.HOUR_OF_DAY)));
        fechaConsulta = dateSel + " " + horaSel;



    }

    private void init() {
        helper = new MyDbHelper(this, Common.DB_NAME, null, 1);
    }

    private void initView() {
        ButterKnife.bind(this);
        toolbar.setTitle("Consultar Placa");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void validarTexto() {


        String textPlaca = txt_placa.getText().toString().substring(0, 3);

        if (txt_placa.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese la placa a verificar", Toast.LENGTH_SHORT).show();
            return;
        } else if (txt_placa.getText().toString().length() <= 4) {
            Toast.makeText(this, "Ingrese una placa válida", Toast.LENGTH_SHORT).show();
            return;
        } else if (!Pattern.matches(".*[0-9].*",txt_placa.getText().toString())) {
            Toast.makeText(this, "Ingrese una placa válida", Toast.LENGTH_SHORT).show();
            return;
        } else if(!Pattern.matches("[a-zA-Z]+",textPlaca)){
            Toast.makeText(this, "Ingrese una placa válida", Toast.LENGTH_SHORT).show();
            return;
        } else{

            fechaRegistro = Common.getFechaActual("dd-MM-yyyy HH:mm:ss");
            fechaConsulta = dateSel+" "+horaSel;

            placalSel =txt_placa.getText().toString();
            validarPicoYPlaca(txt_placa.getText().toString());

        }


    }

    private void validarPicoYPlaca(String placaSel) {

        if (diaSel == null) {
            final Calendar c = Calendar.getInstance();
            diaSel = new DiaSemana(String.valueOf(c.get(Calendar.DAY_OF_WEEK)), Common.getDayOfWeek(c.get(Calendar.DAY_OF_WEEK)));

        }

        if (validarFechaPicoYPlaca(diaSel.getId(), Common.getUltimoNumeroPlaca(placaSel))) {
            validarHoraPicoYPlaca();
        } else {
            guardarRegConsultaBitacora(false);
            showAlert("¡TRANQUILO!", "El vehículo con la placa " + placalSel + ", NO se encuentra en pico y placa y puede transitar tranquilamente.", (dialog, which) -> {
                dialog.dismiss();
                limpiarComponentes();
            });

        }

    }

    private void validarHoraPicoYPlaca() {

        if (Common.validarRestriccionHora(timeSel)) {

            guardarRegistroSancion();
            guardarRegConsultaBitacora(true);

            message= new StringBuilder().append("El vehículo con la placa ").append(placalSel).append(", se encuentra en su día de pico y placa y dentro del horario prohibido.");

            showAlert("¡CUIDADO! REQUIERE SANCIÓN...", message.toString(), (dialog, which) -> {
                dialog.dismiss();
                if(totalReincidencia > 1){
                    showAlert("PLACA REINCIDENTE...", "La placa "+placalSel+" cuenta con "+totalReincidencia+" sanciones por Pico y placa.", (DialogInterface dialog2, int which2) -> {
                        dialog2.dismiss();
                    });
                }

                limpiarComponentes();
            });


        } else {

            guardarRegConsultaBitacora(false);
            showAlert("¡ATENCIÓN A LA HORA! ", "El vehículo con la placa " + placalSel + ", se encuentra en su día de pico y placa, se recomienda estar atento y NO conducir el vehículo en el horario prohibido. ", (dialog, which) -> {
                dialog.dismiss();
                limpiarComponentes();
            });
        }


    }

    private void guardarRegistroSancion() {

        Multa multaSel = new Multa(0,placalSel,Common.getFechaActual("dd/MM/yyyy HH:mm:ss"));
        SQLiteDatabase db = null;

        try {

            //abrimos bd
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MultaContract.MultaEntry.COLUMN_FECHA_REGISTRO, multaSel.getFechaRegistro());
            values.put(MultaContract.MultaEntry.COLUMN_PLACA, multaSel.getPlaca());

            db.insert(MultaContract.MultaEntry.TABLE_NAME, MultaContract.MultaEntry.COLUMN_ID, values);

            totalReincidencia = validarReincidencia(db, placalSel);


        } catch (Exception e) {

            Toast.makeText(this, "No se pudo guardar el registro" , Toast.LENGTH_SHORT).show();

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
            Toast.makeText(this, "La placa no existe", Toast.LENGTH_SHORT).show();

        } finally {
            if (cursor != null)
                cursor.close();

        }

        return total ;


    }


    private void showAlert(String title, String message,DialogInterface.OnClickListener actionPositive){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("ACEPTAR",actionPositive);
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void guardarRegConsultaBitacora(boolean conInfraccion) {

        SQLiteDatabase db = null;

        Bitacora regBitacora = new Bitacora();
        regBitacora.setPlaca(txt_placa.getText().toString());
        regBitacora.setFechaRegistro(fechaRegistro);
        regBitacora.setInfraccion(conInfraccion ? 1 : 0);
        regBitacora.setFechaConsulta(fechaConsulta);


        try {

            //abrimos bd
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(BitacoraContract.BitacoraEntry.COLUMN_FECHA_REGISTRO, regBitacora.getFechaRegistro());
            values.put(BitacoraContract.BitacoraEntry.COLUMN_PLACA, regBitacora.getPlaca());
            values.put(BitacoraContract.BitacoraEntry.COLUMN_FECHA_CONSULTA, regBitacora.getFechaConsulta());
            values.put(BitacoraContract.BitacoraEntry.COLUMN_INFRACCION, regBitacora.getInfraccion());

            db.insert(BitacoraContract.BitacoraEntry.TABLE_NAME, BitacoraContract.BitacoraEntry.COLUMN_ID, values);


        } catch (Exception e) {

            Toast.makeText(this, "No se puedo guardar el registro", Toast.LENGTH_SHORT).show();

        } finally {
            if (db.isOpen())
                db.close();
        }
    }

    private void limpiarComponentes() {

        txt_placa.setText("");
        cargarFechaActual();

    }

    private boolean validarFechaPicoYPlaca(String id, String ultimoDigito) {

        Cursor cursor = null;
        int total = 0;

        SQLiteDatabase db = helper.getReadableDatabase();

        try {

            cursor = db.rawQuery("SELECT COUNT(*) AS total FROM " + HorarioContract.HorarioEntry.TABLE_NAME + " " +
                    " WHERE " + HorarioContract.HorarioEntry.COLUMN_ID + "=" + id + " " +
                    " AND (" + HorarioContract.HorarioEntry.COLUMN_PLACA_INI + "='" + ultimoDigito + "' OR " + HorarioContract.HorarioEntry.COLUMN_PLACA_FIN + "='" + ultimoDigito+"') ", null);


            if (cursor.moveToFirst()) {
                total = cursor.getInt(0);
            }

            cursor.close();
            db.close();


        } catch (Exception e) {
            Toast.makeText(this, "La placa no existe", Toast.LENGTH_SHORT).show();

        } finally {
            if (cursor != null)
                cursor.close();

        }

        return total > 0;


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

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


}
