package com.gwu.carpool.web;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.gwu.carpool.api.CarpoolApi;
import com.gwu.carpool.api.dao.CarpoolDAO;
import com.gwu.carpool.api.dao.impl.MockDAO;
import com.gwu.carpool.api.dao.impl.MongoDAO;
import com.gwu.carpool.web.health.CarpoolHealthCheck;
import com.gwu.carpool.web.resources.*;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import java.util.EnumSet;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;

import static org.eclipse.jetty.servlets.CrossOriginFilter.*;

import org.eclipse.jetty.servlets.CrossOriginFilter;


public class CarpoolWeb extends Application<CarpoolConfiguration> {
    public static void main(String[] args) throws Exception {
        new CarpoolWeb().run(args);
    }

	@Override
	public void run(CarpoolConfiguration configuration, Environment environment) throws Exception {
		final CarpoolDAO dao = new MongoDAO(configuration.getMongoDatabaseName());
		//final CarpoolDAO dao = new MockDAO();
        final CarpoolApi api = new CarpoolApi(dao);
        final UserResource resourceUser = new UserResource(api);
        final LoginResource resourceLogin = new LoginResource(api);
        final SignupResource resourceSignup = new SignupResource(api);
        final EventResource resourceEvent = new EventResource(api);
        final CarpoolHealthCheck healthCheck = new CarpoolHealthCheck();
        environment.healthChecks().register("carpool", healthCheck);
        configureCors(environment);

        environment.jersey().register(resourceUser);
        environment.jersey().register(resourceEvent);
        environment.jersey().register(resourceLogin);
        environment.jersey().register(resourceSignup);
		
	}
	
	private void configureCors(Environment environment) {
	    Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
	    filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
	    filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
	    filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
	    filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
	    filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
	    filter.setInitParameter("allowCredentials", "true");
	  }
}