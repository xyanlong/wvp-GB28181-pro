package zlm.hook.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import zlm.hook.event.OnPlayEvent;

/**
 * @author ziduye
 */
@Slf4j
@Component
public class OnPlayEventListener implements ApplicationListener<OnPlayEvent> {

	@Override
	public void onApplicationEvent(OnPlayEvent event) {

	}
}
