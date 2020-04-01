package com.lmj.o2o.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: QRCodeUtils
 * Description:
 * date: 2020/3/26 11:30
 *
 * @author MJ
 */
@Slf4j
public class QRCodeUtils {


    public static void getQRcode(OutputStream outputStream,String shortUrl) {
        try {

            Map<EncodeHintType, Object> map = new HashMap<>();
            map.put(EncodeHintType.CHARACTER_SET,"utf-8");
            map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix matrix = new QRCodeWriter().encode(shortUrl, BarcodeFormat.QR_CODE, 200, 200, map);
            MatrixToImageWriter.writeToStream(matrix,"png",outputStream,
                    new MatrixToImageConfig(Color.BLACK.getRGB(),Color.WHITE.getRGB()));
        }catch (Exception e){
            e.printStackTrace();
            log.debug("生成二维码失败",e);
        }

    }
}
