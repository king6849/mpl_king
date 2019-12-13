package com.king.mpl.Utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Random;

/**
 * @ClassName FileUtils
 * @Description TODO
 * @Author mai
 * @Date 2019/12/7 19:13
 **/
public class FileUtils {
    public static String saveFile(String name,String path,MultipartFile file){
        //原始文件名
        String originalFilename=file.getOriginalFilename();
        String suffix=originalFilename.substring(originalFilename.lastIndexOf("."));
        //拼接：名字+后缀
        String fileName=name+suffix;
        try{
            File dir=new File(path);
            //如果文件夹不存在就创建
            if (!dir.exists()){
                dir.mkdir();
            }
            //将图片保存到硬盘
            File newFile=new File(path,fileName);
            file.transferTo(newFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return fileName;
    }

    public static String getRandomName(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        //StringBuffer是线程安全的，而StringBuilder则没有实现线程安全功能，所以性能略高。
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            //生成0~62的数字，包含0不包含62
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
