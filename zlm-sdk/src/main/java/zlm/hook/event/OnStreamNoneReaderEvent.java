package zlm.hook.event;


import zlm.hook.HookType;

import java.util.Map;

/**
 * @author ziduye
 */
public class OnStreamNoneReaderEvent extends HookEvent {

	public OnStreamNoneReaderEvent(Object source, Map data) {
		super(HookType.ON_STREAM_NONE_READER,source);
		this.data = data;
	}

	private Map data;

}
