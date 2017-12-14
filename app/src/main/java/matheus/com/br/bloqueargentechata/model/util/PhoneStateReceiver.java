package matheus.com.br.bloqueargentechata.model.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

import matheus.com.br.bloqueargentechata.model.dao.BlacklistDAO;

public class PhoneStateReceiver extends BroadcastReceiver {

    private ITelephony telephonyService;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                Class c = Class.forName(tm.getClass().getName());
                Method m = c.getDeclaredMethod("getITelephony");
                m.setAccessible(true);

                telephonyService = (ITelephony) m.invoke(tm);
                Bundle bundle = intent.getExtras();
                String incomingNumber = bundle.getString("incoming_number");

                BlacklistDAO blacklistDao = new BlacklistDAO(context);
                if (blacklistDao.getByNumber(incomingNumber).getPhoneNumber().equals(incomingNumber)) {
                    Toast.makeText(context, "Lista negra", Toast.LENGTH_SHORT).show();
                    telephonyService.endCall();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
