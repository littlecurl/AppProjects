package org.example.base;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 从数据库中读取用户信息
 * 需要加入mysql、druid的驱动依赖
 * (注意druid-SpringBoot不能用来测试,需要引入原生的druid)
 * 并创建好数据库、表 同时 赋初值
 * <p>
 * 用法比其他几个多一步骤，必须 setPermissionsLookupEnabled 开启否则无法鉴权
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JDBCRealmTest {

    @Test
    public void jdbcRealmTest() {
        // 1. 构建SecurityManager
        DefaultSecurityManager manager = new DefaultSecurityManager();
        // 2. JDBC从数据库连接中读取数据源
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setPermissionsLookupEnabled(true);  // 比其他几个多一步骤，必须开启否则无法鉴权
        jdbcRealm.setDataSource(dataSource);


        // 以下为原始配置，可自定义修改
        String authenticationQuery = "select password from users where username = ?";
        String userRolesQuery = "select role_name from user_roles where username = ?";
        String permissionsQuery = "select permission from roles_permissions where role_name = ?";
        jdbcRealm.setAuthenticationQuery(authenticationQuery);
        jdbcRealm.setUserRolesQuery(userRolesQuery);
        jdbcRealm.setPermissionsQuery(permissionsQuery);


        // 3. 配置管理器
        manager.setRealm(jdbcRealm);
        SecurityUtils.setSecurityManager(manager);

        // 4. 获取主题
        Subject subject = SecurityUtils.getSubject();

        // 5. 主题提交认证,相当于用户输入信息
        UsernamePasswordToken token = new UsernamePasswordToken("test", "123456");
        try {
            // 认证
            subject.login(token);
            // 鉴权
            subject.checkRoles("test");
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


}
