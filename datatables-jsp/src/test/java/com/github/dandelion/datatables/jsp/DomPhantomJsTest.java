/*
 * [The "BSD licence"]
 * Copyright (c) 2013 Dandelion
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of Dandelion nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.dandelion.datatables.jsp;

import java.io.File;
import java.util.ArrayList;

import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.testing.ServletTester;
import org.eclipse.jetty.webapp.WebAppContext;
import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.service.DriverService;

import com.github.dandelion.datatables.core.web.servlet.DatatablesServlet;
import com.github.dandelion.datatables.entity.Person;
import com.github.dandelion.datatables.utils.Mock;

public abstract class DomPhantomJsTest extends FluentAdapter {
    private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(1024, 768);

    public static final String TABLE_ID = "myTableId";
    
    protected static WebDriver driver;
    private static Server webServer;
    protected static ServletTester servletTester;
    
    @BeforeClass
    public static void configure_server() throws Exception {
        
        webServer = new Server();

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setHost("127.0.0.1");
        connector.setPort(9090);
        webServer.addConnector(connector);

        WebAppContext context = new WebAppContext("src/test/webapp", "/");
        
        context.setAttribute("persons", Mock.persons);
        context.setAttribute("emptyList", new ArrayList<Person>());
        context.setAttribute("nullList", null);
        
        ServletHolder jsp = context.addServlet(JspServlet.class, "*.jsp");
        jsp.setInitParameter("classpath", context.getClassPath());
        
        webServer.setHandler(context);
        webServer.setStopAtShutdown(true);
        
        servletTester = new ServletTester();
        servletTester.addServlet(DatatablesServlet.class, "/datatablesController");
        servletTester.start();
    }

    @Before
    public void start_server() {
        try {
            webServer.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void stop_server() {
        try {
            webServer.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Rule
    public TestWatcher lifecycle = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            if (null == driver) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    @Override
                    public void run() {
                        if (driver != null) {
                            driver.quit();
                        }
                    }
                });

                Capabilities capabilities = new DesiredCapabilities();
                DriverService service = PhantomJSDriverService.createDefaultService(capabilities);
                driver = new PhantomJSDriver(service, capabilities);
            }

            driver.manage().deleteAllCookies();
            driver.manage().window().setSize(DEFAULT_WINDOW_SIZE);
            initFluent(driver).withDefaultUrl(defaultUrl());
        }
        

        @Override
        protected void succeeded(Description description) {
            snapshotFile(description).delete();
        }

        @Override
        protected void failed(Throwable e, Description description) {
            takeScreenShot(snapshotFile(description).getAbsolutePath());
        }

        private File snapshotFile(Description description) {
            return new File("snapshots", description.getMethodName() + ".png");
        }
    };

    public String defaultUrl() {
        return "http://127.0.0.1:9090";
    }
    
    @SuppressWarnings("unchecked")
	public FluentList<FluentWebElement> getTable(){
    	return find("#" + TABLE_ID + "_wrapper").find("table");
    }
    
    public FluentWebElement getBody(){
    	return findFirst("body");
    }
}
