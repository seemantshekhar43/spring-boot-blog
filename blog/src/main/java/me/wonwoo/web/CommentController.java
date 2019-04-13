package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.Comment;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.dto.CommentDto;
import me.wonwoo.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostRepository postRepository;

    @ModelAttribute
    public Post post(@ModelAttribute CommentDto commentDto){
        return postRepository.findById(commentDto.getPostId()).orElse(null);
    }

    @PostMapping
    public String createComment(@ModelAttribute @Valid CommentDto commentDto, BindingResult bindingResult,
                                @AuthenticationPrincipal User user, Model model){
        if(bindingResult.hasErrors()){
            return "post/post";
        }
        model.addAttribute("comment",commentService.createComment(
                new Comment(commentDto.getContent(),
                        new Post(commentDto.getPostId()),
                        user)));
        return "redirect:/posts/"+commentDto.getPostId();
    }

    @PostMapping("/{postId}/{commentId}")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return "redirect:/posts/"+postId;
    }
}
