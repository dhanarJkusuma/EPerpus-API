package id.veintechnology.apps.library.id.veintechnology.apps.service.storage;

import org.springframework.core.io.Resource;

public interface StorageService {
    Resource loadFile(String filename);
}
