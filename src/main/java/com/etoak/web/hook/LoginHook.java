package com.etoak.web.hook;

import com.blade.ioc.annotation.Bean;
import com.blade.mvc.hook.Signature;
import com.blade.mvc.hook.WebHook;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.http.Session;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Bean
public class LoginHook implements WebHook {

    @Override
    public boolean before(Signature signature) {

        Request request  = signature.request();
        Response response = signature.response();
        Session session = request.session() ;

        String uri = request.uri();
        String ip  = request.address();
        log.info("UserAgent: {}", request.userAgent());
        log.info("用户访问地址: {}, 来路地址: {}", uri, ip);

        // 禁止该ip访问
//        String ip  = request.address();
//        if (TaleConst.BLOCK_IPS.contains(ip)) {
//            response.text("You have been banned, brother");
//            return false;
//        }
//
//        log.info("UserAgent: {}", request.userAgent());
//        log.info("用户访问地址: {}, 来路地址: {}", uri, ip);


//       没有进行站点初始化的直接跳转初始化首页
//        if (!TaleConst.INSTALL && !uri.startsWith("/install")) {
//            response.redirect("/install");
//            return false;
//        }


        //进入后台登陆
//        if (uri.startsWith("/admin") && !uri.startsWith("/admin/login")) {
//            if (null == user) {
//                response.redirect("/admin/login");
//                return false;
//            }
//            request.attribute("PLUGIN_MENUS", TaleConst.PLUGIN_MENUS);
//        }


        //放行资源类;
        if (uri.startsWith("/static")) {
            return true;
        }


        //判断用户是否登陆;  但是要放行 以/index 和 /user 开头的. 不然 无法到达首页 , 也无法登陆和注册;
        if( !uri.startsWith("/index") && !uri.startsWith("/user") && !uri.equals("/")){
            String  userid   = session.attribute("userid");
            if (null == userid ) {
                response.redirect("/user/sign_in");
                return false ;
            }
        }
        return true;
    }

}
