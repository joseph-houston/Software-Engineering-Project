package com.cs4910.CBWebApp.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.cs4910.CBWebApp.Models.KanbanWorkflowWarnings;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class KanbanWorkflowWarningsPdf extends AbstractPdfView {
	 protected void buildPdfDocument(Map model,
	   Document document, PdfWriter writer, HttpServletRequest request,
	   HttpServletResponse response) throws Exception {
		KanbanWorkflowWarnings report  =  (KanbanWorkflowWarnings) model.get("command");
		
		
		Paragraph header = new Paragraph(new Chunk("Kanban Workflow Warnings",FontFactory.getFont(FontFactory.HELVETICA, 18)));
//		Paragraph by = new Paragraph(new Chunk("Report Data: " + report.display() ,FontFactory.getFont(FontFactory.HELVETICA, 12)));
		List<Paragraph> paraList = new ArrayList<Paragraph>();
		for(String s : report.displayPDF())
		{
			paraList.add( new Paragraph(s,FontFactory.getFont(FontFactory.HELVETICA, 12)));
		}
		document.add(header);
//		document.add(by);
		for(Paragraph p : paraList)
		{
			document.add(p);
		}
	 }
}
