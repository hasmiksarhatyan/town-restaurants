package am.itspace.townrestaurantscommon.utilCommon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileUtil {

    @Value("${project.images.folder}")
    private String folderPath;

    public List<String> uploadImages(MultipartFile[] files) throws IOException {
        List<String> pictures = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty() && file.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                File newFile = new File(folderPath + File.separator + fileName);
                file.transferTo(newFile);
                pictures.add(fileName);
            }
        }
        return pictures;
    }

    public byte[] getImage(String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(folderPath + File.separator + fileName));
    }
}
