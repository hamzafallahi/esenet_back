package com.example.demo.service.impl;

import com.example.demo.entity.Members;
import com.example.demo.repository.MembersRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmailService emailService;
    private final MembersRepository membersRepository;

    public void register(Members request) throws MessagingException, IOException {
        membersRepository.save(request);

        // Send confirmation email to the registered user
        emailService.sendHtmlEmail(
                request.getEmail(),
                "Confirm your attendance",
                "welcome to esent 2024 you confirmed and reserved your place at the event and your registered number is "+ request.getId()
        );
        sendUsersExcelReport();
    }

    public void sendUsersExcelReport() throws MessagingException, IOException {
        List<Members> allMembers = membersRepository.findAll();

        // Create Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Members");

        // Define header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("First Name");
        headerRow.createCell(2).setCellValue("Last Name");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Number");

        // Populate sheet with member data
        int rowIndex = 1;
        for (Members member : allMembers) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(member.getId());
            row.createCell(1).setCellValue(member.getFirstname());
            row.createCell(2).setCellValue(member.getLastname());
            row.createCell(3).setCellValue(member.getEmail());
            row.createCell(4).setCellValue(member.getNumber());
        }

        // Resize columns to fit data
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        // Convert workbook to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();

        // Send email with Excel attachment
        emailService.sendEmailWithAttachment(
                "hamzafallahi7@gmail.com",
                "User List Report",
                "Attached is the latest user list.",
                "members.xlsx",
                byteArrayOutputStream.toByteArray()
        );
    }
}
