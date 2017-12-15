package matheus.com.br.bloquearnumerochato.model.util;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

import matheus.com.br.bloquearnumerochato.R;
import matheus.com.br.bloquearnumerochato.model.dao.BlacklistDAO;
import matheus.com.br.bloquearnumerochato.model.service.BlacklistService;

public class PhoneStateReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                Class c = Class.forName(tm.getClass().getName());
                Method m = c.getDeclaredMethod("getITelephony");
                m.setAccessible(true);

                ITelephony telephonyService = (ITelephony) m.invoke(tm);
                Bundle bundle = intent.getExtras();
                String incomingNumber = bundle.getString("incoming_number");

                BlacklistService blacklistService = new BlacklistService(context);
                if (blacklistService.getByNumber(incomingNumber).getPhoneNumber().equals(incomingNumber)) {
                    Toast.makeText(context, R.string.blacklist, Toast.LENGTH_SHORT).show();
                    telephonyService.endCall();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
