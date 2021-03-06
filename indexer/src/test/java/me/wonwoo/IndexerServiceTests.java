package me.wonwoo;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.util.ResourceUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 27..
 */
public class IndexerServiceTests {

  private ExecutorService executorService = Executors.newCachedThreadPool();

  private IndexerService indexerService;

  @Before
  public void setup() {
    indexerService = new IndexerService(executorService);
  }

  @Test
  public void index() {
    indexerService.index(new Indexer<Bean>() {

      @Override
      public void preHandle() {

      }

      @Override
      public Iterable<Bean> indexItems() {
        return Collections.singletonList(new Bean("wonwoo"));
      }

      @Override
      public void indexItem(Bean index) {
        assertThat(index.getId()).isEqualTo("wonwoo");

      }
      @Override
      public void error(Bean index, Throwable e) {

      }

      @Override
      public void postHandle() {

      }
    });

  }

  @Test
  public void indexException() {
    indexerService.index(new Indexer<Bean>() {
      @Override
      public void preHandle() {

      }
      @Override
      public Iterable<Bean> indexItems() {
        return Collections.singletonList(new Bean("wonwoo"));
      }

      @Override
      public void indexItem(Bean index) {
        throw new RuntimeException();

      }
      @Override
      public void error(Bean index, Throwable e) {
        assertThat(new RuntimeException()).hasCause(e);
      }

      @Override
      public void postHandle() {

      }
    });

  }

  private static class Bean {
    private final String id;

    private Bean(String id) {
      this.id = id;
    }

    public String getId() {
      return id;
    }
  }

}