package zlm.hook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/index/hook")
public class ZLMHttpHookListener {

	@Autowired
	private HookHandle hookHandle;

	/**
	 * 流量统计事件，播放器或推流器断开时并且耗用流量超过特定阈值时会触发此事件，
	 * 阈值通过配置文件general.flowThreshold配置；
	 * 此事件对回复不敏感。
	 */
	@ResponseBody
	@PostMapping(value = "/on_flow_report", produces = "application/json;charset=UTF-8")
	public ResponseEntity onFlowReport(@RequestBody java.util.Map param){
		Map map = hookHandle.onFlowReport(param);
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
	/**
	 * 访问http文件服务器上hls之外的文件时触发。
	 */
	@ResponseBody
	@PostMapping(value = "/on_http_access", produces = "application/json;charset=UTF-8")
	public ResponseEntity onHttpAccess(@RequestBody java.util.Map param){
		Map map = hookHandle.on_http_access(param);
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
	/**
	 * 播放器鉴权事件，rtsp/rtmp/http-flv/ws-flv/hls的播放都将触发此鉴权事件；
	 * 如果流不存在，那么先触发on_play事件然后触发on_stream_not_found事件。
	 * 播放rtsp流时，如果该流启动了rtsp专属鉴权(on_rtsp_realm)那么将不再触发on_play事件。
	 */
	@ResponseBody
	@PostMapping(value = "/on_play", produces = "application/json;charset=UTF-8")
	public ResponseEntity onPlay(@RequestBody java.util.Map param){
		Map map = hookHandle.on_play(param);
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
	/**
	 * rtsp/rtmp/rtp推流鉴权事件。
	 */
	@ResponseBody
	@PostMapping(value = "/on_publish", produces = "application/json;charset=UTF-8")
	public ResponseEntity onPublish(@RequestBody java.util.Map param){
		Map map = hookHandle.on_publish(param);
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
	/**
	 * 录制mp4完成后通知事件；
	 * 此事件对回复不敏感。
	 */
	@ResponseBody
	@PostMapping(value = "/on_record_mp4", produces = "application/json;charset=UTF-8")
	public ResponseEntity onRecordMp4(@RequestBody java.util.Map param){
		Map map = hookHandle.on_record_mp4(param);
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
	/**
	 * 该rtsp流是否开启rtsp专用方式的鉴权事件，开启后才会触发on_rtsp_auth事件。
	 * 需要指出的是rtsp也支持url参数鉴权，它支持两种方式鉴权。
	 */
	@ResponseBody
	@PostMapping(value = "/on_rtsp_realm", produces = "application/json;charset=UTF-8")
	public ResponseEntity onRtspRealm(@RequestBody java.util.Map param){
		Map map = hookHandle.on_rtsp_realm(param);
		return new ResponseEntity(map,HttpStatus.OK);
	}

	/**
	 * rtsp专用的鉴权事件，先触发on_rtsp_realm事件然后才会触发on_rtsp_auth事件。
	 */
	@ResponseBody
	@PostMapping(value = "/on_rtsp_auth", produces = "application/json;charset=UTF-8")
	public ResponseEntity onRtspAuth(@RequestBody java.util.Map param){
		Map map = hookHandle.on_rtsp_auth(param);
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
	/**
	 * shell登录鉴权，ZLMediaKit提供简单的telnet调试方式
	 * 使用telnet 127.0.0.1 9000能进入MediaServer进程的shell界面。
	 */
	@ResponseBody
	@PostMapping(value = "/on_shell_login", produces = "application/json;charset=UTF-8")
	public ResponseEntity onShellLogin(@RequestBody java.util.Map param){
		Map map = hookHandle.on_shell_login(param);
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
	/**
	 * rtsp/rtmp流注册或注销时触发此事件；
	 * 此事件对回复不敏感。
	 */
	@ResponseBody
	@PostMapping(value = "/on_stream_changed", produces = "application/json;charset=UTF-8")
	public ResponseEntity onStreamChanged(@RequestBody java.util.Map param){
		Map map = hookHandle.on_stream_changed(param);
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
	/**
	 * 流无人观看时事件，用户可以通过此事件选择是否关闭无人看的流。
	 */
	@ResponseBody
	@PostMapping(value = "/on_stream_none_reader", produces = "application/json;charset=UTF-8")
	public ResponseEntity onStreamNoneReader(@RequestBody java.util.Map param){
		Map map = hookHandle.on_stream_none_reader(param);
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
	/**
	 * 流未找到事件，用户可以在此事件触发时，立即去拉流，这样可以实现按需拉流；
	 * 此事件对回复不敏感。
	 */
	@ResponseBody
	@PostMapping(value = "/on_stream_not_found", produces = "application/json;charset=UTF-8")
	public ResponseEntity onStreamNotFound(@RequestBody java.util.Map param){
		Map map = hookHandle.on_stream_not_found(param);
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
	/**
	 * 服务器启动事件，可以用于监听服务器崩溃重启；
	 * 此事件对回复不敏感。
	 */
	@ResponseBody
	@PostMapping(value = "/on_server_started", produces = "application/json;charset=UTF-8")
	public ResponseEntity onServerStarted(@RequestBody java.util.Map param){
		Map map = hookHandle.on_server_started(param);
		return new ResponseEntity(map,HttpStatus.OK);
	}
}
