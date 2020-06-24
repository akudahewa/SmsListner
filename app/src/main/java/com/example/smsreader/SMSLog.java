package com.example.smsreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSLog extends BroadcastReceiver {
    //private BlockingDeque<String> queue = new LinkedBlockingDeque>String>();

    private String TAG = this.getClass().getSimpleName();

    public SMSLog(){
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"Recevied a sms to target device");
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from = null;
            String msg_body;
            int appointmentNo = 0;
            if (bundle != null){
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        msg_body = msgs[i].getMessageBody();
//                        Log.i(TAG, msg:" sms from :"+msg_from);
                        Log.i(TAG,"RECEIVED SOURCE :"+msg_from);
                        appointmentNo=getAppoinmentNo(context,msg_from,msg_body);
                        Log.i(TAG,"Genarated appoinment no :"+appointmentNo);
                        sendSMS(msg_from,generateMsgBody(appointmentNo));
                    }
                }
                catch(Exception e) {
                    System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeee "+e.getStackTrace());
                   e.printStackTrace();
                   sendSMS(msg_from,generateErrorResponseMsgBody());
                }
            }
        }

    }
    private String generateMsgBody(int appoinmentNo){
        String msg ="Appoinment No :"+appoinmentNo;
        return msg;
    }
    
    private String generateErrorResponseMsgBody(){
        return "Sorry !!. Try again later";
    }

    private boolean sendSMS(String number,String msg){
        Log.i(TAG,"SEND SMS TO CLIENT :"+number);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, msg, null, null);
        return true;
    }



    private int getAppoinmentNo(Context context,String source,String code) throws Exception{
        ApiClient apiClient = new ApiClient();
        return apiClient.getNextAppoinmentNumber(context,source,code);
    }
}
