package com.breno.apicastgroup.services.util;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.breno.apicastgroup.entities.Employee;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGenerator {

    private static Path generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return path;
    }

    public static String generateEmployeeQRCode(Employee employee) {
    	
    	String pathGenerateQrCode = "./qrcodes/" + employee.getRegistration() + ".png";
        try {
            generateQRCodeImage(employee.toString(), 350, 350, pathGenerateQrCode);
        } catch (WriterException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } 
        
        return pathGenerateQrCode;
    }
}