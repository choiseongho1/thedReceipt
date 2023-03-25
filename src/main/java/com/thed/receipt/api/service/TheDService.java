package com.thed.receipt.api.service;

// import net.sourceforge.tess4j.Tesseract;

import java.io.File;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
// import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.springframework.stereotype.Service;

import com.thed.receipt.api.domain.Domain;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TheDService {
    // private Tesseract tesseract;

    public TheDService() {
        // this.tesseract = new Tesseract();
        // tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
    }

    public void extractText(Domain data, int i, String now) throws Exception {
        // File imageFile = new File("C:/receipt_img.png");
        // tesseract.setLanguage("kor+eng");
        // String text = tesseract.doOCR(imageFile);

        // 이미지를 텍스트 층으로 구성하여 텍스트 변경
        BufferedImage image = null;
        // FontMetrics metrics = null;
        image = ImageIO.read(new File("C:/receipt_img.png")); // 배경 파일 불러오기

        Graphics g = image.getGraphics();
        // PDFont font = PDType0Font.load(doc, new File("C:/NanumGothic.ttf"));
        g.setColor(Color.BLACK);
        g.setFont(new Font("배달의민족 도현", Font.BOLD, 15));
        g.drawString("[주문하신 상품] ", 180, 240); // 문자열 삽입

        g.setFont(new Font("배달의민족 도현", Font.BOLD, 20));
        g.drawString("ㆍ", 190, 270); // 문자열 삽입

        g.setFont(new Font("배달의민족 도현", Font.PLAIN, 14));
        g.drawString(data.getItemName(), 210, 270); // 문자열 삽입

        g.setFont(new Font("배달의민족 도현", Font.PLAIN, 20));
        g.drawString("ㆍ", 190, 350); // 문자열 삽입

        g.setFont(new Font("배달의민족 도현", Font.PLAIN, 14));
        g.drawString("수령자:" + data.getBuyer(), 210, 350); // 문자열 삽입

        ImageIO.write(image, "png",
                new File("C:/" + now + "/" + data.getBuyer() + "_" + data.getItemName() + i + ".png")); // 문자열이 삽입된 PNG
                                                                                                        // 파일 저장
        g.dispose();

        // BufferedImage image = ImageIO.read(new File("C:/receipt_img.png"));
        // PDPage page = new PDPage(new PDRectangle(image.getWidth(),
        // image.getHeight()));
        // PDDocument doc = new PDDocument();
        // doc.addPage(page);
        // PDPageContentStream content = new PDPageContentStream(doc, page);
        // PDFont font = PDType0Font.load(doc, new File("C:/NanumGothic.ttf"));

        // // 변경된 텍스트 이미지에 적용
        // content.beginText();
        // content.setFont(font, 12);
        // content.newLineAtOffset(185, 230); // 좌표
        // content.showText(newText);
        // content.endText();
        // content.close();

    }
}