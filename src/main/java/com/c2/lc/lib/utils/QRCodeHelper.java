package com.c2.lc.lib.utils;

import com.beust.jcommander.internal.Maps;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Log4j2
@Component
public class QRCodeHelper {

    public String generateQRCode(String data, String format) throws IOException, WriterException {
        return generateQRCode(data, format, 400, 400, false);
    }
    public String generateQRCode(String data, String format, int width, int height) throws IOException, WriterException {
        return generateQRCode(data, format, width, height, false);
    }
    public String generateQRCode(String data, String format, int width, int height, boolean base64Encoded) throws IOException, WriterException {
        Map<EncodeHintType, Object> hints = Maps.newHashMap();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height, hints);

        OutputStream os = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, format, os);
        String qrCode = os.toString();

        if (base64Encoded) {
            qrCode = new SystemHelper().getEncodedString(qrCode);
        }
        return qrCode;
    }

    public String generateQRCodeBase64(String data, String format, int width, int height)
            throws IOException, WriterException {
        Map<EncodeHintType, Object> hints = Maps.newHashMap();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        var bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height, hints);
        var os = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, format, os);
        return Base64.encodeBase64String(os.toByteArray());
    }

    public String readQRCode(String data) throws IOException, NotFoundException {
        return readQRCode(data, false);
    }
    public String readQRCode(String data, boolean base64Encoded) throws IOException, NotFoundException {

        String input = data;
        if (base64Encoded) {
            input = new SystemHelper().getDecodedString(data);
        }
        BinaryBitmap binaryBitmap = new BinaryBitmap(
                                        new HybridBinarizer(
                                            new BufferedImageLuminanceSource(
                                                ImageIO.read(
                                                    new ByteArrayInputStream(input.getBytes()) {
                                        }))));

        Result result = new MultiFormatReader().decode(binaryBitmap);

        return result.getText();
    }

}
