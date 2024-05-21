import { fetchVehicleData } from './BeachcombService'
import { fetchVehicleStatus } from './ControlService'
import { fetchInventory } from './InventoryService'
import type { VehicleInfo } from '@/types'

export const fetchAll = async (): Promise<VehicleInfo[]> => {
  const [vehicleData, vehicleStatus, inventory] = await Promise.all([
    fetchVehicleData(),
    fetchVehicleStatus(),
    fetchInventory()
  ])

  const vehicleInfoMap = new Map<string, VehicleInfo>()

  vehicleData.forEach((data) => {
    vehicleInfoMap.set(data.vin, {
      vin: data.vin,
      data,
      ...vehicleInfoMap.get(data.vin)
    })
  })

  vehicleStatus.forEach((status) => {
    vehicleInfoMap.set(status.vin, {
      vin: status.vin,
      status,
      ...vehicleInfoMap.get(status.vin)
    })
  })

  inventory.forEach((vehicle) => {
    vehicleInfoMap.set(vehicle.vin, {
      vin: vehicle.vin,
      info: vehicle,
      ...vehicleInfoMap.get(vehicle.vin)
    })
  })

  return Array.from(vehicleInfoMap.values())
}
