package com.lmj.o2o.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: QRCodeUtils
 * Description:
 * date: 2020/3/18 21:48
 *
 * @author MJ
 */
@Slf4j
public class QRCodeUtils {


  public static ByteArrayOutputStream getQRCode(String content) {
      Map<EncodeHintType, Object> map = new HashMap<>();
      map.put(EncodeHintType.CHARACTER_SET,"utf-8");
      map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
      ByteArrayOutputStream byteArrayOutputStream=null;
      try {
          BitMatrix matrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, 200, 200, map);
           byteArrayOutputStream = new ByteArrayOutputStream();
          MatrixToImageWriter.writeToStream(matrix,"png",byteArrayOutputStream,
                  new MatrixToImageConfig(Color.RED.getRGB(), Color.WHITE.getRGB()));
      } catch (IOException e) {
          e.printStackTrace();
          log.debug("生成二维码失败",e.getMessage());
      } catch (WriterException e) {
          e.printStackTrace();
          log.debug("编码二维码信息失败",e.getMessage());
      }
      return byteArrayOutputStream;

  }


}
