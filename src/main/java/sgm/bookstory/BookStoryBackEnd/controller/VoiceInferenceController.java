package sgm.bookstory.BookStoryBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sgm.bookstory.BookStoryBackEnd.entities.Voice;
import sgm.bookstory.BookStoryBackEnd.entities.VoiceInference;
import sgm.bookstory.BookStoryBackEnd.models.ResponseModel;
import sgm.bookstory.BookStoryBackEnd.services.VoiceInferenceService;
import sgm.bookstory.BookStoryBackEnd.services.VoiceService;

import java.util.List;

@RestController
@RequestMapping("/api/voiceInference")
public class VoiceInferenceController {
    @Autowired
    private VoiceInferenceService voiceInferenceService;
    @PostMapping("/add")
    public ResponseModel<VoiceInference> addVoiceInference(@RequestBody VoiceInference voiceInference){
        final VoiceInference savedVoiceInference = voiceInferenceService.addVoiceInference(voiceInference);
        return new ResponseModel<>(HttpStatus.OK.value(), "Voice Inference Saved", savedVoiceInference);
    }
    @PostMapping("/remove")
    public ResponseModel<VoiceInference> removeVoiceInference(@RequestBody VoiceInference voiceInference){
        final VoiceInference removedVoiceInference = voiceInferenceService.removeVoiceInference(voiceInference);
        return new ResponseModel<>(HttpStatus.OK.value(), "Voice Inference Removed", removedVoiceInference);
    }
    @GetMapping("/{voiceName}")
    public ResponseModel<List<VoiceInference>> getAllVoiceInferenceByUser_UserEmailAndBook_BookId(
        @PathVariable(name = "userEmail") String userEmail,
        @RequestParam(name = "bookId") String bookId) {
        final List<VoiceInference> savedVoiceInferences = voiceInferenceService.getAllVoiceInferenceByUser_UserEmailAndBook_BookId(userEmail,Long.parseLong(bookId));
        // userEmail, bookId에 대한 모든 voiceInference 값 반환
        return new ResponseModel<>(HttpStatus.OK.value(), "VoiceInference list by email & bookId", savedVoiceInferences);
    }
}
