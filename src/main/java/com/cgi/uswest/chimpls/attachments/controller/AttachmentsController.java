package com.cgi.uswest.chimpls.attachments.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cgi.uswest.chimpls.attachments.objects.Attachment;
import com.cgi.uswest.chimpls.attachments.objects.AttachmentRepository;

@RefreshScope
@RestController 
public class AttachmentsController {

	  @Autowired
	  private AttachmentRepository attachmentRepository;
	
		@RequestMapping(path = "/add/{idmessage}", method = RequestMethod.POST)
		public @ResponseBody String addNewAttachment(@PathVariable String idmessage,
				@RequestParam String filename,
				@RequestParam MultipartFile binary) throws IOException, SQLException {
			
			Blob blob = new javax.sql.rowset.serial.SerialBlob(binary.getBytes());
			
			Attachment attachment = new Attachment(idmessage, filename, blob);
			attachmentRepository.save(attachment);

			return "Saved";
			
		}
		
		@RequestMapping("/attachmentsByMessage/{idmessage}")
		public Set<Attachment> findAttachmentsByMessage(@PathVariable String idmessage) {
			Set<Attachment> attachments = attachmentRepository.findAllByIdmessage(idmessage);
			
			// remove binary files from the return objects (will get one-by-one on demand)
			Iterator<Attachment> iterator = attachments.iterator();
			
			while(iterator.hasNext()) {
				Attachment a = iterator.next();
				
				a.setBinaryfile(null);
			}
			
			return attachments;
		}
		
		@RequestMapping("/attachmentById/{id}")
		public @ResponseBody byte[] findAttachmentById(@PathVariable String id,
				HttpServletResponse response) throws SQLException {
			Attachment attachment = attachmentRepository.findOneById(new BigDecimal(id));
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\""
		            + attachment.getFilename() + "\"");
			
			return attachment.getBinaryfile().getBytes(1, (int) attachment.getBinaryfile().length());
		}
	  
}
