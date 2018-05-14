import request from '@/utils/request'

export function listConsumeLog(query) {
  return request({
    url: '/consumeLog/list',
    method: 'get',
    params: query
  })
}

export function createConsumeLog(data) {
  return request({
    url: '/consumeLog/create',
    method: 'post',
    data
  })
}

export function readConsumeLog(data) {
  return request({
    url: '/consumeLog/read',
    method: 'get',
    data
  })
}

export function updateConsumeLog(data) {
  return request({
    url: '/consumeLog/update',
    method: 'post',
    data
  })
}

export function deleteConsumeLog(data) {
  return request({
    url: '/consumeinfo/delete',
    method: 'post',
    data
  })
}

