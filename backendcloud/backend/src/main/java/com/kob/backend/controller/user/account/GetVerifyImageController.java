package com.kob.backend.controller.user.account;

import com.kob.backend.utils.VerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GetVerifyImageController {

    @Autowired
    VerifyUtil verifyUtil;

    // 生成验证码图片
    @GetMapping("/api/user/account/getVerifyImage/")
    @ResponseBody
    public Map<String, String> getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> resp = new HashMap<>();
        try {
            response.setContentType("image/png");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expire", "0");
            response.setHeader("Pragma", "no-cache");
            // 直接返回图片
//            verifyUtil.getRandomCodeImage(request, response);

            // 返回base64
            String base64String = verifyUtil.getRandomCodeBase64(request, response);
            resp.put("url", "data:image/png;base64," + base64String);
            resp.put("error_message", "success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }
}
