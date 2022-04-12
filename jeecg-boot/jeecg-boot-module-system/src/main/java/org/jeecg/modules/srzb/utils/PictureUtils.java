package org.jeecg.modules.srzb.utils;

import org.apache.commons.codec.binary.Base64;
import org.jeecg.common.api.vo.Result;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class PictureUtils {

    /**
     * 获取图片地址
     */
    public static String getPictureAddress(MultipartHttpServletRequest multipartRequest) throws Exception{
            // 获取上传的文件
            MultipartFile multiFile = multipartRequest.getFile("file");
            InputStream inputStream = multiFile.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            inputStream.close();
            byte[] data = outStream.toByteArray();
            Base64 base64 = new Base64();
            //先压缩再base64
            byte[] zip = StreamCompressionUtils.zip(data);
            String picture = base64.encodeToString(zip);
            return picture;
    }

    /**
     * 反转义图片地址，用于展示
     * @param pictureUrl
     * @return
     */
    public static String convertPictureAddress(String pictureUrl){
        Base64 base64 = new Base64();
        byte[] decompression = base64.decode(pictureUrl);
        byte[] unzip = StreamCompressionUtils.unZip(decompression);
        String picture = base64.encodeToString(unzip);
        return picture;
    }
}
