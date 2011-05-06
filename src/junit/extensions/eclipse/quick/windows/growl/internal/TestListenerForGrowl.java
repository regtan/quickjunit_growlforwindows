package junit.extensions.eclipse.quick.windows.growl.internal;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import junit.extensions.eclipse.quick.windows.growl.internal.preferences.Preference;

import org.eclipse.jdt.junit.ITestRunListener;
import org.eclipse.jdt.junit.JUnitCore;
import org.eclipse.jdt.junit.model.ITestElement.Result;
import org.eclipse.jdt.junit.model.ITestRunSession;
import org.kirino.growlforwindows.Application;
import org.kirino.growlforwindows.GrowlControler;
import org.kirino.growlforwindows.Notification;


@SuppressWarnings("deprecation")
public class TestListenerForGrowl implements ITestRunListener {

	private static final String QUICK_J_UNIT = "Quick JUnit ";
	private static final String TEST_OK = QUICK_J_UNIT + "Test OK";
	private static final String TEST_FAILURE = QUICK_J_UNIT + "Test FAILURE";
	private static final String TEST_ERROR = QUICK_J_UNIT + "Test ERROR";
	public TestListenerForGrowl() {
		final GrowlControler gc = new GrowlControler();
		final Application quickJunit = new Application(QUICK_J_UNIT);

		final Notification notificationTestOk = new Notification("TEST_OK",TEST_OK,true);
		final Notification notificationTestFailure = new Notification("TEST_FAILURE",TEST_FAILURE,true);
		final Notification notificationTestError = new Notification("TEST_ERROR",TEST_ERROR,true);

		List<Notification> notificationList = new ArrayList<Notification>();
		notificationList.add(notificationTestOk);
		notificationList.add(notificationTestFailure);
		notificationList.add(notificationTestError);

		try {
			gc.registerNotify(quickJunit, notificationList);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JUnitCore.addTestRunListener(new org.eclipse.jdt.junit.TestRunListener() {
			private final TemplateParser parser = new TemplateParser();
			@Override
			public void sessionFinished(ITestRunSession session) {
				String template = Preference.TEMPLATE.getValue();
				parser.setTemplate(template);
				Result testResult = session.getTestResult(true);
				String parseTemplate = parser.parseTemplate(session);
				try {

					if(Result.ERROR.equals(testResult)){
						gc.sendNotify(QUICK_J_UNIT, "TEST_ERROR", testResult.toString(), parseTemplate);
					}else if(Result.FAILURE.equals(testResult)){
						gc.sendNotify(QUICK_J_UNIT, "TEST_FAILURE", testResult.toString(), parseTemplate);
					}else{
						gc.sendNotify(QUICK_J_UNIT, "TEST_OK", testResult.toString(), parseTemplate);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	public void testEnded(String testId, String testName) {
	}

	public void testFailed(int status, String testId, String testName,
			String trace) {
	}

	public void testReran(String testId, String testClass, String testName,
			int status, String trace) {
	}

	public void testRunEnded(long elapsedTime) {
	}

	public void testRunStarted(int testCount) {
	}

	public void testRunStopped(long elapsedTime) {
	}

	public void testRunTerminated() {
	}

	public void testStarted(String testId, String testName) {
	}

}
