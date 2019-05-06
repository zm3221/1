package com.sikalenko.java1101.Organizer.app.src.main.java.com.example.app19;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class FragmentMain extends ListFragment {

    DB db;
    SimpleCursorAdapter adapter;
    private static final long BUNDLE_CODE = 0;
    private long code;

    public static FragmentMain newInstance(final long b_code){
        final FragmentMain fragment = new FragmentMain();
        final Bundle arg = new Bundle();
        arg.putLong(String.valueOf(BUNDLE_CODE), b_code);
        fragment.setArguments(arg);
        return fragment;
    }
    public FragmentMain(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null && getArguments().containsKey(String.valueOf(BUNDLE_CODE))){
            code = getArguments().getLong(String.valueOf(BUNDLE_CODE));
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(getListView());
    }

    @Override
    public void onResume() {
        super.onResume();

        db = new DB(getActivity());
        db.open();

        if (code == 0) getActivity().setTitle("Текущие");
        else if (code == -1) getActivity().setTitle("Просроченные");
        else if (code == -2) getActivity().setTitle("Выполненные");
        else getActivity().setTitle(db.getListName(code));

        Cursor curs = db.getData(code);

        String[] from = new String[]
                {DBH.M_TITLE, DBH.M_DT, DBH.M_IDL};
        int[] to = new int[] { R.id.text1, R.id.text2, R.id.text3 };

        adapter = new SimpleCursorAdapter(getActivity(), R.layout.item, curs, from, to, 0);
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == 2){
                    Long dtLong = cursor.getLong(columnIndex);
                    String dtString = DateUtils.formatDateTime(getActivity(), dtLong,
                            DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE|
                                    DateUtils.FORMAT_SHOW_TIME);
                    TextView text2 = (TextView) view;
                    text2.setText(dtString);
                    return true;
                }
                if (columnIndex == 3){
                    long idlLong = cursor.getLong(columnIndex);
                    String idlStr = db.getListName(idlLong);
                    TextView text3 = (TextView) view;
                    text3.setText(idlStr);
                    return true;
                }
                return false;
            }
        });
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), AddEditActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        db.close();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.Add) {
            Intent intent = new Intent(getActivity(), AddEditActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "Удалить");
        menu.add(0, 2, 0, "Редактировать");
    }

    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == 1) {
            AdapterView.AdapterContextMenuInfo acmi =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            db.delItem(acmi.id);
            db.delNtf(getActivity(), acmi.id);
            adapter.swapCursor(db.getData(code));
            return true;
        }

        else if (item.getItemId() == 2) {
            AdapterView.AdapterContextMenuInfo acmi =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Intent intent = new Intent(getActivity().getApplicationContext(), AddEditActivity.class);
            intent.putExtra("id", acmi.id);
            startActivity(intent);
            return true;
        }

        return super.onContextItemSelected(item);
    }

}

