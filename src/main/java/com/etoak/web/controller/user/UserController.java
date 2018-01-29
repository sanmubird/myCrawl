package com.etoak.web.controller.user;

import com.aliyuncs.exceptions.ClientException;
import com.blade.ioc.annotation.Inject;
import com.blade.kit.UUID;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.http.Session;
import com.etoak.common.aliyunsms.AliyunSMSService;
import com.etoak.common.aliyunsms.AliyunSMSServiceParam;
import com.etoak.common.commonUtil.DateUtil;
import com.etoak.common.commonUtil.VerifyCodeUtil;
import com.etoak.common.entity.user.User;
import com.etoak.web.service.userservice.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Path("user")
public class UserController {

    @Inject
    private UserService userService ;

    @GetRoute("sign_in")
    public String signin() { //到达登录页面
        return "/user/signin.html";
    }

    @GetRoute("sign_up")
    public String signup() { //到达注册页面
        return "/user/signup.html";
    }

    @GetRoute("updateinfo")
    public String updateInfo() { //到达注册页面
        return "/user/updateinfo.html";
    }


    @GetRoute("loginout")
    public String loginout(Request request){
        Session session = request.session();
        String  userid   = session.attribute("userid");
        String  username = session.attribute("username");
        log.info("退出登陆：{}", userid );
        if (null != userid ) {
            session.remove("userid");
            session.remove("username");
            session.remove("userphone");
            session.remove("useremail");
            session.remove("password");
        }
        return "/index.html";
    }

    @GetRoute("usercenter")
    public  String gotoMycenter(Request request){
        Session session = request.session();
        String  userid   = session.attribute("userid");
        if (null != userid ) {
            return "/user/usercenter.html";
        }else{
            return "/user/signin.html";
        }
    }


    @GetRoute("verifyAccount")
    @JSON
    public Map<String,String> verifyAccount(@Param String account) { //验证账户是否存在
        Map<String,String> verifyResultMap = new HashMap<>();
        if(account == null || "".equals(account) ){
            verifyResultMap.put("result","null");
        }

        User user = new User();
        int i = (int) user.where("phone",account).or("email",account).count();
        if(i == 0 ){
            verifyResultMap.put("result","ok");
        }else{
            verifyResultMap.put("result","no");
        }
        return verifyResultMap ;
    }

    @PostRoute("register")
    @JSON
    public Map<String,String> register(User user){
        Map<String,String> registerResultMap = new HashMap<>();
        user.setUserid(UUID.UU32());
        user.setRegisterdate(DateUtil.getDate());
        user.save();
        userService.senRegisterEmail(user.getUsername(),user.getEmail());
        log.info("注册成功：{}", user.getUserid());
        registerResultMap.put("result","ok");
        return registerResultMap ;
    }


    @PostRoute("update")
    @JSON
    public Map<String,String> update(User user){
        Map<String,String> registerResultMap = new HashMap<>();
        String userid = user.getUserid();
        String username = user.getUsername();
        String email = user.getEmail();
        user.update(userid);
        userService.senUpdateInfoEmail( username , email );
        log.info("更新信息：{}", userid);
        registerResultMap.put("result","ok");
        return registerResultMap ;
    }

    @GetRoute("verifyCode")
    public void getVerifyCode(Response response ,@Param String phone )  { //获取手机短信验证码
        String verifycode = VerifyCodeUtil.getVerifyCode();
        Map<String, String> map = new HashMap<String, String>();
        map.put("SMSTYPE","verify");
        map.put("PHONENUMBER", phone);
        map.put("CODE", verifycode );
        AliyunSMSServiceParam sp = new AliyunSMSServiceParam(map);
        try {
            AliyunSMSService.sendSms(sp);
        } catch (ClientException e) {
            log.error("发送手机验证码失败:", e);
        }
        response.cookie("verifycode",verifycode,6*60);
    }

    @GetRoute("login")
    @JSON
    public Map<String,String> login(Request request ,@Param String account , @Param String password) { //验证账户是否存在
        Map<String,String> loginResultMap = new HashMap<>();
        User user = new User();
        int i = (int) user.where("phone",account).and("password",password).count();
        if(i == 1 ){
            loginResultMap.put("result","ok");
        }else{
            int j = (int) user.where("email",account ).and("password",password).count();
            if( j == 1){
                loginResultMap.put("result","ok");
            }else{
                loginResultMap.put("result","no");
            }
        }
        if(loginResultMap.get("result").equals("ok")){
            User user1 = new User();
            User user2 = user1.where("phone",account).or("email",account).find();
            log.info("登录成功：{}", user2.getUserid());
            request.session().attribute("userid",user2.getUserid());
            request.session().attribute("username",user2.getUsername());
            request.session().attribute("password",user2.getPassword());
            request.session().attribute("userphone",user2.getPhone());
            request.session().attribute("useremail",user2.getEmail());
        }
        return loginResultMap ;
    }


    @GetRoute("sessionuserinfo")
    @JSON
    public Map<String,String> getSessionuserinfo(Request request) { //验证账户是否存在
        Map<String,String> sessionuserinfotMap = new HashMap<>();
        Session session = request.session();
        String  userid   = session.attribute("userid");
        if (null == userid ) {
            sessionuserinfotMap.put("isnull","true");
            return sessionuserinfotMap ;
        }
        sessionuserinfotMap.put("isnull","false");
        sessionuserinfotMap.put("userid",session.attribute("userid"));
        sessionuserinfotMap.put("username",session.attribute("username"));
        sessionuserinfotMap.put("userphone",session.attribute("userphone"));
        sessionuserinfotMap.put("useremail",session.attribute("useremail"));
        sessionuserinfotMap.put("password",session.attribute("password"));
        return sessionuserinfotMap ;
    }


}
