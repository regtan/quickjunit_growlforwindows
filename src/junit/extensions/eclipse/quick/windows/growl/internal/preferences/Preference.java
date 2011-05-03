package junit.extensions.eclipse.quick.windows.growl.internal.preferences;

import junit.extensions.eclipse.quick.windows.growl.internal.GrowlForWindowsActivator;

import org.eclipse.jface.preference.IPreferenceStore;


/**
 * Constants for plug-in preferences
 */
public enum Preference {

	TEMPLATE;
	
	public String getValue() {
		IPreferenceStore store = GrowlForWindowsActivator.getDefault().getPreferenceStore();
		return store.getString(name());
	}
	
	public void setValue(String value){
		IPreferenceStore store = GrowlForWindowsActivator.getDefault().getPreferenceStore();
		store.setValue(name(), value);
	}
	
}
