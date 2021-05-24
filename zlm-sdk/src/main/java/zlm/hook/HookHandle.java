package zlm.hook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import zlm.hook.event.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * HookHandle
 *
 * @author ziduye(a)gmail.com HookHandle
 * @version 2021/5/21 10:04
 */
@Component
@Slf4j
public class HookHandle {
//    @Autowired
//    private SIPCommander cmder;
//
//    @Autowired
//    private IPlayService playService;
//
//    @Autowired
//    private IVideoManagerStorager storager;
//
//    @Autowired
//    private IRedisCatchStorage redisCatchStorage;
//
//    @Autowired
//    private ZLMServerManger zlmServerManger;
//
//    @Autowired
//    private ZLMMediaListManager zlmMediaListManager;
//
//    @Autowired
//    private MediaConfig mediaConfig;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Value("${userSettings.autoApplyPlay}")
    private boolean autoApplyPlay;

    /**
     * 流量统计事件，播放器或推流器断开时并且耗用流量超过特定阈值时会触发此事件
     * @param json
     * @return
     */
    public Map onFlowReport(Map json){
        if (log.isDebugEnabled()) {
            log.debug("ZLM HOOK on_flow_report API调用，参数：" + json.toString());
        }
        //暂不做处理
        applicationEventPublisher.publishEvent(new OnFlowReportEvent(this,json));
        Map result = new HashMap();
        result.put("code", 0);
        result.put("msg", "success");
        return result;
    }

    public Map on_http_access(Map json){
        if (log.isDebugEnabled()) {
            log.debug("ZLM HOOK on_http_access API 调用，参数：" + json.toString());
        }
        //暂不做处理
        applicationEventPublisher.publishEvent(new OnHttpAccessEvent(this,json));
        Map result = new HashMap();
        result.put("code", 0);
        result.put("err", "");
        result.put("path", "");
        result.put("second", 600);
        return result;
    }


    public Map on_play(Map json){
        if (log.isDebugEnabled()) {
            log.debug("ZLM HOOK on_play API调用，参数：" + json.toString());
        }
        //暂不做处理
        applicationEventPublisher.publishEvent(new OnPlayEvent(this,json));
        Map result = new HashMap();
        result.put("code", 0);
        result.put("msg", "success");
        return result;
    }


    public Map on_publish(Map json){
        if (log.isDebugEnabled()) {
            log.debug("ZLM HOOK on_publish API调用，参数：" + json.toString());
        }
        //暂不做处理
        applicationEventPublisher.publishEvent(new OnPublishEvent(this,json));
        Map result = new HashMap();
        result.put("code", 0);
        result.put("msg", "success");
        result.put("enableHls", true);
        result.put("enableMP4", false);
        result.put("enableRtxp", true);
        return result;
    }


    public Map on_record_mp4(Map json){
        if (log.isDebugEnabled()) {
            log.debug("ZLM HOOK on_record_mp4 API调用，参数：" + json.toString());
        }
        //暂不做处理
        applicationEventPublisher.publishEvent(new OnRecordMp4Event(this,json));
        Map result = new HashMap();
        result.put("code", 0);
        result.put("msg", "success");
        return result;
    }


    public Map on_rtsp_realm(Map json){
        if (log.isDebugEnabled()) {
            log.debug("ZLM HOOK on_rtsp_realm API调用，参数：" + json.toString());
        }
        //暂不做处理
        applicationEventPublisher.publishEvent(new OnRtspRealmEvent(this,json));
        Map result = new HashMap();
        result.put("code", 0);
        result.put("realm", "");
        return result;
    }

    public Map on_rtsp_auth(Map json){
        if (log.isDebugEnabled()) {
            log.debug("ZLM HOOK on_rtsp_auth API调用，参数：" + json.toString());
        }
        //暂不做处理
        applicationEventPublisher.publishEvent(new OnRtspAuthEvent(this,json));
        Map result = new HashMap();
        result.put("code", 0);
        result.put("encrypted", false);
        result.put("passwd", "test");
        return result;
    }


    public Map on_shell_login(Map json){
        if (log.isDebugEnabled()) {
            log.debug("ZLM HOOK on_shell_login API调用，参数：" + json.toString());
        }
        // TODO 如果是带有rtpstream则开启按需拉流
        // String app = json.getString("app");
        // String stream = json.getString("stream");

        //暂不做处理
        applicationEventPublisher.publishEvent(new OnShellLoginEvent(this,json));
        Map result = new HashMap();
        result.put("code", 0);
        result.put("msg", "success");
        return result;
    }


    public Map on_stream_changed(Map json){
        if (log.isDebugEnabled()) {
            log.debug("ZLM HOOK on_stream_changed API调用，参数：" + json.toString());
        }

        // 流消失移除redis play
//        String app = json.getString("app");
//        String streamId = json.getString("stream");
//        String schema = json.getString("schema");
//        JSONArray tracks = json.getJSONArray("tracks");
//        boolean regist = json.getBoolean("regist");
//        if (tracks != null) {
//            log.info("[stream: " + streamId + "] on_stream_changed->>" + schema);
//        }
//        if ("rtmp".equals(schema)){
//            if ("rtp".equals(app) && !regist ) {
//                StreamInfo streamInfo = redisCatchStorage.queryPlayByStreamId(streamId);
//                if (streamInfo!=null){
//                    redisCatchStorage.stopPlay(streamInfo);
//                    storager.stopPlay(streamInfo.getDeviceID(), streamInfo.getChannelId());
//                }else{
//                    streamInfo = redisCatchStorage.queryPlaybackByStreamId(streamId);
//                    redisCatchStorage.stopPlayback(streamInfo);
//                }
//            }else {
//                if (!"rtp".equals(app) ){
//                    if (regist) {
//                        zlmMediaListManager.addMedia(app, streamId);
//                    }else {
//                        zlmMediaListManager.removeMedia(app, streamId);
//                    }
//                }
//            }
//        }
        applicationEventPublisher.publishEvent(new OnStreamChangedEvent(this,json));
        Map result = new HashMap();
        result.put("code", 0);
        result.put("msg", "success");
        return result;
    }


    public Map on_stream_none_reader(Map json){
        if (log.isDebugEnabled()) {
            log.debug("ZLM HOOK on_stream_none_reader API调用，参数：" + json.toString());
        }
//        String streamId = json.get("stream");
//        String app = json.getString("app");

        applicationEventPublisher.publishEvent(new OnStreamNoneReaderEvent(this,json));

        Map result = new HashMap();
//        if ("rtp".equals(app)){
//            result.put("code", 0);
//            result.put("close", true);
//            StreamInfo streamInfoForPlayCatch = redisCatchStorage.queryPlayByStreamId(streamId);
//            if (streamInfoForPlayCatch != null) {
//                if (redisCatchStorage.isChannelSendingRTP(streamInfoForPlayCatch.getChannelId())) {
//                    result.put("close", false);
//                } else {
//                    cmder.streamByeCmd(streamInfoForPlayCatch.getDeviceID(), streamInfoForPlayCatch.getChannelId());
//                    redisCatchStorage.stopPlay(streamInfoForPlayCatch);
//                    storager.stopPlay(streamInfoForPlayCatch.getDeviceID(), streamInfoForPlayCatch.getChannelId());
//                }
//            }else{
//                StreamInfo streamInfoForPlayBackCatch = redisCatchStorage.queryPlaybackByStreamId(streamId);
//                if (streamInfoForPlayBackCatch != null) {
//                    cmder.streamByeCmd(streamInfoForPlayBackCatch.getDeviceID(), streamInfoForPlayBackCatch.getChannelId());
//                    redisCatchStorage.stopPlayback(streamInfoForPlayBackCatch);
//                }
//            }
//        }else {
//            result.put("code", 0);
//            result.put("close", false);
//        }
        return result;

    }


    public Map on_stream_not_found(Map json){
        if (log.isDebugEnabled()) {
            log.debug("ZLM HOOK on_stream_not_found API调用，参数：" + json.toString());
        }
        if (autoApplyPlay) {
//            String app = json.getString("app");
//            String streamId = json.getString("stream");
//            StreamInfo streamInfo = redisCatchStorage.queryPlayByStreamId(streamId);
//            if ("rtp".equals(app) && streamId.contains("gb_play") && streamInfo == null) {
//                String[] s = streamId.split("_");
//                if (s.length == 4) {
//                    String deviceId = s[2];
//                    String channelId = s[3];
//                    Device device = storager.queryVideoDevice(deviceId);
//                    if (device != null) {
//                        UUID uuid = UUID.randomUUID();
//                        cmder.playStreamCmd(device, channelId, (JSONObject response) -> {
//                            log.info("收到订阅消息： " + response.toJSONString());
//                            playService.onPublishHandlerForPlay(response, deviceId, channelId, uuid.toString());
//                        }, null);
//                    }
//
//                }
//
//            }

        }
        applicationEventPublisher.publishEvent(new OnStreamNotFoundEvent(this,json));
        Map result = new HashMap();
        result.put("code", 0);
        result.put("msg", "success");
        return result;
    }


    public Map on_server_started(Map json){
        if (log.isDebugEnabled()) {
            log.debug("ZLM HOOK on_server_started API调用，参数：" + json.toString());
        }
//        ZLMServerConfig ZLMServerConfig = JSON.toJavaObject(json, ZLMServerConfig.class);
//        zlmServerManger.updateServerCatch(ZLMServerConfig);
        // 重新发起代理
        applicationEventPublisher.publishEvent(new OnServerStartedEvent(this,json));
        Map result = new HashMap();
        result.put("code", 0);
        result.put("msg", "success");
        return result;
    }

}
