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
