package zlm.hook.event;


import zlm.hook.HookType;

import java.util.Map;

/**
 * @author ziduye
 */
public class OnRtspRealmEvent extends HookEvent {

	public OnRtspRealmEvent(Object source, Map data) {
		super(HookType.ON_RTSP_REALM,source);
		this.data = data;
	}

	private Map data;

}
