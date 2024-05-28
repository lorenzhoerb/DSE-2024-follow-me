import type { BasicVehicleInfo } from '@/types'
import axios from 'axios'

const axiosInv = axios.create({
  baseURL: import.meta.env.VITE_URL_API_INVENTORY
})

export const fetchInventory = (): Promise<BasicVehicleInfo[]> => {
  return new Promise((resolve, reject) => {
    axiosInv
      .get('/vehicles')
      .then((response) => {
        resolve(response.data)
      })
      .catch((error) => {
        console.error('Error fetching vehicles:', error)
        reject(new Error('Failed to fetch vehicles'))
      })
  })
}
