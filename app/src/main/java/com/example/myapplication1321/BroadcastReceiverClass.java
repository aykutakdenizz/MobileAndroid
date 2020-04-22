package com.example.myapplication1321;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class BroadcastReceiverClass extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getAction();

        if (state.equals("android.intent.action.PHONE_STATE")) {
            String savedNumber;
            if (intent.getExtras().getString(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                savedNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Toast.makeText(context, "IN COMING:" + savedNumber, Toast.LENGTH_SHORT).show();

            }
            else if (intent.getExtras().getString(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){

            }
            else if (intent.getExtras().getString(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                savedNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Toast.makeText(context, "OUT GOING:" + savedNumber, Toast.LENGTH_SHORT).show();
            }

        }
        else if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            String number = getSMSNumber(bundle);
            Toast.makeText(context, "IN SMS:"+number, Toast.LENGTH_SHORT).show();
        }

    }


    private String getSMSNumber(Bundle bundle){
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] msgs = new SmsMessage[pdus.length];
        String number = "";
        for (int i=0; i<msgs.length; i++)
        {
            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i], "3gpp" );
            number += msgs[i].getDisplayOriginatingAddress();
        }
        return number;
    }
}
