package junit.extensions.eclipse.quick.windows.growl.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

import org.eclipse.jface.preference.IPreferenceStore;

import junit.extensions.eclipse.quick.windows.growl.internal.GrowlForWindowsActivator;

import static junit.extensions.eclipse.quick.windows.growl.internal.preferences.Preference.*;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = GrowlForWindowsActivator.getDefault().getPreferenceStore();
		store.setDefault(TEMPLATE.name(), "${name} passed:${ok_counts} failure:${fail_counts} Total:${total_counts}"); //$NON-NLS-1$
	}

}
