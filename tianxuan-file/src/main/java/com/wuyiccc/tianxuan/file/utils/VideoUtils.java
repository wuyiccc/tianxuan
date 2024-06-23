package com.wuyiccc.tianxuan.file.utils;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/6/23 08:49
 */
@Slf4j
public class VideoUtils {


    public static byte[] getVideoFirstFrame(String path) {
        Frame frame = null;
        int flag = 0;
        byte[] imageBytes = null;

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();

            try (InputStream inputStream = conn.getInputStream();
                 FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(inputStream)) {
                fFmpegFrameGrabber.start();

                int ftp = fFmpegFrameGrabber.getLengthInFrames();
                log.info("时长: " + ftp / fFmpegFrameGrabber.getFrameRate() / 60);

                while (flag < ftp) {
                    frame = fFmpegFrameGrabber.grabImage();
                    if (Objects.nonNull(frame)) {
                        BufferedImage bufferedImage = FrameToBufferedImage(frame);

                        // 处理视频旋转
                        String rotate = fFmpegFrameGrabber.getVideoMetadata("rotate");
                        if (rotate != null && !rotate.isEmpty()) {
                            bufferedImage = rotateImage(bufferedImage, Integer.parseInt(rotate));
                        }

                        if (bufferedImage != null) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(bufferedImage, "jpg", baos);
                            imageBytes = baos.toByteArray();
                            break;
                        }
                    }
                    flag++;
                }

                fFmpegFrameGrabber.stop();
            }
        } catch (Exception e) {
            log.error("Error while extracting video frame", e);
        }

        return imageBytes;
    }

    private static BufferedImage FrameToBufferedImage(Frame frame) {
        Java2DFrameConverter converter = new Java2DFrameConverter();
        return converter.convert(frame);
    }

    private static BufferedImage rotateImage(BufferedImage image, int angle) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage rotatedImage = new BufferedImage(height, width, image.getType());
        Graphics2D graphics = rotatedImage.createGraphics();
        AffineTransform transform = new AffineTransform();
        transform.translate((height - width) / 2, (width - height) / 2);
        transform.rotate(Math.toRadians(angle), width / 2, height / 2);
        graphics.setTransform(transform);
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return rotatedImage;
    }
}
