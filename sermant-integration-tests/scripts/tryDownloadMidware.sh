#
# Copyright (C) 2022-2022 Huawei Technologies Co., Ltd. All rights reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
#

#!/bin/bash

#======================================CSE配置======================================
CSE_ADDRESS=https://cse-bucket.obs.cn-north-1.myhuaweicloud.com/LocalCSE/Local-CSE-2.1.3-linux-amd64.zip
CSE_FILE_NAME=Local-CSE-2.1.3-linux-amd64.zip

#======================================ZK配置======================================
ZK_ADDRESS=https://archive.apache.org/dist/zookeeper/zookeeper-3.6.3/apache-zookeeper-3.6.3-bin.tar.gz
ZK_FILE_NAME=apache-zookeeper-3.6.3-bin.tar.gz

#======================================Nacos server配置======================================
NACOS_ADDRESS=https://github.com/alibaba/nacos/releases/download/1.4.2/nacos-server-1.4.2.tar.gz
NACOS_FILE_NAME=nacos-server-1.4.2.tar.gz

NACOS_210_ADDRESS=https://github.com/alibaba/nacos/releases/download/2.1.0/nacos-server-2.1.0.tar.gz
NACOS_210_FILE_NAME=nacos-server-2.1.0.tar.gz

#======================================Service Center配置======================================
SERVICE_CENTER_ADDRESS=https://github.com/apache/servicecomb-service-center/releases/download/v2.1.0/apache-servicecomb-service-center-2.1.0-linux-amd64.tar.gz
SERVICE_CENTER_FILE_NAME=apache-servicecomb-service-center-2.1.0-linux-amd64.tar.gz

#重试次数
TRY_TIMES=3

#实际下载后的文件名
FILE_NAME=$CSE_FILE_NAME

#实际下载地址
ADDRESS=$CSE_ADDRESS

download(){
  echo "root path: ${ROOT_PATH}"
  for ((i=1; i<=${TRY_TIMES};i++))
  do
    echo "try download cse at $i time"
    wget ${ADDRESS} -O ${ROOT_PATH}/${FILE_NAME}
    if [ $? == 0 ];then
      break
    fi
    sleep 3
  done
  ls -l
  if [ -f ${ROOT_PATH}/${FILE_NAME} ];then
    echo "download ${FILE_NAME} success"
  else
    exit 1
  fi
}

# 中间件类型, 当前支持zk, cse
midleware=$1
if [ $midleware == "zk" ];then
  ADDRESS=$ZK_ADDRESS
  FILE_NAME=$ZK_FILE_NAME
elif [ $midleware == "nacos" ]; then
  ADDRESS=$NACOS_ADDRESS
  FILE_NAME=$NACOS_FILE_NAME
elif [ $midleware == "nacos210" ]; then
  ADDRESS=$NACOS_210_ADDRESS
  FILE_NAME=$NACOS_210_FILE_NAME
elif [ $midleware == "sc" ]; then
  ADDRESS=$SERVICE_CENTER_ADDRESS
  FILE_NAME=$SERVICE_CENTER_FILE_NAME
else
  ADDRESS=$CSE_ADDRESS
  FILE_NAME=$CSE_FILE_NAME
fi

# 判断文件是否存在
num=`ls -l ${ROOT_PATH} | grep ${FILE_NAME} | wc -l`;
if [ $num -eq 0 ];then
  download
else
  echo ${FILE_NAME} " exist when download " ${midleware}
fi



