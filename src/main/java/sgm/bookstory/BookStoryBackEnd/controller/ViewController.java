package sgm.bookstory.BookStoryBackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sgm.bookstory.BookStoryBackEnd.entities.View;
import sgm.bookstory.BookStoryBackEnd.models.ResponseModel;
import sgm.bookstory.BookStoryBackEnd.services.ViewService;

import java.util.List;

@RestController
@RequestMapping("/api/view")
public class ViewController {
    @Autowired
    private ViewService viewService;
    @PostMapping("/add")
    public ResponseModel<View> addView(@RequestBody View view){
        final View savedView = viewService.addView(view);
        viewService.updateBookInfo_MANUAL(savedView.getBook());
        return new ResponseModel<>(HttpStatus.OK.value(), "View Saved", savedView);
    }
}
