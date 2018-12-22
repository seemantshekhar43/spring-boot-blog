package me.wonwoo.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import me.wonwoo.domain.model.Comment;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.dto.CommentDto;
import me.wonwoo.service.CommentService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by wonwoo on 2017. 2. 15..
 */
@WebMvcTest(CommentController.class)
public class CommentControllerTests extends AbstractControllerTests {

  @MockBean
  private CommentService commentService;

  @MockBean
  private PostRepository postRepository;

  @Autowired
  private MockMvc mockMvc;

  @Before
  public void setup() {
    given(postRepository.findById(any(Long.class)))
      .willReturn(Optional.of(new Post("post test", "Y")));
  }

  @Test
  public void createComment() throws Exception {

    final Comment value = new Comment("comment content", new Post(1L), new User());
    given(commentService.createComment(any()))
      .willReturn(value);
    CommentDto commentDto = new CommentDto();
    commentDto.setContent("comment content");
    commentDto.setPostId(1L);
    mockMvc.perform(post("/comments").with(csrf()).param("content", "comment content")
      .param("postId", "1"))
      .andExpect(status().isFound())
      .andExpect(header().string(HttpHeaders.LOCATION, "/posts/1"));
    verify(commentService, atLeastOnce()).createComment(any());

  }

  @Test
  public void deleteComment() throws Exception {
    doNothing().when(commentService).deleteComment(any());
    mockMvc.perform(post("/comments/{postId}/{commentId}", "1", "1").with(csrf()))
      .andExpect(status().isFound())
      .andExpect(header().string(HttpHeaders.LOCATION, "/posts/1"));
    verify(commentService, atLeastOnce()).deleteComment(1L);
  }
}