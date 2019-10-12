package com.itheima.controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    private HttpServletRequest request;

    private Date vistTime;//开始时间
    private Class aClass;//访问的类
    private Method method;//访问访问


    //前置通知,获取开始时间,执行的方法和执行的类
    @Before("execution(* com.itheima.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        vistTime=new Date();
        aClass = jp.getTarget().getClass();

        //获取执行方法的Method对象
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        if (args==null||args.length==0){
            //获取无参方法
            aClass.getMethod(methodName);
        }else {
            //获取有参方法
            //创建一个与args同等大小的数组容器
            Class[] classArgs=new Class[args.length];
            for (int i=0;i<args.length;i++){
                //将参数中的类循环赋给容器
                classArgs[i]=args[i].getClass();
            }
            aClass.getMethod(methodName,classArgs);
        }

    }


    //后置通知
    @After("execution(* com.itheima.controller.*.*(..))")
    public void doAfter(JoinPoint jp){
        //获取访问时长
        long time = new Date().getTime() - vistTime.getTime();

        //获取url
        String url="";
        //1.获取类上注解的url
        if (aClass!=null&&method!=null&&aClass!=LogAop.class){
            RequestMapping classAnnotation = (RequestMapping) aClass.getAnnotation(RequestMapping.class);
            if (classAnnotation!=null){
                String[] classValue = classAnnotation.value();
                //2.获取方法上的url
                RequestMapping methodAnnotation = this.method.getAnnotation(RequestMapping.class);
                if (methodAnnotation!=null){
                    String[] methodValue = methodAnnotation.value();
                    url=classValue[0]+methodValue[0];
                }
            }
        }

        //获取访问的Ip地址
        String ip = request.getRemoteAddr();

        //获取操作用户名
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();
    }
}
