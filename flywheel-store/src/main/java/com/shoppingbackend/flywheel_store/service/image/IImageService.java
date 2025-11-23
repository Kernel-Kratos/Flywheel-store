package com.shoppingbackend.flywheel_store.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shoppingbackend.flywheel_store.dto.ImageDto;
import com.shoppingbackend.flywheel_store.model.Image;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List <MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);

}
