package com.epam.spring.theater.view;

import com.epam.spring.theater.dto.EventDto;
import com.epam.spring.theater.dto.TicketDto;
import com.epam.spring.theater.model.Rating;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

public class PdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<TicketDto> tickets = (List<TicketDto>) model.get("tickets");
        PdfPTable table = new PdfPTable(7);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase(format("All tickets are booked by %s", model.get("userEmail"))));
        cell.setColspan(7);
        table.addCell(cell);
        table.addCell("#");
        table.addCell("Name");
        table.addCell("Hall");
        table.addCell("Rate");
        table.addCell("Price");
        table.addCell("Seat");
        table.addCell("Date");
        int counter = 0;
        for (TicketDto ticket : tickets) {
            table.addCell(String.valueOf(++counter));
            ofNullable(ticket.getEvent()).map(EventDto::getName).ifPresent(table::addCell);
            table.addCell(ticket.getAuditoriumName());
            ofNullable(ticket.getEvent()).map(EventDto::getRating).map(Rating::name).ifPresent(table::addCell);
            ofNullable(ticket.getTicketPrice()).map(BigDecimal::toString).ifPresent(table::addCell);
            ofNullable(ticket.getSeat()).map(seat -> seat.toString()).ifPresent(table::addCell);
            ofNullable(ticket.getDateTime()).map(Date::toString).ifPresent(table::addCell);
        }
        document.add(table);
    }
}
