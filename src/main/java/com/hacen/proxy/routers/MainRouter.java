package com.hacen.proxy.routers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.proxy.handler.ProxyHandler;
import io.vertx.httpproxy.HttpProxy;

@Component
@ComponentScan
public class MainRouter extends AbstractVerticle {

	 // Called when verticle is deployed
	 public void start() {
		 
		 // Proxy Server
		 HttpServer proxyServer = vertx.createHttpServer();
		 Router proxyRouter = Router.router(vertx);
		 proxyServer.requestHandler(proxyRouter);
		 proxyServer.listen(8080);
		 
		 /*
		Using ProxyHandler
		The last interesting part is to route proxy server requests to the origin server, so you need to create HttpProxy with specified target and ProxyHandler.
		  */
		 HttpClient proxyClient = vertx.createHttpClient();

		 HttpProxy httpProxy = HttpProxy.reverseProxy(proxyClient);
		 httpProxy.origin(8181, "localhost");

		 proxyRouter
		 .route().handler(ProxyHandler.create(httpProxy));
	 }

	 // Optional - called when verticle is undeployed
	 public void stop() {
	 }
}
