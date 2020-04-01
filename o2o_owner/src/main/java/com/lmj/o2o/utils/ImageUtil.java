package com.lmj.o2o.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.csource.common.FastdfsException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * ClassName: ImageUtil
 * Description: fastdfs工具类
 * date: 2020/3/8 9:54
 *
 * @author MJ
 */
public class ImageUtil {

    private static final String FASTDFS_CONFIG_FILE_NAME="fdfs_client.conf";

    static {
        try {
            ClientGlobal.init(FASTDFS_CONFIG_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FastdfsException e) {
            e.printStackTrace();
        }
    }

    public static String uploadImage(String fileName,String originFileName,byte[] bytes) {
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer,
                storageServer);
        NameValuePair nvp [] = new NameValuePair[]{
                new NameValuePair("item_id", "100010"),
                new NameValuePair("width", "80"),
                new NameValuePair("height", "90")
        };
        String extName = getImgExtensionName(originFileName);
        StringBuilder sb = new StringBuilder();
        try {
            String[] fileIds = storageClient.uploadFile(bytes, extName, nvp);
            sb.append("/").append(fileIds[0]).append("/").append(fileIds[1]);
            System.out.println(sb.toString());
     /*       System.out.println(fileIds.length);
            System.out.println("组名：" + fileIds[0]);
            System.out.println("路径: " + fileIds[1]);*/
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FastdfsException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    private static String getImgExtensionName(String originFileName) {
        int i = originFileName.lastIndexOf(".");
        return originFileName.substring(i+1);

    }


    public static byte[] getThumbnailBytes(MultipartFile file) {
        BufferedImage image = null;
        byte[] thumbnailBytes=null;
        try {
            image = Thumbnails.of(file.getInputStream()).size(200, 200).outputQuality(0.25f).asBufferedImage();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "JPEG", out);
            thumbnailBytes=out.toByteArray();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return thumbnailBytes;
    }
}
