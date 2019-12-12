##项目当中遇到的一些零碎的知识整理 

---
idea牛逼快捷键：  
当你需要对许多行的相同位置加上同一段文字时，按住alt键，鼠标左键下拉需要添加的行
就可以添加同一段文字了，如果需要跳到行末统一添加就在原先的基础上，按住ctrl，
按右方向键跳到尾部统一添加文字

ctrl+shift+f12:窗口最大化

shift+enter：不论光标在哪，都直接切换到下一行

alt+insert：提示添加setter，getter

ctrl+e：切换到不同的文件

ctrl+y：删除一整行

ctrl+x：剪切一整行

ctrl+w：逐级选中

alt+左右方向：切换已经打开的文件

ctrl+r：修改同样的内容

ctrl+alt+o：删除无用的包

解决intellij idea卡顿的方法:进入电脑中intellij idea的安装目录中去，找到其中的bin文件夹，
在bin文件夹中有两个文件，分别叫做:idea.exe.vmoptions与idea64.exe.vmoptions
修改成

-Xms1024m  
-Xmx4096m  
-XX:ReservedCodeCacheSize=1024m  
-XX:+UseConcMarkSweepGC  
-XX:+UseParNewGC  
-XX:+DisableExplicitGC  
-XX:SoftRefLRUPolicyMSPerMB=50  
-Xverify:none  
-ea  
-Dsun.io.useCanonCaches=false  
-Djava.net.preferIPv4Stack=true

---
markdown有关知识：

markdown超链接  
[spring主页](https://spring.io)

---
数据传输对象（DTO)(Data Transfer Object),一个好的编程习惯：当传输的变量超过
2个时，用对象封装起来

导入jquery时记得刷新一下maven，不然不起效，不要盲目检查路径

js中的字符串需要append的时候，没有append方法，用+=实现append的效果

js中的map用法在发布问题中有体现，概括一下：  
增：set，删：delete，查：get

标签有多个class用空格连接

thymeleaf引用后端传来的变量的时候有红色下划线报错，实际上并没有错，在设置中关掉就行（expression variables）

需要了解自动部署的方法可以看视频25集

ajax不支持重定向，在后端无法return一个页面，因此需要在ajax的success函数内重定向window.location="指定页面地址"

jquery引用需要放在bootstrap前面，不然还是会报错

myBatis-plus异常提示For input string: "{0=null}"  
解决方法：在<when test=""></when>标签中接收参数的时候test中去掉#{}直接写上参数的名称就可以了<if test=""></if>也是一样！！！！

写拦截器的时候需要注意：拦截器本身不能实现自动注入，是因为拦截器是通过new的方式创建的拦截器，
通过new出来的实例是没有交给spring进行管理的，没有被spring管理的实例，
spring是无法自动注入bean的，所以为null  
解决办法：通过@bean注解注入拦截器到spring当中,同时给拦截器加上@compent注解，
参照WebConfig

引入js文件的时候，如果a.js要用到b.js中的方法的话，b.js的引用需要放在a.js的前面