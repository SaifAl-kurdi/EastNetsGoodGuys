package zookeeper;

import lombok.Getter;
import lombok.Setter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Getter @Setter
public class ZookeeperClientService {

    private CuratorFramework curatorFramework;

    @PostConstruct
    public void init() {
        String zookeeperServer = System.getProperty("zookeeper.server", "127.0.0.1:2181");
        curatorFramework = CuratorFrameworkFactory.newClient(zookeeperServer, new ExponentialBackoffRetry(1000, 3));
        curatorFramework.start();
        System.out.println("Notification Service Connected to ZooKeeper: " + zookeeperServer);
    }

    @PreDestroy
    public void close() {
        if (curatorFramework != null) {
            curatorFramework.close();
        }
    }
}
