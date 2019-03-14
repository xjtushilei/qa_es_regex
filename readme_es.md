# 安装
es可以从官网下载，但是没有包含中文分词，安装中文分词插件ik的方法 https://github.com/medcl/elasticsearch-analysis-ik

或者使用我已经安装好的版本  https://dev-1252377804.cos.ap-beijing.myqcloud.com/elasticsearch-6.2.4.zip


windows下启动es：先es解压上面下载到的，解压到剩余磁盘空间比较多的地方。es中存的文件都会在es的文件夹里。然后打开es解压后目录的bin目录，双击“elasticsearch.bat”。

打开浏览器，输入 http://localhost:9200/ ，验证其是否开启成功


# 开发

spring和es有官网的联合客户端，本内容使用的就是联合的一个客户端。比较方便。

find语法可参考 https://www.cnblogs.com/chengyangyang/p/10233337.html

