package com.example.demo0814.Controller;

import com.example.demo0814.Pojo.Dianyingxinxi;
import com.example.demo0814.Pojo.LeiXingTongJi;
import com.example.demo0814.Pojo.Result;
import com.example.demo0814.Pojo.Yonghu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    // init JdbcTemplate
    @Autowired // init object
    private JdbcTemplate jdbc;

    // one request, one method
    // Login
    // http://localhost:8080/doLogin
    @PostMapping("/doLogin")
    public Result doLogin(@RequestBody Yonghu yonghu) {

        // 1. get data from web
        System.out.println(yonghu);

        // init Result
        Result result = new Result();

        // 2. get data from database
        // first init SQL sentence
        Yonghu userinfo = null;
        try {
            userinfo = jdbc.queryForObject("select * from yonghu where zhanghao = ? and mima = ?",
                    new BeanPropertyRowMapper<>(Yonghu.class), yonghu.getZhanghao(), yonghu.getMima());

            result.setCode(200);
            result.setResult(userinfo);
            return result;
        // 3. verify
        // if get, return yonghu, else null
        } catch (DataAccessException e) {
            // print exception
            e.printStackTrace();
            result.setCode(201);
            result.setResult("出现异常 " + e.getMessage());
            return result;
        }
    }


    // Register
    // http://localhost:8080/doRegister
    @PostMapping("/doRegister")
    public Result doRegister(@RequestBody Yonghu yonghu) {

        // init Result
        Result result = new Result();

        // 1. get data from web
        List<Yonghu> userinfo = jdbc.query("select * from yonghu where zhanghao = ? and mima = ?",
                new BeanPropertyRowMapper<>(Yonghu.class), yonghu.getZhanghao(), yonghu.getMima());
        System.out.println(userinfo);

        // 2. insert
        // if exists
        if (!userinfo.isEmpty())
        {
            result.setCode(202);
            result.setResult("用户已存在");
            return result;
        }

        else {
            try {
                jdbc.update("insert into yonghu(xingming, zhanghao, mima, age, leibie) VALUES (?, ?, ?, ?, ?)",
                        yonghu.getXingming(), yonghu.getZhanghao(), yonghu.getMima(), yonghu.getAge(), yonghu.getLeibie());

                result.setCode(200);
                result.setResult("注册成功！");
                return result;
            } catch (DataAccessException e) {
                e.printStackTrace();
                result.setCode(201);
                result.setResult("注册失败！");
                return result;
            }
        }
    }


    // show infomation
    // http://localhost:8080/dianyings
    @GetMapping("/dianyings")
    public Result dianyingsByLeixing(Integer leixingID) {
        System.out.println("电影类型：" + leixingID);

        Result result = new Result();

        // if leixingID is valid, select ID, else select all
        try {
            if (leixingID != null) {
                result.setCode(201);
                result.setResult(
                        jdbc.query("select * from dianyingxinxi where leixingid=?",
                        new BeanPropertyRowMapper<>(Dianyingxinxi.class), leixingID)
                );
                return result;
            }
            else {
                result.setCode(202);
                result.setResult(
                        jdbc.query("select * from dianyingxinxi", new BeanPropertyRowMapper<>(Dianyingxinxi.class))
                );
                return result;
            }
        } catch (DataAccessException e) {
            result.setCode(203);
            result.setResult("信息出错");
            return result;
        }
    }

    // http://localhost:8080/updAccount
    @PostMapping("/updAccount")
    public Result updAccount(@RequestBody Yonghu yonghu) {
        System.out.println(yonghu);
        Result result = new Result();

        try {
            jdbc.update("update yonghu set mima = ? where zhanghao = ?",
                    yonghu.getMima(), yonghu.getZhanghao());

            result.setCode(200);
            result.setResult(jdbc.queryForObject("select * from yonghu where zhanghao = ? and mima = ?",
                    new BeanPropertyRowMapper<>(Yonghu.class), yonghu.getZhanghao(), yonghu.getMima()));

            return result;

        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(201);
            result.setResult("出错");
            return result;
        }
    }

    @GetMapping("/leixingtongji")
    public Result tongjileixing(){
        Result r = new Result();
        try {
            List<LeiXingTongJi> tjs  = jdbc.query("select lx.id,lx.leixingming name,count(dy.id)  value  from dianyingxinxi dy inner join leixing lx  on dy.leixingid = lx.id  GROUP BY lx.id,lx.leixingming", new BeanPropertyRowMapper<>(LeiXingTongJi.class));
            r.setResult(tjs);
            r.setCode(200);
        } catch (DataAccessException e) {
            r.setResult("失败");
            r.setCode(201);
        }
        return r;
    }
    //返回值 以json 格式描绘  刚好符合饼图要求

}
