package id.veintechnology.apps.library.id.veintechnology.apps.api.open;

import id.veintechnology.apps.library.id.veintechnology.apps.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping("/storage/image")
public class PublicApi{

    private final StorageService storageService;

    @Autowired
    public PublicApi(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(path = "/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping(path = "/custom/{hash}/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> getCustomFile(@PathVariable("hash") String hashFolder, @PathVariable("filename") String filename) {
        String filePath = String.join("/", hashFolder, filename);
        Resource file = storageService.loadCustomResource(filePath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }


}