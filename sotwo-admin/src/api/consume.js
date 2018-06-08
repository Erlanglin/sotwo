import request from '@/utils/request'




export function addConsumeLog(data) {
  return request({
    url: '/consume/addConsumeLog',
    method: 'post',
    data
  })
}


export function updateStatus(data) {
  return request({
    url: '/consume/updateStatus',
    method: 'get',
    data
  })
}



export function logList(query) {
  return request({
    url: '/consume/logList',
    method: 'get',
    params: query
  })
}

export function infoList(query) {
  return request({
    url: '/consume/infoList',
    method: 'get',
    params: query
  })
}


