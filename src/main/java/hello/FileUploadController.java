package hello;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
    
    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }
    
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload( @RequestParam("choice") Integer choice,
                                                 @RequestParam("file") MultipartFile file){
        System.out.println("Choice is " + choice);
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = 
                        new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + file.getOriginalFilename() + "!";
            } catch (Exception e) {
                return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + file.getOriginalFilename() + " because the file was empty.";
        }
    }

}
