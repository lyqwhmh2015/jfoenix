package com.hzaihua.jfoenix.controller.Device;

import com.hzaihua.jfoenix.controller.measure.AddFixedMeasureController;
import com.hzaihua.jfoenix.entity.InfoNoiseDevice;
import com.hzaihua.jfoenix.entity.StateNoise;
import com.hzaihua.jfoenix.service.DeviceManageService;
import com.hzaihua.jfoenix.service.InfoNoiseService;
import com.hzaihua.jfoenix.util.BeanFactoryUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;


@ViewController(value = "/views/fxml/device/editDeviceAfter.fxml")
public class EditFixedDeviceController {

    //前半部分输入内容
    @FXML private TextField NoiseDeviceCode;//设备编号
    @FXML private PasswordField DevicePassword;//设备连接密码
    @FXML private TextField LinkPort;//设备连接端口
    @FXML private TextField MicrophoneHeight;//话筒高度
    @FXML private ComboBox<String> FunCode;//功能区代码
    @FXML private TextField DTUSIM;

    //后半部分复选框内容
    @FXML private JFXCheckBox IsAutoAdjust;//是否自动较时
    @FXML private JFXCheckBox IsReadMin;//是否自动读取分钟统计数据
    @FXML private JFXCheckBox IsReadHour;//是否自动读取小时统计数据
    @FXML private JFXCheckBox IsReadDay;//是否自动读取天统计数据
    @FXML private JFXCheckBox IsReadLp;//是否自动读取瞬时声级数据
    @FXML private JFXCheckBox IsReadLeq1s;//是否自动读取每秒等效声级数据
    @FXML private JFXCheckBox IsReadOct;//是否自动读取频谱分析数据
    @FXML private JFXCheckBox IsReadWea;//是否自动读取气象数据
    @FXML private JFXCheckBox IsReadCar;//是否自动读取交通数据
    @FXML private JFXCheckBox IsReadDust;//是否自动读取空气数据
    @FXML private JFXCheckBox IsReadEvent;//是否自动读取事件数据
    @FXML private JFXCheckBox IsOpenVoice;//是否开启实时语音服务

    @FXML private Text actiontarget; //错误信息提示框
    @FXML private JFXButton editAddFixedDevice; //确认修改按钮

    InfoNoiseService infoNoiseService = BeanFactoryUtil.getApplicationContext().getBean(InfoNoiseService.class);
    DeviceManageService deviceManageService = BeanFactoryUtil.getApplicationContext().getBean(DeviceManageService.class);
    int deviceLinkType = 0;
    String deviceType = null;
    @PostConstruct
    public void init() {
        String deviceCode = AddFixedMeasureController.infoNoiseDevice.getDeviceCode();

        for (InfoNoiseDevice infoNoiseDevice : AddFixedMeasureController.noiseList) {
            if(infoNoiseDevice.getDeviceCode().equals(deviceCode)){
                NoiseDeviceCode.setText(infoNoiseDevice.getDeviceCode());
                DevicePassword.setText(infoNoiseDevice.getDevicePassword());
                LinkPort.setText(infoNoiseDevice.getLinkPort());
                DTUSIM.setText(infoNoiseDevice.getDTUSIM());
                MicrophoneHeight.setText(infoNoiseDevice.getMicrophoneHeight());
                FunCode.setValue(infoNoiseDevice.getFunCode());
                deviceLinkType = infoNoiseDevice.getLinkType();
                deviceType = infoNoiseDevice.getDeviceType();
            }
        }
        System.out.println(deviceLinkType);
        System.out.println(deviceCode+deviceType);


        //确认提交操作
        editAddFixedDevice.setOnAction(event -> {
            Stage stage = (Stage) editAddFixedDevice.getScene().getWindow();
            InfoNoiseDevice device = new InfoNoiseDevice();

            String noiseCode = NoiseDeviceCode.getText();
            String password = DevicePassword.getText();
            String linkPort = LinkPort.getText();
            String microphoneHeight = MicrophoneHeight.getText();
            String dtusim = DTUSIM.getText();

            //判断必须输入项是否为空
            String reg = "^\\d{6}$";
            String rege = "^\\d{4}$";
            String re = "^(-?)[1-9]+\\d*|0";
            String regex = "^[1][\\d]{10}";

            if ("".equals(noiseCode)) {
                actiontarget.setText("设备编号不允许为空");
            } else if ("".equals(password)) {
                actiontarget.setText("设备密码不能为空");
            } else if (!password.matches(reg)) {
                actiontarget.setText("设备密码输入格式不正确,必须纯数字");
            } else if ("".equals(linkPort)) {
                actiontarget.setText("端口号不能为空");
            } else if (!linkPort.matches(rege)) {
                actiontarget.setText("端口号输入格式不正确,必须是纯数字");
            } else if (dtusim.matches(re) && !(dtusim.matches(regex))) {
                actiontarget.setText("SIM卡号输入格式不正确");
            } else {
                device.setDeviceCode(noiseCode);
                device.setDeviceType(deviceType);
                device.setDevicePassword(password);
                device.setLinkType(deviceLinkType);
                device.setLinkPort(linkPort);
                device.setDTUSIM(dtusim);
                device.setFunCode(FunCode.getValue());
                device.setMicrophoneHeight(microphoneHeight);
                device.setIsAutoLink(1);
                if (IsAutoAdjust.isSelected()) {
                    device.setIsAutoAdjust(1);
                } else {
                    device.setIsAutoAdjust(0);
                }
                if (IsReadMin.isSelected()) {
                    device.setIsReadMin(1);
                } else {
                    device.setIsReadMin(0);
                }
                if (IsReadHour.isSelected()) {
                    device.setIsReadHour(1);
                } else {
                    device.setIsReadHour(0);
                }
                if (IsReadDay.isSelected()) {
                    device.setIsReadDay(1);
                } else {
                    device.setIsReadDay(0);
                }
                if (IsReadLp.isSelected()) {
                    device.setIsReadLp(1);
                } else {
                    device.setIsReadLp(0);
                }
                if (IsReadLeq1s.isSelected()) {
                    device.setIsReadLeq1s(1);
                } else {
                    device.setIsReadLeq1s(0);
                }
                if (IsReadOct.isSelected()) {
                    device.setIsReadOct(1);
                } else {
                    device.setIsReadOct(0);
                }
                if (IsReadWea.isSelected()) {
                    device.setIsReadWea(1);
                } else {
                    device.setIsReadWea(0);
                }
                if (IsReadCar.isSelected()) {
                    device.setIsReadCar(1);
                } else {
                    device.setIsReadCar(0);
                }
                if (IsReadDust.isSelected()) {
                    device.setIsReadDust(1);
                } else {
                    device.setIsReadDust(0);
                }
                if (IsReadEvent.isSelected()) {
                    device.setIsReadEvent(1);
                } else {
                    device.setIsReadEvent(0);
                }
                if (IsOpenVoice.isSelected()) {
                    device.setIsOpenVoice(1);
                } else {
                    device.setIsOpenVoice(0);
                }
                device.setStateType(1);
                AddFixedMeasureController.noiseList.add(device);
                StateNoise stateNoise = new StateNoise();
                stateNoise.setDeviceCode(noiseCode);
                stateNoise.setLinkState(0);
                deviceManageService.insertState(stateNoise);
                stage.close();
            }
        });
    }
}
