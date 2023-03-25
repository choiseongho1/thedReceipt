package com.thed.receipt.api.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.thed.receipt.api.domain.Domain;
import com.thed.receipt.api.service.TheDService;

// import net.sourceforge.tess4j.TesseractException;

@RestController
public class Controller {

    @Autowired
    private TheDService sampleService;

    @GetMapping
    public String index() {
        return "/index.html";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/api/upload")
    public void upload(@RequestParam("file") MultipartFile file, Model model)
            throws IOException, InvalidFormatException, Exception {
        List<Domain> dataList = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3

        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c1 = Calendar.getInstance();
        String now = sdf.format(c1.getTime());

        System.out.println("Today=" + now);
        String path = "C:\\" + now; // 폴더 경로
        File Folder = new File(path);

        // 해당 디렉토리가 없다면 디렉토리를 생성.
        if (!Folder.exists()) {
            try {
                Folder.mkdir(); // 폴더 생성합니다. ("새폴더"만 생성)
                System.out.println("폴더가 생성완료.");
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            System.out.println("폴더가 이미 존재합니다..");
        }

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

            Domain data = new Domain();

            data.setItemName(row.getCell(0).getStringCellValue());
            data.setBuyer(row.getCell(1).getStringCellValue());

            dataList.add(data);

            sampleService.extractText(data, i, now);

        }
    }

}
