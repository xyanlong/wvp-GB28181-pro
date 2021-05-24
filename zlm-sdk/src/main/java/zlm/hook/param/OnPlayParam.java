package zlm.hook.param;

import lombok.Data;

/**
 * FlowReportParam
 *
 * @author ziduye(a)gmail.com FlowReportParam
 * @version 2021/5/21 9:59
 */
@Data
public class OnPlayParam {
    /**
     * 流应用名
     */
    private String app;

    /**
     * TCP链接唯一ID
     */
    private String id;

    /**
     * 客户端ip
     */
    private String ip;

    /**
     * 客户端端口号
     */
    private int port;

    /**
     * 推流或播放url参数
     */
    private String params;

    /**
     * 播放或推流的协议，可能是rtsp、rtmp、http
     */
    private String schema;

    /**
     * 流ID
     */
    private String stream;

    /**
     * 流虚拟主机
     */
    private String vhost;

    /**
     * 服务器id,通过配置文件设置
     */
    private String mediaServerId;
}
