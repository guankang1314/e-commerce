package com.imooc.ecommerce.notifier;

import org.springframework.stereotype.Component;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author qingtian
 * @description:自定义告警
 * @Package com.imooc.ecommerce.notifier
 * @date 2021/12/4 19:11
 */
@Slf4j
@Component
public class QingtianNotifier extends AbstractEventNotifier {


    protected QingtianNotifier(InstanceRepository repository) {
        super(repository);
    }

    /** 实现的时间的通知**/
    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {

        return Mono.fromRunnable(() -> {

            if (event instanceof InstanceStatusChangedEvent) {
                log.info("Instance Status Change: [{}],[{}],[{}]",
                        instance.getRegistration().getName(),event.getInstance(),
                        ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus());
            }else {
                log.info("Instance Info: [{}],[{}],[{}]",
                        instance.getRegistration().getName(),event.getInstance()
                ,event.getType());
            }
        });
    }
}
