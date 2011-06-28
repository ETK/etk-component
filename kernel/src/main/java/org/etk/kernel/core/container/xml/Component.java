package org.etk.kernel.core.container.xml;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.etk.kernel.core.container.configuration.ConfigurationManagerImpl;
import org.jibx.runtime.IMarshallingContext;

public class Component {

	final URL documentURL;

	String key;

	String type;

	String jmxName;

	String description;

	ArrayList plugins;

	private ArrayList<ComponentPlugin> componentPlugins;

	ArrayList listeners;

	InitParams initParams;

	boolean showDeployInfo = false;

	boolean multiInstance = false;

	public Component() {
		documentURL = ConfigurationManagerImpl.getCurrentURL();
	}

	public URL getDocumentURL() {
		return documentURL;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String s) {
		this.key = s;
	}

	public String getJMXName() {
		return jmxName;
	}

	public void setJMXName(String s) {
		jmxName = s;
	}

	public String getType() {
		return type;
	}

	public void setType(String s) {
		type = s;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String s) {
		description = s;
	}

	public List getPlugins() {
		return plugins;
	}

	public void setPlugins(ArrayList list) {
		plugins = list;
	}

	public List<ComponentPlugin> getComponentPlugins() {
		return componentPlugins;
	}

	public void setComponentPlugins(ArrayList<ComponentPlugin> list) {
		if (list != null) {
			// Sort the list of component plugins first
			Collections.sort(list);
		}
		componentPlugins = list;
	}

	public List getListeners() {
		return listeners;
	}

	public void setListeners(ArrayList list) {
		listeners = list;
	}

	public InitParams getInitParams() {
		return initParams;
	}

	public void setInitParams(InitParams ips) {
		initParams = ips;
	}

	public boolean getShowDeployInfo() {
		return showDeployInfo;
	}

	public void setShowDeployInfo(boolean b) {
		showDeployInfo = b;
	}

	public boolean isMultiInstance() {
		return multiInstance;
	}

	public void setMultiInstance(boolean b) {
		multiInstance = b;
	}

	public void preGet(IMarshallingContext ictx) {
		ConfigurationMarshallerUtil.addURLToContent(documentURL, ictx);
	}
}
