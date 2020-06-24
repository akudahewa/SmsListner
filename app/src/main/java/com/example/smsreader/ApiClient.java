package com.example.smsreader;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.smsreader.exception.BookingApiException;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiClient {

    private String endPoint = "http://api.androiddeft.com/volley/json_object.json";
    private static final String KEY_EMPLOYEE_ID = "employee_id";
    private String TAG =this.getClass().getSimpleName();
    private int nextAppoinmentNo;

    public int getNextAppoinmentNumber(Context context,String source,String code) throws BookingApiException {
        Log.i(TAG,"Invorking REST API ...");
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, endPoint, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG,"Response from REST API :"+response);
                        try {
                            nextAppoinmentNo = response.getInt(KEY_EMPLOYEE_ID);
                        } catch (JSONException e) {
                            Log.e(TAG,e.getMessage());

                        }
                    }
                },new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,error.getMessage());
                    }
                });
        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
        if(nextAppoinmentNo !=0){
            return nextAppoinmentNo;
        }
        throw new BookingApiException("Api Error");
    }


    public int accessEndPoint(Context context){
        Integer employeeId =0;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, endPoint, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("!!!!!!!!!!!!!!!!!!! "+response );
                            //Parse the JSON response
                            Integer employeeId = response.getInt(KEY_EMPLOYEE_ID);
                            System.out.println("MMMMMMMMMMMMMMMMMM "+employeeId);
                            try {

                             //   SmsManager sms = SmsManager.getDefault(); // using android SmsManager sms.sendTextMessage(phone_Num, null, send_msg, null, null); // adding number and text
                             //   sms.sendTextMessage("5554", null, "Test msg", null, null);

                            } catch (Exception e) {


                                e.printStackTrace();

                            }
//                            String name = response.getString(KEY_NAME);
//                            String dob = response.getString(KEY_DOB);
//                            String designation = response.getString(KEY_DESIGNATION);
//                            String contactNumber = response.getString(KEY_CONTACT_NUMBER);
//                            String email = response.getString(KEY_EMAIL);
//                            String salary = response.getString(KEY_SALARY);
//
//                            //Create String out of the Parsed JSON
//                            StringBuilder textViewData = new StringBuilder().append("Employee Id: ")
//                                    .append(employeeId.toString()).append(NEW_LINE);
//                            textViewData.append("Name: ").append(name).append(NEW_LINE);
//                            textViewData.append("Date of Birth: ").append(dob).append(NEW_LINE);
//                            textViewData.append("Designation: ").append(designation).append(NEW_LINE);
//                            textViewData.append("Contact Number: ").append(contactNumber).append(NEW_LINE);
//                            textViewData.append("Email: ").append(email).append(NEW_LINE);
//                            textViewData.append("Salary: ").append(salary).append(NEW_LINE);
//
//                            //Populate textView with the response
//                            mTxtDisplay.setText(textViewData.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
        return employeeId;

    }
}
