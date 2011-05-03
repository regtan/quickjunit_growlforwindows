package junit.extensions.eclipse.quick.windows.growl.internal;


import junit.extensions.eclipse.quick.windows.growl.internal.preferences.Preference;
import net.sf.libgrowl.Application;
import net.sf.libgrowl.GrowlConnector;
import net.sf.libgrowl.Notification;
import net.sf.libgrowl.NotificationType;

import org.eclipse.jdt.junit.ITestRunListener;
import org.eclipse.jdt.junit.JUnitCore;
import org.eclipse.jdt.junit.model.ITestElement.Result;
import org.eclipse.jdt.junit.model.ITestRunSession;


@SuppressWarnings("deprecation")
public class TestListenerForGrowl implements ITestRunListener {

	private static final String QUICK_J_UNIT = "Quick JUnit ";
	private static final String TEST_OK = QUICK_J_UNIT + "Test OK";
	private static final String TEST_FAILURE = QUICK_J_UNIT + "Test FAILURE";
	private static final String TEST_ERROR = QUICK_J_UNIT + "Test ERROR";
	private static final String PATH_IMG_OK = "c:/quick_junit_icons/tsuiteok.gif";
	private static final String PATH_IMG_FAILURE = "c:/quick_junit_icons/tsuitefail.gif";
	private static final String PATH_IMG_ERROR = "c:/quick_junit_icons/tsuiteerror.gif";

	public TestListenerForGrowl() {
		final GrowlConnector growlConnector = new GrowlConnector();
		final Application quickJunit = new Application(QUICK_J_UNIT);

		final NotificationType notificationTestOk = new NotificationType("TEST_OK",TEST_OK,PATH_IMG_OK);
		final NotificationType notificationTestFailure = new NotificationType("TEST_FAILURE",TEST_FAILURE,PATH_IMG_FAILURE);
		final NotificationType notificationTestError = new NotificationType("TEST_ERROR",TEST_ERROR,PATH_IMG_ERROR);
		final NotificationType[] notificationTypes = new NotificationType[] {notificationTestOk,
				notificationTestFailure,
				notificationTestError};

		growlConnector.register(quickJunit, notificationTypes);

		JUnitCore.addTestRunListener(new org.eclipse.jdt.junit.TestRunListener() {
			private final TemplateParser parser = new TemplateParser();
			@Override
			public void sessionFinished(ITestRunSession session) {
				String template = Preference.TEMPLATE.getValue();
				parser.setTemplate(template);
				Result testResult = session.getTestResult(true);
				Notification notification;
				String parseTemplate = parser.parseTemplate(session);
				if(Result.ERROR.equals(testResult)){
					notification = new Notification(quickJunit, notificationTestError, testResult.toString(), parseTemplate);
				}else if(Result.FAILURE.equals(testResult)){
					notification = new Notification(quickJunit, notificationTestFailure, testResult.toString(), parseTemplate);
				}else{
					notification = new Notification(quickJunit, notificationTestOk, testResult.toString(), parseTemplate);
				}
				growlConnector.notify(notification);

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
