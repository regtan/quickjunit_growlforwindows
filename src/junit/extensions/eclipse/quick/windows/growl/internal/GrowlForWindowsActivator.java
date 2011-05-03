package junit.extensions.eclipse.quick.windows.growl.internal;

import org.eclipse.ui.plugin.AbstractUIPlugin;

public class GrowlForWindowsActivator extends AbstractUIPlugin {
	
	private static GrowlForWindowsActivator plugin;
	
	public GrowlForWindowsActivator(){
		GrowlForWindowsActivator.plugin = this;
	}
	
	public static GrowlForWindowsActivator getDefault(){
		return GrowlForWindowsActivator.plugin;
	}

}
