package org.iMage.plugins;

/**
 * Abstract parent class for plug-ins for JMJRST
 * 
 */
public abstract class JmjrstPlugin implements Comparable<JmjrstPlugin> {

	/**
	 * The plug-in's priority
	 */
	protected PluginPriority priority;

	/**
	 * Sets the plug-in's priority
	 * 
	 */
	public abstract void setPriority(PluginPriority priority);

	/**
	 * Returns the plug-in's priority
	 * 
	 * @return the plug-in's priority
	 */
	public abstract PluginPriority getPriority();

	/**
	 * Returns the text displayed in the menu label for this plug-in
	 * 
	 * @return The text for the menu labels for the plug-in
	 */
	public abstract String getMenuText();

	/**
	 * Returns the name of this plug-in
	 * 
	 * @return the name of the plug-in
	 */
	public abstract String getName();

	/**
	 * JMJRST pushes the main application to every subclass - so plug-ins are
	 * allowed to look at Main as well.
	 * 
	 * @param main
	 *            JMJRST main application
	 */
	public abstract void init(org.jis.Main main);

	/**
	 * Runs plug-in
	 */
	public abstract void run();

	/**
	 * Returns whether the plug-in can be configured or not
	 * 
	 * @return true if the plug-in can be configured.
	 */
	public abstract boolean isConfigurable();

	/**
	 * Open a configuration dialogue.
	 */
	public abstract void configure();

	/**
	 * Implement comparator functionality - plug-ins are compared on basis of
	 * their priority.
	 * 
	 * Compares this plug-in with the specified plug-in for order. Returns a -1
	 * , zero, or 1 if this plug-in's priority is less than, equal to, or
	 * greater than the specified plug-in's priority.
	 * 
	 * If any of the priorities is <code>null</code> its plug-in's priority is
	 * less than the other except for the case that both are <code>null</code>.
	 * In this case they are equal.
	 * 
	 * @param otherPlugin
	 *            the plug-in that we should compare us to
	 * 
	 */
	
	@Override
	public int compareTo(JmjrstPlugin otherPlugin) {
		if (this.getPriority() == null) {
			if (otherPlugin.getPriority() == null) {
			return 0;
			} else {
			return -1;
			}
			} else {
			if (otherPlugin.getPriority() == null) {
			return 1;
			} else {
			return this.getPriority().
			compareTo(otherPlugin.getPriority());
					}
				}
	}
}
