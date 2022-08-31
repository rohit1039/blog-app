package com.blog.api.validations;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@Component
public class ImageFileValidator {

    /**
     * 
     * @param image
     * @return
     */
    public boolean checkImage(MultipartFile image) {

	String mimetype = image.getContentType();
	if (mimetype == null) {
	    return false;
	}
	System.out.println("Content type " + mimetype);
	String type = mimetype.split("/")[1];
	System.out.println("File type " + type);
	boolean value = !(type.equals("jpg") || type.equals("jpeg") || type.equals("png")
		|| type.equals("octet-stream")) ? false : true;
	return value;
    }
}