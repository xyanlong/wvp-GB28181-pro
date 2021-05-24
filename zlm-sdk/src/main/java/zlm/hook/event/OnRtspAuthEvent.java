package zlm.hook.event;


import zlm.hook.HookType;

import java.util.Map;

/**
 * @author ziduye
 */
public class OnRtspAuthEvent extends HookEvent {

	public OnRtspAuthEvent(Object source, Map data) {
		super(HookType.ON_RTSP_AUTH,source);
		this.data = data;
	}

	private Map data;

}
