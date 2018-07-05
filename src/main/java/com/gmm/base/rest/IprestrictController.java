package com.gmm.base.rest;

/*
 * Author: Muthu Mariyappan
 * Date : 05.07.2018
 * It is a Aspect - uses Around advice determine whether joinpoint(login execution) is successful or not.
 * Blocked Ip addresses are stored in ipblocklist file. At execution, the stored file data get modeled as hashset.
 * LinkedList is used to send list of message as response
 * 
 * */

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest; // needed to get client's ip address

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IprestrictController {

	@RequestMapping(value = "/login")
	public LinkedList<String> login(HttpServletRequest request, LinkedList<String> listOfMsgs) {
		String ip = request.getRemoteAddr().toString(); //gets client's ip address
		listOfMsgs.offer("IP "+ip+" logging into the system...");
		return listOfMsgs;
	}
}