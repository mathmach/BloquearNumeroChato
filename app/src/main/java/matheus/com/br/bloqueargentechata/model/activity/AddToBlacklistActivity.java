package matheus.com.br.bloqueargentechata.model.activity;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import matheus.com.br.bloqueargentechata.R;
import matheus.com.br.bloqueargentechata.model.dao.BlacklistDAO;
import matheus.com.br.bloqueargentechata.model.entity.Blacklist;

public class AddToBlacklistActivity extends AppCompatActivity implements GridView.OnClickListener {

    private EditText etPhone;
    private Button btnReset, btnSubmit;
    private BlacklistDAO blackListDao;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_number);

        blackListDao = new BlacklistDAO(this);

        etPhone = findViewById(R.id.et_phone);
        btnReset = findViewById(R.id.btn_reset);
        btnSubmit = findViewById(R.id.btn_submit);

        btnReset.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSubmit) {
            if (etPhone.getText().toString().trim().length() > 0) {
                final Blacklist phone = new Blacklist();

                if (blackListDao.getByNumber(etPhone.getText().toString()) == null) {
                    phone.setPhoneNumber(etPhone.getText().toString());
                    blackListDao.create(phone);

                    showDialog();
                } else {
                    showDialogErr();
                }
                finish();
            } else {
                showMessageDialog(getString(R.string.invalid_number));
            }
        } else if (v == btnReset) {
            reset();
        }
    }

    private void reset() {
        etPhone.setText("");
    }

    private void showDialog() {
        Toast.makeText(this, R.string.telephone_added_blacklist, Toast.LENGTH_SHORT).show();
    }

    private void showDialogErr() {
        Toast.makeText(this, R.string.telephone_already_blacklist, Toast.LENGTH_SHORT).show();
    }

    private void showMessageDialog(final String message) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
