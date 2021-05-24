package zlm.hook.event;


import zlm.hook.HookType;

import java.util.Map;

/**
 * @author ziduye
 */
public class OnServerStartedEvent extends HookEvent {

	public OnServerStartedEvent(Object source, Map data) {
		super(HookType.ON_SERVER_STARTED,source);
		this.data = data;
	}

	private Map data;

}
