package com.project.moflis.participant.controller;

import com.project.moflis.participant.service.ParticipantService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

}
