# IP-block-using-springboot-AOP


This is simple demo of Spring boot AOP to block ip addresses. 

List of blocked ip addresses stored in flat file. At runtime, the list gets modeled as a hashset for faster lookup.

Using @Around advice in the aspect, we decide whether login should be allowed or not. I used ProceedingJoinPoint class to determine when to execute the target object's method(join point).

@Around advice and ProceedingJoinPoint gives the flexibility to decide method execution

Please take a look at the source code for more information and use tutorial links to understand the aop jargons.

# Tutorial links

* https://www.javatpoint.com/spring-aop-tutorial

* https://www.mkyong.com/spring3/spring-aop-aspectj-annotation-example/

* https://www.mkyong.com/spring3/spring-aop-aspectj-annotation-example/

* http://www.javainuse.com/spring/spring-boot-aop
