package zlm.hook.event;


import zlm.hook.HookType;

import java.util.Map;

/**
 * @author ziduye
 */
public class OnStreamNotFoundEvent extends HookEvent {

	public OnStreamNotFoundEvent(Object source, Map data) {
		super(HookType.ON_STREAM_NOT_FOUND,source);
		this.data = data;
	}

	private Map data;

}
