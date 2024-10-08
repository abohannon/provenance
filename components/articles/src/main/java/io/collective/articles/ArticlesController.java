package io.collective.articles;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.collective.restsupport.BasicHandler;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ArticlesController extends BasicHandler {
    private final ArticleDataGateway gateway;

    public ArticlesController(ObjectMapper mapper, ArticleDataGateway gateway) {
        super(mapper);
        this.gateway = gateway;
    }

    @Override
    public void handle(String target, Request request, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        get("/articles", List.of("application/json", "text/html"), request, servletResponse, () -> {

        // todo:done - query the articles gateway for *all* articles, map record to infos, and send back a collection of article infos

          var articles = gateway.findAll();
          var articleInfos = articles.stream().map(articleRecord -> new ArticleInfo(articleRecord.getId(), articleRecord.getTitle())).toList();

          writeJsonBody(servletResponse, articleInfos);
        });

        get("/available", List.of("application/json"), request, servletResponse, () -> {

          // todo:done - query the articles gateway for *available* articles, map records to infos, and send back a collection of article infos

          var articles = gateway.findAvailable();
          var articleInfos = articles.stream().map(articleRecord -> new ArticleInfo(articleRecord.getId(), articleRecord.getTitle())).toList();

          writeJsonBody(servletResponse, articleInfos);
        });
    }
}
