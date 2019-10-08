package com.home.frvajoao.picoyplacaapp.consultarPlaca;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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

import com.home.frvajoao.picoyplacaapp.R;
import com.home.frvajoao.picoyplacaapp.common.Common;
import com.home.frvajoao.picoyplacaapp.model.Bitacora;
import com.home.frvajoao.picoyplacaapp.model.DiaSemana;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class FormValidarPlacaActivity extends AppCompatActivity implements IConsultarPlacaContract.View {

    private int mYear, mMonth, mDay, mHour, mMinute;
    private String dateSel;
    private String horaSel;
    private int timeSel;
    private DiaSemana diaSel;
    private String fechaRegistro;
    private String fechaConsulta;
    int totalReincidencia = 0;
    StringBuilder message;
    //declaramos el presenter
    private IConsultarPlacaContract.Presenter presenter;

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

                    horaSel = Common.getMinuteFormat(hourOfDay) + ":" + Common.getMinuteFormat(minute) + ":" + Common.getMinuteFormat(c.get(Calendar.SECOND));

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

        if (isPrimeraVez) {
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
        presenter = new ConsultarPlacaPresenter(this);
    }

    private void initView() {
        ButterKnife.bind(this);
        toolbar.setTitle("Consultar Placa");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void validarTexto() {

        presenter.isValidData(txt_placa.getText().toString());

    }


    private void showAlert(String title, String message, DialogInterface.OnClickListener actionPositive) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("ACEPTAR", actionPositive);
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void limpiarComponentes() {

        txt_placa.setText("");
        cargarFechaActual();

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


    @Override
    public void onErrorInputType(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSuccessfullyInputText() {

        fechaRegistro = Common.getFechaActual("dd-MM-yyyy HH:mm:ss");
        fechaConsulta = dateSel + " " + horaSel;
        placalSel = txt_placa.getText().toString();
        presenter.validarFechaPP(placalSel, diaSel, fechaConsulta, this);


    }

    @Override
    public void validateFechaPP(boolean isDayPP) {

        if (isDayPP)
            presenter.validarHoraPP(timeSel, placalSel);
        else {

            Bitacora regBitacora = new Bitacora();
            regBitacora.setPlaca(txt_placa.getText().toString());
            regBitacora.setFechaRegistro(fechaRegistro);
            regBitacora.setInfraccion(0);
            regBitacora.setFechaConsulta(fechaConsulta);


            presenter.saveRegBitacora(regBitacora);
            showAlert("¡TRANQUILO!", "El vehículo con la placa " + placalSel + ", NO se encuentra en pico y placa y puede transitar tranquilamente.", (dialog, which) -> {
                dialog.dismiss();
                limpiarComponentes();
            });

        }

    }

    @Override
    public void displayNumeroReincidencia(int totalReincidencia) {
        this.totalReincidencia = totalReincidencia;
    }

    @Override
    public void showMessageDialog(String title, StringBuilder message, boolean isSancion) {

        showAlert(title, message.toString(), (dialog, which) -> {
            dialog.dismiss();
            if (isSancion && totalReincidencia > 1) {

                showAlert("PLACA REINCIDENTE...", "La placa " + placalSel + " cuenta con " + totalReincidencia + " sanciones por Pico y placa.", (DialogInterface dialog2, int which2) -> {
                    dialog2.dismiss();
                });

            }

            limpiarComponentes();
        });

    }

    @Override
    public void displaySaveSuccessFullySancion() {

        Bitacora regBitacora = new Bitacora();
        regBitacora.setPlaca(txt_placa.getText().toString());
        regBitacora.setFechaRegistro(fechaRegistro);
        regBitacora.setInfraccion(1);
        regBitacora.setFechaConsulta(fechaConsulta);

        presenter.saveRegBitacora(regBitacora);


    }

    @Override
    public void displaySaveSuccessFullyBitacora() {


    }

    @Override
    public void displayPlacaNotInHorarioPP() {

        Bitacora regBitacora = new Bitacora();
        regBitacora.setPlaca(txt_placa.getText().toString());
        regBitacora.setFechaRegistro(fechaRegistro);
        regBitacora.setInfraccion(0);
        regBitacora.setFechaConsulta(fechaConsulta);

        presenter.saveRegBitacora(regBitacora);
    }
}
