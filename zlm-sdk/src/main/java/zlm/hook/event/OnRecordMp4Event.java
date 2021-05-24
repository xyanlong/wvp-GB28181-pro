package zlm.hook.event;


import zlm.hook.HookType;

import java.util.Map;

/**
 * @author ziduye
 */
public class OnRecordMp4Event extends HookEvent {

	public OnRecordMp4Event(Object source, Map data) {
		super(HookType.ON_RECORD_MP4,source);
		this.data = data;
	}

	private Map data;

}
