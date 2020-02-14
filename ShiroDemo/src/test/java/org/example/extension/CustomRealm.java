package org.example.extension;

import com.alibaba.druid.util.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.Map;

/**
 * 密码进行加密
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
/***************************** 数据库中存储加密密码  ****************************************/
        userMap.put("test", getEncryptPassword("123456"));
        userMap.put("admin", getEncryptPassword("123456"));
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
/***************************** 认证加盐  ****************************************/
        info.setCredentialsSalt(ByteSource.Util.bytes("salt123"));
        return info;
    }


    private String getEncryptPassword(String password) {
        return new Md5Hash(password, "salt123", 3).toString();
    }

}
