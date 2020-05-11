package com.company;
import java.io.Serializable;
//Child elements are desktop and laptop
//Parent object should set both child classes to Serializable for file i/o
public class Computer implements Serializable {


    String processorBrand;
    String processorSpeed;
    String videoOutput; //VGA or HDMI
    String hardDriveSize;   //Standard sizes are 2.5 and 3.5inches
    String RAMSize;     //how many GB / MB (gigabytes)
    String displayMonitorSize;

    //huge constructor
    public Computer(
            String processorBrand, String processorSpeed, String videoOutput,
            String hardDriveSize,
            String RAMSize, String displayMonitorSize
    ){
        this.processorBrand = processorBrand;
        this.processorSpeed = processorSpeed;
        this.videoOutput = videoOutput;
        this.hardDriveSize = hardDriveSize;
        this.RAMSize = RAMSize;
        this.displayMonitorSize = displayMonitorSize;
    }


}
