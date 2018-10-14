package caceresenzo.android.libs.exception;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

@SuppressWarnings("deprecation")
public class UploadTraceToServerExceptionHandler implements UncaughtExceptionHandler {
	
	private UncaughtExceptionHandler defaultUncaughtExceptionHandler;
	
	private String localPath;
	private String url;
	
	private TraceUploadListener traceUploadListener = null;
	
	/*
	 * if any of the parameters is null, the respective functionality will not be used
	 */
	public UploadTraceToServerExceptionHandler(String localPath, String url) {
		this.localPath = localPath;
		this.url = url;
		this.defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
	}
	
	public UploadTraceToServerExceptionHandler(String localPath, String url, TraceUploadListener traceUploadListener) {
		this(localPath, url);
		this.traceUploadListener = traceUploadListener;
	}
	
	public void uncaughtException(Thread thread, Throwable throwable) {
		String timestamp = "" + System.currentTimeMillis();
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		throwable.printStackTrace(printWriter);
		String stacktrace = result.toString();
		printWriter.close();
		String filename = timestamp + ".stacktrace";
		
		if (traceUploadListener != null) {
			String newFilename = traceUploadListener.updateFileName(timestamp);
			// filename = newFilename == null ? filename : newFilename;
			filename = newFilename;
			traceUploadListener.onTraceUpload(throwable);
		} else {
			Log.i(UploadTraceToServerExceptionHandler.class.getSimpleName(), "Crash uncaught: no traceUploadListener has been set!");
		}
		
		if (localPath != null) {
			writeToFile(stacktrace, filename);
		}
		if (url != null) {
			sendToServer(stacktrace, filename);
		}
		
		defaultUncaughtExceptionHandler.uncaughtException(thread, throwable);
		
		System.exit(0);
	}
	
	private void writeToFile(String stacktrace, String filename) {
		try {
			File file = new File(localPath + "/" + filename);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
			bufferedWriter.write(stacktrace);
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	private void sendToServer(String stacktrace, String filename) {
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("filename", filename));
		nameValuePairs.add(new BasicNameValuePair("stacktrace", stacktrace));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			defaultHttpClient.execute(httpPost);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public static void setAsDefault(String localPath, String url) {
		setAsDefault(localPath, url, null);
	}
	
	public static void setAsDefault(String localPath, String url, TraceUploadListener traceUploadListener) {
		if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof UploadTraceToServerExceptionHandler)) {
			Thread.setDefaultUncaughtExceptionHandler(new UploadTraceToServerExceptionHandler(localPath, url, traceUploadListener));
		}
	}
	
	public interface TraceUploadListener {
		void onTraceUpload(Throwable throwable);
		
		String updateFileName(String timestamp);
	}
	
}