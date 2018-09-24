package id.veintechnology.apps.library.id.veintechnology.apps.service.storage;

import id.veintechnology.apps.library.id.veintechnology.apps.service.storage.exception.StorageErrorException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {
    Resource loadFile(String filename);

    Path getRootPath();
    Path store(MultipartFile file, String filename) throws StorageErrorException;
    Path store(MultipartFile file, String path, String filename, boolean hashDirectory) throws StorageErrorException;
    Path load(String filename);
    Resource loadCustomResource(String filename);
    Path initFolderUpload(String path) throws IOException;

    String composeImageUrl(String imageUrl);
    String composeStaticUrl(String imageUrl);
}
