import request from '@/utils/request'
import type { DashboardStats } from '@/types'

export const dashboardApi = {
  getStats: (): Promise<DashboardStats> =>
    request.get('/dashboard/stats'),
}
