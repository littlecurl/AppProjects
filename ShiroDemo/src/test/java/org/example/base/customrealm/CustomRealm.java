package org.example.base.customrealm;

import com.alibaba.druid.util.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义 CustomRealm
 * 继承自 AuthorizingRealm
 * 而 AuthorizingRealm 又继承自 AuthorizingRealm
 * 而 AuthorizingRealm 又继承自 AuthenticatingRealm
 * 最终 AuthenticatingRealm 继承自 CachingRealm
 * <p>
 * 根据 JVM 类加载双亲委派机制 可知方法的执行顺序为先父类,再子类
 * 因此,先执行 认证, 再执行 鉴权
 * 也可以自行打断点进行验证
 */
public class CustomRealm extends AuthorizingRealm {
    /**
     * 测试用户信息
     * 假装是从数据库中取出来的
     */
    Map<String, String> userMap = new HashMap<>();
    Map<String, String> roleMap = new HashMap<>();
    Map<String, String> permissionMap = new HashMap<>();

    {
        // 静态初始化
        userMap.put("test", "123456");
        userMap.put("admin", "123456");
        roleMap.put("test", "test");
        roleMap.put("admin", "admin");
        permissionMap.put("test", "user:list");
        permissionMap.put("admin", "*");
    }

    /**
     * 鉴权
     * 通过用户名拿到角色
     * 再通过角色拿到权限
     *
     * @param principalCollection 用户信息
     * @return 返回权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        String role = roleMap.get(username);
        String permission = permissionMap.get(role);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(role);
//        info.addRole();
        info.addStringPermission(permission);
//        info.addObjectPermissions();
        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken 前端传来的 token
     * @return 返回用户信息
     * @throws AuthenticationException 认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = userMap.get(username);
        // 此处如果不拦截 password 判断返回 null,则会抛出NPE异常
        if (StringUtils.isEmpty(password)) {
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, username);
        return info;
    }
}
