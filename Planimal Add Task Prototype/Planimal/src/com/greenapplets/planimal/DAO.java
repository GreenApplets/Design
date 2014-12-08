package com.greenapplets.planimal;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.ContextWrapper;

public class DAO extends ContextWrapper{
	private static final String DATA = "PlanimalSave.txt";
	static File file= new File(DATA); //file where the tasks are written
	//MainActivity m = new MainActivity();
	static List<Task2> taskList = new ArrayList<Task2>();
	
	public DAO(Context base){
		super(base);
	}
	
	public static void writeToFile(Context ctx, Task2 task) throws IOException {
		try {
			FileOutputStream fos = ctx.openFileOutput(DATA, Context.MODE_APPEND);
			String temp = dateToString(task.deadline);
			String data = task.taskname + ";" + task.desc + ";" + task.venue + ";" + temp + ";" + task.pic.getPICName() + ";" + task.pic.getPassword();
			fos.write(data.getBytes());
			// for line separator (CR, LF)
			fos.write(13); // for newline
			fos.write(10); // for newline
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readFromFile(Context ctx) throws IOException, ParseException {
		BufferedReader br = null;
		FileInputStream fis = null;
		if (file != null) {
			try {
				fis = ctx.openFileInput(DATA);
				br= new BufferedReader(new InputStreamReader(fis));
				String line = br.readLine();
				//byte[] dataArray = new byte[fis.available()];
				while(line != null){
					String[] data= line.split(";");
					String dltemp = data[3];
					Task2 temp = new Task2();
					Date datetemp = null;
					try {
						datetemp = temp.convert(dltemp);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Task2 b= new Task2(data[0], data[1], data[2], datetemp,data[4], data[5]);
					taskList.add(b);
					line=br.readLine();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}catch(IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						fis.close();
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static String dateToString (Date date){
		String formatted = date.getMonth() + "/" +date.getDay()+ "/" + ((date.getYear()) + 1900) + " at " + date.getHours() + ":" + date.getMinutes();
		return formatted;
	}
	
}
