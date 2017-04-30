package com.crossover.conferencemanagement.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import java.util.Calendar;

/**
 * Created by PareshDudhat on 13-03-2017.
 */

public class CommonUtils {
    public static void showDatePicker(String date, Context context, DatePickerDialog.OnDateSetListener dateSetListener) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (!TextUtils.isEmpty(date)) {
            String[] dateParts = date.split("-");
            year = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]) - 1;
            day = Integer.parseInt(dateParts[2]);
        }
        DatePickerDialog dateDialog = new DatePickerDialog(context,
                dateSetListener, year, month, day);
        dateDialog.show();
    }

    public static AlertDialog.Builder getListDialog
            (Context context, String title, final String[] values,
             final boolean[] checkedItems, String negativeButtonString, final DialogListener dialogListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMultiChoiceItems(values, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if (which == 0) {
                    for (int i = 1; i < checkedItems.length; i++) {
                        checkedItems[i] = isChecked;
                        ((AlertDialog) dialog).getListView().setItemChecked(i, isChecked);
                    }
                } else {
                    if (!isChecked) {
                        checkedItems[0] = false;
                        ((AlertDialog) dialog).getListView().setItemChecked(0, false);
                    } else {
                        boolean allWebSelected = true;
                        for (int i = 1; i < checkedItems.length; i++) {
                            if (!checkedItems[i]) {
                                allWebSelected = false;
                                break;
                            }
                        }
                        checkedItems[0] = allWebSelected;
                        ((AlertDialog) dialog).getListView().setItemChecked(0, allWebSelected);
                    }
                }
                // builder.setMultiChoiceItems(values, checkedItems, this);
                dialogListener.onDialogMultiItemSelected(which, isChecked);
            }
        });
        builder.setNegativeButton(negativeButtonString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogListener.onDialogCancel();
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogListener.onDialogOk();
            }
        });
        return builder;
    }
}
