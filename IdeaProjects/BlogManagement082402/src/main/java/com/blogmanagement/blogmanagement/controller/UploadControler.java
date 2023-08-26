package com.blogmanagement.blogmanagement.controller;

import com.blogmanagement.blogmanagement.Pojo.Result;
import com.blogmanagement.blogmanagement.Pojo.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

//@CrossOrigin
//@RestController
//public class UploadControler {
//    @Autowired
//    private JdbcTemplate jdbc;
//    @Value("${picserver.rootpath}")
//    private String host;
//    @PostMapping("/upload")
//    public Result doUpload(@RequestParam("file") MultipartFile file, @RequestParam("id") int id){//file就能接收 请求参数名为 file 的⽂件
//        System.out.println(id);
//        Result result = new Result();
//        String fname = file.getOriginalFilename(); //得到上传的⽂件的⽂件名
//        int pos = fname.lastIndexOf("."); //得到 .所在的位置
//        String houZhui = fname.substring(pos);//截取从pos这个位置开始 到结束的字符串 其实就是
//        String newFileName = UUID.randomUUID()+houZhui;
//        try {
//            //以新的⽂件名 上传到 d:/upload/images/
//            file.transferTo(new File("D:/upload/images"+newFileName));
//            //返回上传成功后 图⽚的完整路径，以供前台保存图⽚路径⽤
//            result.setCode(200);
//            jdbc.update("update userinfo set avatart=? where userid=?","http://localhost:6060/"+newFileName, id);
//            result.setResult("http://localhost:6060/images/"+newFileName);
//            return result;
//        } catch (IOException e) {
//            e.printStackTrace();
//            result.setCode(201);
//            result.setResult("上传失败");
//            return result;
//        }
//    }
//}

@CrossOrigin
@RestController
public class UploadControler {
    @Autowired
    private JdbcTemplate jdbc;
    @Value("${picserver.rootpath}")
    private String host;
    @PostMapping("/upload")
    public Result doUpload(@RequestParam("file") MultipartFile file, @RequestParam("id") int id){//file就能接收 请求参数名为 file 的⽂件
        System.out.println(id);
        Result result = new Result();
        String fname = file.getOriginalFilename(); //得到上传的⽂件的⽂件名
        int pos = fname.lastIndexOf("."); //得到 .所在的位置
        String houZhui = fname.substring(pos);//截取从pos这个位置开始 到结束的字符串 其实就是
        String newFileName = UUID.randomUUID()+houZhui;
        try {
            //以新的⽂件名 上传到 d:/upload/images/
            file.transferTo(new File("D:\\upload\\images\\"+newFileName));
            //返回上传成功后 图⽚的完整路径，以供前台保存图⽚路径⽤
            result.setCode(200);
            jdbc.update("update userinfo set avatart=? where userid=?","http://localhost:6060/"+newFileName, id);
            Userinfo userinfonew=null;
            userinfonew = jdbc.queryForObject("select * from userinfo where userid = ?", new BeanPropertyRowMapper<>(Userinfo.class), id);
            System.out.println(userinfonew.getAvatart());
            result.setResult("http://localhost:6060/"+newFileName);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result.setCode(201);
            result.setResult("上传失败");
            return result;
        }
    }
}
