package com.crossover.conferencemanagement.util;

import android.os.Parcelable;

/**
 * Created by PareshDudhat on 13-03-2017.
 */

public interface DialogListener extends Parcelable{
    void onDialogItemSelected(int index);

    void onDialogCancel();

    void onDialogOk();

    void onDialogMultiItemSelected(int which, boolean isChecked);
}
