package projekti.logic.control;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import projekti.logic.repository.UploadsRepository;
import projekti.logic.service.HomeService;
import projekti.logic.service.UploadsService;

@Controller
public class UploadsController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private UploadsRepository uploadsRepository;

    @Autowired
    private UploadsService uploadsService;

    // LOGGED IN
    // GET-REQUESTS
    @Secured("USER")
    @RequestMapping(value = "/EmployeeSearch/Users/{useralias}/FileUpload/{fileuploadId}/RetrieveFileUpload", method = RequestMethod.GET)
    public ResponseEntity<byte[]> retrieveFileUpload(Model model, @PathVariable String useralias,
            @PathVariable Long fileuploadId) {
        return this.uploadsService.retrieveFileUpload(fileuploadId);
    }

    @Secured("USER")
    @GetMapping("/EmployeeSearch/Users/{useralias}/Uploads")
    public String userUploads(Model model, @PathVariable String useralias) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            model.addAttribute("fileuploads", this.uploadsRepository.findByUseralias(useralias));
            return "uploads";
        }
    }

    // LOGGED IN
    // POST-REQUESTS
    @Secured("USER")
    @RequestMapping(value = "/EmployeeSearch/Users/{useralias}/FileUpload", method = RequestMethod.POST)
    public String fileUploadNew(Model model, @RequestParam("fileUploaded") MultipartFile uploadedFile,
            @PathVariable String useralias) throws IOException {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            this.uploadsService.newFileUpload(useralias, uploadedFile);
            return "redirect:/EmployeeSearch/Users/" + useralias + "/Uploads";
        }
    }

    // LOGGED IN
    // DELETE-REQUESTS
    @Secured("USER")
    @RequestMapping(value = "/EmployeeSearch/Users/{useralias}/FileUpload/{fileuploadId}/DeleteFileUpload", method = RequestMethod.DELETE)
    public String deleteFileUpload(Model model, @PathVariable String useralias,
            @PathVariable Long fileuploadId) throws IOException {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            this.uploadsService.deleteFileUpload(useralias, fileuploadId);
            return "redirect:/EmployeeSearch/Users/" + useralias + "/Uploads";
        }
    }
}
