package id.veintechnology.apps.library.id.veintechnology.apps.service.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class StorageProperties {

    @Value("${storage.base-path}")
    private String storageBasePath;

    @Value("${storage.base-url}")
    private String storageBaseUrl;

    @Value("${storage.static-url}")
    private String storageStaticUrl;

    public String getStorageBasePath() {
        return storageBasePath;
    }

    public String getStorageBaseUrl() {
        return storageBaseUrl;
    }

    public String getStorageStaticUrl() {
        return storageStaticUrl;
    }
}
