package zlm.hook.event;


import zlm.hook.HookType;

import java.util.Map;

/**
 * @author ziduye
 */
public class OnShellLoginEvent extends HookEvent {

	public OnShellLoginEvent(Object source, Map data) {
		super(HookType.ON_SHELL_LOGIN,source);
		this.data = data;
	}

	private Map data;

}
