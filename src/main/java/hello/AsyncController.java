package hello;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Coming from
 * https://github.com/netgloo/spring-boot-samples/blob/master/spring-boot-file-upload-with-ajax/src/main/java/netgloo/controllers/MainController.java
 */
@Controller
public class AsyncController {

    // The Environment object will be used to read parameters from the
    // application.properties configuration file
    @Autowired
    private Environment env;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(
            @RequestParam("uploadfile") MultipartFile uploadfile, @RequestParam("choice") Integer choice) {

        try {
            System.out.println("Choice in async is " + choice);
            // Get the filename and build the local file path
            String filename = uploadfile.getOriginalFilename();
            String filepath = Paths.get("", filename).toString();

            // Save the file locally
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    } // method uploadFile

} // class MainController