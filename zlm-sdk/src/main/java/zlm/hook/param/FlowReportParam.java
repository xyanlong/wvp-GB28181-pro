package zlm.hook.param;

import lombok.Data;

/**
 * FlowReportParam
 *
 * @author ziduye(a)gmail.com FlowReportParam
 * @version 2021/5/21 9:59
 */
@Data
public class FlowReportParam {
    /**
     * 流应用名
     */
    private String app;

    /**
     * tcp链接维持时间，单位秒
     */
    private int duration;

    /**
     * 推流或播放url参数
     */
    private String params;

    /**
     * true为播放器，false为推流器
     */
    private boolean player;

    /**
     * 播放或推流的协议，可能是rtsp、rtmp、http
     */
    private String schema;

    /**
     * 流ID
     */
    private String stream;

    /**
     * 耗费上下行流量总和，单位字节
     */
    private int totalBytes;

    /**
     * 流虚拟主机
     */
    private String vhost;

    /**
     * 客户端ip
     */
    private String ip;

    /**
     * 客户端端口号
     */
    private int port;

    /**
     * TCP链接唯一ID
     */
    private String id;

    /**
     * 服务器id,通过配置文件设置
     */
    private String mediaServerId;
}
