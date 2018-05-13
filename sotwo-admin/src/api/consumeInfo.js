import request from '@/utils/request'

export function listConsumeInfo(query) {
  return request({
    url: '/consumeInfo/list',
    method: 'get',
    params: query
  })
}

export function createConsumeInfo(data) {
  return request({
    url: '/consumeInfo/create',
    method: 'post',
    data
  })
}

export function readConsumeInfo(data) {
  return request({
    url: '/consumeInfo/read',
    method: 'get',
    data
  })
}

export function updateConsumeInfo(data) {
  return request({
    url: '/consumeInfo/update',
    method: 'post',
    data
  })
}

export function deleteConsumeInfo(data) {
  return request({
    url: '/consumeinfo/delete',
    method: 'post',
    data
  })
}

