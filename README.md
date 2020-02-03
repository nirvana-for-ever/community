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

requestbody中使用大小写会出现无法映射的问题
https://blog.csdn.net/qq_26075861/article/details/54016591

postman:https://getman.cn/  
如何给谷歌使用postman：https://blog.csdn.net/dearwind153/article/details/52235749

发送ajax用requestbody接参数时，要求发送的参数必须为json字符串，
同时需要加上contentType:"application/json",

JSON字符串 与 JS 对象的关系

var obj = {a: 'Hello', b: 'World'}; 
//这是一个对象，注意键名也是可以使用引号包裹的

var json = '{"a": "Hello", "b": "World"}'; 
//这是一个 JSON 字符串，本质是一个字符串

JSON 和 JS 对象互转
要实现从JSON字符串转换为JS对象，使用 JSON.parse() 方法：
var obj = JSON.parse('{"a": "Hello", "b": "World"}'); 
//结果是 {a: 'Hello', b: 'World'}

要实现从JS对象转换为JSON字符串，使用 JSON.stringify() 方法：
var json = JSON.stringify({a: 'Hello', b: 'World'}); 
//结果是 '{"a": "Hello", "b": "World"}'

当我们需要在用户端（前端）储存一个值，并且这个值在多个页面中都可以获取到，
比如用户在评论前是处在登录状态，但是用户在另一个页面中退出登录了，
这时候就不能进行评论，需要打开一个登录页进行登录，登录完毕后自动关掉登录页
如何实现自动关闭登录页？设计登录页的时候，登录成功之后会跳转到首页，
所以打开登录页前需要设置一对key-value，在跳转回首页加载时读取这个
key-value，如果能读取得到，就说明需要关闭这个页面。
可以用以下两种方式设置：

1.sessionStorage：将数据保存在session对象中。所谓session，是指用户在浏览某个网站时，从进入网站到浏览器关闭所经过的这段时间，也就是用户浏览这个网站所花费的时间。session对象可以用来保存在这段时间内所要求保存的任何数据。

2.localStorage：将数据保存在客户端本地的硬件设备(通常指硬盘，也可以是其他硬件设备)中，即使浏览器被关闭了，该数据仍然存在，下次打开浏览器访问网站时仍然可以继续使用。

设置方式：window.localStorage.setItem()

需要看看mybatis逆向工程中的各种ByExample的用法

使用fastjson时出现$ref: "$.[0]"的解决办法：
https://www.jianshu.com/p/224df499fa0b

js中拼接标签的时候，onclick事件拼接字符串的时候需要传递字符串，比如  
$("#id").append("<span onClick='reply(\""+ string  +"\","+id+")'</span>")  
其中string为字符串变量，需要按如上的方式进行，详见：
https://www.cnblogs.com/springlight/p/5782637.html