package com.blogmanagement.blogmanagement.controller;

import com.blogmanagement.blogmanagement.Pojo.*;
import com.blogmanagement.blogmanagement.Pojo.Number;
import com.blogmanagement.blogmanagement.util.HttpUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;


@RestController
@CrossOrigin(origins = "*")
public class Usercontroller {

    @Autowired
    private JdbcTemplate jdbc;

    //用户登录
    @PostMapping("/dologin")
    @ApiOperation(value = "用户登录", httpMethod="POST", notes = "至少需要发送:姓名,密码")
    public Result dologin(@RequestBody Userinfo logininfo){
        Result result=new Result();
        Userinfo userinfo=null;
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        try {
            userinfo = jdbc.queryForObject("select * from userinfo where name = ? and password = ? and valid =1", new BeanPropertyRowMapper<>(Userinfo.class), logininfo.getName(), logininfo.getPassword());
            jdbc.update("insert into loginlog(content) VALUES (?);",userinfo.getRole()+" "+userinfo.getUserid()+":"+userinfo.getName()+" 已登录 登陆时间:"+formattedTime);
            result.setCode(200);
            result.setResult(userinfo);
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(201);
            result.setResult("用户名或密码错误");
            return result;
        }
    }

    //用户注册
    @RequestMapping ("/doregister")
    @ApiOperation(value = "用户注册", httpMethod="POST", notes = "需要发送:姓名,密码,省份,城市,性别,角色,手机号")
    public Result doregister(@RequestBody Userinfo registerinfo){
        Result result=new Result();
        result.setCode(203);
        result.setResult("注册失败");
        if(registerinfo.getName()==null||registerinfo.getName().length()>20) return result;
        if(registerinfo.getPassword()==null) return result;
        if(registerinfo.getPhonenum().length()!=11) return result;
        if(registerinfo.getRole()==null) return result;
        if(registerinfo.getPassword().length()<=5||registerinfo.getPassword().length()>=20) return result;
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        try {
            jdbc.queryForObject("select * from userinfo where name = ? ", new BeanPropertyRowMapper<>(Userinfo.class), registerinfo.getName());
            result.setCode(202);
            result.setResult("该用户名已经被占用");
            return result;
        } catch (DataAccessException e) {
            try {
                jdbc.update("insert into userinfo(name,password,province,city,gender,`create`,role,phonenum) VALUES (?,?,?,?,?,?,?,?);",registerinfo.getName(),registerinfo.getPassword(),registerinfo.getProvince(),registerinfo.getCity(),registerinfo.getGender(),currentTime,registerinfo.getRole(),registerinfo.getPhonenum());
                Userinfo userinfo=null;
                userinfo = jdbc.queryForObject("select * from userinfo where name = ?", new BeanPropertyRowMapper<>(Userinfo.class), registerinfo.getName());
                jdbc.update("insert into editlog(content) VALUES (?);",userinfo.getRole()+" "+userinfo.getUserid()+":"+userinfo.getName()+" 注册成功 注册时间:"+formattedTime);
                result.setCode(200);
                result.setResult("注册成功");
                return result;
            } catch (DataAccessException ex) {
                ex.printStackTrace();
                return result;
            }
        }
    }

    //用户注销
    @RequestMapping("/dologout")
    @ApiOperation(value = "用户注销", httpMethod="POST", notes = "至少需要发送:id")
    public Result dologout(@RequestBody Userinfo logoutinfo){
        Result result=new Result();
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        try {
            Userinfo userinfo=jdbc.queryForObject("select * from userinfo where userid = ? and valid =1", new BeanPropertyRowMapper<>(Userinfo.class), logoutinfo.getId());
            jdbc.update("UPDATE userinfo SET valid = 0 WHERE userid =?",logoutinfo.getId());
            jdbc.update("insert into editlog(content) VALUES (?);",userinfo.getRole()+" "+userinfo.getUserid()+":"+userinfo.getName()+" 已注销 注销时间:"+formattedTime);
            result.setCode(200);
            result.setResult("注销成功");
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(204);
            result.setResult("注销失败");
            return result;
        }
    }

    //用户恢复
    @RequestMapping("/dorecovery")
    @ApiOperation(value = "用户恢复", httpMethod="POST", notes = "至少需要发送:id")
    public Result dorecovery(@RequestBody Userinfo recoveryinfo){
        Result result=new Result();
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        try {
            Userinfo userinfo=jdbc.queryForObject("select * from userinfo where userid = ?", new BeanPropertyRowMapper<>(Userinfo.class), recoveryinfo.getId());
            jdbc.update("UPDATE userinfo SET valid = 1 WHERE userid =?",recoveryinfo.getId());
            jdbc.update("insert into editlog(content) VALUES (?);",userinfo.getRole()+" "+userinfo.getUserid()+":"+userinfo.getName()+" 已注销 注销时间:"+formattedTime);
            result.setCode(200);
            result.setResult("恢复成功");
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(205);
            result.setResult("恢复失败");
            return result;
        }
    }

    //找回密码
    @RequestMapping("/retrievepassword")
    @ApiOperation(value = "找回密码", httpMethod="POST", notes = "需要发送:姓名,手机号")
    public Result retrievepassword(@RequestBody Userinfo userinfo){
        Result result=new Result();
        try {
            String password=jdbc.queryForObject("select password from userinfo where name=?and phonenum=?",String.class,userinfo.getName(),userinfo.getPhonenum());
            result.setCode(200);
            result.setResult(password);
            return result;
        }catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(206);
            result.setResult("找回");
            return result;
        }
    }

    //用户编辑资料
    @RequestMapping("/Editdata")
    @ApiOperation(value = "用户编辑资料", httpMethod="POST", notes = "需要发送:用户id,加上修改的属性(昵称,头像，性别，简介，省份，城市，密码)")
    public Result Editdata(@RequestBody Userinfo userinfo){
        Result result=new Result();
        String sql=new String();
        System.out.println(userinfo);
        try {
            sql = "UPDATE userinfo SET `name` = ? WHERE userid = ?";
            if (userinfo.getName() != null) {
                jdbc.update(sql, userinfo.getName(), userinfo.getUserid());
            }
//            sql = "UPDATE userinfo SET avatart = ? WHERE userid = ?";
//            if (userinfo.getAvatart() != null) {
//                jdbc.update(sql, userinfo.getAvatart(), userinfo.getUserid());
//            }
            sql = "UPDATE userinfo SET gender = ? WHERE userid = ?";
            if (userinfo.getGender() != null) {
                jdbc.update(sql, userinfo.getGender(), userinfo.getUserid());
            }
            sql = "UPDATE userinfo SET  signature= ? WHERE userid = ?";
            if (userinfo.getSignature() != null) {
                jdbc.update(sql, userinfo.getSignature(), userinfo.getUserid());
            }
            sql = "UPDATE userinfo SET  province= ? WHERE userid = ?";
            if (userinfo.getProvince() != null) {
                jdbc.update(sql, userinfo.getProvince(), userinfo.getUserid());
            }
            sql = "UPDATE userinfo SET city= ? WHERE userid = ?";
            if (userinfo.getCity() != null) {
                jdbc.update(sql, userinfo.getCity(), userinfo.getUserid());
            }
            sql = "UPDATE userinfo SET password= ? WHERE userid = ?";
            if (userinfo.getPassword()!=null&&userinfo.getPassword() .length()>5&&userinfo.getPassword() .length()<20) {
                jdbc.update(sql, userinfo.getPassword(), userinfo.getUserid());
            }
            Userinfo userinfonew=null;
            userinfonew = jdbc.queryForObject("select * from userinfo where userid = ?", new BeanPropertyRowMapper<>(Userinfo.class), userinfo.getUserid());
            System.out.println(userinfonew.getAvatart());
            result.setCode(200);
            result.setResult(userinfonew);
            return result;
        }catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(207);
            result.setResult("修改失败");
            return result;
        }
    }

    //写评论
    @RequestMapping("/docomment")
    @ApiOperation(value = "写评论", httpMethod="POST", notes = "需要发送:文章id,评论者id(author),评论内容,先导id")
    public Result docmment(@RequestBody Comment comment){
        Result result=new Result();
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        try {
            jdbc.update("insert into comment(postid, author,`create`,content,preid) VALUES (?,?,?,?,?);",comment.getPostid(),comment.getAuthor(),currentTime,comment.getContent(),comment.getPreid());
            Userinfo userinfo=null;
            userinfo = jdbc.queryForObject("select * from userinfo where userid = ?", new BeanPropertyRowMapper<>(Userinfo.class), comment.getAuthor());
            jdbc.update("insert into editlog(content) VALUES (?);",userinfo.getRole()+" "+userinfo.getUserid()+":"+userinfo.getName()+" 发表了一条评论:"+comment.getContent()+" 发表时间:"+formattedTime);
            jdbc.update("update post set commentnum=commentnum+1 where postid=?",comment.getPostid());
            result.setCode(200);
            result.setResult("评论成功");
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(208);
            result.setResult("评论失败");
            return result;
        }
    }

    //删评论
    @RequestMapping("/DEcomment")
    @ApiOperation(value = "删除评论", httpMethod="POST", notes = "至少需要发送:文章id,评论id")
    public Result DEcomment(@RequestBody Comment comment){
        Result result=new Result();
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        try {
            jdbc.update("UPDATE comment SET valid = 0 WHERE commentid =?",comment.getCommentid());
            Userinfo userinfo=null;
            userinfo = jdbc.queryForObject("select * from userinfo where userid = ?", new BeanPropertyRowMapper<>(Userinfo.class), comment.getAuthor());
            jdbc.update("insert into editlog(content) VALUES (?);",userinfo.getRole()+" "+userinfo.getUserid()+":"+userinfo.getName()+" 的评论:"+comment.getContent()+" 已被删除 删除时间:"+formattedTime);
            jdbc.update("update post set commentnum=commentnum-1 where postid=?",comment.getPostid());

            result.setCode(200);
            result.setResult("删除成功");
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(209);
            result.setResult("删除失败");
            return result;
        }
    }

    //恢复评论
    @RequestMapping("/REcomment")
    @ApiOperation(value = "恢复评论", httpMethod="POST", notes = "至少需要发送:评论id")
    public Result REcomment(@RequestBody Comment comment){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        Result result=new Result();
        try {
            jdbc.update("UPDATE comment SET valid = 1 WHERE commentid =?",comment.getCommentid());
            Userinfo userinfo=null;
            userinfo = jdbc.queryForObject("select * from userinfo where userid = ?", new BeanPropertyRowMapper<>(Userinfo.class), comment.getAuthor());
            jdbc.update("insert into editlog(content) VALUES (?);",userinfo.getRole()+" "+userinfo.getUserid()+":"+userinfo.getName()+" 的评论:"+comment.getContent()+" 已被恢复 恢复时间:"+formattedTime);
            result.setCode(200);
            result.setResult("恢复成功");
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(210);
            result.setResult("恢复失败");
            return result;
        }
    }

    //文章点赞
    @RequestMapping("/thumbup")
    @ApiOperation(value = "文章点赞取消踩", httpMethod="POST", notes = "至少需要发送:文章id")
    public Result thumbup(@RequestBody Post post){
        Result result=new Result();
        try {
            jdbc.update("update post set thumbupnum=thumbupnum+1 where postid=?",post.getPostid());
            result.setCode(200);
            result.setResult("点赞成功");
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(211);
            result.setResult("点赞失败");
            return result;
        }
    }

    //文章取消点赞
    @RequestMapping("/DEthumbup")
    @ApiOperation(value = "文章取消赞点踩", httpMethod="POST", notes = "至少需要发送:文章id")
    public Result DEthumbup(@RequestBody Post post){
        Result result=new Result();
        try {
            jdbc.update("update post set thumbupnum=thumbupnum-1 where postid=?",post.getPostid());
            result.setCode(200);
            result.setResult("取消成功");
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(212);
            result.setResult("取消失败");
            return result;
        }
    }

    //发表文章
    @RequestMapping("/publish")
    @ApiOperation(value = "发表文章", httpMethod="POST", notes = "需要发送:文章题目,文章类型,文章内容,是否允许评论,作者id")
    public Result publish(@RequestBody Post post){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        Result result=new Result();
        try {
            jdbc.update("insert into post(tags, title,categories,content,`create`,allowcomment,userid) VALUES (?,?,?,?,?,?,?);",post.getTags(),post.getTitle(),post.getCategories(),post.getContent(),currentTime,post.getAllowcomment(),post.getUserid());
            Userinfo userinfo=null;
            userinfo = jdbc.queryForObject("select * from userinfo where userid = ?", new BeanPropertyRowMapper<>(Userinfo.class), post.getUserid());
            jdbc.update("insert into editlog(content) VALUES (?);",userinfo.getRole()+" "+userinfo.getUserid()+":"+userinfo.getName()+" 发布了一篇博客 "+post.getTitle()+ " 发布时间:"+formattedTime);
            result.setCode(200);
            result.setResult("发表成功");
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(213);
            result.setResult("发表失败");
            return result;
        }
    }

    //删除文章
    @RequestMapping("/DEpublish")
    @ApiOperation(value = "删除文章", httpMethod="POST", notes = "至少需要发送:文章id")
    public Result DEpublish(@RequestBody Post post){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        Result result=new Result();
        try {
            jdbc.update("UPDATE post SET valid = 0 WHERE postid =?",post.getPostid());
            Userinfo userinfo=null;
            userinfo = jdbc.queryForObject("select * from userinfo,post where post.postid=? and userinfo.userid=post.userid", new BeanPropertyRowMapper<>(Userinfo.class), post.getPostid());
            jdbc.update("insert into editlog(content) VALUES (?);",userinfo.getRole()+" "+userinfo.getUserid()+":"+userinfo.getName()+" 的博客 "+post.getTitle()+ "已被删除 删除时间:"+formattedTime);
            result.setCode(200);
            result.setResult("删除成功");
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(214);
            result.setResult("删除失败");
            return result;
        }
    }

    //恢复文章
    @RequestMapping("/REpublish")
    @ApiOperation(value = "恢复文章", httpMethod="POST", notes = "至少需要发送:文章id")
    public Result REpublish(@RequestBody Post post){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        Result result=new Result();
        try {
            jdbc.update("UPDATE post SET valid = 1 WHERE postid =?",post.getPostid());
            Userinfo userinfo=null;
            userinfo = jdbc.queryForObject("select * from userinfo,post where post.postid=? and userinfo.userid=post.userid", new BeanPropertyRowMapper<>(Userinfo.class), post.getPostid());
            jdbc.update("insert into editlog(content) VALUES (?);",userinfo.getRole()+" "+userinfo.getUserid()+":"+userinfo.getName()+" 的博客 "+post.getTitle()+ "已被恢复 恢复时间:"+formattedTime);
            result.setCode(200);
            result.setResult("恢复成功");
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(215);
            result.setResult("恢复失败");
            return result;
        }
    }

    //个人主页
    @RequestMapping("/home")
    @ApiOperation(value = "自己的文章", httpMethod="POST", notes = "至少需要发送:用户id")
    public Result home(@RequestBody Userinfo userinfo){
        Result result=new Result();
        List<Post> posts= null;
        try {
            posts = jdbc.query("select postid, tags,title,categories,userid,readnum,thumbupnum,commentnum,thumbnall from post where userid=?  order by `create` DESC",new BeanPropertyRowMapper<>(Post.class),userinfo.getUserid());
            result.setCode(200);
            result.setResult(posts);
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(216);
            result.setResult("进入失败");
            return result;
        }

    }

    //首页
    @RequestMapping("/index")
    @ApiOperation(value = "首页", httpMethod="GET", notes = "可以发送:类别")
    public Result index(String categories){
        Result result=new Result();
        List<Post> posts= null;
        try {
            if(categories.equals("全部")) posts = jdbc.query("select postid, tags,title,categories,userid,readnum,thumbupnum,commentnum,thumbnall from post where valid =1 order by `create` DESC ",new BeanPropertyRowMapper<>(Post.class));
            else posts = jdbc.query("select postid, tags,title,categories,userid,readnum,thumbupnum,commentnum,thumbnall from post where categories =? and valid =1 order by `create` DESC ",new BeanPropertyRowMapper<>(Post.class),categories);
            result.setCode(200);
            result.setResult(posts);
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(217);
            result.setResult("进入失败");
            return result;
        }
    }

    //文章内容
    @RequestMapping("/post")
    @ApiOperation(value = "文章内容", httpMethod="POST", notes = "至少需要发送:文章id")
    public Result post(@RequestBody Post post){
        Result result=new Result();
        Post postall=null;
        try {
            postall=jdbc.queryForObject("select * from post where postid =?",new BeanPropertyRowMapper<>(Post.class),post.getPostid());
            jdbc.update("update post set readnum=readnum+1 where postid=?",post.getPostid());
            result.setCode(200);
            result.setResult(postall);
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(218);
            result.setResult("进入失败");
            return result;
        }
    }

    //评论消息
    @RequestMapping("/CommentMessage")
    @ApiOperation(value = "获取用户被评论消息", httpMethod="POST",notes="至少需要:用户id")
    public Result CommentMessage(@RequestBody Userinfo userinfo){
        Result result=new Result();
        try {
            List<CommentMessage> list=jdbc.query("select name, commentDate, content, title as blogName ,avatart as avatarUrl, postid from (select post.userid, post.title, comment.content, userinfo.name, comment.`create` as commentDate ,avatart, comment.postid from post,comment,userinfo where post.postid=comment.postid and userinfo.userid=comment.author) as t where t.userid=? order by commentDate DESC",new BeanPropertyRowMapper<>(CommentMessage.class),userinfo.getUserid());
            result.setCode(200);
            result.setResult(list);
            return result;
        }catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(219);
            result.setResult("发生错误");
            return result;
        }
    }

    //收藏
    @RequestMapping("/docollect")
    @ApiOperation(value = "收藏",httpMethod = "POST",notes = "需要发送:文章id,用户id")
    public Result docollect(@RequestBody Collect collect) {
        Result result = new Result();
        LocalDateTime currentTime = LocalDateTime.now();
        try {
            if(!(jdbc.queryForObject("select count(*) from collect where collect.userid=? and collect.postid=?",Long.class,collect.getUserid(),collect.getPostid()).equals(1))){
                jdbc.update("insert into collect(postid,userid,`create`) VALUES (?,?,?);", collect.getPostid(), collect.getUserid(), currentTime);
                jdbc.update("update post set collectnum=collectnum+1 where postid=?",collect.getPostid());
            }
            result.setCode(200);
            result.setResult("收藏成功");
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(220);
            result.setResult("收藏错误");
            return result;
        }
    }

    //取消收藏
    @RequestMapping("/DEcollect")
    @ApiOperation(value = "取消收藏",httpMethod = "POST",notes = "需要发送:文章id,用户id")
    public Result DEcollect(@RequestBody Collect collect) {
        Result result = new Result();
        LocalDateTime currentTime = LocalDateTime.now();
        try {
            jdbc.update("delete from collect where postid=? and userid=?;", collect.getPostid(), collect.getUserid());
            jdbc.update("update post set collectnum=collectnum-1 where postid=?",collect.getPostid());
            result.setCode(200);
            result.setResult("取消成功");
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(221);
            result.setResult("取消错误");
            return result;
        }
    }

    //收藏消息
    @RequestMapping("/CollectMessage")
    @ApiOperation(value = "获取用户被收藏消息", httpMethod="POST",notes="至少需要:用户id")
    public Result CollectMessage(@RequestBody Userinfo userinfo){
        Result result=new Result();
        try {
            List<CollectMessage> list=jdbc.query("select distinct title as blogName,name, commentDate,avatart as avatarUrl from  (select post.userid ,post.title,userinfo.name,collect.`create` as commentDate ,avatart from post,collect,userinfo where post.postid=collect.postid and userinfo.userid=collect.userid) as t where t.userid=? order by `commentDate` DESC",new BeanPropertyRowMapper<>(CollectMessage.class),userinfo.getUserid());
            result.setCode(200);
            result.setResult(list);
            return result;
        }catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(222);
            result.setResult("发生错误");
            return result;
        }
    }

    //收藏内容展示
    @RequestMapping("/GetCollect")
    @ApiOperation(value = "收藏内容展示", httpMethod="POST",notes="至少需要:用户id")
    public Result GetCollect(@RequestBody Userinfo userinfo){
        Result result=new Result();
        try {
            List<Post> list=jdbc.query("select post.postid as postid,tags,title,categories,post.userid,readnum,thumbupnum,commentnum,thumbnall from post,collect where collect.userid=? and post.postid=collect.postid order by collect.`create` DESC",new BeanPropertyRowMapper<>(Post.class),userinfo.getUserid());
            result.setCode(200);
            result.setResult(list);
            return result;
        }catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(223);
            result.setResult("发生错误");
            return result;
        }
    }

    //码龄分布
    @GetMapping("/CreationAge")
    @ApiOperation(value = "获取码龄分布", httpMethod="GET")
    public Result CreationAge(){
        LocalDateTime nowtime = LocalDateTime.now();
        Result result=new Result();
        try {
            Long num1=jdbc.queryForObject("select count(*)  from userinfo where TIMESTAMPDIFF(YEAR ,`create`,?) between 0 and 9",Long.class,nowtime);
            jdbc.update("update creationage set value=? where name='0-9'",num1);
            Long num2=jdbc.queryForObject("select count(*)  from userinfo where TIMESTAMPDIFF(YEAR ,`create`,?) between 10 and 19",Long.class,nowtime);
            jdbc.update("update creationage set value=? where name='10-19'",num2);
            Long num3=jdbc.queryForObject("select count(*)  from userinfo where TIMESTAMPDIFF(YEAR ,`create`,?) between 20 and 29",Long.class,nowtime);
            jdbc.update("update creationage set value=? where name='20-29'",num3);
            Long num4=jdbc.queryForObject("select count(*)  from userinfo where TIMESTAMPDIFF(YEAR ,`create`,?) between 30 and 39",Long.class,nowtime);
            jdbc.update("update creationage set value=? where name='30-39'",num4);
            Long num5=jdbc.queryForObject("select count(*)  from userinfo where TIMESTAMPDIFF(YEAR ,`create`,?) between 40 and 49",Long.class,nowtime);
            jdbc.update("update creationage set value=? where name='40-49'",num5);
            List<Creationage> list=jdbc.query("select * from creationage",new BeanPropertyRowMapper<>(Creationage.class));
            result.setCode(200);
            result.setResult(list);
            System.out.println(result);
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(224);
            result.setResult("发生错误");
            return result;
        }
    }

    //近七天社区活跃度
    @GetMapping("/liveness")
    @ApiOperation(value = "获取最近七天的文章数据", httpMethod="GET")
    public Result liveness(){
        Result result=new Result();
        try {
            List<Liveness> list = jdbc.query("SELECT DATE_FORMAT(`create`, '%m-%d') AS `date`, COUNT(DATE(`create`)) AS `num`\n" +
                    "FROM post\n" +
                    "WHERE `create` >= CURDATE() - INTERVAL 7 DAY\n" +
                    "GROUP BY DATE(`create`)\n" +
                    "ORDER BY `date` ASC;\n", new BeanPropertyRowMapper<>(Liveness.class));
            result.setCode(200);
            result.setResult(list);
            System.out.println(result);
            return result;
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(225);
            result.setResult("发生错误");
            return result;
        }
    }

    //博客类别分析
    @GetMapping("/categories")
    @ApiOperation(value = "获取前六个类别，并把剩下的合并到其他", httpMethod="GET")
    public Result categories(){
        Result result=new Result();
        try {
            List<Categories> list = jdbc.query("SELECT categories as name, count as value\n" +
                    "FROM (\n" +
                    "         SELECT categories, count\n" +
                    "         FROM (SELECT categories, COUNT(*) AS count FROM post GROUP BY categories ORDER BY count DESC) AS t\n" +
                    "         ORDER BY count DESC\n" +
                    "         LIMIT 6\n" +
                    "     ) AS top_categories\n" +
                    "UNION\n" +
                    "SELECT '其他类别', SUM(count) AS 个数\n" +
                    "FROM (\n" +
                    "         SELECT count\n" +
                    "         FROM (SELECT categories, COUNT(*) AS count FROM post GROUP BY categories ORDER BY count DESC) AS t\n" +
                    "         ORDER BY count DESC\n" +
                    "         LIMIT 10000 OFFSET 6 \n" +
                    "     ) AS remaining_categories;\n", new BeanPropertyRowMapper<>(Categories.class));
            result.setCode(200);
            result.setResult(list);
            return result;
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(226);
            result.setResult("发生错误");
            return result;
        }
    }

    //获取各省份博客作者的数量
    @GetMapping("/getauthorbyprovince")
    @ApiOperation(value = "获取各省份博客作者的数量(修正值)", httpMethod="GET")
    public Result getauthorbyprovince() {

        Result result=new Result();

        try {
            List<Authorbyprovince>authors = jdbc.query("select province as name, count(*) * 100 as value from userinfo group by province",  new BeanPropertyRowMapper<>(Authorbyprovince.class));
            result.setCode(200);
            result.setResult(authors);
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(227);
            result.setResult("获得不了博客作者所在地");
            return result;
        }
    }

    //获取博客总数
    @GetMapping("/gettotalblogs")
    @ApiOperation(value = "获取博客总数", httpMethod = "GET")
    public Result gettotalblogs() {
        Result result=new Result();
        try {
            int totalBlogs = jdbc.queryForObject("SELECT COUNT(*) FROM post where valid=1", Integer.class);
            result.setResult(totalBlogs);
            result.setCode(200);
            return result;
        }catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(228);
            result.setResult("获取错误");
            return result;
        }
    }

    //获取用户总数
    @GetMapping("/gettotalusers")
    @ApiOperation(value = "获取用户总数", httpMethod = "GET")
    public Result gettotalusers() {
        Result result=new Result();
        try {
            int totalusers = jdbc.queryForObject("SELECT COUNT(*) FROM userinfo where valid=1", Integer.class);
            result.setResult(totalusers);
            result.setCode(200);
            return result;
        }catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(229);
            result.setResult("获取错误");
            return result;
        }
    }

    //发送最近几条发文
    @GetMapping("/GetPost")
    @ApiOperation(value = "发送最近几条发文", httpMethod = "GET")
    public Result GetPost(){
        Result result=new Result();
        try {
            List<GetPost> list=jdbc.query("select userinfo.name,post.title as article,DATE_FORMAT(post.create, '%m-%d') as time from userinfo,post where post.userid=userinfo.userid and post.valid=1 order by post.`create` DESC limit 5",new BeanPropertyRowMapper<>(GetPost.class));
            result.setResult(list);
            result.setCode(200);
            return result;
        }catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(230);
            result.setResult("获取错误");
            return result;
        }
    }

    //获取最近几条操作记录
    @GetMapping("/GeteditLog")
    @ApiOperation(value = "获取最近几条操作记录", httpMethod = "GET")
    public Result GeteditLog(){
        Result result=new Result();
        try {
            List<String> list=jdbc.queryForList("select content from editlog ",String.class);
            result.setResult(list);
            System.out.println(list);
            result.setCode(200);
            return result;
        }catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(231);
            result.setResult("获取错误");
            return result;
        }
    }

    //获取最近几条登录记录
    @GetMapping("/GetloginLog")
    @ApiOperation(value = "获取最近几条登录记录", httpMethod = "GET")
    public Result GetloginLog(){
        Result result=new Result();
        try {
            List<String> list=jdbc.queryForList("select content from loginlog ",String.class);
            result.setResult(list);
            result.setCode(200);
            return result;
        }catch (DataAccessException e) {
            e.printStackTrace();
            result.setCode(232);
            result.setResult("获取错误");
            return result;
        }
    }

    //获取类别
    @GetMapping("/GetCategories")
    @ApiOperation(value = "获取类别", httpMethod="GET")
    public Result GetCategories(){
        Result result=new Result();
        try {
            List<GetCategories> list = jdbc.query("select categoriesid as id,categories as name from categories",new BeanPropertyRowMapper<>(GetCategories.class));
            result.setCode(200);
            result.setResult(list);
            return result;
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(233);
            result.setResult("发生错误");
            return result;
        }
    }

    //获取用户信息
    @GetMapping("/GetUserinfo")
    @ApiOperation(value = "获取用户信息", httpMethod="GET")
    public Result GetUserinfo(){
        Result result=new Result();
        try {
            List<Userinfo> list = jdbc.query("select * from userinfo ", new BeanPropertyRowMapper<>(Userinfo.class));
            result.setCode(200);
            result.setResult(list);
            return result;
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(234);
            result.setResult("发生错误");
            return result;
        }
    }

    //数据库模糊查询
    @GetMapping("/search")
    @ApiOperation(value = "搜索模块",httpMethod = "GET",notes = "可以搜索用户名和文章内容")
    public Result search(String key){
        Result result=new Result();
        String keyword="%"+key+"%";
        System.out.println(keyword);
        try {
            List<Post> posts=jdbc.query("select * from post,userinfo where (content like ? or name like ? or title like ? or tags like ? or categories like ? )and post.userid=userinfo.userid order by post.`create` DESC", new BeanPropertyRowMapper<>(Post.class),keyword,keyword,keyword,keyword,keyword);
            result.setCode(200);
            result.setResult(posts);
            return result;
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(235);
            result.setResult("发生错误");
            return result;
        }
    }

    //分页发送用户信息
    @RequestMapping("/Perpage")
    @ApiOperation(value = "分页发送用户信息",httpMethod = "POST",notes = "需要发送:每页数,页数,可以发送部分昵称")
    public Result Perpage(@RequestBody Perpage perpage){
        Result result=new Result();
        try {
            int cr=(perpage.getPage()-1)*perpage.getPerpage();
            if(perpage.getKey()!=null){
                String keyword="%"+perpage.getKey()+"%";
                List<userAdmin> list =jdbc.query("select userid as id,name as user_num ,password as user_pass,`create` as add_time from userinfo where valid=1 and name like ? limit ? OFFSET ?", new BeanPropertyRowMapper<>(userAdmin.class),keyword,perpage.getPerpage(),cr);
                result.setCode(200);
                System.out.println(list);
                result.setResult(list);
                return result;
            }
            else {
                List<userAdmin> list  =jdbc.query("select userid as id,name as user_num ,password as user_pass,`create` as add_time from userinfo where valid=1 limit ? OFFSET ?", new BeanPropertyRowMapper<>(userAdmin.class),perpage.getPerpage(),cr);
                result.setCode(200);
                result.setResult(list);
                return result;
            }
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(236);
            result.setResult("发生错误");
            return result;
        }
    }

    //管理员获取三个总数
    @GetMapping("/num")
    @ApiOperation(value = "管理员获取三个总数", httpMethod="GET")
    public Result num(){
        Result result=new Result();
        try {
            Integer num1=jdbc.queryForObject("select sum(readnum) from post",Integer.class);
            Integer num2=jdbc.queryForObject("select sum(thumbupnum) from post",Integer.class);
            Integer num3=jdbc.queryForObject("select sum(commentnum) from post",Integer.class);
            List<Integer> list=new ArrayList<>();
            list.add(num1);
            list.add(num2);
            list.add(num3);
            result.setCode(200);
            result.setResult(list);
            return result;
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(237);
            result.setResult("发生错误");
            return result;
        }

    }

    //获取用户信息
    @PostMapping("/GetUserinfo")
    @ApiOperation(value = "获取用户信息", httpMethod="POST",notes = "发送用户id")
    public Result GetUserinfo(@RequestBody Userinfo userinfo){
        System.out.println(userinfo);
        Result result=new Result();
        try {
            Userinfo userinfo1 = jdbc.queryForObject("select * from userinfo where userid=?", new BeanPropertyRowMapper<>(Userinfo.class),userinfo.getUserid());
            result.setCode(200);
            result.setResult(userinfo1);
            return result;
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(234);
            result.setResult("发生错误");
            return result;
        }
    }

    //个人获取四个总数
    @PostMapping ("/personnum")
    @ApiOperation(value = "个人获取四个总数", httpMethod="POST",notes = "用户id")
    public Result personnum(@RequestBody Userinfo userinfo){
        Result result=new Result();
        Number number=new Number();
        try {
            Integer num1=jdbc.queryForObject("select sum(readnum) from post where post.userid=?",Integer.class,userinfo.getUserid());
            Integer num2=jdbc.queryForObject("select sum(thumbupnum) from post where post.userid=?",Integer.class,userinfo.getUserid());
            Integer num3=jdbc.queryForObject("select sum(commentnum) from post where post.userid=?",Integer.class,userinfo.getUserid());
            Integer num4=jdbc.queryForObject("select count(*) from collect,post where collect.userid=? and collect.postid=post.postid",Integer.class,userinfo.getUserid());
            if (num1==null) number.setReadnum(0);
            else number.setReadnum(num1);
            if (num2==null) number.setThumbupnum(0);
            else number.setThumbupnum(num2);
            if (num3==null) number.setCommentnum(0);
            else number.setCommentnum(num3);
            if (num4==null) number.setCollectnum(0);
            else number.setCollectnum(num4);
            result.setCode(200);
            result.setResult(number);
            return result;
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(238);
            result.setResult("发生错误");
            return result;
        }
    }

    //分页发送文章信息
    @RequestMapping("/PerpagePost")
    @ApiOperation(value = "分页发送文章信息",httpMethod = "POST",notes = "需要发送:每页数,页数,可以发送部分内容")
    public Result PerpagePost(@RequestBody Perpage perpage){
        Result result=new Result();
        try {
            int cr=(perpage.getPage()-1)*perpage.getPerpage();
            if(perpage.getKey()!=null){
                String keyword="%"+perpage.getKey()+"%";
                List<postAdmin> list =jdbc.query("select postid as id,title as post_name ,name as post_author,readnum as post_readnum from post,userinfo where post.valid=1 and userinfo.valid=1 and post.userid=userinfo.userid and (title like ? or name like ?) limit ? OFFSET ?", new BeanPropertyRowMapper<>(postAdmin.class),keyword,keyword,perpage.getPerpage(),cr);
                result.setCode(200);
                result.setResult(list);
                return result;
            }
            else {
                List<postAdmin> list  =jdbc.query("select postid as id,title as post_name ,name as post_author,readnum as post_readnum from post,userinfo where post.valid=1 and userinfo.valid=1 and post.userid=userinfo.userid limit ? OFFSET ?", new BeanPropertyRowMapper<>(postAdmin.class),perpage.getPerpage(),cr);
                result.setCode(200);
                result.setResult(list);
                return result;
            }
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(239);
            result.setResult("发生错误");
            return result;
        }
    }

    //我要投诉
    @RequestMapping("/complain")
    @ApiOperation(value = "投诉反馈",httpMethod = "POST",notes = "需要发送:用户id,内容")
    public Result complain(@RequestBody Complain complain){
        Result result=new Result();
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        try {
            jdbc.update("insert into complain(userid,content,`create`) VALUES (?,?,?);",complain.getUserid(),complain.getContent(),currentTime);
            Userinfo userinfo=null;
            userinfo = jdbc.queryForObject("select * from userinfo where userid = ?", new BeanPropertyRowMapper<>(Userinfo.class), complain.getUserid());
            jdbc.update("insert into editlog(content) VALUES (?);",userinfo.getRole()+" "+userinfo.getUserid()+":"+userinfo.getName()+" 提出了一条投诉:"+complain.getContent() +" 投诉时间:"+formattedTime);
            result.setCode(200);
            result.setResult("反馈成功");
            return result;
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(240);
            result.setResult("反馈失败");
            return result;
        }

    }

    //投诉展示
    @RequestMapping("/showcomplain")
    @ApiOperation(value = "投诉反馈",httpMethod = "POST",notes = "需要发送:每页数,页数,可以发送部分内容")
    public Result showcomplain(@RequestBody Perpage perpage){
        Result result=new Result();
        try {
            int cr=(perpage.getPage()-1)*perpage.getPerpage();
            if(perpage.getKey()!=null){
                String keyword="%"+perpage.getKey()+"%";
                List<complainAdmin> list =jdbc.query("select complainid as id ,content as complain_content,complain.`create` as complain_time,name as complain_user from complain,userinfo where complain.valid=1 and userinfo.valid=1 and complain.userid=userinfo.userid and (userinfo.name like ? or complain.content like ?)limit ? OFFSET ?", new BeanPropertyRowMapper<>(complainAdmin.class),keyword,keyword,perpage.getPerpage(),cr);
                result.setCode(200);
                result.setResult(list);
                return result;
            }
            else {
                List<complainAdmin> list  =jdbc.query("select complainid as id ,content as complain_content,complain.`create` as complain_time,name as complain_user from complain,userinfo where complain.valid=1 and userinfo.valid=1 and complain.userid=userinfo.userid limit ? OFFSET ?", new BeanPropertyRowMapper<>(complainAdmin.class),perpage.getPerpage(),cr);
                result.setCode(200);
                result.setResult(list);
                return result;
            }
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(241);
            result.setResult("发生错误");
            return result;
        }
    }

    //登录日志展示
    @RequestMapping("/showloginlog")
    @ApiOperation(value = "登录日志展示",httpMethod = "POST",notes = "需要发送:每页数,页数,可以发送部分内容")
    public Result showloginlog(@RequestBody Perpage perpage){
        Result result=new Result();
        try {
            int cr=(perpage.getPage()-1)*perpage.getPerpage();
            if(perpage.getKey()!=null){
                String keyword="%"+perpage.getKey()+"%";
                List<Log> list =jdbc.query("select logid as id,content as loginlog from loginlog where (content like ?) limit ? OFFSET ?", new BeanPropertyRowMapper<>(Log.class),keyword,perpage.getPerpage(),cr);
                result.setCode(200);
                System.out.println(list);
                result.setResult(list);
                return result;
            }
            else {
                List<Log> list  =jdbc.query("select logid as id,content as loginlog from loginlog  limit ? OFFSET ?", new BeanPropertyRowMapper<>(Log.class),perpage.getPerpage(),cr);
                result.setCode(200);
                result.setResult(list);
                return result;
            }
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(242);
            result.setResult("发生错误");
            return result;
        }
    }

    //操作日志展示
    @RequestMapping("/showeditlog")
    @ApiOperation(value = "操作日志展示",httpMethod = "POST",notes = "需要发送:每页数,页数,可以发送部分内容")
    public Result showeditlog(@RequestBody Perpage perpage){
        Result result=new Result();
        try {
            int cr=(perpage.getPage()-1)*perpage.getPerpage();
            if(perpage.getKey()!=null){
                String keyword="%"+perpage.getKey()+"%";
                List<Log> list =jdbc.query("select logid as id,content as editlog  from editlog where content like ? limit ? OFFSET ?  ", new BeanPropertyRowMapper<>(Log.class),keyword,perpage.getPerpage(),cr);
                result.setCode(200);
                System.out.println(list);
                result.setResult(list);
                return result;
            }
            else {
                List<Log> list  =jdbc.query("select logid,content as editlog from editlog limit ? OFFSET ?", new BeanPropertyRowMapper<>(Log.class),perpage.getPerpage(),cr);
                result.setCode(200);
                result.setResult(list);
                return result;
            }
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(243);
            result.setResult("发生错误");
            return result;
        }
    }

    //评论区
    @RequestMapping("/comment")
    @ApiOperation(value = "评论区",httpMethod = "POST",notes = "需要发送:文章id")
    public Result comment(@RequestBody Post post){
        Result result=new Result();
        try {
            List<Commentarea> list=jdbc.query("select commentid as id,userid,avatart as avatarUrl,name as nickName,comment.`create`as createTime,content,preid as parentId  from comment,userinfo where postid=? and comment.author=userinfo.userid and comment.valid=1 order by comment.`create` ASC",new BeanPropertyRowMapper<>(Commentarea.class),post.getPostid());
            result.setCode(200);
            result.setResult(list);
            return result;
        }catch (DataAccessException e){
            e.printStackTrace();
            result.setCode(244);
            result.setResult("发生错误");
            return result;
        }
    }

    //短信验证
    @PostMapping ("/text")
    @ApiOperation(value = "短信验证",httpMethod = "POST",notes = "需要发送:手机号")
    public Result text(@RequestBody Phonenum phonenum) {
        Result result=new Result();
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "51f4841186224dbf85312aaccfd168fd";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phonenum.getPhonenum());
        Random random = new Random();
        int randomNumber = 10000 + random.nextInt(90000);
        querys.put("param", "**code**:"+String.valueOf(randomNumber)+",**minute**:5");

//smsSignId（短信前缀）和templateId（短信模板），可登录国阳云控制台自助申请。参考文档：http://help.guoyangyun.com/Problem/Qm.html

        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "a09602b817fd47e59e7c6e603d3f088d");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从\r\n\t    \t* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java\r\n\t    \t* 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            result.setCode(200);
            result.setResult(String.valueOf(randomNumber));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(245);
            result.setResult("发送失败");
            return result;
        }
    }
    //更改文章信息
    @RequestMapping("/editpost")
    @ApiOperation(value = "更改文章信息",httpMethod = "POST",notes = "发送:postid")
    public Result editpost(@RequestBody Post post){
        Result result=new Result();
        try {
            jdbc.update("update post set title=? where postid=?",post.getTitle(),post.getPostid());
            result.setCode(200);
            result.setResult("修改成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(246);
            result.setResult("修改失败");
            return result;
        }
    }
}
