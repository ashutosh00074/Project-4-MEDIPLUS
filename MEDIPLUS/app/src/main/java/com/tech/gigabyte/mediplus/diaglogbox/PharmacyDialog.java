package com.tech.gigabyte.mediplus.diaglogbox;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tech.gigabyte.mediplus.PharmacyInfo;
import com.tech.gigabyte.mediplus.database.DatabaseHelper;
import com.tech.gigabyte.mediplus.R;
import com.tech.gigabyte.mediplus.util.Utility;

/**
 * Created by GIGABYTE on 4/7/2017.
 * Dialog for storing NAME,PHONE,ADDRESS
 */

public class PharmacyDialog extends DialogFragment {
    EditText name, num,address;
    Button save,cancel;
    String p_name, p_num, p_address;
    DatabaseHelper helper;

    @Override
    //This is typically used to show an AlertDialog instead of a generic Dialog
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater Inflater =getActivity().getLayoutInflater();
        View view= Inflater.inflate(R.layout.dialog_pharma,null);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom);
        builder.setView(view);
        builder.setTitle("Enter Pharmacy Details");
        setCancelable(false);
        Dialog dialog=builder.create();
        init(view);
        return dialog;
    }
    public void init(View v)
    {
        name=(EditText)v.findViewById(R.id.et_name);
        num =(EditText)v.findViewById(R.id.et_num);
        address=(EditText)v.findViewById(R.id.et_address);
        save=(Button)v.findViewById(R.id.btn_Save_pharmacy);
        cancel=(Button)v.findViewById(R.id.btn_cancel_pharmacy);
        helper= DatabaseHelper.getInstance(getActivity());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_name =name.getText().toString();
                p_num = num.getText().toString();
                p_address =address.getText().toString();
                boolean isnum= Utility.isNum(p_num);
                if(isnum) {
                    long iresult = helper.insertInPharmacy(p_name, p_num, p_address);
                    if (iresult != -1) {
                        Toast.makeText(getActivity(), "Successfully Saved", Toast.LENGTH_SHORT).show();
                        Intent Home = new Intent(getActivity(), PharmacyInfo.class);
                        startActivity(Home);
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(getActivity(), "Enter valid phone num", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
