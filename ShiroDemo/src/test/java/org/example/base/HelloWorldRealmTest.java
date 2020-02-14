package org.example.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 以硬编码的方式存储用户信息
 * <p>
 * SimpleAccountRealm 没有权限校验
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldRealmTest {

    @Test
    public void contextLoads() {

    }

    @Test
    public void shiroDemoTest() {
        // 1. 构建一个安全管理器环境
        // 注意 DefaultSecurityManager 和 DefaultWebSecurityManager 的区别
        // DefaultSecurityManager 用于本地测试
        // DefaultWebSecurityManager 用于Web整合
        DefaultSecurityManager manager = new DefaultSecurityManager();

        // 2. 创建一个数据源
        SimpleAccountRealm realm = new SimpleAccountRealm();
        realm.addAccount("littlecurl", "123456", "admin", "test");

        // 3. 配置管理器
        manager.setRealm(realm);
        SecurityUtils.setSecurityManager(manager);

/***************************** 以下代码不变  ****************************************/
        // 4. 获得主题
        Subject subject = SecurityUtils.getSubject();

        // 5. 主题提交认证,相当于用户输入信息
        UsernamePasswordToken token = new UsernamePasswordToken("littlecurl", "123456");
        try {
            // 认证
            subject.login(token);
            // 鉴权
            subject.checkRoles("admin", "test123");

        } catch (UnknownAccountException uae) {
            System.out.println("账号不存在");
        } catch (IncorrectCredentialsException exception) {
            System.out.println("用户名或密码不匹配");
        } catch (LockedAccountException lae) {
            System.out.println("账户被锁定");
        } catch (AuthenticationException ae) {
            System.out.println("认证异常");
        } catch (UnauthorizedException ex) {
            System.out.println("鉴权失败，没有权限");
        }

        // 6. 查看认证结果
        if (subject.isAuthenticated()) {
            System.out.println("认证成功");
            subject.logout();
            System.out.println("退出登录: " + !subject.isAuthenticated());
        } else {
            System.out.println("认证失败");
        }
    }
}
