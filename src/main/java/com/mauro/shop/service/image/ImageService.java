package com.mauro.shop.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mauro.shop.dto.ImageDto;
import com.mauro.shop.exeptions.ResourceNotFoundException;
import com.mauro.shop.model.Image;
import com.mauro.shop.model.Product;
import com.mauro.shop.repository.ImageRepository;
import com.mauro.shop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

	private final ImageRepository imageRepository;
	private final IProductService productService;
	@Override
	public Image getImageById(Long id) {
		return imageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No image fond with id:"+id));
	}

	@Override
	public void deleteImageById(Long id) {
		imageRepository.findById(id).ifPresentOrElse(imageRepository :: delete, ()->{
			throw new ResourceNotFoundException("No image fond with id:"+id);
		});
		
	}

	@Override
	public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
		Product product = productService.getProductById(productId);
		List<ImageDto> SavedImageDtos = new ArrayList<>();
		for (MultipartFile file : files) {
			try {
				Image image = new Image();
				image.setFileName(file.getOriginalFilename());
				image.setFileType(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));
				image.setProduct(product);
				
				String buldDownloadUrl = "/api/v1/images/image/download/";
				String downloadUrl = buldDownloadUrl+ image.getId();
				image.setDownloadUrl(downloadUrl);
				Image savedImage = imageRepository.save(image);
				
				savedImage.setDownloadUrl(buldDownloadUrl+savedImage.getId());
				imageRepository.save(savedImage);
				
				ImageDto imageDto = new ImageDto();
				imageDto.setId(savedImage.getId());
				imageDto.setFileName(savedImage.getFileName());
				imageDto.setDownloadUrl(savedImage.getDownloadUrl());
				SavedImageDtos.add(imageDto);
				
			}catch(IOException | SQLException e) {
				throw new RuntimeException(e.getMessage());
				
			}
		}
		return SavedImageDtos;
	}

	@Override
	public void updateImage(MultipartFile file, Long imageId) {
		Image image = getImageById(imageId);
		try {
			image.setFileName(file.getOriginalFilename());
			image.setImage(new SerialBlob(file.getBytes()));
			imageRepository.save(image);
		} catch (IOException | SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}

}
