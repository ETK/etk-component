package org.etk.kernel.core.container.management;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.management.MBeanServer;

import org.etk.kernel.container.CachingContainer;
import org.etk.kernel.core.container.monitor.jvm.J2EEServerInfo;
import org.picocontainer.ComponentAdapter;
import org.picocontainer.PicoContainer;
import org.picocontainer.PicoException;
import org.picocontainer.PicoRegistrationException;
import org.picocontainer.defaults.ComponentAdapterFactory;
import org.picocontainer.defaults.DuplicateComponentKeyRegistrationException;

public class ManageableContainer extends CachingContainer {

	private static MBeanServer findMBeanServer() {
		J2EEServerInfo serverenv_ = new J2EEServerInfo();
		return serverenv_.getMBeanServer();
	}

	/** . */
	private static final ThreadLocal<ManageableComponentAdapterFactory> hack = new ThreadLocal<ManageableComponentAdapterFactory>();

	/** . */
	final ManagementContextImpl managementContext;

	/** . */
	private MBeanServer server;

	/** . */
	private final Set<ManagementProvider> providers;

	/** . */
	private final ManageableContainer parent;

	public ManageableContainer() {
		this((PicoContainer) null);
	}

	public ManageableContainer(PicoContainer parent) {
		this(new MX4JComponentAdapterFactory(), parent);
	}

	public ManageableContainer(ComponentAdapterFactory componentAdapterFactory,
			PicoContainer parent) {
		super(getComponentAdapterFactory(componentAdapterFactory), parent);

		// Yeah this is not pretty but a necessary evil to make it work
		ManageableComponentAdapterFactory factory = hack.get();
		factory.container = this;
		hack.set(null);

		// The synchronized wrapper, here will not impact runtime performances
		// so it's fine
		this.providers = Collections
				.synchronizedSet(new HashSet<ManagementProvider>());

		//
		ManagementContextImpl parentCtx = null;
		if (parent instanceof ManageableContainer) {
			ManageableContainer manageableParent = (ManageableContainer) parent;
			parentCtx = manageableParent.managementContext;
		}

		//
		this.parent = parent instanceof ManageableContainer ? (ManageableContainer) parent
				: null;

		//
		if (parentCtx != null) {
			server = parentCtx.container.server;
			managementContext = new ManagementContextImpl(parentCtx, this);
		} else {
			server = findMBeanServer();
			managementContext = new ManagementContextImpl(this);
			addProvider(new JMXManagementProvider(server));
		}
	}

	public ManageableContainer(ComponentAdapterFactory componentAdapterFactory) {
		this(componentAdapterFactory, null);
	}

	@Managed
	@ManagedName("RegisteredComponentNames")
	@ManagedDescription("Return the list of the registered component names")
	public Set<String> getRegisteredComponentNames() throws PicoException {
		Set<String> names = new HashSet<String>();
		Collection<ComponentAdapter> adapters = getComponentAdapters();
		for (ComponentAdapter adapter : adapters) {
			Object key = adapter.getComponentKey();
			String name = String.valueOf(key);
			names.add(name);
		}
		return names;
	}

	private static ManageableComponentAdapterFactory getComponentAdapterFactory(
			ComponentAdapterFactory componentAdapterFactory) {
		ManageableComponentAdapterFactory factory = new ManageableComponentAdapterFactory(
				componentAdapterFactory);
		hack.set(factory);
		return factory;
	}

	public ManagementContext getManagementContext() {
		return managementContext;
	}

	public final MBeanServer getMBeanServer() {
		return server;
	}

	@Override
	public ComponentAdapter registerComponent(ComponentAdapter componentAdapter)
			throws DuplicateComponentKeyRegistrationException {
		return super.registerComponent(componentAdapter);
	}

	public ComponentAdapter registerComponentInstance(Object componentKey,
			Object componentInstance) throws PicoRegistrationException {
		ComponentAdapter adapter = super.registerComponentInstance(
				componentKey, componentInstance);
		if (managementContext != null) {
			managementContext.register(componentInstance);

			// Register if it is a management provider
			if (componentInstance instanceof ManagementProvider) {
				ManagementProvider provider = (ManagementProvider) componentInstance;
				addProvider(provider);
			}
		}
		return adapter;
	}

	/**
	 * Returns the list of the providers which are relevant for this container.
	 * 
	 * @return the providers
	 */
	Collection<ManagementProvider> getProviders() {
		HashSet<ManagementProvider> allProviders = new HashSet<ManagementProvider>();
		computeAllProviders(allProviders);
		return allProviders;
	}

	private void computeAllProviders(Set<ManagementProvider> allProviders) {
		if (parent != null) {
			parent.computeAllProviders(allProviders);
		}

		//
		allProviders.addAll(providers);
	}

	boolean addProvider(ManagementProvider provider) {
		// Prevent double registration just in case...
		if (providers.contains(provider)) {
			return false;
		}

		//
		providers.add(provider);

		// Perform registration of already registered managed components
		managementContext.install(provider);

		//
		return true;
	}
}
