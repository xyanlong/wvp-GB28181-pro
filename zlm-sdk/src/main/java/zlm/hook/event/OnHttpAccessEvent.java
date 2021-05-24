package zlm.hook.event;


import zlm.hook.HookType;

import java.util.Map;

/**
 * @author ziduye
 */
public class OnHttpAccessEvent extends HookEvent {

	public OnHttpAccessEvent(Object source, Map data) {
		super(HookType.ON_HTTP_ACCESS,source);
		this.data = data;
	}

	private Map data;

}
