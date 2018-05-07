#!/bin/bash

# 请注意
# 1. 本脚本的作用是把本项目编译的结果保存到deploy文件夹中，然后上传到云主机
# 2. 运行本脚本前，请确认Spring Boot模块已经编译，同时sotwo-admin模块也已经便宜
# 3. util/upload.sh脚本是运行在开发机中，bin/deploy.sh脚本是运行在云主机中
# 4. 这是一个简单的脚本，开发者可以按照自己需求修改

# 请设置云主机的IP地址
CVM=XXX.XXX.XXX.XXX
# 请设置本地SSH私钥文件id_rsa
ID_RSA=/XXX/id_rsa

# 复制三个Spring Boot应用
# 需要注意的是target目录里面存在两种jar，一种是当前模块纯编译代码的jar，另外一种是包含依赖库的可执行jar，
# 这里我们需要的是可执行jar
cp  -f ./sotwo-os-api/target/sotwo-os-api-*-exec.jar ./deploy/sotwo-os-api/sotwo-os-api.jar
cp  -f ./sotwo-wx-api/target/sotwo-wx-api-*-exec.jar ./deploy/sotwo-wx-api/sotwo-wx-api.jar
cp  -f ./sotwo-admin-api/target/sotwo-admin-api-*-exec.jar ./deploy/sotwo-admin-api/sotwo-admin-api.jar

# 压缩sotwo-admin应用
tar -zcvf ./deploy/sotwo-admin/dist.tar -C ./sotwo-admin/dist .

# 复制数据库
cp  -f ./sotwo-db/sql/sotwo_schema.sql ./deploy/sotwo-db/sotwo_schema.sql
cp  -f ./sotwo-db/sql/sotwo.sql ./deploy/sotwo-db/sotwo.sql

# 上传云主机
scp -i $ID_RSA -r  ./deploy ubuntu@$CVM:/home/ubuntu/
