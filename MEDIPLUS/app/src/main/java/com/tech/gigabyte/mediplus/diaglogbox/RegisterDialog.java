package com.tech.gigabyte.mediplus.diaglogbox;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tech.gigabyte.mediplus.R;

/**
 * Created by GIGABYTE on 4/7/2017.
 * DIALOG for Registration of new user
 */

public class RegisterDialog extends DialogFragment {
    EditText UID, pass;
    Button register, cancel;

    @Override
    //Registration Alert Dialog .
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_register, null);
        builder.setView(view);
        init(view);
        Dialog dialog = builder.create();
        setCancelable(false);
        return dialog;
    }

    public void init(View view) {
        UID = (EditText) view.findViewById(R.id.register_UID);
        pass = (EditText) view.findViewById(R.id.register_PASS);

        register = (Button) view.findViewById(R.id.register_btn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Interface for accessing and modifying preference data returned by getSharedPreferences(String, int).
                SharedPreferences shaPreferences = getActivity().getSharedPreferences("USERLOGININFO", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shaPreferences.edit();
                editor.putString("USERNAME", UID.getText().toString());
                editor.putString("PASSWORD", pass.getText().toString());
                editor.apply();
                Toast.makeText(getActivity(), "Successfully stored the values", Toast.LENGTH_SHORT).show();
                dismiss();
            }

        });
        cancel = (Button) view.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });
    }

}
