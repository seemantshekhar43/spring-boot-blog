package me.wonwoo.domain.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
@Entity
@Getter
@EntityListeners(value = AuditingEntityListener.class)
public class Category {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @CreatedDate
  private LocalDateTime regDate;

  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  private List<Post> post = new ArrayList<>();

  Category(){
  }

  public Category(Long id){
    this.id = id;
  }

  public Category(String name){
    this.name = name;
  }
}
