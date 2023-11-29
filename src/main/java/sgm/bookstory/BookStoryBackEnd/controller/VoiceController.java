package sgm.bookstory.BookStoryBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sgm.bookstory.BookStoryBackEnd.entities.Voice;
import sgm.bookstory.BookStoryBackEnd.models.ResponseModel;
import sgm.bookstory.BookStoryBackEnd.services.VoiceService;

import java.util.List;

@RestController
@RequestMapping("/api/voice")
public class VoiceController {
    @Autowired
    private VoiceService voiceService;
    @PostMapping("/add")
    public ResponseModel<Voice> addVoice(@RequestBody Voice voice){
        final Voice savedVoice = voiceService.addVoice(voice);
        return new ResponseModel<>(HttpStatus.OK.value(), "Voice Saved", savedVoice);
    }
    @PostMapping("/remove")
    public ResponseModel<Voice> removeVoice(@RequestBody Voice voice){
        final Voice removedVoice = voiceService.removeVoice(voice);
        return new ResponseModel<>(HttpStatus.OK.value(), "Voice Removed", removedVoice);
    }
    @PostMapping("/update")
    public ResponseModel<Voice> updateVoiceStatus(@RequestBody Voice voice){
        final Voice updatedVoice = voiceService.updateVoiceStatus(voice);
        return new ResponseModel<>(HttpStatus.OK.value(), "Voice Updated", updatedVoice);
    }
    @GetMapping("/{userEmail}")
    public ResponseModel<List<Voice>> getAllVoiceByUser_UserEmail(
        @PathVariable(name = "userEmail") String userEmail) {
        final List<Voice> savedVoices = voiceService.getAllVoiceByUser_UserEmail(userEmail);
        // userEmail에 대한 모든 favorite 값 반환
        return new ResponseModel<>(HttpStatus.OK.value(), "Voice list by email", savedVoices);
    }
}
