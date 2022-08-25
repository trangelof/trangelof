package com.hacen.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.hacen.proxy.routers.MainRouter;

import io.vertx.core.Vertx;

@SpringBootApplication
public class HacenProxy {
	
	@Autowired private MainRouter mainRouter;

	public static void main(String[] args) {
		SpringApplication.run(HacenProxy.class, args);
	}

	
	/* Este evento se ejecuta despues de que se inice la aplicacion.
	 * De este modo nos aseguramos de que el servidor VertX tiene disponible todos los Beans .
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void deployVerticle() 
	{
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(mainRouter);
	}
}
