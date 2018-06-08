#!/bin/bash

# 请注意
# 1. 本脚本的作用是停止当前Spring Boot应用，然后再次部署
# 2. 解压dist.zip到/home/sotwo/dist，

#部署sotwo-admin静态文件应用
cd /home/sotwo/sotwo-admin
rm -rf dist
mkdir dist
unzip /home/sotwo/dist.zip /home/sotwo/sotwo-admin/
cd .

#部署三个Spring Boot应用
#如果服务已经启动，则尝试停止
/etc/init.d/sotwo-os-api stop
/etc/init.d/sotwo-wx-api stop
/etc/init.d/sotwo-admin-api stop

#部署Spring Boot应用成服务
ln -f -s /home/sotwo/sotwo-os-api.jar /etc/init.d/sotwo-os-api
ln -f -s /home/sotwo/sotwo-wx-api.jar /etc/init.d/sotwo-wx-api
ln -f -s /home/sotwo/sotwo-admin-api.jar /etc/init.d/sotwo-admin-api

#启动服务
/etc/init.d/sotwo-os-api restart
/etc/init.d/sotwo-wx-api restart
/etc/init.d/sotwo-admin-api restart

# tomcat8服务也启动
service tomcat stop
service tomcat start