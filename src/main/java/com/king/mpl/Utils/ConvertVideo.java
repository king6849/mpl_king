package com.king.mpl.Utils;

import java.lang.annotation.Target;

import ch.qos.logback.classic.gaffer.PropertyUtil;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ConvertVideo {
    //源视频地址
    private static final String rootInputPath = "D:\\idea\\mp4\\mp4_tmp\\";
    //输出视频地址(本地配置)
    public static final String rootOutputPath = "D:\\idea\\mp4\\";
    //视频截图，封面
    public static final String rootOutputImgPath = "D:\\idea\\mp4img\\";
    //所在位置(本地配置)
    private static final String ffmpegPath = "D:\\idea\\projects\\mpl\\src\\main\\resources\\static\\ffmpeg.exe";

    // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
    @Async
    public boolean AllToMp4(String upFilePath, String name) {
        if (!checkContentType(upFilePath)) {
            return false;
        }
        // 创建一个List集合来保存转换视频文件为flv格式的命令
        List<String> convert = new ArrayList<String>();
        convert.add(ffmpegPath); // 添加转换工具路径
        convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
        convert.add(upFilePath); // 添加要转换格式的视频文件的路径
        System.out.println(upFilePath);
        convert.add("-qscale");     //指定转换的质量
        convert.add("6");
        convert.add("-ab");        //设置音频码率
        convert.add("64");
        convert.add("-ac");        //设置声道数
        convert.add("2");
        convert.add("-ar");        //设置声音的采样频率
        convert.add("22050");
        convert.add("-r");        //设置帧频
        convert.add("24");
        convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
        convert.add(rootOutputPath + name + ".mp4");
        boolean mark = true;
        ProcessBuilder builder = new ProcessBuilder();
        try {

            builder.command(convert);
//            builder.redirectErrorStream(true);
            builder.start();

        } catch (Exception e) {
            mark = false;
            e.printStackTrace();
        }
        return mark;
    }


    public boolean checkContentType(String upFilePath) {
        //取得视频后缀-
        String type = upFilePath.substring(upFilePath.lastIndexOf(".") + 1, upFilePath.length()).toLowerCase();
        System.out.println("源视频类型为:" + type);
        // 如果是ffmpeg能解析的格式:(asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等)
        if (type.equals("avi")) {
            return true;
        } else if (type.equals("mpg")) {
            return true;
        } else if (type.equals("wmv")) {
            return true;
        } else if (type.equals("3gp")) {
            return true;
        } else if (type.equals("mov")) {
            return true;
        } else if (type.equals("mp4")) {
            return true;
        } else if (type.equals("asf")) {
            return true;
        } else if (type.equals("asx")) {
            return true;
        } else if (type.equals("flv")) {
            return true;
        } else if (type.equals("mkv")) {
            return true;
        }
        // 如果是ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
        // 就先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
        else if (type.equals("wmv9")) {
            return false;
        } else if (type.equals("rm")) {
            return false;
        } else if (type.equals("rmvb")) {
            return false;
        }
        System.out.println("上传视频格式异常");
        return false;
    }

    public boolean screenImage(String upFilePath, String name) {
        // 创建一个List集合来保存从视频中截取图片的命令
        List<String> cutpic = new ArrayList<String>();
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(upFilePath); // 要截图的视频源文件
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add("1"); // 添加起始时间为第17秒
        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001"); // 添加持续时间为1毫秒
        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add(350 + "*" + 240); // 添加截取的图片大小为350*240
        cutpic.add(rootOutputImgPath + name + ".jpg"); // 添加截取的图片的保存路径
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(cutpic);
            builder.redirectErrorStream(true);
            builder.start();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        ConvertVideo video = new ConvertVideo();
//        video.AllToMp4("G:\\学习资料\\github教程\\two.wmv", "two");
        video.screenImage("G:\\学习资料\\github教程\\two.wmv", "two");
    }


}