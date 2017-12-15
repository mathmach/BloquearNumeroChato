package matheus.com.br.bloquearnumerochato.model.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import matheus.com.br.bloquearnumerochato.R;
import matheus.com.br.bloquearnumerochato.model.entity.Blacklist;
import matheus.com.br.bloquearnumerochato.model.service.BlacklistService;

public class AddToBlacklistActivity extends AppCompatActivity implements GridView.OnClickListener {

    private EditText etPhone;
    private Button btnReset, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_number);

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
                BlacklistService blacklistService = new BlacklistService(this);
                if (blacklistService.getByNumber(etPhone.getText().toString()) == null) {
                    final Blacklist phone = new Blacklist();

                    phone.setPhoneNumber(etPhone.getText().toString());
                    blacklistService.create(phone);

                    showMessageDialog(getString(R.string.telephone_added_blacklist));
                } else {
                    showMessageDialog(getString(R.string.telephone_already_blacklist));
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

    private void showMessageDialog(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
