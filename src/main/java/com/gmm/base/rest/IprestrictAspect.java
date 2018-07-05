package com.gmm.base.rest;


/*
 * Author: Muthu Mariyappan
 * Date : 05.07.2018
 * It is a Aspect - uses Around advice determine whether joinpoint(login execution) is successful or not.
 * Blocked Ip addresses are stored in ipblocklist file. At execution file data get modeled as hashset.
 * Add 127.0.0.1 to ipblocklist if you want to block local login
 * */

import java.io.File;
import java.io.FileNotFoundException;
import javax.servlet.http.HttpServletRequest;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;



@Aspect
@Component
class IprestrictAspect {
	
	Set<String> blockedIpPool; //Holds blocked ip addresses
	private final String IP_BLOCK_LIST_PATH = "L:\\spring-tool-suite-3.9.4.RELEASE-e4.7.3a-win32\\projs\\iprestrict\\src\\main\\java\\ipblocklist";
	
	/*This default constructor builds hashset containing blocked ip address from ipblocklist file*/
	IprestrictAspect(){ 
		blockedIpPool = new HashSet<>();
		Scanner sn = null;
		try {
			sn = new Scanner(new File(IP_BLOCK_LIST_PATH));
		
		} catch (FileNotFoundException e) {
			System.out.println("\nError finding ipblocklist file");
		}
		
		
		while(sn.hasNextLine()){
			blockedIpPool.add(sn.nextLine().trim());
		}
		sn.close();
	}
	
	
	
	//Using Around advice to decide whether login should happen or not
	
	@Around("(execution(* IprestrictController.log*(..)))")
	public LinkedList<String> checkBlockList(ProceedingJoinPoint jp){
		
		String ip = ((HttpServletRequest)jp.getArgs()[0]).getRemoteAddr().toString(); //gets the argument of original target object's method
		LinkedList<String> listofmsgs = null;
		if(jp.getArgs()[1] instanceof LinkedList<?>)
			listofmsgs = (LinkedList<String>) jp.getArgs()[1];
		if(blockedIpPool.contains(ip)){ //checks whether the ip belongs to list of blocked ips
			listofmsgs.offer("Your IP address "+ip+" is blocked. Please contact the administrator!"); //adds this msg to list of messages
			return listofmsgs;
		}
		else{
			try {
				jp.proceed(); // If ip is not blocked then executes target object's login method
				listofmsgs.offerLast("Successful login!");
			} 
			catch (Throwable e) {
				listofmsgs.offerLast("Error logging in to the system.");
			}
			return listofmsgs;
		}
	}
}