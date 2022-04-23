package com.warehouse.controller;

import com.warehouse.entity.Attachment;
import com.warehouse.payload.CategoryDto;
import com.warehouse.payload.Result;
import com.warehouse.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/")
    public ResponseEntity<Result> addPhoto(MultipartHttpServletRequest request)  {
        return attachmentService.addPhoto(request);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Result> getPhotoById(@PathVariable("id") Long id,  HttpServletResponse response) throws IOException {
        return attachmentService.getPhotoById(id,response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Result> getAll(HttpServletResponse response) throws IOException {
        return attachmentService.getAll(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deletePhoto(@PathVariable("id") Long id){
        return attachmentService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updatePhoto(MultipartHttpServletRequest request, @PathVariable("id") Long id) throws IOException {
        return attachmentService.updatePhoto( request,id);
    }
}
