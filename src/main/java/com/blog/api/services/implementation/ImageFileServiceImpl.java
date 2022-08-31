package com.blog.api.services.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.api.services.ImageFileService;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@Service
public class ImageFileServiceImpl implements ImageFileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

	// file name
	String imageName = file.getOriginalFilename();

	// customized imageFileName for all files
	String randomID = UUID.randomUUID().toString();
	String customizedFileName = randomID.concat(imageName.substring(imageName.lastIndexOf(".")));

	// full imagePath
	String imagePath = path + File.separator + customizedFileName;

	// create folder to save the images
	File f = new File(path);
	if (!f.exists()) {
	    f.mkdir();
	}

	// copy the file
	Files.copy(file.getInputStream(), Paths.get(imagePath));

	return customizedFileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

	// full imagePath
	String imagePath = path + File.separator + fileName;

	InputStream is = new FileInputStream(imagePath);

	return is;
    }

}
