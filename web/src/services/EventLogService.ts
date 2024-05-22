import type { LogInfo } from '@/types'
import axios from 'axios'

const axiosInv = axios.create({
  baseURL: import.meta.env.VITE_URL_API_EVENT
})

export const fetchEvents = (): Promise<LogInfo[]> => {
  return new Promise((resolve, reject) => {
    axiosInv
      .get('/events')
      .then((response) => {
        const events: LogInfo[] = response.data.map((event: any) => ({
          ...event,
          timestamp: new Date(event.timestamp)
        }))
        resolve(events)
      })
      .catch((error) => {
        console.error('Error fetching vehicles:', error)
        reject(new Error('Failed to fetch events'))
      })
  })
}
