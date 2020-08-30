package com.czy1344.vodservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.czy1344.vodservice.service.VoDService;
import com.czy1344.vodservice.util.ConstantPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 2020/8/2 14:32
 *
 * @author czy1344
 * 说明：
 */
@Service
public class VoDServiceImpl implements VoDService {
    private String accessKeyId = ConstantPropertiesUtil.KEY_ID;
    private String accessKeySecret = ConstantPropertiesUtil.KEY_SECRET;

    /**
     * 通过绝对路径上传文件
     *
     * @param file
     * @return
     */
    @Override
    public String uploadVideoToVoD(MultipartFile file) {
        String videoId = "";
        try {
            videoId = uploadVideoStream(accessKeyId, accessKeySecret,
                    file.getOriginalFilename(), file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoId;
    }

    /**
     * 通过流的方式上传一个视频
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     * @param inputStream
     * @return
     */
    private String uploadVideoStream(String accessKeyId, String accessKeySecret,
                                     String title, String fileName, InputStream inputStream) {
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        if (response.isSuccess()) {
            return response.getVideoId();
        } else {
            return response.getMessage();
        }

    }

    /*获取播放地址函数*/
    private GetPlayInfoResponse getPlayInfo(DefaultAcsClient client, String videoId) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }

    /**
     * 初始化一个客户端
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     * @throws ClientException
     */
    private DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    @Override
    public String getVideoPlayUrl(String videoId) {
        String url = "";
        try {
            url = getVideoPlayUrl(accessKeyId, accessKeySecret, videoId);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 获取视频播放地址
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param videoId
     * @return
     * @throws ClientException
     */
    private String getVideoPlayUrl(String accessKeyId, String accessKeySecret, String videoId) throws ClientException {
        String videoPlayUrl = "";
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            response = getPlayInfo(client, videoId);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                //System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
                videoPlayUrl = playInfo.getPlayURL();
            }
            //Base信息
            //System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            //System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        return videoPlayUrl;
    }

    /**
     * 删除阿里云视频
     *
     * @param videoId
     * @return
     */
    @Override
    public boolean deleteVideo(String videoId) {
        try {
            DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(videoId);
            client.getAcsResponse(request);
            return true;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除多个视频
     */
    @Override
    public void deleteVideosOnAliyun(List<String> list) {
        try {
            DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String join = StringUtils.join(list.toArray(), ",");
            // 支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(join);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
