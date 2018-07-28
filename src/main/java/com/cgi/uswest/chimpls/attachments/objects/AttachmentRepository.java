package com.cgi.uswest.chimpls.attachments.objects;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "attachment", path = "attachment")
public interface AttachmentRepository extends CrudRepository<Attachment, BigDecimal> {

	Set<Attachment> findAllByIdmessage(@Param("idmessage") String idmessage);
	
}
