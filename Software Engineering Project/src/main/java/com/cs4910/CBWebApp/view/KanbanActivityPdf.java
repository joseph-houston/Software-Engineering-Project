package com.cs4910.CBWebApp.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

<<<<<<< HEAD
=======
import com.cs4910.CBWebApp.domain.DomainEmployee;
>>>>>>> refs/remotes/origin/Joe_Local
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class KanbanActivityPdf extends AbstractPdfView {
	 protected void buildPdfDocument(Map model,
	   Document document, PdfWriter writer, HttpServletRequest request,
	   HttpServletResponse response) throws Exception {
		String str = (String) model.get("command");
		Paragraph header = new Paragraph(new Chunk("Kanban Activity Report",FontFactory.getFont(FontFactory.HELVETICA, 18)));
		Paragraph by = new Paragraph(new Chunk("Report Data: " + str ,FontFactory.getFont(FontFactory.HELVETICA, 12)));
			  
		document.add(header);
		document.add(by);
	 }
}
