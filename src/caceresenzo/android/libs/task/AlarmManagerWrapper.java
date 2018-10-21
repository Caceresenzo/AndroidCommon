package caceresenzo.android.libs.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import caceresenzo.libs.string.StringUtils;

/**
 * Alarm Utilities<br>
 * <br>
 * Help from: <a href="https://stackoverflow.com/questions/5938213/android-alarmmanager-rtc-wakeup-vs-elapsed-realtime-wakeup?noredirect=1&lq=1">a stackoverflow post</a>
 * 
 * @author Enzo CACERES
 */
public class AlarmManagerWrapper {
	
	/* Tag */
	public static final String TAG = AlarmManagerWrapper.class.getSimpleName();
	
	/* Constants */
	public static final int NO_REPEAT = -1;
	public static final int NO_REQUEST_CODE = -1;
	
	public static final String DEFAULT_ALARM_INTENT_BASE_NAME = "AlarmIntent_";
	
	/* Variables */
	private Context context;
	private String alarmBaseName;
	private ArrayList<String> alarmIntents;
	
	/* Constructor */
	public AlarmManagerWrapper() {
		this(null, DEFAULT_ALARM_INTENT_BASE_NAME);
	}
	
	/* Constructor */
	public AlarmManagerWrapper(Context context) {
		this(context, DEFAULT_ALARM_INTENT_BASE_NAME);
	}
	
	/* Constructor */
	public AlarmManagerWrapper(String alarmBaseName) {
		this(null, alarmBaseName);
	}
	
	/* Constructor */
	public AlarmManagerWrapper(Context context, String alarmBaseName) {
		this.context = context;
		this.alarmBaseName = alarmBaseName;
		
		this.alarmIntents = new ArrayList<>();
		
		if (!StringUtils.validate(alarmBaseName)) {
			alarmBaseName = DEFAULT_ALARM_INTENT_BASE_NAME;
		}
	}
	
	/**
	 * Attach/update the wrapper context
	 * 
	 * @param context
	 *            New context
	 * @return Itself
	 */
	public AlarmManagerWrapper context(Context context) {
		this.context = context;
		
		return this;
	}
	
	/**
	 * Start an alarm
	 * 
	 * @param repeatInterval
	 *            Time in milliseconds between sending
	 * @return Alarm name
	 * 
	 * @throws NullPointerException
	 *             If attached context is null
	 */
	public String schedule(long repeatInterval) {
		return schedule(repeatInterval, null);
	}
	
	/**
	 * Start an alarm
	 * 
	 * @param repeatInterval
	 *            Time in milliseconds between sending
	 * @param clazz
	 *            Set a custom alarm class
	 * @return Alarm name
	 * 
	 * @throws NullPointerException
	 *             If attached context is null
	 */
	public String schedule(long repeatInterval, Class<?> clazz) {
		return schedule(NO_REQUEST_CODE, repeatInterval, clazz);
	}
	
	/**
	 * Start an alarm
	 * 
	 * @param requestCode
	 *            Request code to check if your alarm has been correctly schedule
	 * @param repeatInterval
	 *            Time in milliseconds between sending
	 * @param clazz
	 *            Set a custom alarm clazz
	 * @return Alarm name
	 * 
	 * @throws NullPointerException
	 *             If attached context is null
	 */
	public String schedule(int requestCode, long repeatInterval, Class<?> clazz) {
		checkContext();
		
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		long now = new Date().getTime();
		
		String intentName = alarmBaseName + now;
		Intent intent = new Intent(intentName);
		
		if (clazz != null) {
			intentName = clazz.getSimpleName();
			intent = new Intent(context, clazz);
		}
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		if (repeatInterval == NO_REPEAT) {
			alarmManager.set(AlarmManager.RTC_WAKEUP, now, pendingIntent);
		} else {
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, now + repeatInterval, repeatInterval, pendingIntent);
		}
		
		Log.i(TAG, (repeatInterval == NO_REPEAT ? "Setting" : "Scheduling every " + repeatInterval) + " an alarm named: " + intentName);
		
		alarmIntents.add(intentName);
		
		return intentName;
	}
	
	/**
	 * Cancel all alarm
	 * 
	 * @throws NullPointerException
	 *             If attached context is null
	 */
	public void cancel() {
		checkContext();
		
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		for (Iterator<String> iterator = alarmIntents.iterator(); iterator.hasNext();) {
			alarmManager.cancel(PendingIntent.getBroadcast(context, 0, new Intent(iterator.next()), PendingIntent.FLAG_UPDATE_CURRENT));
			
			iterator.remove();
		}
	}
	
	/**
	 * Cancel alarm by its name
	 * 
	 * @param name
	 *            Alarm clazz
	 * 
	 * @throws NullPointerException
	 *             If attached context is null
	 */
	public void cancel(Class<?> clazz) {
		checkContext();
		
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		alarmManager.cancel(PendingIntent.getBroadcast(context, 0, new Intent(context, clazz), PendingIntent.FLAG_UPDATE_CURRENT));
	}
	
	/**
	 * Check and throw an {@link NullPointerException} is the context is null<br>
	 * Attach one with {@link #context(Context)}
	 * 
	 * @throws NullPointerException
	 *             If no {@link #context(Context)} has been called
	 */
	private void checkContext() {
		if (context == null) {
			throw new NullPointerException();
		}
	}
	
}