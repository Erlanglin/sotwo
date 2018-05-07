#!/bin/bash

# 请注意
# 1. 本脚本的作用是停止当前Spring Boot应用，然后再次部署
# 2. 解压dist.tar到/home/ubuntu/deploy/sotwo-admin/dist，
#    而这个目录也正是tomcat配置静态文件目录的路径（见1.5.3.5节）


#部署sotwo-admin静态文件应用
cd /home/ubuntu/deploy/sotwo-admin
rm -rf dist
mkdir dist
tar -zxvf dist.tar -C dist
cd .

#部署三个Spring Boot应用
#如果服务已经启动，则尝试停止
sudo /etc/init.d/sotwo-os-api stop
sudo /etc/init.d/sotwo-wx-api stop
sudo /etc/init.d/sotwo-admin-api stop

#部署Spring Boot应用成服务
sudo ln -f -s /home/ubuntu/deploy/sotwo-os-api/sotwo-os-api.jar /etc/init.d/sotwo-os-api
sudo ln -f -s /home/ubuntu/deploy/sotwo-wx-api/sotwo-wx-api.jar /etc/init.d/sotwo-wx-api
sudo ln -f -s /home/ubuntu/deploy/sotwo-admin-api/sotwo-admin-api.jar /etc/init.d/sotwo-admin-api

#启动服务
sudo /etc/init.d/sotwo-os-api restart
sudo /etc/init.d/sotwo-wx-api restart
sudo /etc/init.d/sotwo-admin-api restart

# tomcat8服务也启动
sudo service tomcat8 stop
sudo service tomcat8 start