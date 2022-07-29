package projekti.logic.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projekti.domain.Upload;
import projekti.logic.repository.UploadsRepository;

@Service
public class UploadsService {

    @Autowired
    private UploadsRepository uploadsRepository;

    // Retrieves parameter fileuploadId upload from repository
    public void deleteFileUpload(String useralias, Long fileuploadId) throws IOException {
        this.uploadsRepository.deleteById(fileuploadId);
    }

    // Saves parameter uploadedFile to repository if it's not empty or wrong format and unless there are already 10 files uploaded
    public void newFileUpload(String useralias, MultipartFile uploadedFile) throws IOException {
        if (this.uploadsRepository.findByUseralias(useralias).size() >= 10) {
            return;
        }
        if (uploadedFile.isEmpty()) {
            return;
        }
        if (!uploadedFile.getContentType().contains("application/")
                && !uploadedFile.getContentType().contains("text/")
                && !uploadedFile.getContentType().contains("image/")) {
            return;
        }
        Upload upload = new Upload();
        upload.setUseralias(useralias);
        upload.setName(uploadedFile.getOriginalFilename());
        upload.setMediaType(uploadedFile.getContentType());
        upload.setUploadSize(uploadedFile.getSize());
        upload.setUpload(uploadedFile.getBytes());
        this.uploadsRepository.save(upload);
    }

    // Retrieves parameter fileuploadId upload from repository
    public ResponseEntity<byte[]> retrieveFileUpload(Long fileuploadId) {
        Upload upload = this.uploadsRepository.getOne(fileuploadId);
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + upload.getName());
        headers.setContentType(MediaType.parseMediaType(upload.getMediaType()));
        headers.setContentLength(upload.getUploadSize());
        return new ResponseEntity<>(upload.getUpload(), headers, HttpStatus.CREATED);
    }
}
