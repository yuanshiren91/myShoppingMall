package com.netease.myShoppingMall.base.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Component
public class FileUploadUtil {	
	
	public static String filePath; 
	
	public static int IMAGE_WIDTH;
	
	public static int IMAGE_HEIGHT;
	
	@Value("${file.path}")
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Value("${goodsImage.width}")
	public void setGoodsWidth(int goodsWidth) {
		this.IMAGE_WIDTH = goodsWidth;
	}
	
	@Value("${goodsImage.height}")
	public void setGoodsHeight(int goodsHeight) {
		this.IMAGE_HEIGHT = goodsHeight;
	}
	
	
    //文件上传  
    public static String uploadFile(MultipartFile file, String pathToSave, String newFileName) throws IOException {       	
    	CommonsMultipartFile tmpFile = (CommonsMultipartFile)file;
		InputStream is = tmpFile.getInputStream(); 
		String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		String saveFilePath = filePath + File.separator + pathToSave;
		//创建文件及目录
        File tempFile = new File(saveFilePath, String.valueOf(newFileName + "." + extension)); 
        if (!tempFile.getParentFile().exists()) {  
            tempFile.getParentFile().mkdir();  
        }  
        if (!tempFile.exists()) {  
            tempFile.createNewFile();  
        }  
        //改变文件大小
        DataOutputStream os = new DataOutputStream(new FileOutputStream(tempFile));
		resizeImage(is, os, IMAGE_WIDTH, IMAGE_HEIGHT, extension);
        
        
        return pathToSave + File.separator + newFileName + "." + extension;
    }  
    
    //文件删除
    public static void deleteFile(String savedFilePath) throws IOException {    	
        File fileToDelete = new File(filePath + File.separator + savedFilePath);
        if (fileToDelete.exists()) {
        	fileToDelete.delete();
        }
    }  
    
    //根据文件路径
    public static File getFile(String savedFilePath) {
    	return new File(filePath + File.separator + savedFilePath);
    }
    
    /**
     * 改变图片大小
     * @param is 上传图片的输入流
     * @param os 目标OutputStream
     * @param newWidth 新图片的宽度
     * @param newHeight 新图片的高度
     * @param format 新图片的格式
     * @throws IOException 
     */
    public static void resizeImage(InputStream is, OutputStream os, int newWidth, int newHeight, String format) throws IOException {
    	BufferedImage bi = ImageIO.read(is);
		BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
		Graphics graphics = image.createGraphics();
		graphics.drawImage(bi, 0, 0, newWidth, newHeight, null);
		ImageIO.write(image, format, os);
		os.flush();
		is.close();
		os.close();
    }
    
    
}
