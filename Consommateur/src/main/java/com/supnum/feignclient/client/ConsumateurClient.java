package mr.supnum.feignclient.client;

import mr.supnum.feignclient.dto.GetServerStatusResponse;
import mr.supnum.feignclient.dto.Server;
import mr.supnum.feignclient.dto.StartServerResponse;
import mr.supnum.feignclient.dto.StopServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@FeignClient(name = "consumateur", url = "${consumateur.service.url}")
public interface ConsumateurClient {

    @GetMapping("/api/servers")
    List<Server> getAllServers();

    @GetMapping("/api/servers/{id}/status")
    GetServerStatusResponse getServerStatus(@PathVariable("id") Long id);

    @PostMapping("/api/servers/{id}/start")
    StartServerResponse startServer(@PathVariable("id") Long id);

    @PostMapping("/api/servers/{id}/stop")
    StopServerResponse stopServer(@PathVariable("id") Long id);
}

