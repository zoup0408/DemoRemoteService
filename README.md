# 无Demo无真相系列之-RemoteService
## 注意点1：Client通过如下方法创建Intent对象  
Intent intent = new Intent();  
intent.setPackage("com.zoup.android.demo.demo_server");  
intent.setAction("com.zoup.android.demo.demo_server.WeatherService");   
  
其中“com.zoup.android.demo.demo_server”是Server的包名
## 注意点2：Client通过AIDL方式访问Server时，aidl文件的包名必须和Server端aidl文件的包名一致。
