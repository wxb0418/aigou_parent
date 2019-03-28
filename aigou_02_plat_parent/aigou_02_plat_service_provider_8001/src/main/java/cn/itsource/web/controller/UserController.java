package cn.itsource.web.controller;

import cn.itsource.domain.User;
import cn.itsource.json.AjaxResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AjaxResult login(@RequestBody User user){
        if("admin".equals(user.getUsername())&&"admin".equals(user.getPassword())){
            return AjaxResult.me().setSuccess(true).setMsg("操作成功！");
        }else {
            return AjaxResult.me().setSuccess(false).setMsg("操作失败");
        }
    }

}
