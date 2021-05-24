package zlm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ZLMRESTfulUtils {

    private final static Logger logger = LoggerFactory.getLogger(ZLMRESTfulUtils.class);

    public final static String ADD_FFMPEG_SOURCE = "/index/api/addFFmpegSource";
    public final static String DEL_FFMPEG_SOURCE = "/index/api/delFFmpegSource";

    public final static String ADD_STREAM_PROXY = "/index/api/addStreamProxy";
    public final static String DEL_STREAM_PROXY = "/index/api/delStreamProxy";

    @Deprecated
    public final static String CLOSE_STREAM = "/index/api/close_stream";
    public final static String CLOSE_STREAMS = "/index/api/close_streams";

    public final static String GET_ALL_SESSION = "/index/api/getAllSession";

    public final static String GET_API_LIST = "/index/api/getApiList";

    public final static String GET_MEDIA_LIST = "/index/api/getMediaList";

    @Deprecated
    public final static String GET_MEDIA_INFO = "/index/api/getMediaInfo";
    @Deprecated
    public final static String IS_MEDIA_ONLINE = "/index/api/isMediaOnline";

    public final static String GET_SERVER_CONFIG = "/index/api/getServerConfig";
    public final static String SET_SERVER_CONFIG = "/index/api/setServerConfig";

    public final static String GET_THREADS_LOAD = "/index/api/getThreadsLoad";
    public final static String GET_WORK_THREADS_LOAD = "/index/api/getWorkThreadsLoad";

    public final static String KICK_SESSION = "/index/api/kick_session";
    public final static String KICK_SESSIONS = "/index/api/kick_sessions";

    public final static String RESTART_SERVER = "/index/api/restartServer";
    public final static String GET_RTP_INFO = "/index/api/getRtpInfo";

    public final static String GET_MP4_RECORD_FILE = "/index/api/getMp4RecordFile";
    public final static String START_RECORD = "/index/api/startRecord";
    public final static String STOP_RECORD = "/index/api/stopRecord";
    public final static String GET_RECORD_STATUS = "/index/api/getRecordStatus";

    public final static String GET_SNAP = "/index/api/getSnap";

    public final static String OPEN_RTP_SERVER = "/index/api/openRtpServer";
    public final static String CLOSE_RTP_SERVER = "/index/api/closeRtpServer";
    public final static String LIST_RTP_SERVER = "/index/api/listRtpServer";
    public final static String START_SEND_RTP = "/index/api/startSendRtp";
    public final static String STOP_SEND_RTP = "/index/api/stopSendRtp";

    public final static String GET_STATISTIC = "/index/api/getStatistic";

    public final static String ON_FLOW_REPORT = "on_flow_report";
    public final static String ON_HTTP_ACCESS = "on_http_access";
    public final static String ON_PLAY = "on_play";
    public final static String ON_PUBLISH = "on_publish";
    public final static String ON_RECORD_MP4 = "on_record_mp4";
    public final static String ON_RTSP_AUTH = "on_rtsp_auth";
    public final static String ON_RTSP_REALM = "on_rtsp_realm";
    public final static String ON_SHELL_LOGIN = "on_shell_login";
    public final static String ON_STREAM_CHANGED = "on_stream_changed";
    public final static String ON_STREAM_NONE_READER = "on_stream_none_reader";
    public final static String ON_STREAM_NOT_FOUND = "on_stream_not_found";
    public final static String ON_SERVER_STARTED = "on_server_started";

    @Autowired
    private MediaConfig mediaConfig;

    public interface RequestCallback{
        void run(JSONObject response);
    }

    public JSONObject sendPost(String api, Map<String, Object> param, RequestCallback callback) {
        OkHttpClient client = new OkHttpClient();
        String url = String.format("http://%s:%s%s",  mediaConfig.getIp(), mediaConfig.getHttpPort(), api);
        JSONObject responseJSON = null;
        logger.debug(url);

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("secret",mediaConfig.getSecret());
        if (param != null && param.keySet().size() > 0) {
            for (String key : param.keySet()){
                if (param.get(key) != null) {
                    builder.add(key, param.get(key).toString());
                }
            }
        }

        FormBody body = builder.build();

        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
            if (callback == null) {
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String responseStr = response.body().string();
                        if (responseStr != null) {
                            responseJSON = JSON.parseObject(responseStr);
                        }
                    }
                } catch (ConnectException e) {
                    logger.error(String.format("连接ZLM失败: %s, %s", e.getCause().getMessage(), e.getMessage()));
                    logger.info("请检查media配置并确认ZLM已启动...");
                }catch (IOException e) {
                    logger.error(String.format("[ %s ]请求失败: %s", url, e.getMessage()));
                }
            }else {
                client.newCall(request).enqueue(new Callback(){

                    @Override
                    public void onResponse(Call call, Response response){
                        if (response.isSuccessful()) {
                            try {
                                String responseStr = Objects.requireNonNull(response.body()).string();
                                callback.run(JSON.parseObject(responseStr));
                            } catch (IOException e) {
                                logger.error(String.format("[ %s ]请求失败: %s", url, e.getMessage()));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        logger.error(String.format("连接ZLM失败: %s, %s", e.getCause().getMessage(), e.getMessage()));
                        logger.info("请检查media配置并确认ZLM已启动...");
                    }
                });
            }



        return responseJSON;
    }

    public void sendPostForImg(String api, Map<String, Object> param, String targetPath, String fileName) {
        OkHttpClient client = new OkHttpClient();
        String url = String.format("http://%s:%s%s",  mediaConfig.getIp(), mediaConfig.getHttpPort(), api);
        JSONObject responseJSON = null;
        logger.debug(url);

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("secret",mediaConfig.getSecret());
        if (param != null && param.keySet().size() > 0) {
            for (String key : param.keySet()){
                if (param.get(key) != null) {
                    builder.add(key, param.get(key).toString());
                }
            }
        }

        FormBody body = builder.build();

        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                if (targetPath != null) {
                    File snapFolder = new File(targetPath);
                    if (!snapFolder.exists()) {
                        snapFolder.mkdirs();
                    }
                    File snapFile = new File(targetPath + "/" + fileName);
                    FileOutputStream outStream = new FileOutputStream(snapFile);
                    outStream.write(response.body().bytes());
                    outStream.close();
                }
            }
        } catch (ConnectException e) {
            logger.error(String.format("连接ZLM失败: %s, %s", e.getCause().getMessage(), e.getMessage()));
            logger.info("请检查media配置并确认ZLM已启动...");
        }catch (IOException e) {
            logger.error(String.format("[ %s ]请求失败: %s", url, e.getMessage()));
        }

    }

    public JSONObject getMediaList(String app, String stream, String schema, RequestCallback callback){
        Map<String, Object> param = new HashMap<>();
        if (app != null) {
            param.put("app",app);
        }
        if (stream != null) {
            param.put("stream",stream);
        }
        if (schema != null) {
            param.put("schema",schema);
        }
        param.put("vhost","__defaultVhost__");
        return sendPost(GET_MEDIA_LIST,param, callback);
    }

    public JSONObject getMediaList(String app, String stream){
        return getMediaList(app, stream,null,  null);
    }

    public JSONObject getMediaList(RequestCallback callback){
        return sendPost(GET_MEDIA_LIST,null, callback);
    }

    public JSONObject getMediaInfo(String app, String schema, String stream){
        Map<String, Object> param = new HashMap<>();
        param.put("app",app);
        param.put("schema",schema);
        param.put("stream",stream);
        param.put("vhost","__defaultVhost__");
        return sendPost(GET_MEDIA_LIST,param, null);
    }

    public JSONObject getRtpInfo(String stream_id){
        Map<String, Object> param = new HashMap<>();
        param.put("stream_id",stream_id);
        return sendPost(GET_RTP_INFO,param, null);
    }

    public JSONObject addFFmpegSource(String src_url, String dst_url, String timeout_ms){
        logger.info(src_url);
        logger.info(dst_url);
        Map<String, Object> param = new HashMap<>();
        param.put("src_url", src_url);
        param.put("dst_url", dst_url);
        param.put("timeout_ms", timeout_ms);
        return sendPost(ADD_FFMPEG_SOURCE,param, null);
    }

    public JSONObject delFFmpegSource(String key){
        Map<String, Object> param = new HashMap<>();
        param.put("key", key);
        return sendPost(DEL_FFMPEG_SOURCE,param, null);
    }

    public JSONObject getMediaServerConfig(){
        return sendPost(GET_SERVER_CONFIG,null, null);
    }

    public JSONObject setServerConfig(Map<String, Object> param){
        return sendPost(SET_SERVER_CONFIG,param, null);
    }

    public JSONObject openRtpServer(Map<String, Object> param){
        return sendPost(OPEN_RTP_SERVER,param, null);
    }

    public JSONObject closeRtpServer(Map<String, Object> param) {
        return sendPost(CLOSE_RTP_SERVER,param, null);
    }

    public JSONObject listRtpServer() {
        return sendPost(LIST_RTP_SERVER,null, null);
    }

    public JSONObject startSendRtp(Map<String, Object> param) {
        return sendPost(START_SEND_RTP,param, null);
    }

    public JSONObject stopSendRtp(Map<String, Object> param) {
        return sendPost(STOP_SEND_RTP,param, null);
    }

    public JSONObject addStreamProxy(String app, String stream, String url, boolean enable_hls, boolean enable_mp4, String rtp_type) {
        Map<String, Object> param = new HashMap<>();
        param.put("vhost", "__defaultVhost__");
        param.put("app", app);
        param.put("stream", stream);
        param.put("url", url);
        param.put("enable_hls", enable_hls?1:0);
        param.put("enable_mp4", enable_mp4?1:0);
        param.put("rtp_type", rtp_type);
        return sendPost(ADD_STREAM_PROXY,param, null);
    }

    public JSONObject closeStreams(String app, String stream) {
        Map<String, Object> param = new HashMap<>();
        param.put("vhost", "__defaultVhost__");
        param.put("app", app);
        param.put("stream", stream);
        param.put("force", 1);
        return sendPost(CLOSE_STREAM,param, null);
    }

    public JSONObject getAllSession() {
        return sendPost(GET_ALL_SESSION,null, null);
    }

    public void kickSessions(String localPortSStr) {
        Map<String, Object> param = new HashMap<>();
        param.put("local_port", localPortSStr);
        sendPost(KICK_SESSION,param, null);
    }

    public void getSnap(String flvUrl, int timeout_sec, int expire_sec, String targetPath, String fileName) {
        Map<String, Object> param = new HashMap<>();
        param.put("url", flvUrl);
        param.put("timeout_sec", timeout_sec);
        param.put("expire_sec", expire_sec);
        sendPostForImg(GET_SNAP,param, targetPath, fileName);
    }
}
