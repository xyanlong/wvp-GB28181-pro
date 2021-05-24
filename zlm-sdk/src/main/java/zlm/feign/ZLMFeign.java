package zlm.feign;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import zlm.config.FeignConfiguration;

import java.net.URI;

/**
 * @author ziduye
 */
@FeignClient(name = "zlm",contextId = "zlm",configuration = FeignConfiguration.class)
public interface ZLMFeign {

    /**
     *
     * @param uri
     * @param secret
     * @param app
     * @param stream
     * @param vhost   __defaultVhost__
     * @param schema
     */
    @RequestLine("GET /index/api/getMediaList")
    void getMediaList(URI uri,
                         @Param("secret") String secret,
                         @Param("app") String app,
                         @Param("stream") String stream,
                         @Param("vhost") String vhost,
                         @Param("schema") String schema );

    @RequestLine("GET /index/api/getRtpInfo")
    void getRtpInfo(URI uri,
                         @Param("secret") String secret,
                         @Param("stream_id") String stream_id );

    @RequestLine("GET /index/api/addFFmpegSource")
    void addFFmpegSource(URI uri,
                         @Param("secret") String secret,
                         @Param("src_url") String src_url,
                         @Param("dst_url") String dst_url,
                         @Param("timeout_ms") String timeout_ms );

    @RequestLine("GET /index/api/delFFmpegSource")
    void delFFmpegSource(URI uri,
                         @Param("secret") String secret,
                         @Param("key") String src_url );

    @RequestLine("GET /index/api/getServerConfig")
    void getServerConfig(URI uri,
                         @Param("secret") String secret );

    /**
     * TODO 参数
     * @param uri
     * @param secret
     */
    @RequestLine("GET /index/api/setServerConfig")
    void setServerConfig(URI uri,
                         @Param("secret") String secret );

    @RequestLine("GET /index/api/openRtpServer")
    void openRtpServer(URI uri,
                         @Param("secret") String secret );

    @RequestLine("GET /index/api/closeRtpServer")
    void closeRtpServer(URI uri,
                         @Param("secret") String secret );

    @RequestLine("GET /index/api/listRtpServer")
    void listRtpServer(URI uri,
                         @Param("secret") String secret );

    @RequestLine("GET /index/api/startSendRtp")
    void startSendRtp(URI uri,
                         @Param("secret") String secret );

    @RequestLine("GET /index/api/stopSendRtp")
    void stopSendRtp(URI uri,
                         @Param("secret") String secret );

    @RequestLine("GET /index/api/addStreamProxy")
    void addStreamProxy(URI uri,
                        @Param("secret") String secret,
                        @Param("app") String app,
                        @Param("stream") String stream,
                        @Param("url") String url,
                        @Param("enable_hls") String enable_hls,
                        @Param("enable_mp4") String enable_mp4,
                        @Param("rtp_type") String rtp_type,
                        @Param("vhost") String vhost );

    @RequestLine("GET /index/api/closeStreams")
    void closeStreams(URI uri,
                        @Param("secret") String secret,
                        @Param("app") String app,
                        @Param("stream") String stream,
                        @Param("force") String force,
                        @Param("vhost") String vhost );

    @RequestLine("GET /index/api/getAllSession")
    void getAllSession(URI uri,
                        @Param("secret") String secret );

    @RequestLine("GET /index/api/kickSessions")
    void kickSessions(URI uri,
                        @Param("secret") String secret,
                        @Param("local_port") String local_port);

    @RequestLine("GET /index/api/kickSessions")
    void kickSessions(URI uri,
                        @Param("secret") String secret,
                        @Param("url") String url,
                        @Param("timeout_sec") String timeout_sec,
                        @Param("expire_sec") String expire_sec
                      );


}
