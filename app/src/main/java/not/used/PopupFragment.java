package not.used;

import com.kyriakosAlexandrou.phdresearch.R;
import com.kyriakosAlexandrou.phdresearch.R.id;
import com.kyriakosAlexandrou.phdresearch.R.layout;
import com.kyriakosAlexandrou.phdresearch.sqlite.DatabaseHelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class PopupFragment extends DialogFragment{
	private EditText etEditTimerTime;
	private Dialog dialog;
	private DatabaseHelper databaseHelper;
	
	 public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	
		 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        //get the layout inflater
	        LayoutInflater inflater = getActivity().getLayoutInflater();
	        final View view = inflater.inflate(R.layout.fragment_popup,null);
//	        databaseHelper = new DatabaseHelper(this);
	        etEditTimerTime = (EditText) view.findViewById(R.id.editTimerTime);
	        
	        //inflate and set the layout for the dialog
	        //pass null as the parent view because its going in the dialog layout
	        builder.setView(view);        
	        builder.setMessage("Edit Timer Time")
	               .setPositiveButton("Done", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   int temp = Integer.parseInt(etEditTimerTime.getText().toString());
//	                	   storeTimerTimeToDB(temp);
	                   }
	               })
	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog                	   
	                   }
	               });
	        dialog = builder.create();
	        // Create the AlertDialog object and return it
	        return builder.create();
	 }	
}
