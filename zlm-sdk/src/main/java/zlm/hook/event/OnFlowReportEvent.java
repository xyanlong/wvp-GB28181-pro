package zlm.hook.event;


import zlm.hook.HookType;

import java.util.Map;

/**
 * @author ziduye
 */
public class OnFlowReportEvent extends HookEvent {

	public OnFlowReportEvent(Object source, Map data) {
		super(HookType.ON_FLOW_REPORT,source);
		this.data = data;
	}

	private Map data;

}
