package me.wonwoo.support.asciidoc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AsciidoctorUtils {
  private static final Log log = LogFactory.getLog(AsciidoctorUtils.class);

  public Readme getReadme(GuideOrganization org, String path) {
    try {
      log.debug(String.format("Fetching README for '%s'", path));
      return org.getReadme(path);
    } catch (Exception ex) {
      String msg = String.format("No README found for '%s'", path);
      log.warn(msg, ex);
      throw new RuntimeException(msg, ex);
    }
  }

  public AsciidocGuide getDocument(GuideOrganization org, String path) {
    try {
      log.debug(String.format("Fetching getting started guide for '%s'", path));
      return org.getAsciidocGuide(path);
    } catch (Exception ex) {
      String msg = String.format("No getting started guide found for '%s'", path);
      log.warn(msg, ex);
      throw new RuntimeException(msg, ex);
    }
  }

  /**
   * Using a project's metadata and tag info, generate a dynamic sidebar.
   *
   * @param asciidocGuide
   * @return
   */
  // This method's approach to generating HTML directly within code will be refactored
  // in https://github.com/spring-io/sagan/issues/223
  public String generateDynamicSidebar(AsciidocGuide asciidocGuide) {
    StringBuilder sidebar = new StringBuilder("<div class='right-pane-widget--container'>\n" + "<div class='related_resources'>\n");
    sidebar.append("<h3>" + "<a name='table-of-sidebar' class='anchor' href='#table-of-sidebar'></a>" + "Table of sidebar</h3>\n");
    sidebar.append(asciidocGuide.getTableOfContents());
    sidebar.append("</div>\n</div>\n" + "<div class='right-pane-widget--container'>\n" + "<div class='related_resources'>\n");
    if (asciidocGuide.getTags().size() > 0) {
      sidebar.append("<h3>" + "<a name='tags' class='anchor' href='#tags'></a>" + "Tags</h3><ul class='inline'>\n");
      for (String tag : asciidocGuide.getTags()) {
        sidebar.append("<li><a href='/guides?filter=").append(tag).append("'>").append(tag).append("</a></li>\n");
      }
    }
    if (asciidocGuide.getProjects().size() > 0) {
      sidebar.append("</ul><h3>" + "<a name='projects' class='anchor' href='#projects'></a>" + "Projects</h3>\n" + "<ul class='sectlevel1'>\n");

      for (String project : asciidocGuide.getProjects()) {

        log.debug("Looking up project metadata for " + project);
//                Project springIoProject = projectMetadataService.getProject(project);
//                sidebar += "<li><a href='" + springIoProject.getSiteUrl() + "'>" + springIoProject.getName()
        sidebar.append("<li><a href='" + "testurl" + "'>" + "testName" + "</a></li>\n");
      }
      sidebar.append("</ul>\n");
    }
    if (asciidocGuide.getUnderstandingDocs().size() > 0) {
      sidebar.append("<h3>" + "<a name='concepts-and-technologies' class='anchor' href='#concepts-and-technologies'></a>" + "Concepts and technologies</h3>\n" + "<ul class='sectlevel1'>\n");
      for (String key : asciidocGuide.getUnderstandingDocs().keySet()) {
        sidebar.append("<li><a href='").append(key).append("'>").append(asciidocGuide.getUnderstandingDocs().get(key)).append("</a></li>\n");
      }
      sidebar.append("</ul>\n");
    }
    sidebar.append("</div>\n</div>");
    return sidebar.toString();
  }
}
