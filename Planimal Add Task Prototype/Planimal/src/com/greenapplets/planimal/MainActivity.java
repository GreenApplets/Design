package com.greenapplets.planimal;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	private static final String DATA = "PlanimalSave.txt";

	final Context context = this;
	
	Button add;
	Button toPet;
	Button toSchedule;
	Button toShop; 
	Button addPIC;
	
	boolean isPromptUp = false;
	
	DAO dao = new DAO(MainActivity.this);
	File file = new File(DATA);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);
		
		add = (Button) findViewById(R.id.addTask);
		
		try {
			readFile();
			populateList();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// jfc this is so long
		// add listener to add button
		add.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isPromptUp){
					isPromptUp = true;
												
					LayoutInflater li = LayoutInflater.from(context);
					View promptView = li.inflate(R.layout.prompt_add, null);
					
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					
					alertDialogBuilder.setView(promptView);
					
					final EditText editPIC = (EditText) promptView.findViewById(R.id.editPIC);
					final EditText editName = (EditText) promptView.findViewById(R.id.editName);
					final EditText editPassword = (EditText) promptView.findViewById(R.id.editPassword);
					final EditText editVenue = (EditText) promptView.findViewById(R.id.editVenue);
					final DatePicker deadline = (DatePicker) promptView.findViewById(R.id.datePicker1);
					
					
					
					alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    @SuppressWarnings("deprecation")
						public void onClick(DialogInterface dialog,int id) {
					    	
					    	Task2 nuTask = new Task2(editName.getText().toString(), " ", editVenue.getText().toString(), new Date(deadline.getYear() - 1900, 
					    			deadline.getMonth(), deadline.getDayOfMonth()), editPIC.getText().toString(), editPassword.getText().toString());
					    	
					    		dao.taskList.add(nuTask);
					    		populateList();
					    		
					    		try {
									writeFile(nuTask);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					    	
					    	isPromptUp = false;
					    }
					  })
					.setNegativeButton("Cancel",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
					    	isPromptUp = false;
					    	dialog.cancel();
					    }
					  });
					
					AlertDialog alertDialog = alertDialogBuilder.create();
					
					alertDialog.show();
				}
			}
		});
	}
	
	public void populateList(){
		String[] fill = new String[dao.taskList.size()];
		
		for(int i = 0; i < dao.taskList.size(); i++){
			fill[i] = dao.taskList.get(i).getTask();
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_task, fill); 
		
		ListView list = (ListView) findViewById(R.id.taskList);
		list.setAdapter(adapter);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//DAO reference
	// loading
	public void readFile() throws IOException, ParseException{
		dao.readFromFile(MainActivity.this);
	}
	
	//DAO reference
	// saving
	public void writeFile(Task2 task) throws IOException{
		dao.writeToFile(MainActivity.this, task);
	}
	
}
