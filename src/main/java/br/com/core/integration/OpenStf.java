package br.com.core.integration;

import br.com.core.properties.PropertiesManager;
import br.com.core.report.ExtentReports;
import com.github.yunusmete.stf.api.STFService;
import com.github.yunusmete.stf.api.ServiceGenerator;
import com.github.yunusmete.stf.model.Device;
import com.github.yunusmete.stf.model.DeviceBody;
import com.github.yunusmete.stf.rest.DeviceResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OpenStf {

    public static String remoteAdb;
    public static String serial;
    public static STFService service;
    public static PropertiesManager setupProperties;

    public OpenStf() {
        setupProperties = new PropertiesManager("Setup.properties");
    }

    public void connectToRemoteDevice(String deviceName)  {
        service = ServiceGenerator.createService(STFService.class,
                setupProperties.getProps().getProperty("urlBaseOpenStf")+"/api/v1",
                setupProperties.getProps().getProperty("acessToken") );
        DeviceResponse devices = service.getDevices();
        List<Device> deviceList = devices.getDevices();
        for (Device device : deviceList) {
            if (device.isPresent()) {
                if (device.getOwner() == null) {
                    if(deviceName.equals(device.getSerial())) {
                        serial = device.getSerial();
                        service.addDeviceToUser(new DeviceBody(serial, 900000));
                        RequestSpecification httpRequest = given();
                        httpRequest.header("Authorization", "Bearer " + setupProperties.getProps().getProperty("acessToken"));
                        httpRequest.header("Content-Type", ContentType.JSON);
                        Response response = httpRequest.post(setupProperties.getProps().getProperty("urlBaseOpenStf") + "/api/v1/user/devices/" + serial + "/remoteConnect");
                        remoteAdb = response.jsonPath().getString("remoteConnectUrl");
                    }
                }
            }
        }
    }

    public void connectToRemoteDevice()  {
        service = ServiceGenerator.createService(STFService.class,
                setupProperties.getProps().getProperty("urlBaseOpenStf")+"/api/v1",
                setupProperties.getProps().getProperty("acessToken") );
        DeviceResponse devices = service.getDevices();
        List<Device> deviceList = devices.getDevices();
        for (Device device : deviceList) {
            if (device.isPresent()) {
                if (device.getOwner() == null) {
                    serial = device.getSerial();
                    service.addDeviceToUser(new DeviceBody(serial, 900000));
                    RequestSpecification httpRequest = given();
                    httpRequest.header("Authorization", "Bearer " + setupProperties.getProps().getProperty("acessToken"));
                    httpRequest.header("Content-Type", ContentType.JSON);
                    Response response = httpRequest.post(setupProperties.getProps().getProperty("urlBaseOpenStf") + "/api/v1/user/devices/" + serial + "/remoteConnect");
                    remoteAdb = response.jsonPath().getString("remoteConnectUrl");
                }
            }
        }
    }

    public void disconnectToRemoteDevice(){
        service.deleteDeviceBySerial(serial);
    }

    public void commandRemoteADB(String adbCommand){
        Runtime rt = Runtime.getRuntime();
        Process pr = null;
        try {
            pr = rt.exec(adbCommand.trim() + " " + remoteAdb);
            pr.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
