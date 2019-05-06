package com.sikalenko.java1101.Organizer.app.src.main.java.com.example.app19;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.NoSuchElementException;

public class AddEditActivity extends AppCompatActivity {

    EditText title;
    TextView tvTime, tvDate, tvNTF, tvStatus;
    Spinner sp;
    CheckBox checkBox;
    Calendar dateTime;
    DB db;
    long ID;
    long dtNTF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        setTitle("Событие");

        db = new DB(this);
        db.open();

        ID = 0;
        dtNTF = 0;

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            ID = extras.getLong("id");

        sp = (Spinner) findViewById(R.id.spinner);
        Cursor c = db.getLists();
        String[] from = new String[] {DBH.L_TITLE};
        int[] to = new int[] { R.id.tvSpinner};

        SimpleCursorAdapter sca = new SimpleCursorAdapter
                (this, R.layout.spiner_item, c, from, to, 0);
        sca.setDropDownViewResource(R.layout.spiner_item);
        sp.setAdapter(sca);

        title = (EditText) findViewById(R.id.etTitle);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvNTF  = (TextView) findViewById(R.id.tvNTF);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        tvStatus = (TextView) findViewById(R.id.textView5);
        dateTime = Calendar.getInstance();
        dateTime.set(Calendar.DAY_OF_YEAR, dateTime.get(Calendar.DAY_OF_YEAR) + 1);
        dateTime.set(Calendar.HOUR, dateTime.get(Calendar.HOUR) + 1);

        String tString = DateUtils.formatDateTime(AddEditActivity.this,
                dateTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME);
        String dString = DateUtils.formatDateTime(AddEditActivity.this,
                dateTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE);
        tvDate.setText(dString);
        tvTime.setText(tString);

        if (ID > 0) {
            Cursor curs = db.getItem(ID);
            curs.moveToFirst();
            title.setText(curs.getString(1));
            long dtLong = curs.getLong(2);
            long idsp = curs.getLong(3);
            int cbState = curs.getInt(4);

            Cursor cNtf = db.getNtf(ID);
            if (cNtf.moveToFirst()) {
                dtNTF = cNtf.getLong(1);
                String dtString = DateUtils.formatDateTime(getApplicationContext(), dtNTF,
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE |
                                DateUtils.FORMAT_SHOW_TIME);
                tvNTF.setText(dtString);
            }

            boolean cbs = cbState == 1 ? true : false;
            int pos = idToPos(sp.getAdapter(), idsp);
            dateTime.setTimeInMillis(dtLong);
            String timeString = DateUtils.formatDateTime(AddEditActivity.this,
                    dtLong, DateUtils.FORMAT_SHOW_TIME);
            String dateString = DateUtils.formatDateTime(AddEditActivity.this,
                    dtLong, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE);

            tvDate.setText(dateString);
            tvTime.setText(timeString);
            sp.setSelection(pos);
            checkBox.setChecked(cbs);
        }
        else{
            tvStatus.setVisibility(View.INVISIBLE);
            checkBox.setVisibility(View.INVISIBLE);
        }

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTimePicker();
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDatePicker();
            }
        });

        tvNTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callNtfPicker();
            }
        });

    }

    public int idToPos(Adapter adapter, long idtps) throws NoSuchElementException{
        int count = adapter.getCount();
        for(int pos = 0; pos < count; pos++)
            if(idtps==adapter.getItemId(pos))
                return pos;
        throw new NoSuchElementException();

    }

    void callTimePicker() {
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        dateTime.set(Calendar.MINUTE, minute);
                        tvTime.setText(DateUtils.formatDateTime(AddEditActivity.this,
                                dateTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
                    }
                }, dateTime.get(Calendar.HOUR_OF_DAY),
                dateTime.get(Calendar.MINUTE), true);
        tpd.show();
    }

    void callDatePicker() {
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateTime.set(Calendar.YEAR, year);
                        dateTime.set(Calendar.MONTH, month);
                        dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        tvDate.setText(DateUtils.formatDateTime(AddEditActivity.this,
                                dateTime.getTimeInMillis(),
                                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE));
                    }
                }, dateTime.get(Calendar.YEAR),
                dateTime.get(Calendar.MONTH),
                dateTime.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    void callNtfPicker(){
        final AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        final CharSequence[] ntfItems = {
                "отсутствует", "в указанное время", "за 5 мин.", "за 15 мин.",
                "за 30 мин.", "за 1 ч.", "за 2 ч.", "за 12 ч.", "за 1 день"
        };

        alt_bld.setSingleChoiceItems(ntfItems, 0, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int item) {

            }
        });
        alt_bld.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

            }  });
        alt_bld.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
            int position = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

            if (position != 0) {
                dtNTF = dateTime.getTimeInMillis();
                switch (position) {
                    case 2: dtNTF -= 5 * 60 * 1000; break;
                    case 3: dtNTF -= 15 * 60 * 1000; break;
                    case 4: dtNTF -= 30 * 60 * 1000; break;
                    case 5: dtNTF -= 60 * 60 * 1000; break;
                    case 6: dtNTF -= 2 * 60 * 60 * 1000; break;
                    case 7: dtNTF -= 12 * 60 * 60 * 1000; break;
                    case 8: dtNTF -= 24 * 60 * 60 * 1000; break;
                }

                String dtString = DateUtils.formatDateTime(getApplicationContext(), dtNTF,
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE |
                                DateUtils.FORMAT_SHOW_TIME);
                tvNTF.setText(String.valueOf(dtString));
            }

            else{
                dtNTF = 0;
                tvNTF.setText(String.valueOf("отсутствует"));
            }

        }  });

        AlertDialog alert = alt_bld.create();
        alert.show();

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_edit, menu);
        MenuItem delBtn = menu.findItem(R.id.DelBtn);

        delBtn.setVisible(ID != 0);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.SaveBtn)  {

            String sNm = title.getText().toString();
            long dt = dateTime.getTimeInMillis();
            long idsp = sp.getSelectedItemId();
            int cbState = checkBox.isChecked() ? 1 : 0;

            if (ID > 0) {

                db.updItem(ID, sNm, dt, idsp, cbState);

                Cursor c = db.getNtf(ID);
                if (c.moveToFirst()){
                    if(dtNTF != 0)
                        db.updNtf(this, ID, dtNTF);
                    else
                        db.delNtf(this, ID);
                }

                else{
                    if(dtNTF != 0)
                        db.addNtf(this, ID, dtNTF);
                }

            }

            else {

                long idm = db.addItem(sNm, dt, idsp, cbState);
                if(dtNTF != 0)
                    db.addNtf(this, idm, dtNTF);
            }
            db.close();
            this.finish();

            return true;
        }

        else if (id == R.id.DelBtn) {

            db.delItem(ID);
            db.delNtf(this, ID);

            db.close();
            this.finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();

    }

}
