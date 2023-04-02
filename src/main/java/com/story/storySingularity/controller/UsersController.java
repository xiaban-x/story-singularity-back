package com.story.storySingularity.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.story.storySingularity.model.VerifyCode;
import com.story.storySingularity.model.dto.UsersLoginDto;
import com.story.storySingularity.model.dto.UsersRegisterDto;
import com.story.storySingularity.model.dto.UsersSmsLoginDto;
import com.story.storySingularity.model.po.Users;
import com.story.storySingularity.service.UsersService;
import com.story.storySingularity.service.VerifyCodeGen;
import com.story.storySingularity.service.impl.VerifyCodeGenImpl;
import com.story.storySingularity.util.RestResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiaban
 */
@Slf4j
@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    //手机号登录
    @PostMapping("/sms_login")
    public RestResponse<Users> smsLogin(@RequestBody UsersLoginDto userLoginDto) {
        if (userLoginDto == null) {
            return RestResponse.validfail("操作失败");
        }
        Users user = new Users();
        if (usersService.selectUserByPhoneNumber(user) == null) {
            user.setPhone(userLoginDto.getPhone());
            user.setUsername("用户" + userLoginDto.getPhone().substring(userLoginDto.getPhone().length() - 4));
        }
        return RestResponse.success(user);
    }

    //用户注册
    @PostMapping("/register")//用@RequestMapping，则什么请求类型都可以
    //restful接口风格，可以用不同的请求方式实现不同的效果
    //使用@PathVariable注解，让方法参数的值对应绑到一个URL模板变量上
    public RestResponse<Users> registerUser(@RequestBody UsersRegisterDto userRegisterDto) {

        if (userRegisterDto == null) {
            return RestResponse.validfail("注册失败");
        }
        if (userRegisterDto.getUsername() == null || userRegisterDto.getPassword() == null || userRegisterDto.getPhone() == null || !userRegisterDto.getIsPassCode()) {
            return RestResponse.validfail("注册失败");
        }
        Users user = new Users();
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(userRegisterDto.getPassword());
        user.setPhone(userRegisterDto.getPhone());
        if (!(usersService.selectUser(user) == null)) {
            return RestResponse.validfail("此账号与密码已重复");
        }
        usersService.saveUser(user);
        return RestResponse.success(user);
    }

    @PostMapping("/code")
    public RestResponse<String> code(@RequestParam("phone") String phone) {
        return RestResponse.success("5w1t");
    }

    //用户登录
    @PostMapping("/login")
    public RestResponse<Users> login(@RequestBody UsersLoginDto userLoginDto) {
        if (userLoginDto == null) {
            return RestResponse.validfail("登录失败");
        }
        Users user = usersService.login(userLoginDto.getPhone());
        return RestResponse.success(user);
    }

    //保存用户资料
    @PostMapping("/save")
    public RestResponse<Users> saveUser(@RequestBody Users user) {
        if (user == null) {
            return RestResponse.validfail("保存失败");
        }
        usersService.update(user, null);
        return RestResponse.success(user);
    }

    @GetMapping("/getUserById/{id}")
    public RestResponse<Users> getById(@PathVariable("id") Integer id) {
        Users user = usersService.getById(id);
        return RestResponse.success(user);
    }

    @GetMapping("/getUserByName/{name}")
    public RestResponse<Users> getByName(@PathVariable("name") String name) {
        LambdaQueryWrapper<Users> usersLambdaQueryWrapper = new LambdaQueryWrapper<>();
        usersLambdaQueryWrapper.eq(Users::getName, name);
        Users user = usersService.getOne(usersLambdaQueryWrapper);
        return RestResponse.success(user);
    }

    @ApiOperation(value = "验证码")
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        VerifyCodeGen iVerifyCodeGen = new VerifyCodeGenImpl();
        try {
            //设置长宽
            VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
            String code = verifyCode.getCode();
            //将VerifyCode绑定session
            request.getSession().setAttribute("VerifyCode", code);
            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
