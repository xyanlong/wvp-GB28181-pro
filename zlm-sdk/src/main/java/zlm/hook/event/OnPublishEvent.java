package zlm.hook.event;


import zlm.hook.HookType;

import java.util.Map;

/**
 * @author ziduye
 */
public class OnPublishEvent extends HookEvent {

	public OnPublishEvent(Object source, Map data) {
		super(HookType.ON_PUBLISH,source);
		this.data = data;
	}

	private Map data;

}
