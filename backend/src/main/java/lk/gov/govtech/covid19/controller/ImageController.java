package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.dto.StoredImage;
import lk.gov.govtech.covid19.dto.StoredImageResponse;
import lk.gov.govtech.covid19.service.ImageService;
import lk.gov.govtech.covid19.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(Constants.IMAGE_API_CONTEXT)
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping(path = "/add")
    public ResponseEntity uploadImage(@RequestParam("image") MultipartFile image){
        StoredImageResponse response = null;
        if (image.isEmpty()){
            System.out.println("empty file");
        }else {
            response = imageService.addImage(image);
        }
        System.gc();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getFiles(@PathVariable("imageId") int imageId){
        StoredImage si = imageService.getImage(imageId);
        return si.getImage();
    }
}