package com.sikalenko.java1101.Organizer.app.src.main.java.com.example.app19;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FragmentLists extends ListFragment {

    DB db;
    SimpleCursorAdapter adapter;

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

        getActivity().setTitle("Списки");
        db = new DB(getActivity());
        db.open();

        Cursor curs = db.getLists();
        String[] from = new String[] {DBH.L_TITLE};
        int[] to = new int[] { R.id.text1};
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.item, curs, from, to, 0);
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        FragmentMain fm =  FragmentMain.newInstance(id);
        android.app.FragmentTransaction fTrans;
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCont, fm);
        fTrans.addToBackStack(null);
        fTrans.commit();
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
            showDlg(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "Удалить");
        menu.add(0, 2, 0, "Переименовать");

    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            AdapterView.AdapterContextMenuInfo acmi =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            db.delItemInList(acmi.id);
            db.delList(acmi.id);
            adapter.swapCursor(db.getLists());
            return true;
        }

        else if (item.getItemId() == 2) {
            AdapterView.AdapterContextMenuInfo acmi =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            showDlg(acmi.id);
            return true;
        }

        return super.onContextItemSelected(item);
    }


    public void showDlg(final long idDlg){

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage("Введите имя:");

        final EditText input = new EditText(getActivity());
        if (idDlg > 0) {
            String name = db.getListName(idDlg);
            input.setText(name);
        }
        alert.setView(input);
        alert.setCancelable(true);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String value = input.getText().toString();
                if (idDlg > 0) {
                    db.updList(idDlg, value);
                } else {
                    db.addList(value);
                }
                adapter.swapCursor(db.getLists());

            }
        });
        alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {}
        });
        alert.show();
    }

}
