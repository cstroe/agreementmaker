package am.app.osgi;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import am.app.mappingEngine.AbstractMatcher;

public class OSGiRegistry {
	
	private List<AbstractMatcher> matcherList;
	private ServiceTracker<AbstractMatcher, AbstractMatcher> matcherTracker;
	private BundleContext context;

	public OSGiRegistry(BundleContext bundleContext){
		//save the context
		context=bundleContext;
		//create the arraylist for the matchers
		matcherList=new ArrayList<AbstractMatcher>();
		//start the service tracker
		startMatcherTracker();
	}

	private void startMatcherTracker(){
		ServiceTrackerCustomizer<AbstractMatcher, AbstractMatcher> customizer = new ServiceTrackerCustomizer<AbstractMatcher,AbstractMatcher>() {
			
			@Override
			public AbstractMatcher addingService(ServiceReference<AbstractMatcher> reference) {
				AbstractMatcher matcher=context.getService(reference);
				matcherList.add(matcher);
				return matcher;
			}
			@Override
			public void modifiedService(ServiceReference<AbstractMatcher> reference,AbstractMatcher service) {
				matcherList.remove(service);
				matcherList.add(context.getService(reference));
			}
			@Override
			public void removedService(ServiceReference<AbstractMatcher> reference,AbstractMatcher service) {
				matcherList.remove(service);
			}
		};
		matcherTracker = new ServiceTracker<AbstractMatcher,AbstractMatcher>(context, AbstractMatcher.class, customizer);
		matcherTracker.open();
	}
	
	public List<String> getMatcherNames(){
		List<String> matcherNames=new ArrayList<String>();
		for(AbstractMatcher m: matcherList)
			matcherNames.add(m.getName());
		return matcherNames;
	}
	
	public AbstractMatcher getMatcherByName(String matcherName)throws MatcherNotFoundException{
		AbstractMatcher matcher=null;
		for(AbstractMatcher m : matcherList){
			if(m.getName().equals(matcherName))
				return matcher;
		}
		throw new MatcherNotFoundException(matcherName+" is not in the system.");
	}
	

}