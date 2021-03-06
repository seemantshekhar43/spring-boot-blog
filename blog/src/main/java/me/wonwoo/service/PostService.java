package me.wonwoo.service;

import static org.apache.commons.lang.StringEscapeUtils.unescapeHtml;

import java.time.LocalDateTime;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import me.wonwoo.blog.PostElasticMapper;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.exception.NotFoundException;
import me.wonwoo.support.elasticsearch.PostElasticSearchService;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.RootNode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wonwoo on 2016. 8. 22..
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private final PostElasticSearchService postElasticSearchService;
  private final PegDownProcessor pegdown;
  private final PostElasticMapper postElasticMapper = new PostElasticMapper();

  public Post createPost(Post post) {
    Post savePost = postRepository.save(post);
    savePost.setRegDate(LocalDateTime.now());
    savePost.setCode(toHtml(savePost.getContent()));
    postElasticSearchService.save(postElasticMapper.map(savePost));
    return savePost;
  }

  private String toHtml(String content) {
    return pegdown.markdownToHtml(unescapeHtml(content));
  }

  public void updatePost(Long id, Post post) {
    Post oldPost = postRepository.findByIdAndYn(id, "Y");
    if (oldPost == null) {
      throw new NotFoundException(id + " not found");
    }
    oldPost.setTitle(post.getTitle());
    oldPost.setTags(post.getTags());
    oldPost.setCode(post.getCode());
    oldPost.setContent(post.getContent());
    oldPost.setCategory(post.getCategory());
    oldPost.setCode(toHtml(oldPost.getContent()));
  }

  public void deletePost(Long id) {
    Post oldPost = postRepository.findByIdAndYn(id, "Y");
    if (oldPost == null) {
      throw new NotFoundException(id + " not found");
    }
    oldPost.delete();
  }
}
