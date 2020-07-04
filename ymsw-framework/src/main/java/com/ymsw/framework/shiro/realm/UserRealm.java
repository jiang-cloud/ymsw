package com.ymsw.framework.shiro.realm;

import com.ymsw.framework.config.AccessPhoneConfig;
import com.ymsw.framework.shiro.service.SysLoginService;
import com.ymsw.framework.util.ShiroUtils;
import com.ymsw.system.domain.SysDictData;
import com.ymsw.system.domain.SysUser;
import com.ymsw.system.service.ISysDictDataService;
import com.ymsw.system.service.ISysMenuService;
import com.ymsw.system.service.ISysRoleService;
import com.ymsw.system.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义Realm 处理登录 权限
 * 
 * @author ymsw
 */
public class UserRealm extends AuthorizingRealm
{
    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private AccessPhoneConfig accessPhoneConfig;
    @Autowired
    private ISysDictDataService iSysDictDataService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0)
    {
        SysUser user = ShiroUtils.getSysUser();
        // 角色列表
        Set<String> roles = new HashSet<String>();
        // 功能列表
        Set<String> menus = new HashSet<String>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 管理员拥有所有权限
        if (user.isAdmin())
        {
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        }
        else
        {
            roles = roleService.selectRoleKeys(user.getUserId());
            menus = menuService.selectPermsByUserId(user.getUserId());
            // 角色加入AuthorizationInfo认证对象
            info.setRoles(roles);
            // 权限加入AuthorizationInfo认证对象
            info.setStringPermissions(menus);
        }
        return info;
    }

    /**
     * 用户名密码登录认证
     */
    /*@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null)
        {
            password = new String(upToken.getPassword());
        }

        SysUser user = null;
        try
        {
            user = loginService.login(username, password);
        }
        catch (CaptchaException e)
        {
            throw new AuthenticationException(e.getMessage(), e);
        }
        catch (UserNotExistsException e)
        {
            throw new UnknownAccountException(e.getMessage(), e);
        }
        catch (UserPasswordNotMatchException e)
        {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        }
        catch (UserPasswordRetryLimitExceedException e)
        {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        }
        catch (UserBlockedException e)
        {
            throw new LockedAccountException(e.getMessage(), e);
        }
        catch (RoleBlockedException e)
        {
            throw new LockedAccountException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }*/

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo()
    {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

    /**
     *手机号验证码 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        //接收输入的用户名
        String phone = (String) token.getPrincipal();
        //查看UsernamePasswordToken可知，getCredentials()方法的返回值是char []类型的，所以不能直接转化成string。
        char [] ch = (char[]) token.getCredentials();
        //接收输入的密码或验证码
        String checkCode = new String(ch);
        SysUser user = userService.selectUserByPhoneNumber(phone) ;
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictLabel("required_code");
        sysDictData.setDictType("ymsw_config");
        //字典表里开启了不需要验证码登录
        List<SysDictData> sysDictDataList = iSysDictDataService.selectDictDataList(sysDictData);
        String dictValue = sysDictDataList.get(0).getDictValue();
        if (user != null) {
            //验证手机号是否无需验证码，可直接登录，配置在application.yml里
            if (accessPhoneConfig.getAccessphones().contains(phone)||("0".equals(dictValue))){
                return new SimpleAuthenticationInfo(user, checkCode,null,getName());
            }
            //需要验证码登录
            Session session = ShiroUtils.getSession();
            String code = (String) session.getAttribute("login" + phone);
            session.removeAttribute("login" + phone);//使用一次后立即清除验证码
            //判断验证码是否正确
            if (code == null || checkCode == null || !code.equals(checkCode)) {
                throw new IncorrectCredentialsException();
            }
            return new SimpleAuthenticationInfo(user, checkCode,null,getName());
        }else {
            return null;
        }
    }
}
