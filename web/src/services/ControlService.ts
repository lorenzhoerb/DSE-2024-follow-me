import axios from 'axios'
import type { VehicleStatus } from '@/types'

const axiosInv = axios.create({
  baseURL: 'http://127.0.0.1:4443/control/'
})

export const fetchVehicleStatus = (): Promise<VehicleStatus[]> => {
  return new Promise((resolve, reject) => {
    axiosInv
      .get('/status')
      .then((response) => {
        resolve(response.data)
      })
      .catch((error) => {
        console.error('Error fetching vehicles:', error)
        reject(new Error('Failed to fetch vehicles'))
      })
  })
}
