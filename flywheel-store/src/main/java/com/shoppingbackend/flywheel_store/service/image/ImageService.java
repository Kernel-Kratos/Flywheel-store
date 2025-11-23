package com.shoppingbackend.flywheel_store.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingbackend.flywheel_store.dto.ImageDto;
import com.shoppingbackend.flywheel_store.exceptions.ResourceNotFoundException;
import com.shoppingbackend.flywheel_store.model.Image;
import com.shoppingbackend.flywheel_store.model.Product;
import com.shoppingbackend.flywheel_store.repository.ImageRepository;
import com.shoppingbackend.flywheel_store.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with  id " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository :: delete, () ->{ throw new ResourceNotFoundException("No image found with id " + id);});
    }

    @Override
    public List<ImageDto> saveImages(List <MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFilename(file.getOriginalFilename()); //by original it means the name uploaded by client
                image.setFiletype(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);//this saves the image along with url in db to generate id
                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);// this saves the image along with the updated url i.e with the id at end in db

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFilename());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);
            } catch(IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        try{
            Image image = getImageById(imageId);
            image.setFilename(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
