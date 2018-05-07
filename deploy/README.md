
1. 项目进一步打包到deploy文件夹中：
   * sotwo-os-api模块编译得到的sotwo-os-api-0.1.0-exec.jar 保存到deploy的sotwo-os-api文件夹中，同时重命名成sotwo-os-api.jar
   * sotwo-wx-api模块编译得到的sotwo-wx-api-0.1.0-exec.jar 保存到deploy的sotwo-wx-api文件夹中，同时重命名成sotwo-wx-api.jar
   * sotwo-admin-api模块编译得到的sotwo-admin-api-0.1.0-exec.jar 保存到deploy的sotwo-admin-api文件夹中，同时重命名成sotwo-admin-api.jar
   * sotwo-admin模块编译以后，把dist文件夹压缩，然后放到deploy的sotwo-admin文件夹中。
   
2. 使用FileZilla把deploy整个文件夹上传到云主机的/home/ubuntu文件夹中

3. 使用PuTTY登录云主机

4. 如果开发者没有部署sotwo数据库，可以运行以下命令：

    ```bash
    cd deploy
    mysql -h localhost -u root -p123456 
    source ./sotwo-db/sotwo_schema.sql 
    source ./sotwo-db/sotwo.sql 
    ```
    注意，123456是开发者所设置的MySQL管理员密码
    警告：
    > sotwo_schema.sql会尝试删除sotwo数据库然后重建一个新的数据。

5. 运行脚本部署运行

    ```bash
    sudo ./deploy/bin/deploy.sh
    ```

6. 测试部署是否成功
  
    请确保云主机的安全组已经允许相应的端口（见1.5.3.1）；
    然后测试是否部署成功(xxx.xxx.xxx.xxx是云主机IP）：

    ```
    http://xxx.xxx.xxx.xxx:8081/os/index/index
    http://xxx.xxx.xxx.xxx:8082/wx/index/index
    http://xxx.xxx.xxx.xxx:8083/admin/index/index
    http://xxx.xxx.xxx.xxx:8080/#/login
    ```

7. 自动上传脚本

    为了简化步骤1和步骤2，完成了util/upload.sh脚本，开发者需要设置相应的云主机IP和密钥文件路径。
    该脚本会自动把当前项目不同模块下的最终部署文件复制到deploy文件夹中，然后上传到云主机。
    注意：
    > 上传脚本没有自动做Spring Boot项目打包和Vue项目打包工作
    
    如果开发者需要先编译项目再上传，可以运行util/lazy.sh。
    注意，运行命令必须在项目主目录中，类似如下命令：
    ```bash
    cd sotwo
    ./deploy/util/lazy.sh
    ```
