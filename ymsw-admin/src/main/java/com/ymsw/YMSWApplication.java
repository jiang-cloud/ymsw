package com.ymsw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ymsw
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class YMSWApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(YMSWApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  客户管理系统启动成功   ლ(´ڡ`ლ)ﾞ  \n" +"_____     _____      _______    _____      _/  |_   |  |__      ____       ____  \n" +
                " /     \\    \\__  \\     \\_  __ \\   \\__  \\     \\   __\\  |  |  \\    /  _ \\     /    \\ \n" +
                "|  Y Y  \\    / __ \\_    |  | \\/    / __ \\_    |  |    |   Y  \\  (  <_> )   |   |  \\\n" +
                "|__|_|  /   (____  /    |__|      (____  /    |__|    |___|  /   \\____/    |___|  /\n" +
                "      \\/         \\/                    \\/                  \\/                   \\/ ");
    }
}