package com.security.Security.demo.conroller;

import com.security.Security.demo.domain.Note;
import com.security.Security.demo.domain.User;
import com.security.Security.demo.repos.NoteRepo;
import com.security.Security.demo.repos.UserRepo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private NoteRepo noteRepo;

    private List<Note> filterList = null;

    @GetMapping("/")
    public String index() {

        return "index";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal User user, Model model) {
        List<Note> notes = noteRepo.findAllByAuthor(user.getUsername());

        model.addAttribute("notes", notes);

        return "home";
    }

    @PostMapping("filter")
    public String filter(
            @AuthenticationPrincipal User user,
            @RequestParam String title,

            Model model
    ) {
        List<Note> notes = noteRepo.findAllByTitleAndAuthor(title, user.getUsername());

        model.addAttribute("notes", notes);

        return "home";
    }

    @GetMapping("/email")
    public String email(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("email", userRepo.findByUsername(user.getUsername()).getEmail());

        return "/emailSet";

    }

    @PostMapping("/email")
    public String email(
            @AuthenticationPrincipal User user,
            @RequestParam String email,
            Model model
    ) {

        User user1 = userRepo.findByUsername(user.getUsername());
        user1.setEmail(email);

        userRepo.save(user1);

        model.addAttribute("email", email);

        return "/emailSet";

    }

    @GetMapping("/download")
    public String downloadPage() {

        return "download";
    }

    @PostMapping("/download")
    public String searchForDownload(
            @AuthenticationPrincipal User user,
            @RequestParam String title,

            Model model
    ) {
        List<Note> notes = noteRepo.findAllByTitleAndAuthor(title, user.getUsername());

        model.addAttribute("notes", notes);

        return "download";

    }

    @GetMapping("/downloadSearch")
    public String download(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        xlsNotes(filterList, response, request);

        return "redirect:/home";
    }

    @GetMapping("/downloadAll")
    public String download(
            @AuthenticationPrincipal User user,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        xlsNotes(noteRepo.findAllByAuthor(user.getUsername()), response, request);

        return "redirect:/home";
    }

    private void xlsNotes(List<Note> notes, HttpServletResponse response, HttpServletRequest request) {
        try {
            String fileName = notes.get(0).getAuthor() + ".xls";
            File file = new File(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            Workbook book = new HSSFWorkbook();
            HSSFSheet sheet = (HSSFSheet) book.createSheet("Заметки");
            sheet.autoSizeColumn(1);

            Cell cell;
            Row row;
            HSSFCellStyle style = (HSSFCellStyle) book.createCellStyle();

            int rowNum = 0;

            row = sheet.createRow(rowNum);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Название");
            cell.setCellStyle(style);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Описание");
            cell.setCellStyle(style);

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Статус");
            cell.setCellStyle(style);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Дата");
            cell.setCellStyle(style);

            for(Note note : notes) {
                rowNum++;
                row = sheet.createRow(rowNum);

                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(note.getTitle());

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(note.getDescription());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(note.getStatus());

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(note.getTime());

            }

            book.write(fileOutputStream);
            book.close();

            byte[] reportBytes = null;

            if (file.exists()) {
                InputStream inputStream = new FileInputStream(fileName);
                String type = file.toURL().openConnection().guessContentTypeFromName(fileName);
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                response.setHeader("Content-Type", type);

                reportBytes = new byte[100];//New change
                OutputStream os = response.getOutputStream();//New change
                int read = 0;

                while ((read = inputStream.read(reportBytes)) != -1) {
                    os.write(reportBytes, 0, read);

                }
                os.flush();
                os.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (IllegalStateException e) {
            e.printStackTrace();

        }
    }

    @GetMapping("/newNote")
    public String newNote() {

        return "newNote";
    }

    @PostMapping("/newNote")
    public String addNote(
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String dateFrom,
            @RequestParam String dateTo,

            Model model
    ) {

        System.out.println(
                "Username: " + user.getUsername() +
                " Title: " + title +
                " Description: " + description +
                "Date to: " + dateTo
        );

        noteRepo.save(new Note(
                user.getUsername(), title, description, dateFrom, dateTo
        ));

        model.addAttribute("notes", noteRepo.findAllByAuthor(user.getUsername()));

        return "redirect:/home";
    }

    @GetMapping("/performed/{note}")
    public String performed(
        @PathVariable Note note,
        Model model

    ) {
        String status = note.getStatus();

        if(status.equals("Выполненно")) {
            note.setStatus("На исполнении");

        } else {
            note.setStatus("Выполненно");

        }

        noteRepo.save(note);

        return "redirect:/home";
    }

    @GetMapping("/delete/{note}")
    public String delete(
            @PathVariable Note note

    ) {
        noteRepo.delete(note);

        return "redirect:/home";
    }


    @GetMapping("noteEdit/{note}")
    public String noteEditForm(
        @PathVariable Note note,
        Model model
    ) {
        model.addAttribute("noteEdit", note);

        return "noteEdit";
    }


    @PostMapping("noteEdit/{note}")
    public String saveNote(
        @RequestParam("noteId") Note note,
        @RequestParam String title,
        @RequestParam String description

    ) {

        note.setTitle(title);
        note.setDescription(description);
        note.updateTime();

        noteRepo.save(note);

        return "redirect:/home";
    }
}



