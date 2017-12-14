package matheus.com.br.bloquearnumerochato.model.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.TextView;

import matheus.com.br.bloquearnumerochato.model.dao.BlacklistDAO;
import matheus.com.br.bloquearnumerochato.model.entity.Blacklist;

import java.util.List;


import matheus.com.br.bloquearnumerochato.R;
import matheus.com.br.bloquearnumerochato.model.util.CustomArrayAdapter;

public class MainActivity extends AppCompatActivity implements GridView.OnClickListener, GridView.OnItemLongClickListener {

    private FloatingActionButton btn_add_blacklist;
    public ListView listview;
    private BlacklistDAO blackListDao;
    public static List<Blacklist> blockList;
    private int selectedRecordPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkPermission();

        btn_add_blacklist = findViewById(R.id.btn_add_blacklist);
        btn_add_blacklist.setOnClickListener(this);
        listview = findViewById(R.id.listview);

        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.list_item, listview, false);
        listview.addHeaderView(rowView);
        listview.setOnItemLongClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
            }
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
    }

    private void populateNoRecordMsg() {
        if (blockList.size() == 0) {
            final TextView tv = new TextView(this);
            tv.setPadding(5, 5, 5, 5);
            tv.setTextSize(15);
            tv.setText(R.string.no_record_found);
            listview.addFooterView(tv);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn_add_blacklist) {
            startActivity(new Intent(this, AddToBlacklistActivity.class));
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            selectedRecordPosition = position - 1;
            showDialog();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        blackListDao = new BlacklistDAO(this);
        blockList = blackListDao.getAllBlacklist();

        if (listview.getChildCount() > 1)
            listview.removeFooterView(listview.getChildAt(listview.getChildCount() - 1));

        listview.setAdapter(new CustomArrayAdapter(this, R.layout.list_item, blockList));

        populateNoRecordMsg();
    }

    private void showDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage(R.string.confirm_delete_record);
        alertDialogBuilder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                try {
                    blackListDao.delete(blockList.get(selectedRecordPosition));
                    blockList.remove(selectedRecordPosition);
                    listview.invalidateViews();
                    selectedRecordPosition = -1;
                    populateNoRecordMsg();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}