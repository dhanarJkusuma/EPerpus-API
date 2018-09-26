package id.veintechnology.apps.library.id.veintechnology.apps.service.storage;

import id.veintechnology.apps.library.id.veintechnology.apps.service.storage.exception.StorageErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Objects;
import java.util.UUID;

@Service
public class DbStorageService implements StorageService{

    private final Path rootLocation = Paths.get("src/main/resources/static/");
    private final StorageProperties storageProperties;
    private final Path customLocation;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public DbStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        this.customLocation = Paths.get(this.storageProperties.getStorageBasePath());
    }

    @Override
    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                logger.error("[Library-API] ", "Resource is not readable. ");
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            logger.error("[Library-API] ", e.getCause());
            e.printStackTrace();
            throw new RuntimeException("FAIL!");
        }
    }

    @Override
    public Path getRootPath() {
        return customLocation;
    }

    @Override
    public Path store(MultipartFile file, String filename) throws StorageErrorException {
        String originFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        filename = String.join(".", filename, getOriginalExtenstion(originFileName));

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new RuntimeException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, customLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
        return load(filename);
    }

    @Override
    public Path store(MultipartFile file, String path, String filename, boolean hashDirectory) throws StorageErrorException {
        Path destinationPath;
        try {
            destinationPath = initFolderUpload(path);
            if(hashDirectory){
                String uniqueID = UUID.randomUUID().toString();
                uniqueID = uniqueID.replace("-","");
                path = path + "/" + uniqueID;
                destinationPath = initFolderUpload(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageErrorException(e.getMessage());
        }
        String originFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        filename = String.join(".", filename, getOriginalExtenstion(originFileName));

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new RuntimeException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationPath.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
        return destinationPath.resolve(filename);
    }

    @Override
    public Path load(String filename) {
        return customLocation.resolve(filename);
    }

    @Override
    public Resource loadCustomResource(String filename) {
        try {
            Path file = customLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    @Override
    public Path initFolderUpload(String path) throws IOException {
        String pathRoot = customLocation.toString();
        Path resultPath = Paths.get(String.join("/", pathRoot, path));
        if(!pathIsExist(resultPath)){
            // create directory
            try{
                Files.createDirectories(resultPath);
            }catch (AccessDeniedException e){
                logger.error("Directory Product Not Found, Access Denied to create a Directory.");
                throw new StorageErrorException("[Agriloka-Error] : Directory Product Not Found, Access Denied to create a Directory.");
            }

        }
        return resultPath;
    }

    @Override
    public String composeImageUrl(String imageUrl) {
        String cleanImageUrl = imageUrl.replace("\\", "/");
        return storageProperties.getStorageBaseUrl() + cleanImageUrl;
    }

    @Override
    public String composeStaticUrl(String imageUrl) {
        String cleanImageUrl = imageUrl.replace("\\", "/");
        return storageProperties.getStorageStaticUrl() + cleanImageUrl;
    }

    /**
     * Return Extension File Type by Filename.
     * @param filename string
     * @return string
     */
    private String getOriginalExtenstion(String filename){
        int dotExtenstion = filename.indexOf(".", -0);
        if(dotExtenstion == -1){
            throw new RuntimeException("Invalid filename !!!");
        }
        return filename.substring(dotExtenstion + 1, filename.length());
    }

    /**
     * Return boolean to checking if destination directory is already exist or not.
     * @param resultPath string
     * @return boolean
     */
    private Boolean pathIsExist(Path resultPath){
        if(Files.isDirectory(resultPath, LinkOption.NOFOLLOW_LINKS)){
            return true;
        }
        return false;
    }

    /**
     * Return destination path, by string path. If destination path doesn't exist, It'll be created.
     * @param path string
     * @return path
     */
}
