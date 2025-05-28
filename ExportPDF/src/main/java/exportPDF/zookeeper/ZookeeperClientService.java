package exportPDF.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ZookeeperClientService {

    private CuratorFramework client;

    @PostConstruct
    public void init() {
        String zookeeperServer = System.getProperty("zookeeper.server", "127.0.0.1:2181");
        client = CuratorFrameworkFactory.newClient(zookeeperServer, new ExponentialBackoffRetry(1000, 3));
        client.start();
        System.out.println("Search Service Connected to ZooKeeper: " + zookeeperServer);
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
        return null; // or throw an exception if not found
    }

    public CuratorFramework getClient() {
        return client;
    }

    @PreDestroy
    public void close() {
        if (client != null) {
            client.close();
        }
    }
}
