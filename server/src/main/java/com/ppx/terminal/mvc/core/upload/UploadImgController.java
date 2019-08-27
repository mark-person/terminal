package com.ppx.terminal.mvc.core.upload;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ppx.terminal.common.controller.ControllerReturn;
import com.ppx.terminal.common.util.ApplicationUtils;

/**
 * 
 * convert -quality 30% 3.jpg out.jpg
 * 
 * ImageMagick
 * 
 * @author mark
 * @date 2019年2月27日
 */
@Controller
@RequestMapping("/uploadImg")
public class UploadImgController {
	
	protected final static String IMG_UPLOAD_PATH = "img/";
	
	protected final static String MODULE_FAQ = "faq/";
	
	@RequestMapping("/uploadFaq") @ResponseBody
	public Map<?, ?> uploadFaq(MultipartFile[] mFile) throws Exception {
		List<String> returnList = new ArrayList<String>();
		
		// 不存就创建文件夹 >>>>>>>>>>>>>>>>>>>>>
		String modulePath = ApplicationUtils.JAR_PARENT_HOME + IMG_UPLOAD_PATH + MODULE_FAQ;
		// 7天一个文件夹2019w11
		Date today = new Date();
		// String.format("%tj", d)一年的第几天
		String dateFolder = String.format("%tY", today) + "w"
				+ String.format("%02d", Integer.parseInt(String.format("%tj", today)) / 10) + "/";
					
		String imgPath = modulePath + dateFolder;
		File imgPathFile = new File(imgPath);
		if (!imgPathFile.exists()) {
			imgPathFile.mkdirs();
		}
		
		System.out.println("ooooooooo..N:" + mFile.length);
		
		for (int i = 0; i < mFile.length; i++) {
			MultipartFile file = mFile[i];
			
			String fileName = file.getOriginalFilename();
			String ext = ".jpg";
			if (!"blob".equals(fileName)) {
				ext = fileName.substring(fileName.lastIndexOf("."));
			}
			
			String imgFileName = UUID.randomUUID().toString() + ext;
			file.transferTo(new File(imgPath + imgFileName));
			
			returnList.add(dateFolder + imgFileName);
		}
		return ControllerReturn.of("list", returnList);
	}

	
	

//	/**
//	 * 判断文件是否是图片
//     * @param file
//     * @return
//     */
//    private boolean isImage(File file) {
//        if (!file.exists()) {
//            return false;
//        }
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(file);
//            if (image == null || image.getWidth() <= 0 || image.getHeight() <= 0) {
//                return false;
//            }
//             return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}