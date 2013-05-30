package com.vladstoick.gotocinema.dialogfragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;

public class ProgressDialogFragment extends DialogFragment {
	 
	public static ProgressDialogFragment newInstance() {
		return new ProgressDialogFragment ();
	}
 
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
 
		final ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setMessage("Se încarcă");
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
 
		// Disable the back button
		OnKeyListener keyListener = new OnKeyListener() {
 
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {

                return keyCode == KeyEvent.KEYCODE_BACK;
            }
		
		};
		dialog.setOnKeyListener(keyListener);
		return dialog;
	}
 
}