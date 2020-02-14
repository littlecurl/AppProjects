package org.example.extension;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * CustomRealmTest基础之上扩展
 * 认证加盐加密
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EncryptPasswordTest {

    @Test
    public void encryptPasswordTest() {
        // 1. 构建SecurityManager
        DefaultSecurityManager manager = new DefaultSecurityManager();

        // 2. 自定义读取数据源
        CustomRealm realm = new CustomRealm();
/***************************** CustomRealm 设置了一个Matcher  ****************************************/
        realm.setCredentialsMatcher(getMatcher());
        // 3. 配置管理器
        manager.setRealm(realm);
        SecurityUtils.setSecurityManager(manager);

/***************************** 以下代码不变  ****************************************/
        // 4. 获得主题
        Subject subject = SecurityUtils.getSubject();

        // 5. 主题提交认证,相当于用户输入信息
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
        try {
            // 认证
            subject.login(token);
            // 鉴权
            subject.checkRoles("admin");
            subject.checkPermissions("user:list", "user:edit");
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

    private HashedCredentialsMatcher getMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(3);
        return matcher;
    }

}
