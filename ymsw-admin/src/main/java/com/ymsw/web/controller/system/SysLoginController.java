package com.ymsw.web.controller.system;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.ymsw.common.annotation.Log;
import com.ymsw.common.core.controller.BaseController;
import com.ymsw.common.core.domain.AjaxResult;
import com.ymsw.common.enums.BusinessType;
import com.ymsw.common.interfaces.Smessage;
import com.ymsw.common.utils.ServletUtils;
import com.ymsw.system.domain.SysUser;
import com.ymsw.system.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录验证
 * 
 * @author ymsw
 */
@Controller
public class SysLoginController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response)
    {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return "login";
    }

   /* @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe)
    {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            return success();
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }*/

    @GetMapping("/unauth")
    public String unauth()
    {
        return "error/unauth";
    }

    /**
     * 获取登录验证码
     * @param phone
     * @param session
     * @return
     * @throws ClientException
     */
    @PostMapping("/getCode")
    @ResponseBody
    @Log(title = "获取验证码", businessType = BusinessType.OTHER)
    public AjaxResult getCode(String phone, HttpSession session) throws ClientException {
        SysUser sysUser = userService.selectUserByPhoneNumber(phone);
        if (sysUser == null){
            return error("用户不存在！");
        }else{
            SendSmsResponse sendSmsResponse = Smessage.sendMessage(phone, session);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")){
                return success("发送短信成功！");
            }else {
                return error(sendSmsResponse.getMessage());
            }

        }
    }

    /**
     * 手机号码+验证码登录
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String phone,String code)
    {
        //主体,当前状态为没有认证的状态“未认证”
        Subject subject = SecurityUtils.getSubject();
        //创建令牌，将手机号和验证码丢进去
        UsernamePasswordToken token = new UsernamePasswordToken(phone, code);
        try {
            //登录
            subject.login(token);
            //登录成功，则认证成功，rememberMe和这个认证的状态是互斥的
            if (subject.isAuthenticated()){
                //获得session
                Session subjectSession = subject.getSession();
                //去数据里查找当前用户
                SysUser user = userService.selectUserByPhoneNumber(phone);
                //将用户存于session中
                subjectSession.setAttribute(phone, user);
            }
            return success("登录成功!");
        } catch (UnknownAccountException ex) {// 用户名没有找到。
            return error("账号不存在!");
        } catch (IncorrectCredentialsException ex) {// 用户名密码不匹配。
            return error("验证码错误!");
        } catch (AuthenticationException e) {//其他异常
            return error("请重新获取验证码！");
        }
    }
}
