package com.example.demo.service.impl;

import com.example.demo.service.QrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QrCodeServiceImpl.class);

    //二维码颜色
    private static final int BLACK = 0xFF000000;
    //二维码颜色
    private static final int WHITE = 0xFFFFFFFF;

    /**
     *
     */
    public String tomakeMode(String strJson) {
        String path = "F:\\qr";
        String filePostfix="png";
        Random random = new Random();
        int i = random.nextInt(10);
        File file = new File(path  +i + "."+filePostfix);
        encode(strJson, file,filePostfix, BarcodeFormat.QR_CODE, 5000, 5000, null);
        return file.getName();
    }

    /**
     *  生成QRCode二维码<br>
     *  在编码时需要将com.google.zxing.qrcode.encoder.Encoder.java中的<br>
     *  static final String DEFAULT_BYTE_MODE_ENCODING = "ISO8859-1";<br>
     *  修改为UTF-8，否则中文编译后解析不了<br>
     * @param contents 二维码的内容
     * @param file 二维码保存的路径，如：C://test_QR_CODE.png
     * @param filePostfix 生成二维码图片的格式：png,jpeg,gif等格式
     * @param format qrcode码的生成格式
     * @param width 图片宽度
     * @param height 图片高度
     * @param hints
     */
    public  void encode(String contents, File file,String filePostfix, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
        try {
            contents = new String(contents.getBytes("UTF-8"), "ISO-8859-1");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, format, width, height);
            writeToFile(bitMatrix, filePostfix, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码图片<br>
     *
     * @param matrix
     * @param format
     *            图片格式
     * @param file
     *            生成二维码图片位置
     * @throws IOException
     */
    public  static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        ImageIO.write(image, format, file);
    }

    /**
     * 生成二维码内容<br>
     *
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
            }
        }
        return image;
    }

}
