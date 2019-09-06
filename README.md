elasticsearch:http://127.0.0.1:9200/

kibana界面:http://127.0.0.1:5601/

启动说明：
  1.先启动Elasticsearch，在安装目录的bin文件夹中启动elasticsearch.bat（以管理员身份运行）即可。这里需要注意一点就是，如果项目中需要用到分词器的话
     要先将分词器的安装包解压后放在Elasticsearch目录的plugins目录下，然后重启Elasticsearch即可。
  2.再启动kibana，在安装目录的bin文件夹中启动kibana.bat（以管理员身份运行）即可。
