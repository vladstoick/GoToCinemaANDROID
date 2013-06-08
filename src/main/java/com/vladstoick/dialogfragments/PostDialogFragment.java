package com.vladstoick.dialogfragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class PostDialogFragment extends DialogFragment {

    public static PostDialogFragment newInstance(int num){

        PostDialogFragment dialogFragment = new PostDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("num", num);
        dialogFragment.setArguments(bundle);

        return dialogFragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final EditText input = new EditText(getActivity());

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Scrie mesajul dorit")
                .setView(input)
                .setPositiveButton("Postează",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
//                                System.out.println(input.getText());
                                Intent data = getActivity().getIntent().putExtra("TEXT", input.getText().toString());

                                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                            }
                        }
                )
                .setNegativeButton("Anulează", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, getActivity().getIntent());
                    }
                })
                .create();
        input.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        return dialog;
    }
}