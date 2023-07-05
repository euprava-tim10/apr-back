package com.example.apr.controller;

import com.example.apr.dto.CreateChangeStatusRequestDto;
import com.example.apr.model.ChangeStatusRequest;
import com.example.apr.model.NotificationStatus;
import com.example.apr.security.permission.IsAdmin;
import com.example.apr.security.permission.IsLoggedIn;
import com.example.apr.service.ChangeStatusRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/changeStatusRequest")
public class ChangeStatusRequestController {

    private final ChangeStatusRequestService changeStatusRequestService;

    ChangeStatusRequestController(ChangeStatusRequestService changeStatusRequestService) {
        this.changeStatusRequestService = changeStatusRequestService;
    }

    @GetMapping
    @IsAdmin
    public ResponseEntity<List<ChangeStatusRequest>> getAll() {
        List<ChangeStatusRequest> all = changeStatusRequestService.findAll();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }


    @PostMapping()
    @IsLoggedIn
    public ResponseEntity<ChangeStatusRequest> createChangeStatusRequest(@RequestBody @Validated CreateChangeStatusRequestDto createChangeStatusRequestDto) {


        ChangeStatusRequest created = changeStatusRequestService.createChangeStatusRequest(createChangeStatusRequestDto);

        if (created == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(created, HttpStatus.CREATED);

    }

    @PutMapping("/accept/{id}")
    @IsAdmin
    public ResponseEntity<Void> acceptedChangeStatusRequest(@PathVariable("id") Long id) {

        ChangeStatusRequest changeStatusRequest = changeStatusRequestService.acceptChangeStatusRequest(id);
        if (!changeStatusRequest.getRequestStatus().equals(NotificationStatus.ACCEPTED)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/reject/{id}")
    @IsAdmin
    public ResponseEntity<Void> rejectChangeStatusRequest(@PathVariable("id") Long id) {

        ChangeStatusRequest changeStatusRequest = changeStatusRequestService.rejectChangeStatusRequest(id);

        if (!changeStatusRequest.getRequestStatus().equals(NotificationStatus.REJECTED)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
