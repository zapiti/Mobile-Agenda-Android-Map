package com.developer.nathan.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.MediaStore;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.developer.nathan.agenda.R;
import com.developer.nathan.agenda.dao.AlunoDAO;

import java.io.Serializable;

/**
 * Created by natha on 27/12/2017.
 */

public class SMSReceiver extends BroadcastReceiver {
    private SmsMessage sms;

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sms = SmsMessage.createFromPdu(pdu, formato);
        } else {
            sms = SmsMessage.createFromPdu(pdu);
        }

 

        String telefone = sms.getDisplayOriginatingAddress();
        AlunoDAO dao = new AlunoDAO(context);
        if (dao.ehAluno(telefone)) {
            Toast.makeText(context, "Chegou um SMS de Aluno!", Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();

        }
        dao.close();
    }
}
