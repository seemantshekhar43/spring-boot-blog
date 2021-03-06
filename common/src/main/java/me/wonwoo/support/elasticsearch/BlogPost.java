package me.wonwoo.support.elasticsearch;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Document(indexName = "blog", type = "post", replicas = 0, refreshInterval = "-1")
public class BlogPost {
  @Id
  @JsonProperty("ID")
  private Integer id;

  @JsonProperty("post_title")
  @Field(analyzer = "simple", store = true)
  private String postTitle;

  @JsonProperty("post_author")
  private Integer postAuthor;

  @Field(analyzer = "fulltext", store = true)
  @JsonProperty("post_content")
  private String postContent;

  @JsonProperty("post_date")
  @Field(store = true)
  private LocalDateTime postDate;

  @JsonProperty("post_type")
  private String postType;

  @JsonProperty("post_status")
  private String postStatus;

  @JsonProperty("post_content_filtered")
  @Field(analyzer = "fulltext", store = true)
  private String postContentFiltered;

  @Field(analyzer = "fulltext", store = true)
  @JsonProperty("highlighted_content")
  private String highlightedContent;

  @JsonProperty("table_of_content")
  private String tableOfContent;

  @JsonProperty("post_id")
  private Long postId;
}
