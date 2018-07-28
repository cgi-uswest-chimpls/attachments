package com.cgi.uswest.chimpls.attachments.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
			return attachmentRepository.findAllByIdmessage(idmessage);
		}
	  
}
