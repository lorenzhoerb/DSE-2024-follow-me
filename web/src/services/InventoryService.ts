import axios from 'axios'

const axiosInv = axios.create({
  baseURL: 'http://127.0.0.1:4444/inventory'
})

export const fetchInventory = (): Promise<[]> => {
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
