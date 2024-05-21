import axios from 'axios'
import type { VehicleData } from '@/types'

const axiosInv = axios.create({
  baseURL: 'http://127.0.0.1:4442/beachcomb/'
})

export const fetchVehicleData = (): Promise<VehicleData[]> => {
  return new Promise((resolve, reject) => {
    axiosInv
      .get('/vehicles')
      .then((response) => {
        const data: VehicleData[] = response.data.map((vehicleData: any) => ({
          ...vehicleData,
          timestamp: new Date(vehicleData.timestamp)
        }))
        resolve(data)
      })
      .catch((error) => {
        console.error('Error fetching vehicles:', error)
        reject(new Error('Failed to fetch vehicles'))
      })
  })
}
