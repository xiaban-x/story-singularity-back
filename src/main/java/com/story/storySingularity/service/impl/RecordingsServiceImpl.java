package com.story.storySingularity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.story.storySingularity.mapper.RecordingsMapper;
import com.story.storySingularity.model.dto.RecordingsReturnDto;
import com.story.storySingularity.model.po.Recordings;
import com.story.storySingularity.service.RecordingsService;
import com.story.storySingularity.service.UsersService;
import com.story.storySingularity.unfbx.chatgpt.OpenAiClient;
import com.story.storySingularity.unfbx.chatgpt.entity.whisper.Whisper;
import com.story.storySingularity.unfbx.chatgpt.entity.whisper.WhisperResponse;
import com.story.storySingularity.unfbx.chatgpt.interceptor.OpenAILogger;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaban
 */
@Slf4j
@Service
public class RecordingsServiceImpl extends ServiceImpl<RecordingsMapper, Recordings> implements RecordingsService {

    @Autowired
    UsersService usersService;

    @Autowired
    RecordingsMapper recordingsMapper;

    @Autowired
    MinioClient minioClient;

    private String filePath = "D:\\audio\\";

    private String mimeType = "audio/x-m4a";

    private String bucket = "audio";
    @Override
    public List<RecordingsReturnDto> getRecordingsByUserId(Integer userId) {
        ArrayList<RecordingsReturnDto> recordingsReturnDtos = new ArrayList<>();
        LambdaQueryWrapper<Recordings> recordingsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        recordingsLambdaQueryWrapper.eq(Recordings::getUserId,userId);
        List<Recordings> recordings = recordingsMapper.selectList(recordingsLambdaQueryWrapper);
        for(Recordings r : recordings){
            RecordingsReturnDto recordingsReturnDto = new RecordingsReturnDto();
            recordingsReturnDto.setUserId(userId);
            recordingsReturnDto.setText(r.getText());
            File file = downloadFileFromMinIO("audio", r.getUrl());
            recordingsReturnDto.setRecordingFile(file);
            recordingsReturnDtos.add(recordingsReturnDto);
        }

        return recordingsReturnDtos;
    }
    @Override
    public Recordings saveRecordings(RecordingsReturnDto recordingsReturnDto) {
        Recordings recordings = new Recordings();
        File recordingFile = recordingsReturnDto.getRecordingFile();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(recordingFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Date d = new Date();
        SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sbf.format(d);
        time = time.replace(" ","T");
//        String fileName = recordingsReturnDto.getUserId()+ "-"+ time + "." +mimeType;
        String fileName = recordingFile.getName();
        //将录音传到本地
        boolean b = writeFile(inputStream, fileName);
        String localFilePath = filePath + fileName;
        if (b){
            //让本地录音上传到minio
            boolean b1 = addMediaFilesToMinIO(localFilePath, mimeType, bucket, fileName);
            if (b1){
                recordings.setUserId(recordingsReturnDto.getUserId());
                recordings.setUrl(fileName);
                OpenAiClient openAiClient = getOpenAiClient();
                //语音转文字
                WhisperResponse whisperResponse =
                        openAiClient.speechToTextTranscriptions(new File(localFilePath)
                                , Whisper.Model.WHISPER_1);
                String text = whisperResponse.getText();
                //繁体转换简体
                text = ZhConverterUtil.convertToSimple(text);
                recordings.setText(text);
                recordingsMapper.insert(recordings);
                return recordings;
            }
        }
        return null;
    }

    @Override
    public RecordingsReturnDto getRecordingsById(Integer id) {
        LambdaQueryWrapper<Recordings> recordingsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        recordingsLambdaQueryWrapper.eq(Recordings::getId,id);
        Recordings recording = recordingsMapper.selectOne(recordingsLambdaQueryWrapper);
        RecordingsReturnDto recordingsReturnDto = new RecordingsReturnDto();
        recordingsReturnDto.setUserId(id);
        recordingsReturnDto.setText(recording.getText());
        File file = downloadFileFromMinIO("audio", recording.getUrl());
        recordingsReturnDto.setRecordingFile(file);

        return recordingsReturnDto;
    }

    public boolean writeFile(InputStream inputStream, String fileName){
        OutputStream os = null;
        try {
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(filePath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 从minio下载文件
     * @param bucket 桶
     * @param objectName 对象名称
     * @return 下载后的文件
     */
    public File downloadFileFromMinIO(String bucket, String objectName){
        //临时文件
        File minioFile = null;
        FileOutputStream outputStream = null;
        try{
            InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
            //创建临时文件
            minioFile=File.createTempFile("minio", ".merge");
            outputStream = new FileOutputStream(minioFile);
            IOUtils.copy(stream,outputStream);
            return minioFile;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    /**
     * @param localFilePath 文件地址
     * @param bucket        桶
     * @param objectName    对象名称
     * @return void
     * @description 将文件写入minIO
     * @author Mr.M
     * @date 2022/10/12 21:22
     */
    public boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName) {
        try {
            UploadObjectArgs testbucket = UploadObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .filename(localFilePath)
                    .contentType(mimeType)
                    .build();
            minioClient.uploadObject(testbucket);
            log.debug("上传文件到minio成功,bucket:{},objectName:{}", bucket, objectName);
            System.out.println("上传成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件到minio出错,bucket:{},objectName:{},错误原因:{}", bucket, objectName, e.getMessage(), e);
        }
        return false;
    }

    public OpenAiClient getOpenAiClient(){
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
//        Proxy proxy = null;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OpenAiClient openAiClient = OpenAiClient.builder()
                .apiKey("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")
                .connectTimeout(500000)
                .writeTimeout(500000)
                .readTimeout(500000)
                .interceptor(Arrays.asList(httpLoggingInterceptor))
                .proxy(proxy)
                .apiHost("https://api.openai.com/")
                .build();
        return openAiClient;
    }
}
