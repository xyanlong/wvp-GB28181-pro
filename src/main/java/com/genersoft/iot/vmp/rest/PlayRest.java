package com.genersoft.iot.vmp.rest;

import com.alibaba.fastjson.JSONObject;
import com.genersoft.iot.vmp.common.StreamInfo;
import com.genersoft.iot.vmp.gb28181.transmit.callback.DeferredResultHolder;
import com.genersoft.iot.vmp.gb28181.transmit.callback.RequestMessage;
import com.genersoft.iot.vmp.gb28181.transmit.cmd.impl.SIPCommander;
import com.genersoft.iot.vmp.service.IPlayService;
import com.genersoft.iot.vmp.storager.IRedisCatchStorage;
import com.genersoft.iot.vmp.storager.IVideoManagerStorager;
import com.genersoft.iot.vmp.vmanager.gb28181.play.bean.PlayResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;

/**
 * @author ziduye
 */
@Api(tags = "国标设备点播")
@CrossOrigin
@RestController
@RequestMapping("/rest/play")
public class PlayRest {

	private final static Logger logger = LoggerFactory.getLogger(PlayRest.class);

	@Autowired
	private SIPCommander cmder;

	@Autowired
	private IVideoManagerStorager storager;

	@Autowired
	private IRedisCatchStorage redisCatchStorage;

	@Autowired
	private DeferredResultHolder resultHolder;

	@Autowired
	private IPlayService playService;

	@ApiOperation("开始点播")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "deviceId", value = "设备ID", dataTypeClass = String.class),
			@ApiImplicitParam(name = "channelId", value = "通道ID", dataTypeClass = String.class),
	})
	@GetMapping("/start/{deviceId}/{channelId}")
	public DeferredResult<ResponseEntity<String>> play(@PathVariable String deviceId,
													   @PathVariable String channelId) {
		PlayResult playResult = playService.play(deviceId, channelId, null, null);
		return playResult.getResult();
	}

	@ApiOperation("停止点播")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "deviceId", value = "设备ID", dataTypeClass = String.class),
			@ApiImplicitParam(name = "channelId", value = "通道ID", dataTypeClass = String.class),
	})
	@GetMapping("/stop/{deviceId}/{channelId}")
	public DeferredResult<ResponseEntity<String>> playStop(@PathVariable String deviceId, @PathVariable String channelId) {

		logger.debug(String.format("设备预览/回放停止API调用，streamId：%s/$s", deviceId, channelId ));

		UUID uuid = UUID.randomUUID();
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<ResponseEntity<String>>();

		// 录像查询以channelId作为deviceId查询
		resultHolder.put(DeferredResultHolder.CALLBACK_CMD_STOP + uuid, result);

		cmder.streamByeCmd(deviceId, channelId, event -> {
			StreamInfo streamInfo = redisCatchStorage.queryPlayByDevice(deviceId, channelId);
			if (streamInfo == null) {
				RequestMessage msg = new RequestMessage();
				msg.setId(DeferredResultHolder.CALLBACK_CMD_STOP + uuid);
				msg.setData("点播未找到");
				resultHolder.invokeResult(msg);
				storager.stopPlay(deviceId, channelId);
			}else {
				redisCatchStorage.stopPlay(streamInfo);
				storager.stopPlay(streamInfo.getDeviceID(), streamInfo.getChannelId());
				RequestMessage msg = new RequestMessage();
				msg.setId(DeferredResultHolder.CALLBACK_CMD_STOP + uuid);
				//Response response = event.getResponse();
				msg.setData(String.format("success"));
				resultHolder.invokeResult(msg);
			}
		});

		if (deviceId != null || channelId != null) {
			JSONObject json = new JSONObject();
			json.put("deviceId", deviceId);
			json.put("channelId", channelId);
			RequestMessage msg = new RequestMessage();
			msg.setId(DeferredResultHolder.CALLBACK_CMD_PlAY + uuid);
			msg.setData(json.toString());
			resultHolder.invokeResult(msg);
		} else {
			logger.warn("设备预览/回放停止API调用失败！");
			RequestMessage msg = new RequestMessage();
			msg.setId(DeferredResultHolder.CALLBACK_CMD_PlAY + uuid);
			msg.setData("streamId null");
			resultHolder.invokeResult(msg);
		}

		// 超时处理
		result.onTimeout(()->{
			logger.warn(String.format("设备预览/回放停止超时，deviceId/channelId：%s/$s ", deviceId, channelId));
			RequestMessage msg = new RequestMessage();
			msg.setId(DeferredResultHolder.CALLBACK_CMD_STOP + uuid);
			msg.setData("Timeout");
			resultHolder.invokeResult(msg);
		});
		return result;
	}


}

