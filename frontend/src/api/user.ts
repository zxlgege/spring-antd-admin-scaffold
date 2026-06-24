import request from '@/utils/request'
import type {
  PageResult,
  UserInfo,
  UserCreateRequest,
  UserUpdateRequest,
  UserQuery,
} from '@/types'

export const userApi = {
  list: (params: UserQuery): Promise<PageResult<UserInfo>> =>
    request.get('/users', { params }),

  getById: (id: number): Promise<UserInfo> =>
    request.get(`/users/${id}`),

  create: (data: UserCreateRequest): Promise<UserInfo> =>
    request.post('/users', data),

  update: (id: number, data: UserUpdateRequest): Promise<UserInfo> =>
    request.put(`/users/${id}`, data),

  delete: (id: number): Promise<void> =>
    request.delete(`/users/${id}`),

  updateStatus: (id: number, status: number): Promise<void> =>
    request.patch(`/users/${id}/status`, { status }),
}
