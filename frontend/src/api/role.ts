import request from '@/utils/request'
import type { Role } from '@/types'

export const roleApi = {
  list: (): Promise<Role[]> =>
    request.get('/roles'),

  getById: (id: number): Promise<Role> =>
    request.get(`/roles/${id}`),

  create: (data: Partial<Role>): Promise<Role> =>
    request.post('/roles', data),

  update: (id: number, data: Partial<Role>): Promise<Role> =>
    request.put(`/roles/${id}`, data),

  delete: (id: number): Promise<void> =>
    request.delete(`/roles/${id}`),
}
