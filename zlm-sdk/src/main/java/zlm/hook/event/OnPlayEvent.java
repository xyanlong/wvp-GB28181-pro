package zlm.hook.event;


import zlm.hook.HookType;

import java.util.Map;

/**
 * @author ziduye
 */
public class OnPlayEvent extends HookEvent {

	public OnPlayEvent(Object source, Map data) {
		super(HookType.ON_PLAY,source);
		this.data = data;
	}

	private Map data;

}
