import axios, { type AxiosResponse, type InternalAxiosRequestConfig } from 'axios'
import { message } from 'antd'
import { useAuthStore } from '@/store/authStore'

const request = axios.create({
  baseURL: '/api',
  timeout: 15000,
  headers: { 'Content-Type': 'application/json' },
})

// ─── Request interceptor ──────────────────────────────────────────────────────
request.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = useAuthStore.getState().token
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error),
)

// ─── Response interceptor ─────────────────────────────────────────────────────
request.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data
    if (res.code !== 200) {
      message.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    return res.data
  },
  (error) => {
    const status = error.response?.status
    if (status === 401) {
      message.error('登录已过期，请重新登录')
      useAuthStore.getState().logout()
      window.location.href = '/login'
    } else if (status === 403) {
      message.error('没有权限执行该操作')
    } else if (status === 404) {
      message.error('请求的资源不存在')
    } else {
      message.error(error.response?.data?.message || '网络错误，请稍后重试')
    }
    return Promise.reject(error)
  },
)

export default request
