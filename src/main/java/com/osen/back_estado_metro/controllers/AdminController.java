package com.osen.back_estado_metro.controllers;


import com.osen.back_estado_metro.dtos.StationDTO;
import com.osen.back_estado_metro.models.Station;
import com.osen.back_estado_metro.models.Status;
import com.osen.back_estado_metro.services.ReportService;
import com.osen.back_estado_metro.services.StationService;
import com.osen.back_estado_metro.services.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.openpdf.text.*;
import org.openpdf.text.Font;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final StationService stationService;
    private final StatusService statusService;
    private final ReportService reportService;

    public AdminController(StationService stationService, StatusService statusService, ReportService reportService) {
        this.stationService = stationService;
        this.statusService = statusService;
        this.reportService = reportService;
    }

    @PostMapping("/new-station")
    public ResponseEntity<?> newStation(@RequestBody String stationName) {

        Status defaultStatus = statusService.findById(1L);
        Station newStation = new Station(null, stationName, defaultStatus);

        return ResponseEntity.ok(newStation);

    }

    @PostMapping("/update-station")
    public ResponseEntity<?> updateStation(@RequestBody Station station) {
        stationService.update(station);
        return ResponseEntity.ok("Actualizacion realizada");

    }


    @GetMapping("/reporte/pdf")
    public ResponseEntity<byte[]> generarReporteGeneral() {
        try {
            List<Station> estaciones = stationService.findAll();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            document.open();

            Font tituloFont = new Font(Font.HELVETICA, 18, Font.BOLD, Color.BLUE);
            Paragraph titulo = new Paragraph("Reporte General de Estado por Estación", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 2});

            Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);
            PdfPCell h1 = new PdfPCell(new Phrase("Estación", headerFont));
            PdfPCell h2 = new PdfPCell(new Phrase("Estado más votado", headerFont));
            h1.setBackgroundColor(Color.DARK_GRAY);
            h2.setBackgroundColor(Color.DARK_GRAY);
            h1.setHorizontalAlignment(Element.ALIGN_CENTER);
            h2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(h1);
            table.addCell(h2);

            for (Station station : estaciones) {
                table.addCell(station.getStationName());

                String statusMasVotado = reportService.findStatusMostVotedByStationName(station.getStationName());
                if (statusMasVotado == null || statusMasVotado.isEmpty()) {
                    statusMasVotado = "Sin votos";
                }
                table.addCell(statusMasVotado);
            }

            document.add(table);
            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "reporte_general.pdf");

            return ResponseEntity.ok().headers(headers).body(baos.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


}
