package com.svetikov.ecommerceshop.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

public class FileUploadService {


    private static String UPLOAD_PATH_ABS = "/home/rozella/JavaEE/E-Commerce Website/src/main/webapp/assets/images";

      public static String uploadFile(MultipartFile file) {

        String code = "PRD" + UUID.randomUUID().toString().substring(26).toUpperCase();

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
                File serverFile = new File(UPLOAD_PATH_ABS + File.separator   + code + "." + fileExtension);
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(serverFile));
                out.write(bytes);
                out.flush();
                out.close();
                return code + "." +  fileExtension;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
          return null;
    }

}
