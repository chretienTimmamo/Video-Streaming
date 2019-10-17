package util;

import android.R.drawable;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class InformationDialog extends AlertDialog.Builder {
	public InformationDialog(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	Context con;

	public void successfull(Context con, String msg) {
		AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(con);

		// set title and icon
		alertDialogBuilder1.setTitle("Information");
		alertDialogBuilder1.setIcon(drawable.ic_dialog_info);
		// set dialog message
		alertDialogBuilder1.setMessage(msg).setCancelable(false)
				.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder1.create();
		// show it
		alertDialog.show();
	}
	
	
}
