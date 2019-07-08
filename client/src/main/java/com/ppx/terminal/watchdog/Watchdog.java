package com.ppx.terminal.watchdog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ppx.terminal.common.log.HeartbeatLogger;
import com.ppx.terminal.netty.client.DiscardClient;



/**
 一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。按顺序依次为：
 秒（0~59）
分钟（0~59）
3 小时（0~23）
4 天（0~31）
5 月（0~11）
6 星期（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
年份（1970－2099）
其中每个元素可以是一个值(如6),一个连续区间(9-12),一个间隔时间(8-18/4)(/表示每隔4小时),一个列表(1,3,5),通配符
“？”字符仅被用于天（月）和天（星期）两个子表达式，表示不指定值 

每隔5秒执行一次：0/5 * * * ?
每隔1分钟执行一次：0 /1 * * ?
“0 0 12 * * ?” 每天中午12点触发
“0 15 10 * * ? *” 每天上午10:15触发
“0 15 10 ? * *” 每天上午10:15触发
 * @author mark
 * @date 2019年6月25日
 */
@Component
public class Watchdog {
    
    @Autowired
    private DiscardClient discardClient;
    
    // 
    private static long lastHeartNanoTime = System.nanoTime();
    
    private static long lastConnectNanoTime = System.nanoTime();
    
    
    public static void setLastHeartNanoTime(long lastHeartNanoTime) {
        Watchdog.lastHeartNanoTime = lastHeartNanoTime;
    }
    
    public static void setLastConnectNanoTime(long lastConnectNanoTime) {
        Watchdog.lastConnectNanoTime = lastConnectNanoTime;
    }
    
    private int watchCount = 1;
    
    @Scheduled(cron = "${netty.watch.interval.cron}")
    public void watchHeart() {
        HeartbeatLogger.info("watchHeart W|" + watchCount);
        
        // 没有心跳超过1分钟, 并且连接上次连接也超1分钟，重建连接(防止有连接没有而不能通信的情况)
        long connectTime = (System.nanoTime() - lastConnectNanoTime) / (1000000 * 1000);
        long heartTime = (System.nanoTime() - lastHeartNanoTime) / (1000000 * 1000);
        
        if (connectTime > 60 && heartTime > 60) {
            HeartbeatLogger.info("watchDog reconnect|" + connectTime + "|" + heartTime);
            try {
                discardClient.run();
            } catch (Exception e) {
                HeartbeatLogger.info("error watchDog reconnect:" + e.getMessage());
                e.printStackTrace();
            }
        }
        watchCount++;
    }
    
   
}
