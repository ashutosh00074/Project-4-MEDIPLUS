package com.tech.gigabyte.mediplus.diaglogbox;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.tech.gigabyte.mediplus.DrugActivity;
import com.tech.gigabyte.mediplus.database.DatabaseHelper;
import com.tech.gigabyte.mediplus.model.MedicineData;
import com.tech.gigabyte.mediplus.R;

import java.util.ArrayList;

/**
 * Created by GIGABYTE on 4/7/2017.
 * An Autocomplete Search Dialog Box For Searching Saved DRUG/Medicine .
 * Animated Search Alert Dialog .
 */

public class SearchDialog extends DialogFragment {
    AutoCompleteTextView d_name;
    Button ok, cancel;
    ArrayList<MedicineData> MedicineList;
    DatabaseHelper helper;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom);

        //Instantiates a layout XML file into its corresponding View objects.
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_search, null);
        builder.setView(view);
        builder.setTitle("Search");
        builder.setIcon(R.drawable.ic_action_search);
        init(view);
        Dialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setCancelable(false);
        return dialog;
    }

    private void init(View view) {
        d_name = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        ok = (Button) view.findViewById(R.id.button_ok);
        cancel = (Button) view.findViewById(R.id.button_cancel);
        helper = DatabaseHelper.getInstance(getActivity());
        MedicineList = new ArrayList<>();
        loaddatatoautotext();
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String search = d_name.getText().toString();
                MedicineList = helper.DrugDetails(search);

                if (MedicineList.isEmpty()) {
                    Toast.makeText(getActivity(), "Sorry Medicine not found", Toast.LENGTH_SHORT).show();
                    d_name.setText("");
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Medicine found", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), DrugActivity.class);
                    i.putExtra("Name", MedicineList.get(0).getName());
                    i.putExtra("Description", MedicineList.get(0).getDesc());
                    i.putExtra("Price", MedicineList.get(0).getPrice());
                    i.putExtra("Id", MedicineList.get(0).getId());
                    i.putExtra("Category", MedicineList.get(0).getCat());
                    i.putExtra("Mediform", MedicineList.get(0).getType());
                    i.putExtra("Instructions", MedicineList.get(0).getInst());
                    startActivity(i);
                }
                MedicineList.clear();
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
                dismiss();

            }
        });
    }

    //Loading Data in AutoCompleteTextView
    public void loaddatatoautotext() {
        ArrayList<String> autonames = new ArrayList<>();
        autonames = helper.search();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, autonames);
        d_name.setThreshold(1);
        d_name.setAdapter(adapter);
    }
}
