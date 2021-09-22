package web.main;
import static spark.Spark.*;

import web.controller.RouteController;

public class MainClass {

	public static void main(String[] args) {
	RouteController rtcontrol=new RouteController();
	rtcontrol.init();
	}

}
