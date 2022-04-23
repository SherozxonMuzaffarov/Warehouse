package com.warehouse.service;

import com.warehouse.entity.Attachment;
import com.warehouse.entity.AttachmentContent;
import com.warehouse.payload.Result;
import com.warehouse.repository.AttachmentContentRepository;
import com.warehouse.repository.AttachmentRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @SneakyThrows
    public ResponseEntity<Result> addPhoto(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
            Attachment attachment = new Attachment();
            attachment.setName(file.getName());
            attachment.setContentType(file.getContentType());
            attachment.setSize(file.getSize());
            Attachment savedAttachment = attachmentRepository.save(attachment);

        AttachmentContent attachmentContent = new AttachmentContent();
        attachmentContent.setAttachment(savedAttachment);
        attachmentContent.setBytes(file.getBytes());
        attachmentContentRepository.save(attachmentContent);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Result("Fayl saqlandi", true,savedAttachment.getId()));
    }

    public ResponseEntity<Result> getPhotoById(Long id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> contentOptional = attachmentContentRepository.findByAttachmentId(id);
            if (contentOptional.isPresent()){
                AttachmentContent attachmentContent = contentOptional.get();

                //fileni nomini berish uchun
                response.setHeader("Content-Disposition",
                        "attachment;fileName=\"" + attachment.getName()+"\"");

                //file typeni berish uchun
                response.setContentType(attachment.getContentType());

                //file contentini berish uchun
                FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
            }


            return ResponseEntity.ok(new Result("Done Successfully", true));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Attachment not found", false));
    }

    public ResponseEntity<Result> getAll(HttpServletResponse response) throws IOException {
        List<Attachment> all = attachmentRepository.findAll();
        for (Attachment x:all){
            Long id = x.getId();
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
            if (optionalAttachment.isPresent()) {
                Attachment attachment = optionalAttachment.get();
                Optional<AttachmentContent> contentOptional = attachmentContentRepository.findByAttachmentId(id);
                if (contentOptional.isPresent()){
                    AttachmentContent attachmentContent = contentOptional.get();

                    //fileni nomini berish uchun
                    response.setHeader("Content-Disposition",
                            "attachment;fileName=\"" + attachment.getName()+"\"");

                    //file typeni berish uchun
                    response.setContentType(attachment.getContentType());

                    //file contentini berish uchun
                    FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
                }


                return ResponseEntity.ok(new Result("Done Successfully", true));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Attachment not found", false));

    }

    public ResponseEntity<Result> deleteById(Long id) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> contentOptional = attachmentContentRepository.findByAttachmentId(id);
            if (contentOptional.isPresent()){
                AttachmentContent attachmentContent = contentOptional.get();
                attachmentContentRepository.deleteById(attachmentContent.getId());
                attachmentRepository.deleteById(attachment.getId());

                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Result("deleted Successfully", true));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Attachment not found ", false));

    }

    public ResponseEntity<Result> updatePhoto(MultipartHttpServletRequest request, Long id) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Optional<AttachmentContent> contentOptional = attachmentContentRepository.findByAttachmentId(id);
            if (contentOptional.isPresent()){

                Iterator<String> fileNames = request.getFileNames();
                MultipartFile file = request.getFile(fileNames.next());

                Attachment attachment = optionalAttachment.get();
                attachment.setName(file.getName());
                attachment.setContentType(file.getContentType());
                attachment.setSize(file.getSize());
                Attachment savedAttachment = attachmentRepository.save(attachment);

                AttachmentContent attachmentContent = contentOptional.get();
                attachmentContent.setAttachment(savedAttachment);
                attachmentContent.setBytes(file.getBytes());
                attachmentContentRepository.save(attachmentContent);

            }


            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Result("Edited Successfully", true));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Attachment not found ): ", false));

    }
}
