package zlm.hook.event;


import zlm.hook.HookType;

import java.util.Map;

/**
 * @author ziduye
 */
public class OnStreamChangedEvent extends HookEvent {

	public OnStreamChangedEvent(Object source, Map data) {
		super(HookType.ON_STREAM_CHANGED,source);
		this.data = data;
	}

	private Map data;

}
