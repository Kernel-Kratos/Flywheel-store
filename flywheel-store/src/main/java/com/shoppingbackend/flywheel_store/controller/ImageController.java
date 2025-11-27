package com.shoppingbackend.flywheel_store.controller;

import org.springframework.http.HttpHeaders;
import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingbackend.flywheel_store.dto.ImageDto;
import com.shoppingbackend.flywheel_store.exceptions.ResourceNotFoundException;
import com.shoppingbackend.flywheel_store.model.Image;
import com.shoppingbackend.flywheel_store.response.ApiResponse;
import com.shoppingbackend.flywheel_store.service.image.IImageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try{
            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new ApiResponse("Upload Successful !!!", imageDtos));
           } catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload Failed !!!", productId));
           }
    }
    @GetMapping("/image/download/{imageId}")
    @Transactional // this is because how psql handles large objects(blobs). 
            // It streams the object and it requires the connection to be active/open at all times. Normal SQL commands operate in auto-commit mode
            //It means after one sql command the connection closes which crashes the server. So 
    public ResponseEntity<Resource> downloadImages(@PathVariable Long imageId) throws SQLException{
            Image image = imageService.getImageById(imageId);
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFiletype()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"")
                    .body(resource);
    }

    @PutMapping("/image/update/{imageId}")
    public ResponseEntity<ApiResponse> updateImages(@PathVariable Long imageId, @RequestBody MultipartFile file){
        try{
            Image image = imageService.getImageById(imageId);
            if (image != null){
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Upadate Success !!!", image));
            }
        } catch(ResourceNotFoundException e){
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Failed", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/delete/{imageId}")
    @Transactional
    public ResponseEntity<ApiResponse> deleteImages(@PathVariable Long imageId){
        try{
            Image image = imageService.getImageById(imageId);
            if (image != null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete Success !!!", null)); //changed from image to null because of getting error 500.
                //Reason : Image object contains only the pointer to the photo. meaning it doesn't have photo loaded. we then delete the photo. 
                // so the pointer to the photo is passed onto to jackson and there's a error because there is not photo there so we get the internal sever error
                // instead of 404. THis is very confusing.
            }
        } catch(ResourceNotFoundException e){
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete Failed", INTERNAL_SERVER_ERROR));
    }
     
}
