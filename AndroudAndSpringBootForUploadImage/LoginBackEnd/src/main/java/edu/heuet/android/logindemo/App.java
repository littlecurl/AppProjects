package edu.heuet.android.logindemo;

import edu.heuet.android.logindemo.dao.UserDOMapper;
import edu.heuet.android.logindemo.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"edu.heuet.android.logindemo"})
@RestController
@MapperScan("edu.heuet.android.logindemo.dao")
public class App  {

    @Autowired
    private UserDOMapper userDOMapper;

    @RequestMapping("/home")
    public String print(){
        UserDO userDO = userDOMapper.selectByPrimaryKey(1);
        if (userDO == null) {
            return "用户信息不存在！";
        } else {
            return userDO.getName();
        }
    }

    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class);
    }
}
