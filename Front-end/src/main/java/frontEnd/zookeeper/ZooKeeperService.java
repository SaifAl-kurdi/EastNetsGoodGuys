package frontEnd.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ZooKeeperService {

    private CuratorFramework client;

    @ConfigProperty(name = "zookeeper.server", defaultValue = "127.0.0.1:2181")
    String zookeeperServer;

    @PostConstruct
    public void init() {
        String zkServer = System.getProperty("zookeeper.server");
        if (zkServer == null || zkServer.isEmpty()) {
            zkServer = "127.0.0.1:2181";
        }

        client = CuratorFrameworkFactory.newClient(zkServer, new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

    public String getServiceAddress(String serviceName) {
        String path = "/services/" + serviceName;
        try {
            byte[] data = client.getData().forPath(path);
            if (data != null) {
                return new String(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PreDestroy
    public void close() {
        if (client != null) {
            client.close();
        }
    }
}
