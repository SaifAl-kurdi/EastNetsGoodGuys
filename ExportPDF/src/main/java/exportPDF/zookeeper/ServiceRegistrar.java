package exportPDF.zookeeper;

import io.quarkus.runtime.StartupEvent;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class ServiceRegistrar {

    @Inject
    ZookeeperClientService zkClientService;

    @ConfigProperty(name = "quarkus.http.port")
    int httpPort;

    @ConfigProperty(name = "service.host", defaultValue = "localhost")
    String serviceHost;

    public void onStart(@Observes StartupEvent ev) {
        registerService();
    }

    private void registerService() {
        try {
            CuratorFramework client = zkClientService.getClient();
            String servicePath = "/services/export-service";
            String serviceUrl = "http://" + serviceHost + ":" + httpPort;
            client.createContainers(servicePath);

            if (client.checkExists().forPath(servicePath) == null) {
                client.create().withMode(CreateMode.EPHEMERAL).forPath(servicePath, serviceUrl.getBytes());
            } else {
                client.setData().forPath(servicePath, serviceUrl.getBytes());
            }

            System.out.println("Export PDF Service  Registered at " + servicePath + " with URL " + serviceUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
