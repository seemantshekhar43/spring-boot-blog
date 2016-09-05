///*
// * ****************************************************************************
// *
// *
// *  Copyright(c) 2015 Helloworld. All rights reserved.
// *
// *  This software is the proprietary information of Helloworld.
// *
// *
// * ***************************************************************************
// */
//
//package me.wonwoo.github.asciidoc;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.social.github.api.GitHub;
//import org.springframework.social.github.api.impl.GitHubTemplate;
//import org.springframework.social.github.connect.GitHubConnectionFactory;
//import org.springframework.util.StringUtils;
//
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Helloworld
// * User : wonwoo
// * Date : 2016-09-05
// * Time : 오후 2:40
// * desc :
// */
//@Configuration
//public class GitHubConfig {
//
//    public static final Log logger = LogFactory.getLog(GitHubConfig.class);
//
//    @Value("${github.client.id}")
//    private String githubClientId;
//
//    @Value("${github.client.secret}")
//    private String githubClientSecret;
//
//    @Value("${github.access.token}")
//    private String githubAccessToken;
//
//    @Bean
//    public GitHubConnectionFactory gitHubConnectionFactory() {
//        GitHubConnectionFactory factory = new GitHubConnectionFactory(githubClientId, githubClientSecret);
//        factory.setScope("user");
//        return factory;
//    }
//
//    @Bean
//    public GitHub gitHubTemplate() {
//        if (StringUtils.isEmpty(githubAccessToken)) {
//            return new GuideGitHubTemplate();
//        }
//        return new GuideGitHubTemplate(githubAccessToken);
//    }
//
//    private static class GuideGitHubTemplate extends GitHubTemplate {
//
//        private GuideGitHubTemplate() {
//            super();
//            logger.warn("GitHub API access will be rate-limited at 60 req/hour");
//        }
//
//        private GuideGitHubTemplate(String githubAccessToken) {
//            super(githubAccessToken);
//        }
//
//        @Override
//        protected List<HttpMessageConverter<?>> getMessageConverters() {
//            List<HttpMessageConverter<?>> converters = new ArrayList<>();
//            converters.add(new JsonStringConverter());
//            converters.add(new MarkdownHtmlConverter());
//            converters.add(new DownloadConverter());
//            converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
//            return converters;
//        }
//    }
//}
