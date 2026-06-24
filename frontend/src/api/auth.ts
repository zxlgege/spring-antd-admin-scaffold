import request from '@/utils/request'
import type { LoginRequest, LoginResponse, UserInfo } from '@/types'

export const authApi = {
  login: (data: LoginRequest): Promise<LoginResponse> =>
    request.post('/auth/login', data),

  logout: (): Promise<void> =>
    request.post('/auth/logout'),

  getProfile: (): Promise<UserInfo> =>
    request.get('/auth/profile'),
}
