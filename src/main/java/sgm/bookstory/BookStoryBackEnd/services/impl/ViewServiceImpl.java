package sgm.bookstory.BookStoryBackEnd.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sgm.bookstory.BookStoryBackEnd.entities.View;
import sgm.bookstory.BookStoryBackEnd.repos.ViewRepository;
import sgm.bookstory.BookStoryBackEnd.services.ViewService;

@Service
public class ViewServiceImpl implements ViewService {
    @Autowired
    private ViewRepository viewRepository;
    @Override
    public View addView(View view) {
        return viewRepository.save(view);
    }
}
