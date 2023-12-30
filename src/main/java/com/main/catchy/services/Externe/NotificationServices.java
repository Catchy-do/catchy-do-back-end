package com.main.catchy.services.Externe;
import com.main.catchy.utils.NotificationBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "feignNotification", url = "${url.notification}")
public interface NotificationServices {

    @PostMapping(path = "notify")
    Map<String, Object> sendNotification(@RequestBody NotificationBody data) ;

}
