package zlm.hook.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import zlm.hook.HookType;

/**
 * @author ziduye
 */
@Getter
@Setter
public class HookEvent extends ApplicationEvent {

	public HookEvent(HookType hookType,Object source) {
		super(source);
		this.hookType = hookType;
	}

	private HookType hookType;

}
