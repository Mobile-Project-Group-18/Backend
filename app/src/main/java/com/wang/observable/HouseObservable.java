package com.wang.observable;

import java.util.Observable;

import com.wang.model.HouseModel;

/**
 * 步数被观察者
 * 
 * Created by waka on 2016/3/2.
 */
public class HouseObservable extends Observable {

    //单例
    private static HouseObservable instance = null;

    public static HouseObservable getInstance() {

        if (null == instance) {
            instance = new HouseObservable();
        }
        return instance;
    }

    //通知观察者更新数据
    public void notifyStepChange(HouseModel houseModel) {
        //关键方法，必须写，具体实现可以查看源码
        setChanged();//设置changeFlag
        notifyObservers(houseModel);//通知观察者
    }

}