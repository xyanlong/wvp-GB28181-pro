package com.genersoft.iot.vmp.rest;

import com.genersoft.iot.vmp.gb28181.bean.Device;
import com.genersoft.iot.vmp.gb28181.bean.DeviceChannel;
import com.genersoft.iot.vmp.storager.IVideoManagerStorager;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author ziduye
 */
@SuppressWarnings("unchecked")
@CrossOrigin
@RestController
@RequestMapping(value = "/rest/devices")
public class DeviceRest {

    private final static Logger logger = LoggerFactory.getLogger(DeviceRest.class);

    @Autowired
    private IVideoManagerStorager storager;

    /**
     * 使用ID查询国标设备
     * @param deviceId 国标ID
     * @return 国标设备
     */
    @ApiOperation("使用ID查询国标设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备ID", required = true, dataTypeClass = String.class),
    })
    @GetMapping("/{deviceId}")
    public ResponseEntity<Device> devices(@PathVariable String deviceId){

        if (logger.isDebugEnabled()) {
            logger.debug("查询视频设备API调用，deviceId：" + deviceId);
        }

        Device device = storager.queryVideoDevice(deviceId);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    /**
     * 查询设备通道
     *
     * @param deviceId 设备id
     * @param online 是否在线  在线 true / 离线 false
     * @param channelType 设备 false/子目录 true
     * @return 通道列表
     */
    @ApiOperation("查询设备通道")
    @GetMapping("/{deviceId}/channels")
    @ApiImplicitParams({
            @ApiImplicitParam(name="deviceId", value = "设备id", required = true ,dataTypeClass = String.class),
            @ApiImplicitParam(name="online", value = "是否在线"  ,dataTypeClass = Boolean.class),
            @ApiImplicitParam(name="channelType", value = "设备/子目录-> false/true" ,dataTypeClass = Boolean.class),
    })
    public ResponseEntity<List<DeviceChannel>> channels(@PathVariable String deviceId,
                                             @RequestParam(required = false) Boolean online,
                                             @RequestParam(required = false) Boolean channelType) {
        if (logger.isDebugEnabled()) {
            logger.debug("查询视频设备通道API调用");
        }

        List<DeviceChannel> deviceChannels = storager.queryChannelsByDeviceId(deviceId);
        return new ResponseEntity<>(deviceChannels,HttpStatus.OK);
    }

}
